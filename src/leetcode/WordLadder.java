package leetcode;

import java.util.*;

public class WordLadder {
    Map<String, List<String>> map;
    List<List<String>> results;

    public static int ladderLength12(String beginWord, String endWord, List<String> wordList) {
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);
        int res = 0;

        while(!queue.isEmpty()) {
            int size = queue.size();

            for(int i=0; i<size; i++) {
                String cur = queue.remove();
                //!!! check and return here
                if(cur.equals(endWord)) return res+1;

                for(int j=0; j<cur.length(); j++) {
                    // StringBuilder sb = new StringBuilder(cur);
                    char[] ch = cur.toCharArray();
                    for(char k='a'; k<='z'; k++) {
                        char old=cur.charAt(j);
                        if(old!=k) {
                            // sb.setCharAt(j, k);
                            ch[j] = k;
                            String newWord = String.valueOf(ch);

                            if(wordList.contains(newWord)) {
                                wordList.remove(newWord);
                                queue.add(newWord);
                            }
                            ch[j] = old;
                        }
                    }
                }
            }
            res++;
        }
        return 0;
    }

    public static int ladderLength11(String beginWord, String endWord, List<String> wordAsList) {
        //!!!! so the rest of the logic will be for the cases that end word is in the given list
        if(!wordAsList.contains(endWord)) return 0;

        Set<String> wordList = new HashSet<String>(wordAsList);
        Set<String> start = new HashSet<String>();
        Set<String> end = new HashSet<String>();
        int length = 1;
        start.add(beginWord);
        wordList.remove(beginWord);

        //!!!!!
        end.add(endWord);
        wordList.remove(endWord);
        //!!!!!

        while(!start.isEmpty()){
            Set<String> next = new HashSet<String>();
            for(String word: start){
                char[] wordArray = word.toCharArray();
                for(int i=0; i<word.length(); i++){
                    char old = wordArray[i];
                    for(char c='a'; c<='z'; c++){
                        wordArray[i] = c;
                        String str = String.valueOf(wordArray);
                        if(end.contains(str))
                            return length+1;
                        if(wordList.contains(str)){
                            next.add(str);
                            wordList.remove(str);
                        }
                    }
                    wordArray[i] = old;
                }
            }
            start = next.size() < end.size() ? next: end;
            end = start.size() < end.size() ? end : next;
            length++;
        }
        return 0;
    }

    //https://leetcode.com/problems/word-ladder/#/description
    public static int ladderLength1(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet(wordList);
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        Set<String> visited = new HashSet<>();

        int len = beginWord.length();
        beginSet.add(beginWord);

        //As the question says, end word must be in the wordList
        if(dict.contains(endWord)) {
            endSet.add(endWord);
        }

        int res = 1;

        while(!beginSet.isEmpty()) {
            Set<String> temp = new HashSet<>();

            for(String cur : beginSet) {
                System.out.println("****"+cur+"****");

                for(int j=0; j<len; j++) {
                    char[] ch = cur.toCharArray();
                    System.out.println("======");

                    for(char k='a'; k<='z'; k++) {

                        if(k == ch[j]) continue;

                        ch[j]=k;
                        String newWord = String.valueOf(ch);
                         System.out.println(newWord);

                        //!!! can't use "newWord.equals(endWord)"
//                         if(newWord.equals(endWord)) return res+1;
                        if(endSet.contains(newWord)) return res+1;

                        if(!visited.contains(newWord) && dict.contains(newWord)) {
                            System.out.println("getone-"+newWord);
                            visited.add(newWord);
                            temp.add(newWord);
                        }
                    }
                }
            }

            System.out.println("res="+res);
            if(temp.size()>=endSet.size()) System.out.println("----switch----");
            beginSet = temp.size()<endSet.size()? temp:endSet;
            endSet = beginSet == temp? endSet:temp;
            res++;
        }

        return 0;
    }

    public List<List<String>> findLadders2(String start, String end, Set<String> dict) {
        //A.
        results = new ArrayList<List<String>>();
        if (dict.size() == 0)
            return results;

        int min = Integer.MAX_VALUE;

        //B.used by Dijisktra search to do BFS
        Queue<String> queue = new ArrayDeque<String>();
        queue.add(start);

        //C.store the min ladder of EACH word, this is the one to be used in backtrack step
        map = new HashMap<String, List<String>>();

        //D.save word and dirs from start word to current word
        Map<String, Integer> ladder = new HashMap<String, Integer>();

        //put all words in dict to ladder
        for (String string : dict)
            ladder.put(string, Integer.MAX_VALUE);
        ladder.put(start, 0);

        dict.add(end);
        //BFS: Dijisktra search
        //Use BFS to construct a graph.
        while (!queue.isEmpty()) {

            String word = queue.poll();

            int step = ladder.get(word) + 1;//'step' indicates how many steps are needed to travel to one word.

            //ensure min ladder, only step number smaller then current min can continue
            if (step > min) break;

            for (int i = 0; i < word.length(); i++) {
                StringBuilder builder = new StringBuilder(word);
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    builder.setCharAt(i, ch);
                    String new_word = builder.toString();
                    if (ladder.containsKey(new_word)) {
                        System.out.println("new_word:" + new_word);

                        //So the whole purpose of ladder here is to remember the dirs to start word for each word.
                        //Then when the word appears, we use the entry to verify if it is the min dirs to start word for that word
                        //If not, then just continue, bypass all the logic behind it. In this way, we make sure only the min
                        //dirs node will be recorded in the Graph (saved in dist)
                        if (step > ladder.get(new_word)) {//Check if it is the shortest path to one word.
                            continue; //continue with for loop on character iteration
                        } else if (step < ladder.get(new_word)) {
                            queue.add(new_word); //BFS
                            ladder.put(new_word, step);
                        } else ;// It is a KEY line. If one word already appeared in one ladder,
                        // !!! Do not insert the same word inside the queue twice. Otherwise it gets TLE.

                        if (map.containsKey(new_word)) //Build adjacent Graph, which is used in backtrack step
                            map.get(new_word).add(word);
                        else {
                            List<String> list = new LinkedList<String>();
                            list.add(word);
                            map.put(new_word, list);
                            //It is possible to write three lines in one:
                            //dist.put(new_word,new LinkedList<String>(Arrays.asList(new String[]{word})));
                            //Which one is better?
                        }

                        //before reaching end, min is always MAX_VALUE
                        if (new_word.equals(end))
                            min = step;

                    }//End if dict contains new_word
                }//End:Iteration from 'a' to 'z'
            }//End:Iteration from the first to the last
        }//End While

        //BackTracking
        LinkedList<String> result = new LinkedList<String>();
        backTrace(end, start, result);

        return results;
    }

    private void backTrace(String word, String start, List<String> list) {
        if (word.equals(start)) {
            list.add(0, start);
            results.add(new ArrayList<String>(list));
            //DFS, after processing from current node, remove the current node when returning back to the previous layer
            list.remove(0);
            return;
        }
        //linked list, add new element at the head.
        //since we start with end and backtrack, the last one added will be start and it is the word ladder we want.
        list.add(0, word);
        if (map.get(word) != null)
            for (String s : map.get(word))
                backTrace(s, start, list);
        //DFS, after processing from current node, remove the current node when returning back to the previous layer
        list.remove(0);
    }

    public static void main(String [] args) {
        String dict[] = {"hot", "dot", "dog", "lot", "log", "cog"};
//        String dict[] = {"hot", "dot", "dog", "lot", "log"};

        List<String> test = new ArrayList<>(Arrays.asList(dict));

        int ans = ladderLength12("hit", "hot", test);

        System.out.println("ans=" + ans);
    }

}