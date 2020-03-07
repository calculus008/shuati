package src.Interviews.Indeed.上机;

import java.util.HashMap;
import java.util.Map;

public class Resume_System {
    /**
     * 实现一个简历的系统，3个API
     * 1) update(String profileId, String field, String value); //这时候版本要+1
     * 2) get(String profileId, int version); //找对应版本的field和value
     * 3) getField(String profileId, int version, String field); //找对应的value
     *
     * 每个用户有一个 profile，然后 profile 里有各种 field 和对应的 value，第一次 update 之后的 version 是 1，
     * 再 update version 变成 2，依此类推。
     * 虽然是 online HackerRank，但是是有面试官坐旁边随时解答问题的。我习惯了 HackerRank 就闷头做题，想了两个思路，
     * 没有和面试官交流就直接写了一种（他倒也说了不用和他讲思路，直接做题就好）。
     */

    class Profile {
        String profileId;
        int curVersion;

        Map<Integer, Map<String, String>> versions;

        public Profile(String profileId) {
            this.profileId = profileId;
            curVersion = 1;
            versions = new HashMap<>();
            versions.put(1, new HashMap<>());
        }
    }

    Map<String, Profile> map = new HashMap<>();

    public void update(String profileId, String field, String value) {
        if (!map.containsKey(profileId)) {
            Profile p = new Profile(profileId);
            p.versions.get(1).put(field, value);

            /**
             * !!!
             */
            map.put(profileId, p);
        } else {
            Profile cur = map.get(profileId);
            Map<String, String> fields = cur.versions.get(cur.curVersion);

            if (fields.containsKey(field)) {
                if (!value.equals(fields.get(field))) {
                    Map<String, String> newVersion = new HashMap<>(fields);
                    newVersion.put(field, value);
                    cur.curVersion++;
                    cur.versions.put(cur.curVersion, newVersion);
                }
            } else {
                fields.put(field, value);
            }
        }
    }

    public String get(String profileId, int version) {
        Map<String, String> fields = getFileds(profileId, version);

        if (fields == null) return null;

        StringBuilder sb = new StringBuilder();
        for (String key : fields.keySet()) {
            sb.append(key).append(":").append(fields.get(key)).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public Map<String, String> getFileds(String profileId, int version) {
        if (!map.containsKey(profileId)) return null;

        Profile p = map.get(profileId);
        if (p.curVersion < version || version <= 0) return null;

        return p.versions.get(version);
    }

    public String getField(String profileId, int version, String field) {
        Map<String, String> fields = getFileds(profileId, version);

        if (fields == null) return null;

        return fields.get(field);
    }

    public static void main(String[] args) {
        Resume_System test = new Resume_System();

        test.update("1111", "name", "kyuan");
        test.update("1111", "city", "san jose");

        test.update("2222", "name", "blah");
        test.update("2222", "city", "sf");

        System.out.println(test.get("1111", 1));
        System.out.println(test.get("2222", 1));

        test.update("2222", "city", "sunnyvale");

        System.out.println(test.get("2222", 1));
        System.out.println(test.get("2222", 2));

        test.update("1111", "age", "49");
        System.out.println(test.get("1111", 1));

        test.update("1111", "city", "beijing");
        System.out.println(test.get("1111", 2));
    }
}
