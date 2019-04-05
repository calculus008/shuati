package Linkedin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Find_Same_Person {
    /**
     * LE_721_Accounts_Merge
     *
     * 如果两个用户id有同样的邮箱说明是同一个人，input有共同邮箱的人们和n个id，输出有多少人，bfs或者unionfind
     */

    /**
     * @param contacts, Person -> Emails that person use
     * @return Group same person together
     *
     * Union Find
     * personToRoot is used as Union Find Set
     */
    Map<Integer, Integer> personToRoot = new HashMap<Integer, Integer>();

    public Map<Integer, List<Integer>> find(HashMap<Integer, List<String>> contacts) {
        Map<String, Integer> emailToPerson = new HashMap<String, Integer>();

        for (Map.Entry<Integer, List<String>> entry : contacts.entrySet()) {
            int personId = entry.getKey();
            List<String> emails = entry.getValue();

            /**
             * !!!
             * Init Union Find Set
             * Default root is itself
             */
            personToRoot.put(personId, personId);

            for (String email : emails) {
                if (!emailToPerson.containsKey(email)) {// A new email
                    emailToPerson.put(email, personId);
                } else {
                    /**
                     * !!!
                     */
                    int newRoot = findRoot(emailToPerson.get(email));
                    personToRoot.put(personId, newRoot);
                }
            }
        }

        Map<Integer, List<Integer>> res = new HashMap<Integer, List<Integer>>();

        /**
         * Extract result as required from Union Find Set (personToRoot)
         */
        for (Map.Entry<Integer, Integer> entry : personToRoot.entrySet()) {
            int personId = entry.getKey();

            /**
             * !!!
             */
            int rootId = findRoot(entry.getValue());

            if (!res.containsKey(rootId)) {
                res.put(rootId, new ArrayList<Integer>());
            }
            res.get(rootId).add(personId);
        }

        return res;
    }

    private int findRoot(Integer id) {
        while (personToRoot.get(id) != id) {
            id = personToRoot.get(id);
        }

        return id;
    }
}
