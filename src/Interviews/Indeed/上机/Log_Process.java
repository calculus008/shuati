package src.Interviews.Indeed.上机;

import java.util.*;

public class Log_Process {
    /**
     * <A> 上机，处理LOG的题目，注意看清楚题目别乱写看清楚题目之后再写他的DOCU讲的很模糊
     * <B> 处理log，每条log中包含timestamp、key、操作类型（click/search）等信息，然后定义了session，
     *     定义session为key相同的并且距离session开始时间小于15min的entry。 让你输出所有session的信息。
     *     test cases分为small,medium,large几种，我的large的内存爆了…
     * <C> 下午上机1个半小时写个函数处理log信息，相关输入输出接口都已写好，直接用就行，用到的唯一的数据结构就是HashMap，
     *     有test case供测试，分为tiny，small，medium，large四个test case，其实我觉得函数大概30分钟左右一般人就能写完了，
     *     也不会有什么错，我就是这种情况，可就是跑large case的时候heap memory会爆掉，然后优化了一个小时也还是没跑过去。。。
     *     不过事后看起来这个应该不重要，起码不影响offer
     * <D> 上机题是用它提供的一些基本工具处理一个log file，
     *     这个file每一行都是(user_id, timestamp,action_type）。
     *     要求根据userid和timestamp划分session，划分规则是一个session里面相邻的两个操作间隔不超过10分钟。
     *     随后输出整理好的sessionlog和统计数据。我把统计数据搞出来了，但是没来的及输出。
     *
     *  输入是按时间戳顺序的 log，其实就是一行一行的 string，其中包含时间 millisecond 的，
     *  种类，和操作，然后要求是把相同种类的 log 挑出来统计操作数量，然后如果时间戳之间超过 15min 就打印一
     *  个空行。总体来说挺简单的，到时候仔细看题目具体要求，别看错了。
     */

    class Session {
        int startTime;
        List<String> actions;

        public Session(int startTime) {
            this.startTime = startTime;
            actions = new ArrayList<>();
        }
    }

    Map<Integer, Session> map = new HashMap<>();

    public void process(String s) {
        if (s == null || s.length() == 0) return;

        String[] parts = s.split(" ");

        int id = Integer.parseInt(parts[0]);
        int time = Integer.parseInt(parts[1]);
        String action = parts[2];

        if (!map.containsKey(id)) {
            map.put(id, new Session(time));
            map.get(id).actions.add(action);
        } else {
            Session e = map.get(id);
            if (time - e.startTime < 15) {
                e.actions.add(action);
            } else {
                System.out.println(Arrays.toString((e.actions.toArray())));
                Session newSession = new Session(time);
                map.put(id, newSession);
                map.get(id).actions.add(action);
            }
        }
    }

    public static void main(String[] args) {
        Log_Process test = new Log_Process();

        Scanner sc = new Scanner(System.in);
        int number = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < number; i++) {
            test.process(sc.nextLine());
        }
    }
}
