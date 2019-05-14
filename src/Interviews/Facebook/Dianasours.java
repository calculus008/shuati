package Interviews.Facebook;

import java.util.*;
import java.io.*;

public class Dianasours {
    /**
     * Dataset1
     * 恐龙名字， 恐龙腿长， 恐龙食性（食草，食肉，两者皆吃，这个是无关feature)
     *
     * Dataset2
     * 恐龙名字， 恐龙某个feature(暂名d1), Stance(两只脚，四只脚等）
     *
     * 要求，选出stance是两只脚走路的恐龙，计算出他们的速度，并根据速度排序，然后从快到慢输出名字。.
     *
     * 速度是根据腿长以及d1来算出来的。
     *
     * 看上述要求，很明显先扫dataset2, 写一个class，然后放到map里。 最后处理第一个dataset， 算出speed,放到heap中。输出。
     */

    class Dino {
        String name;
        //String stance;
        double speed;
        double stride;
        double leg;

        Dino(String in_name, double in_stride) {
            name = in_name;
            stride = in_stride;
        }
    }

    public class dinosaur {
        public void main(String[] arg) {
            Map<String, Dino> map = new HashMap<>();
            // put file in the "project folder"
            List<String> data1 = parseFile(new File("dataset1.csv"));
            List<String> data2 = parseFile(new File("dataset2.csv"));

            PriorityQueue<Dino> Q = new PriorityQueue<>(new Comparator<Dino>() {
                public int compare(Dino a, Dino b) {
                    return (a.speed - b.speed) < 0 ? 1 : -1;
                }
            });

            for (String s2 : data2) {
                String[] dino = s2.split(",");
                if (dino[2].equals("bipedal")) {
                    Dino d = new Dino(dino[0], Double.parseDouble(dino[1]));
                    map.put(dino[0], d);
                }
            }

            for (String s1 : data1) {
                String[] dino = s1.split(",");
                if (map.containsKey(dino[0])) {
                    double leg = Double.parseDouble(dino[1]);
                    Dino d = map.get(dino[0]);
                    d.speed = (d.stride / leg - 1) * Math.sqrt(leg * 9.8);
                    Q.offer(d);
                }
            }

            while (!Q.isEmpty()) {
                Dino d = Q.poll();
                System.out.println(d.name + "  " + d.speed);
            }
        }

        public List<String> parseFile(File f) {
            List<String> list = new LinkedList<>();

            try {
                Scanner input = new Scanner(f);
                while (input.hasNextLine()) {
                    list.add(input.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return list;
        }
    }
}
