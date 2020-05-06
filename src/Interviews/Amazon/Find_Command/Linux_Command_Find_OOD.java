package Interviews.Amazon.Find_Command;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * From leetcode discussion
 * https://leetcode.com/discuss/interview-question/369272/Amazon-or-Onsite-or-Linux-Find-Command
 *
 * Implemnet linux find command as an api ,the api will support finding files that has given size
 * requirements and a file with a certain format like
 *
 * find all file >5mb
 * find all xml
 *
 * Assume file class
 * {
 *  get name()
 *  directorylistfile()
 *  getFile()
 * }
 * create a library flexible that is flexible
 * Design clases,interfaces.
 *
 * The focus of the question is not implement details (such as how to traverse etc), but on OOD principles.
 *
 * !!!
 * Key Points:
 * As for what I would expect (not necessarily all of these):
 *
 * 1.Obviously coming straight to the right design (encapsulating the Filtering logic into its own interface etc...),
 *   with an explanation on why this approach is good. I'm obviously open to alternate approaches as long as they
 *   are as flexible and elegant.
 *   Use interface or abstract class (Param, Filter, Option)
 *
 * 2.Implement boolean logic: AND/OR/NOT, here I want to see if the candidate understands object composition
 *   My design use parser to deal with operands. "Object composition"??
 *
 * 3.Support for symlinks. Rather than seeing the implementation (which I don't really care about) I want to
 *   understand the trade-offs of adding yet another parameter to the find method VS other options (eg. Constructor).
 *   Keep adding params to a method is usually bad.
 *   Add new Filter and Option class
 *
 * 4.How to handle the case where the result is really big (millions of files), and you may not be able to put all
 *   of them into a List.(!!!)
 *   Pagination like "more" option?
 *   Spill to disk or other temp device?
 */


/**
 * Overall Design
 *
 * 1.Filters can be combined with logic operands OR/AND/NOT, it can be parsed by a LL parser.
 *
 * 2.Options don't have operand, they apply to action, such as "followSymbolickLink", "maxDepth" etc.
 *
 * 3.Parsers : Each filter and option should have its own parser to parse from input params (String[] input).
 *
 *   All parser implements interface Parser.
 *
 * 4.An ExcutionContext contains all info for running command:
 *  parsers (hashmap)
 *  filters (list)
 *  options (list)
 *  path
 *  followSymbolicLink (boolean)
 *
 * 5.Executor runs the command, its constructor takes filters, options,
 *
 * 6.ExecutionBuilder takes input, get correct parser, setup filters, options.
 *
 * Exeecuter.execute run the command
 */

enum ParamType {
    OPTION, FILTER;
}

// Option/Predicate/Action的基类
abstract class Param {
    public abstract ParamType getType();
}

interface Parser {
//abstract class Parser {
    // 该解析器所处理的参数名，例如"maxdepth"，"type"，"size"
    public abstract String getName();

    // 解析逻辑的实现
    public abstract Param parse(Stack<String> args);
}

/**
 * All filter will implement Filter interface
 */
abstract class Filter extends Param {
    public abstract boolean evaluate(ExecutionContext context);

    public ParamType getType() {
        return ParamType.FILTER;
    }
}

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

class FileNameFilterParser implements Parser {
    @Override
    public String getName() {
        return "name";
    }

    @Override
    public Filter parse(Stack<String> args) {
        return new FileNameFilter(FileSystems.getDefault().getPathMatcher("glob:" + args.pop()));
    }
}

class FileTypeFilterParser implements Parser {
    @Override
    public String getName() {
        return "type";
    }

    @Override
    public Filter parse(Stack<String> args) {
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

class FileSizeFilterParser implements Parser {
    @Override
    public String getName() {
        return "size";
    }

    @Override
    public Filter parse(Stack<String> args) {
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

abstract class Option extends Param {
    public ParamType getType() {
        return ParamType.OPTION;
    }

    // 每个Option需要实现setup()方法以配置ExecutionContext
    public void setup(ExecutionContext context) {};
}

class FollowSymbolicLinkOption extends Option {
    @Override
    public void setup(ExecutionContext context) {
        context.setFollowSymbolicLink();
    }
}

class FollowSymbolicLinkOptionParser implements Parser {
    @Override
    public String getName() {
        return "L";
    }

    @Override
    public Param parse(Stack<String> args) {
        return new FollowSymbolicLinkOption();
    }
}

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
    private static Map<String, Parser> optionParserRegistry = new HashMap<>();

    private static void Register(Parser parser) {
        optionParserRegistry.put(parser.getName(), parser);
    }

    // 在这里注册所有的OptionParser子类
    static {
        Register(new FollowSymbolicLinkOptionParser());
        Register(new FileTypeFilterParser());
        Register(new FileNameFilterParser());
        Register(new FileSizeFilterParser());
    }

    private Stack<String> tokens;

    // 给定输入参数，生成执行器
    // 这里包含了一个简易的LL(1) recursive descent parser
    public Executor generateExecutor(String[] args) {
        tokens = new Stack<String>();

        /**
         * !!!
         * Start from end of the input String array, push each one into stack
         */
        for (int i = args.length - 1; i >= 0; --i) {
            tokens.push(args[i]);
        }

        if (tokens.empty()) {
            throw new RuntimeException("Requires at least one path argument");
        }
        final Path filePath = Paths.get(tokens.pop());

        ArrayList<Option> options = new ArrayList<>();
        ArrayList<Filter> filters = new ArrayList<>();
//        ArrayList<Action> actions = new ArrayList<>();

        while (!tokens.empty()) {
//            Param p = parse();


//            Param p = parseOr();
//            switch (p.getType()) {
//                case OPTION:
//                    options.add((Option) node);
//                    break;
//                case PREDICATE:
//                    filters.add((Predicate) node);
//                    break;
//                case ACTION:
//                    actions.add((Action) node);
//                    break;
//                default:
//                    throw new RuntimeException("Unsupport enum value " + node.getKind().name());
//            }
        }

        Filter filterPredicate = null;
        if (filters.size() == 1) {
            filterPredicate = filters.get(0);
        } else if (filters.size() > 1) {
//            filterPredicate = new LogicalAnd(filters);
        }

        tokens = null;
        return new Executor(filePath, options, filterPredicate);
    }

//    private Param parse() {
//        final Parser parser = optionParserRegistry.get(name);
//
//        if (parser == null) {
//            throw new RuntimeException("Unrecognized option " + name);
//        }
//        // Parser各自的parse()方法用于解析参数的arguments
//        // 例如 -size +1M，那么"size"所对应的parser应当知道如何解析"+1M"
//        return parser.parse(tokens);
//    }
}

class Executor {
    private Path startPath;
    private ArrayList<Option> options;
    private Filter filters;
//        private ArrayList<Action> actions;
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

            /**
             * !!!
             * The place filter takes effect.
             *
             * 谓词（predicate，类似SQL中where语句）求值如果值为false的话表示当前文件不满足用户设定的过滤条件，那么跳过当前文件
             **/
            if (filters != null && !filters.evaluate(context)) {
                return FileVisitResult.CONTINUE;
            }
            // 满足过滤条件，首先打印当前路径
            System.out.println(fileOrDir.toString());
            // 然后执行所有actions
//                for (Action action : actions) {
//                    action.invoke(context);
//                }
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

    /**
     * 构造函数
     * Pass in all params (filters, options) in constructor.
     * " Keep adding params to a method is usually bad"
     *
     * For this design, we can add params : filters, options (like maxDepth).
     * We need to
     * 1.add new filter/option class, extends base Filter/Option class.
     * 2.parsers that extends base Parser class
     * 3.register new parsers in ExecutionGenerator.
     **/
    public Executor(Path startPath, ArrayList<Option> options,
                    Filter filterPredicate) {
        this.startPath = startPath;
        this.options = options;
        this.filters = filterPredicate;
//            this.actions = actions;
    }

    public void Execute() throws IOException {
        context = new ExecutionContext();
        // 首先用options初始化context（例如maxdepth参数）
        for (Option option : options) {
            option.setup(context);
        }
        // 然后初始化actions（例如打开输出文件）
//            for (Action action : actions) {
//                action.initialize();
//            }

        // Walk file tree，利用NodeVistor处理回调
        Files.walkFileTree(startPath, EnumSet.of(FileVisitOption.FOLLOW_LINKS),
                context.getMaxDepth(), new NodeVistor());
        // actions的完结处理（例如flush并关闭输出文件）
//            for (Action action : actions) {
//                action.finalize();
//            }
    }
}



