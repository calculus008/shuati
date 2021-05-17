package Interviews.Amazon;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Stack;
import java.util.Map;

    /**
     * https://www.1point3acres.com/bbs/thread-431401-1-1.html
     *
     * Linux的find命令其实还是相当复杂的一个命令，参数种类很多，官方文档在这里：
     * https://www.gnu.org/software/fin ... _mono/find.html#Top
     *
     * 文档里在序章的1.3节介绍了find命令的四大类参数：
     * options：affect overall operation rather than the processing of a specific file;
     * tests：return a true or false value, depending on the file's attributes;
     * actions：have side effects and return a true or false value; and
     * operators：connect the other arguments and affect when and whether they are evaluated.
     *
     * 这个分类方法稍微有点土，毕竟find是很早以前设计的工具了。现在来看的话find命令本质上是一个针对文件系统
     * 的查询引擎（Query Engine）。Options相当于全局的configuration，tests（各种过滤器）和 operations
     * （“与或非”逻辑联结词）可以合称为谓词（predicate，类似于SQL中的where语句），而actions则是一些side
     * effect操作
     *
     * 总体设计上：
     * (1) 首先要设计对所存储数据的access path，也就你怎么访问/遍历这些数据 —— 对应文件系统就是简单粗暴的
     * walk file tree
     * (2) 然后需要将各种参数转化为一定的中间表示（IR，intermediate representation），比如说上面提到的option
     * ，predicate和action都可以是一个抽象类，然后针对每个参数继承其中一个并实现相应功能
     * (3) 得到了中间表示，还可以设计优化器对其进行优化。比如说Linux find是可以启动优化功能的（-O1或者-O2或者-O3），
     * 会reorder过滤条件的执行顺序。举个简单例子，考虑过滤条件“找到文本中包含Hello world并且后缀为.cpp的文件”，
     * 那么我们肯定希望先执行后缀名.cpp的这个过滤，然后在满足后缀的文件中再查找Hello wolrd关键词
     * (4) 最后需要生成一个执行器，根据优化后的中间表示（一般称为执行计划Execution Plan），walk file tree并且执
     * 行相应的filter和action。
     *
     * 关于OOP的部分，首先问一个最根本的问题：我们为什么需要OOP，或者说OOP可以做到什么？
     *
     * 其实OOP好处还是挺多的.. 但最重要的用处之一就是系统的可扩展性（extensibility）。比如说现在系统支持了若干种过滤器，
     * 那么当我们想要添加一个新的过滤器的时候，是否需要修改很多处已有的代码？还是只需要在某个地方独立地实现一下该过滤器的逻辑，
     * 然后将其注册到过滤器列表（registry）中？ —— 我们肯定希望的是后者，并可以通过OOP的继承与动态绑定机制实现。
     *
     * 还有一点题外话.. OOP不是表达抽象、实现多态的唯一/最好的机制，具体要看应用场景。函数式以及C++模板元编程（静多态）
     * 都有其非常适用的场景。
     *
     * #
     * or/and/not的优先级（结合性）是依次升高的，就和加减乘除一样，一个表达式不一定是从左往右计算。比如说 A -or B -and C 的运算顺序是 A -or ( B -and C )，而不是 (A -or B) -and C
     *
     * 现在我们要写一个parser来正确地解析A -or B -and C这样的表达式，那么这个parser其实有若干种写法：
     * --
     * (1) 可以用解LeetCode上计算器题目的那种stack的方法，其本质上是一个下推自动机（但是扩展性不好不适合处理复杂情况）
     * (2) 可以写一个LR parser，主要是构造并使用一个shift-reduce跳转表
     * (3) 可以写一个LL parser，也叫“递归下降解析器”，主要是写一堆递归方法层层调用..
     * --
     *
     * 然后上面代码用的方法是(3)。至于为什么是parseOr()里面调用parseAnd()，然后parseAnd()里面调用parseNot().. 这和LL文法的结构有关，可以参考一下这篇知乎文章：
     * https://zhuanlan.zhihu.com/p/31271879
     *
     * 里面提到了一个算数表达式的例子（也就是计算器了）可以仔细看一下，+/* 本质上和 or/and 是一样的处理
     *
     * #Reference
     * FileSearch
     * https://mkyong.com/java/search-directories-recursively-for-file-in-java/
     *
     * 我觉得不用想的太复杂，有点像 588的变种，搜索就dfs/bfs就行
     */

// 需要通过命令行提供参数，可以先打包成jar文件然后通过命令行执行
// 例如：
// java -jar find.jar . -name data.txt
// java -jar find.jar . -name \*.txt
// java -jar find.jar /Some/Path -maxdepth 3 -name \*.h -or -name \*.cpp
// java -jar find.jar /Some/Path \( -name \*.h -or -name \*.cpp \) -and -size +100K

//    public void class Main {
//        public static void main(String[] args) throws IOException {
//            Executor exec = new ExecutionGenerator().generateExecutor(args);
//            exec.Execute();
//        }
//    }

        ////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////// 执行器 ////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
class Executor {
    private Path startPath;
    private ArrayList<Option> options;
    private Predicate filterPredicate;
    private ArrayList<Action> actions;
    private ExecutionContext context;

    // 这里利用了Java本身提供的Files.walkFileTree()方法以及FileVisitor回调接口
    // 参考这里的介绍：[url]https://docs.oracle.com/javase/tutorial/essential/io/walk.html[/url]
    private class NodeVistor implements FileVisitor<Path> {
        private FileVisitResult visitFileOrDirectory(Path fileOrDir, BasicFileAttributes attr) {
            // 检查是不是Symbolic link
            if (attr.isSymbolicLink() && !context.shouldFollowSymbolicLink()) {
                return FileVisitResult.SKIP_SUBTREE;
            }
            context.setFilePath(fileOrDir);
            context.setBasicFileAttributes(attr);
            // 谓词（predicate，类似SQL中where语句）求值
            // 如果值为false的话表示当前文件不满足用户设定的过滤条件，那么跳过当前文件
            if (filterPredicate != null && !filterPredicate.evaluate(context)) {
                return FileVisitResult.CONTINUE;
            }
            // 满足过滤条件，首先打印当前路径
            System.out.println(fileOrDir.toString());
            // 然后执行所有actions
            for (Action action : actions) {
                action.invoke(context);
            }
            return FileVisitResult.CONTINUE;
        }

        // 访问每一个文件时的回调
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            return visitFileOrDirectory(file, attrs);
        }

        // 访问每一个目录之前的回调
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return visitFileOrDirectory(dir, attrs);
        }

        // 访问每一个目录之后的回调
        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        // 遇到错误时的回调
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    // 构造函数
    public Executor(Path startPath, ArrayList<Option> options,
                    Predicate filterPredicate, ArrayList<Action> actions) {
        this.startPath = startPath;
        this.options = options;
        this.filterPredicate = filterPredicate;
        this.actions = actions;
    }

    public void Execute() throws IOException {
        context = new ExecutionContext();
        // 首先用options初始化context（例如maxdepth参数）
        for (Option option : options) {
            option.setup(context);
        }
        // 然后初始化actions（例如打开输出文件）
        for (Action action : actions) {
            action.initialize();
        }
        // Walk file tree，利用NodeVistor处理回调
        Files.walkFileTree(startPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
                context.getMaxDepth(), new NodeVistor());
        // actions的完结处理（例如flush并关闭输出文件）
        for (Action action : actions) {
            action.finalize();
        }
    }
}

        ////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////// 执行上下文 //////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
class ExecutionContext {
    // Options
    private int maxDepth = Integer.MAX_VALUE;
    private boolean followSymbolicLink = false;

    // Runtime attributes
    private Path filePath;
    private BasicFileAttributes fileAttr;

    // Getters and setters
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setFollowSymbolicLink() {
        followSymbolicLink = true;
    }

    public boolean shouldFollowSymbolicLink() {
        return followSymbolicLink;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setBasicFileAttributes(BasicFileAttributes fileAttr) {
        this.fileAttr = fileAttr;
    }

    public BasicFileAttributes getBasicFileAttributes() {
        return fileAttr;
    }
}

        ////////////////////////////////////////////////////////////////////////////////
/////////////////////////////// 解析参数并生成执行器 //////////////////////////////
////////////////////////////////////////////////////////////////////////////////
class ExecutionGenerator {
    private static Map<String, OptionParser> optionParserRegistry = new HashMap<>();

    private static void Register(OptionParser parser) {
        optionParserRegistry.put(parser.getName(), parser);
    }

    // 在这里注册所有的OptionParser子类
    static {
        Register(new MaxDepthOptionParser());
        Register(new FollowSymbolicLinkOptionParser());
        Register(new FileTypeFilterParser());
        Register(new FileNameFilterParser());
        Register(new FileSizeFilterParser());
        Register(new WriteToFileActionParser());
    }

    private Stack<String> tokens;

    // 给定输入参数，生成执行器
    // 这里包含了一个简易的LL(1) recursive descent parser
    public Executor generateExecutor(String[] args) {
        tokens = new Stack<String>();
        for (int i = args.length - 1; i >= 0; --i) {
            tokens.push(args[i]);
        }

        if (tokens.empty()) {
            throw new RuntimeException("Requires at least one path argument");
        }
        final Path filePath = Paths.get(tokens.pop());

        ArrayList<Option> options = new ArrayList<>();
        ArrayList<Predicate> predicates = new ArrayList<>();
        ArrayList<Action> actions = new ArrayList<>();

        while (!tokens.empty()) {
            PlanNode node = parseOr();
            switch (node.getKind()) {
                case OPTION:
                    options.add((Option) node);
                    break;
                case PREDICATE:
                    predicates.add((Predicate) node);
                    break;
                case ACTION:
                    actions.add((Action) node);
                    break;
                default:
                    throw new RuntimeException("Unsupport enum value " + node.getKind().name());
            }
        }

        Predicate filterPredicate = null;
        if (predicates.size() == 1) {
            filterPredicate = predicates.get(0);
        } else if (predicates.size() > 1) {
            filterPredicate = new LogicalAnd(predicates);
        }

        tokens = null;
        return new Executor(filePath, options, filterPredicate, actions);
    }

    // 解析 "... -or ..." 这样的输入
    private PlanNode parseOr() {
        ArrayList<PlanNode> operands = new ArrayList<>();
        operands.add(parseAnd());
        while (nextTokenIs("-or") || nextTokenIs("-o")) {
            tokens.pop();
            operands.add(parseAnd());
        }
        if (operands.size() == 1) {
            return operands.get(0);
        }
        ArrayList<Predicate> predicates = new ArrayList<>();
        for (PlanNode node : operands) {
            if (node.getKind() != PlanNodeKind.PREDICATE) {
                throw new RuntimeException("Logical OR can only be applied to predicates");
            }
            predicates.add((Predicate) node);
        }
        return new LogicalOr(predicates);
    }

    // 解析 "... -and ..." 这样的输入
    private PlanNode parseAnd() {
        ArrayList<PlanNode> operands = new ArrayList<>();
        operands.add(parseNot());
        while (nextTokenIs("-and") || nextTokenIs("-a")) {
            tokens.pop();
            operands.add(parseNot());
        }
        if (operands.size() == 1) {
            return operands.get(0);
        }
        ArrayList<Predicate> predicates = new ArrayList<>();
        for (PlanNode node : operands) {
            if (node.getKind() != PlanNodeKind.PREDICATE) {
                throw new RuntimeException("Logical AND can only be applied to predicates");
            }
            predicates.add((Predicate) node);
        }
        return new LogicalAnd(predicates);
    }

    // 解析 "-not ..." 这样的输入
    private PlanNode parseNot() {
        boolean negate = false;
        if (nextTokenIs("-not") || nextTokenIs("-n")) {
            tokens.pop();
            negate = true;
        }
        PlanNode operand = parseAtom();
        if (!negate) {
            return operand;
        }
        if (operand.getKind() != PlanNodeKind.PREDICATE) {
            throw new RuntimeException("Logical NOT can only be applied to a predicate");
        }
        return new LogicalNot((Predicate) operand);
    }

    // 解析括号表达式 "( ... )" 或者是基础的 Option / Filter / Action
    private PlanNode parseAtom() {
        if (nextTokenIs("(")) {
            tokens.pop();
            PlanNode node = parseOr();
            if (!nextTokenIs(")")) {
                throw new RuntimeException("Unmatched parenthesis");
            }
            tokens.pop();
            return node;
        }
        if (tokens.isEmpty()) {
            throw new RuntimeException("Unexpected end of input stream");
        }
        if (!tokens.peek().startsWith("-")) {
            throw new RuntimeException("Unexpected token " + tokens.peek());
        }
        // 这个name就是参数名，例如-type的"type"，-size的"size"
        final String name = tokens.pop().substring(1);
        // 在registry中找到与参数名对应的parser
        final OptionParser parser = optionParserRegistry.get(name);
        if (parser == null) {
            throw new RuntimeException("Unrecognized option " + name);
        }
        // Parser各自的parse()方法用于解析参数的arguments
        // 例如 -size +1M，那么"size"所对应的parser应当知道如何解析"+1M"
        return parser.parse(tokens);
    }

    private boolean nextTokenIs(String value) {
        return !tokens.isEmpty() && value.equals(tokens.peek());
    }
}

////////////////////////////////////////////////////////////////////////////////
////////////////////// Option/ Predicate / Action 的抽象 ////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 这边是OOP设计比较重要的部分

enum PlanNodeKind {
    OPTION, PREDICATE, ACTION
}

// Option/Predicate/Action的基类
abstract class PlanNode {
    public abstract PlanNodeKind getKind();
}

// 继承这个类以添加一个新的Option
abstract class Option extends PlanNode {
    // 每个Option需要实现setup()方法以配置ExecutionContext
    public abstract void setup(ExecutionContext context);

    @Override
    public PlanNodeKind getKind() {
        return PlanNodeKind.OPTION;
    }
}

// 继承这个类以添加一个新的Predicate
abstract class Predicate extends PlanNode {
    // 每个Predicate需要实现evaluate()方法以过滤当前访问的文件或目录
    public abstract boolean evaluate(ExecutionContext context);

    @Override
    public PlanNodeKind getKind() {
        return PlanNodeKind.PREDICATE;
    }
}

// 继承这个类以添加一个新的Action
abstract class Action extends PlanNode {
    // 每个Action需要实现invoke()方法以针对当前访问的文件或目录作出对应操作
    public abstract void invoke(ExecutionContext context);

    public void initialize() {
    }

    public void finalize() {
    }

    @Override
    public PlanNodeKind getKind() {
        return PlanNodeKind.ACTION;
    }
}

// Filter也就是GNU find官方文档上所说的"tests"
// 例如：
//   -name a.txt
//   -size +1MB
//   -type f
// 这些都是Filter
// 继承这个类以添加一个新的Filter
abstract class Filter extends Predicate {
}

// Option/Filter/Action的解析器基类
abstract class OptionParser {
    // 该解析器所处理的参数名，例如"maxdepth"，"type"，"size"
    public abstract String getName();

    // 解析逻辑的实现
    public abstract PlanNode parse(Stack<String> args);
}

////////////////////////////////////////////////////////////////////////////////
///////////////////// 逻辑联结词 And/Or/Not (与/或/非) 的实现 //////////////////////
////////////////////////////////////////////////////////////////////////////////

// 逻辑“与”
class LogicalAnd extends Predicate {
    private ArrayList<Predicate> operands;

    public LogicalAnd(ArrayList<Predicate> operands) {
        this.operands = operands;
    }

    // “与”操作返回true当且仅当所有子谓词都返回true
    @Override
    public boolean evaluate(ExecutionContext context) {
        for (final Predicate operand : operands) {
            if (!operand.evaluate(context)) {
                return false;
            }
        }
        return true;
    }
}

// 逻辑“或”
class LogicalOr extends Predicate {
    private ArrayList<Predicate> operands;

    public LogicalOr(ArrayList<Predicate> operands) {
        this.operands = operands;
    }

    // “或”操作返回true当且仅当任意一个子谓词返回true
    @Override
    public boolean evaluate(ExecutionContext context) {
        for (final Predicate operand : operands) {
            if (operand.evaluate(context)) {
                return true;
            }
        }
        return false;
    }
}

// 逻辑“非”
class LogicalNot extends Predicate {
    private Predicate operand;

    public LogicalNot(Predicate operand) {
        this.operand = operand;
    }

    // “非”操作返回true当且仅当子谓词返回false
    @Override
    public boolean evaluate(ExecutionContext context) {
        return !operand.evaluate(context);
    }
}

////////////////////////////////////////////////////////////////////////////////
//////////////////////// Option/ Filter / Action 的子类 /////////////////////////
////////////////////////////////////////////////////////////////////////////////

// 添加一个新的Filter / Action大部分情况下只需要继承并实现相应的基类以及OptionParser基类
// 添加一个新的Option可能还需要修改ExecutionContext并在Executor中做对应修改

        // 查找时的最大深度，例如：
// -maxdepth 10
class MaxDepthOption extends Option {
    private int maxDepth;

    public MaxDepthOption(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    @Override
    public void setup(ExecutionContext context) {
        context.setMaxDepth(maxDepth);
    }
}

class MaxDepthOptionParser extends OptionParser {
    @Override
    public String getName() {
        return "maxdepth";
    }

    @Override
    public PlanNode parse(Stack<String> args) {
        return new MaxDepthOption(Integer.parseInt(args.pop()));
    }
}

// 是否处理symbolic link：
// -L
class FollowSymbolicLinkOption extends Option {
    @Override
    public void setup(ExecutionContext context) {
        context.setFollowSymbolicLink();
    }
}

class FollowSymbolicLinkOptionParser extends OptionParser {
    @Override
    public String getName() {
        return "L";
    }

    @Override
    public PlanNode parse(Stack<String> args) {
        return new FollowSymbolicLinkOption();
    }
}

// 设定查找的是文件还是目录，例如：
// -type f
// -type d
class FileTypeFilter extends Filter {
    public enum FileType {
        DIRECTORY, FILE
    }

    private FileType targetFileType;

    public FileTypeFilter(FileType targetFileType) {
        this.targetFileType = targetFileType;
    }

    @Override
    public boolean evaluate(ExecutionContext context) {
        switch (targetFileType) {
            case FILE:
                return context.getBasicFileAttributes().isRegularFile();
            case DIRECTORY:
                return context.getBasicFileAttributes().isDirectory();
        }
        throw new RuntimeException("Unsupported enum value: " + targetFileType.name());
    }
}

class FileTypeFilterParser extends OptionParser {
    @Override
    public String getName() {
        return "type";
    }

    @Override
    public PlanNode parse(Stack<String> args) {
        final String param = args.pop();
        switch (param) {
            case "f":
                return new FileTypeFilter(FileTypeFilter.FileType.FILE);
            case "d":
                return new FileTypeFilter(FileTypeFilter.FileType.DIRECTORY);
        }
        throw new RuntimeException("Unsupport file type: " + param);
    }
}

        // 设定文件名glob pattern，例如：
// -name a.txt
// -name \*.txt
// -name \*data\*.txt
// 注意命令行下不能直接写-name *.txt，否则glob会把*.txt展开为当前目录下的所有match的文件
class FileNameFilter extends Filter {
    private PathMatcher pathMatcher;

    public FileNameFilter(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    @Override
    public boolean evaluate(ExecutionContext context) {
        final Path filePath = context.getFilePath();
        return pathMatcher.matches(filePath) ||
                pathMatcher.matches(filePath.getFileName());
    }
}

class FileNameFilterParser extends OptionParser {
    @Override
    public String getName() {
        return "name";
    }

    @Override
    public PlanNode parse(Stack<String> args) {
        return new FileNameFilter(FileSystems.getDefault().getPathMatcher("glob:" + args.pop()));
    }
}

// 过滤文件size，例如：
// -size 1MB
// -size +1KB
// -size -1GB
class FileSizeFilter extends Filter {
    public enum OpType {
        EQUAL, GREATER_EQUAL, LESS_EQUAL
    }

    private long targetFileSize;
    private OpType op;

    public FileSizeFilter(OpType op, long targetFileSize) {
        this.op = op;
        this.targetFileSize = targetFileSize;
    }

    @Override
    public boolean evaluate(ExecutionContext context) {
        long fileSize = context.getBasicFileAttributes().size();
        switch (op) {
            case EQUAL:
                return fileSize == targetFileSize;
            case GREATER_EQUAL:
                return fileSize >= targetFileSize;
            case LESS_EQUAL:
                return fileSize <= targetFileSize;
        }
        throw new RuntimeException("Unsupported enum value: " + op.name());
    }
}

class FileSizeFilterParser extends OptionParser {
    @Override
    public String getName() {
        return "size";
    }

    @Override
    public PlanNode parse(Stack<String> args) {
        String param = args.pop();
        FileSizeFilter.OpType op = FileSizeFilter.OpType.EQUAL;
        int digitStart = 0;
        if (param.startsWith("+")) {
            op = FileSizeFilter.OpType.GREATER_EQUAL;
            digitStart = 1;
        } else if (param.startsWith("-")) {
            op = FileSizeFilter.OpType.LESS_EQUAL;
            digitStart = 1;
        }
        int digitEnd = digitStart;
        while (digitEnd < param.length() && Character.isDigit(param.charAt(digitEnd))) {
            ++digitEnd;
        }
        if (digitEnd == digitStart) {
            throw new RuntimeException("Invalid file size specification: " + param);
        }
        long targetFileSize = Long.parseLong(param.substring(digitStart, digitEnd));
        if (digitEnd != param.length()) {
            final String unit = param.substring(digitEnd);
            switch (unit.toLowerCase()) {
                case "k":
                case "kb":
                    targetFileSize *= 1024;
                    break;
                case "m":
                case "mb":
                    targetFileSize *= 1024 * 1024;
                    break;
                case "g":
                case "gb":
                    targetFileSize *= 1024 * 1024 * 1024;
                    break;
                default:
                    throw new RuntimeException("Invalid file size unit: " + unit);
            }
        }
        return new FileSizeFilter(op, targetFileSize);
    }
}

        // 将结果写到某个文件中，例如：
// -writetofile output.txt
class WriteToFileAction extends Action {
    private String fileName;
    private PrintWriter pw;

    public WriteToFileAction(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void initialize() {
        try {
            pw = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finalize() {
        pw.close();
    }

    @Override
    public void invoke(ExecutionContext context) {
        pw.println(context.getFilePath());
    }
}

class WriteToFileActionParser extends OptionParser {
    @Override
    public String getName() {
        return "writetofile";
    }

    @Override
    public PlanNode parse(Stack<String> args) {
        return new WriteToFileAction(args.pop());
    }
}


