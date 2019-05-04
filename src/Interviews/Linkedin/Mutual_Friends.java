package Interviews.Linkedin;

public class Mutual_Friends {
    /**
     * MapReduce
     *
     *
     * 共同朋友
     *
     * 1. A B C D E F
     *
     * 2. B A C D E
     *
     * 3. C A B E
     *
     * 4. D B E
     *
     * 5. E A BC D
     *
     * 6. F A
     *
     * 第一个字母表示本人，其他是他的朋友，找出有共同朋友的人，和共同的朋友是谁
     *
     * 解答：
     *
     * 思路：例如A，他的朋友是B\C\D\E\F\，那么BC的共同朋友就是A。所以将BC作为key，将A作为value，在map端输出即可！其他的朋友循环处理。
     *
     *
     *
     *
     * public class FindFriend {
     *
     *
     *
     *           public static class ChangeMapper extends Mapper<Object, Text, Text,
     *
     * Text>{
     *
     *                     @Override
     *
     *                     public void map(Object key, Text value, Context context) throws
     *
     * IOException, InterruptedException {
     *
     *                               StringTokenizer itr = new StringTokenizer(value.toString());
     *
     *                                   Text owner = new Text();
     *
     *                                   Set<String> set = new TreeSet<String>();
     *
     *                               owner.set(itr.nextToken());
     *
     *                               while (itr.hasMoreTokens()) {
     *
     *                                       set.add(itr.nextToken());
     *
     *                               }
     *
     *                              String[] friends = new String[set.size()];
     *
     *                              friends = set.toArray(friends);
     *
     *
     *
     *                              for(int i = 0; i< friends.length; i++){
     *
     *                                       for(int j=i+1;j<friends.length;j++){
     *
     *                                               String outputkey = friends[i]+friends[j];
     *
     *                                               context.write(new Text(outputkey),owner);
     *
     *                                       }
     *
     *                               }
     *
     *                     }
     *
     *            }
     *
     *
     *
     *            public static class FindReducer extends Reducer<Text,Text,Text,Text>
     *
     * {
     *
     *                          public void reduce(Text key, Iterable<Text> values,
     *
     *                                          Context context) throws IOException,
     *
     * InterruptedException {
     *
     *                                    String  commonfriends ="";
     *
     *                                for (Text val : values) {
     *
     *                                   if(commonfriends == ""){
     *
     *                                           commonfriends = val.toString();
     *
     *                                    }else{
     *
     *                                           commonfriends =
     *
     * commonfriends+":"+val.toString();
     *
     *                                    }
     *
     *                                 }
     *
     *                               context.write(key, new
     *
     * Text(commonfriends));
     *
     *                          }
     *
     *            }
     *
     *
     *
     *
     *
     *         public static void main(String[] args) throws IOException,
     *
     *          InterruptedException, ClassNotFoundException {
     *
     *
     *
     *             Configuration conf = new Configuration();
     *
     *              String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
     *
     *              if (otherArgs.length < 2) {
     *
     *                System.err.println("args error");
     *
     *                System.exit(2);
     *
     *             }
     *
     *              Job job = new Job(conf, "word count");
     *
     *              job.setJarByClass(FindFriend.class);
     *
     *              job.setMapperClass(ChangeMapper.class);
     *
     *              job.setCombinerClass(FindReducer.class);
     *
     *              job.setReducerClass(FindReducer.class);
     *
     *              job.setOutputKeyClass(Text.class);
     *
     *              job.setOutputValueClass(Text.class);
     *
     *              for (int i = 0; i < otherArgs.length - 1; ++i) {
     *
     *                FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
     *
     *              }
     *
     *              FileOutputFormat.setOutputPath(job,
     *
     *                new Path(otherArgs[otherArgs.length - 1]));
     *
     *              System.exit(job.waitForCompletion(true) ? 0 : 1);
     *
     *
     *
     *         }
     *
     *
     *
     * }
     *
     *
     *
     * 结果：
     *
     * 1. AB      E:C:D
     *
     * 2. AC      E:B
     *
     * 3. AD      B:E
     *
     * 4. AE      C:B:D
     *
     * 5. BC      A:E
     *
     * 6. BD      A:E
     *
     * 7. BE      C:D:A
     *
     * 8. BF      A
     *
     * 9. CD      E:A:B
     *
     * 10. CE      A:B
     *
     * 11. CF      A
     *
     * 12. DE      B:A
     *
     * 13. DF      A
     *
     * 14. EF      A
     */
}
