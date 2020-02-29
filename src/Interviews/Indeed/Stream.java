package Interviews.Indeed;

import java.util.Iterator;

class Stream {
    Iterator<Integer> it;

    public Stream(Iterator<Integer> it) {
        this.it = it;
    }

    public boolean move() {
        return it.hasNext();
    }

    public int getValue() {
        return it.next();
    }
}
