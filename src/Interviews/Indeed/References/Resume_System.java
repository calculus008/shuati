package src.Interviews.Indeed.References;

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
        String id;
        int version;

        /**
         * key - version number
         * val - map of fields (key-value)
         */
        Map<Integer, Map<String, String>> versionToMap;

        public Profile(String id) {
            this.id = id;
            this.version = 1;
            versionToMap = new HashMap<>();
            versionToMap.put(1, new HashMap<>());
        }
    }

    Map<String, Profile> profiles;
    public Resume_System() {
        profiles = new HashMap<>();
    }

    public void update(String profileId, String field, String value) {
        if (!profiles.containsKey(profileId)) {
            Profile profile = new Profile(profileId);
            profile.versionToMap.get(profile.version).put(field, value);
            profiles.put(profileId, profile);
        } else {
            Profile profile = profiles.get(profileId);
            Map<String, String> fields = profile.versionToMap.get(profile.version);
            if (fields.containsKey(field)) {
                profile.versionToMap.put(profile.version + 1, new HashMap<>(fields));
                profile.version++;
                profile.versionToMap.get(profile.version).put(field, fields.get(field) + ", " + value);
            } else {
                /**
                 * ? Need to clarify :
                 * 1. if one filed is changed, should version increase?
                 * 2. When should we update version number? only when adding new field?
                 * 3. If the field value is the same as the input, should we increase version number?
                 */
                fields.put(field, value);
            }
        }
    }

    public String get(String profileId, int version) {
        if (!profiles.containsKey(profileId))
            return null;
        else {
            Profile profile = profiles.get(profileId);
            Map<String, String> fields = profile.versionToMap.get(version);
            StringBuilder sb = new StringBuilder();
            sb.append("{\"" + profileId + "\": ");
            for (String field : fields.keySet()) {
                sb.append("\"" + field + "\": " + "\"" + fields.get(field) + "\",");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public String getField(String profileId, int version, String field) {
        if (!profileId.contains(profileId))
            return null;
        else {
            Profile profile = profiles.get(profileId);
            if (!profile.versionToMap.containsKey(version))
                return null;
            else {
                Map<String, String> fields = profile.versionToMap.get(version);
                if (fields.containsKey(field)) {
                    return fields.get(field);
                } else {
                    return null;
                }
            }
        }
    }
}
