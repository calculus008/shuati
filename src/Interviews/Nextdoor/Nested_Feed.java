package Interviews.Nextdoor;

/**
class ObjectType(Enum):
	NORMAL = 1
	PHOTO = 2
	VIDEO = 3

feed_objects = [
	{
		'id': 1,
		'subject': 'This is a subject',
		'body': 'This is a body',
		'type': ObjectType.PHOTO,
		'score': 100
	},
	{
		'id': 2,
		'subject': 'This is a subject 2',
		'body': 'This is a body 2',
		'type': ObjectType.PHOTO,
		'score': 99
	},
	{
		'id': 3,
		'subject': 'This is a subject 3',
		'body': 'This is a body 3',
		'type': ObjectType.NORMAL,
		'score': 85
	},
	{
		'id': 4,
		'subject': 'This is a subject 4',
		'body': 'This is a body 4',
		'type': ObjectType.VIDEO,
		'score': 95
	},
	{
		'id': 5,
		'subject': 'This is a subject 5',
		'body': 'This is a body 5',
		'type': ObjectType.PHOTO,
		'score' 94
	},
	[...]
]

 Write a method that groups content where type = PHOTO together into one object (we'll call it a 'rollup') to be
 displayed in the feed, with a limit of 3 items per rollup. How you structure this is up to you.

 The input to the method is the `feed_objects` list. Make sure the output includes all of the elements of the
 original input (including non `PHOTO` types)

 Be sure to write some unit tests.

 Few notes:
 - The feed is ordered by `score`
 - We want higher scored items to be grouped together
*/

import java.util.Iterator;
import java.util.List;
import java.util.*;

class Nested_Feed {
    static class FeedObject {
        int id;
        String subject;
        String body;
        ObjectType type;
        int score;

        public FeedObject(int id, String subject, String body, ObjectType type, int score) {
            this.id = id;
            this.subject = subject;
            this.body = body;
            this.type = type;
            this.score = score;
        }
    }

    static enum ObjectType {
        NORMAL,
        PHOTO,
        VIDEO
    }

    static class NestedFeed {
        FeedObject feed;
        boolean isFeed;
        List<NestedFeed> rollup;
    }

    public static List<NestedFeed> getFeeds(List<FeedObject> feeds) {
        List<NestedFeed> res = new ArrayList<>();
        if (feeds == null || feeds.size() == 0) return res;

        Collections.sort(feeds, (a, b) -> b.score - a.score);

        for (int i = 0, j = 0; i < feeds.size(); i++) {
            FeedObject cur = feeds.get(i);

            if (cur.type != ObjectType.PHOTO) {
                NestedFeed nf = new NestedFeed();
                nf.feed = cur;
                nf.isFeed = true;
                res.add(nf);
            } else {
                if (i < j) continue;

                List<NestedFeed> rollup = new ArrayList<>();
                NestedFeed nf1 = new NestedFeed();
                nf1.feed = feeds.get(i);
                nf1.isFeed = true;
                rollup.add(nf1);

                j = i + 1;
                while (rollup.size() < 3 && j < feeds.size()) {
                    while ( j < feeds.size() && feeds.get(j).type != ObjectType.PHOTO) {
                        j++;
                    }

                    if (j != feeds.size()) {
                        NestedFeed nf2 = new NestedFeed();
                        nf2.feed = feeds.get(j);
                        nf2.isFeed = true;
                        rollup.add(nf2);
                        j++;
                    }
                }

                NestedFeed nf = new NestedFeed();
                nf.rollup = rollup;
                nf.isFeed = false;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        FeedObject fo1 = new FeedObject(1, "This is a subject", "This is a body1", ObjectType.PHOTO, 100);
        FeedObject fo2 = new FeedObject(2, "This is a subject", "This is a body2", ObjectType.PHOTO, 99);
        FeedObject fo3 = new FeedObject(1, "This is a subject", "This is a body3", ObjectType.NORMAL, 85);
        FeedObject fo4 = new FeedObject(1, "This is a subject", "This is a body4", ObjectType.VIDEO, 95);
        FeedObject fo5 = new FeedObject(1, "This is a subject", "This is a body5", ObjectType.PHOTO, 94);

        List<FeedObject> input = new ArrayList<>();
        input.add(fo1);
        input.add(fo2);
        input.add(fo3);
        input.add(fo4);
        input.add(fo5);

        List<NestedFeed> res = getFeeds(input);

        Iterator<NestedFeed> it = res.iterator();
        while (it.hasNext()) {
            NestedFeed cur = it.next();
            if (cur.isFeed) {
                System.out.println(cur.feed.id);
            } else {
                for (NestedFeed nf : cur.rollup) {
                    System.out.println(nf.feed.id);
                }
            }
        }
    }
}

