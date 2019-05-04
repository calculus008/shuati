package Interviews.Google;

    /**
     * 静态内部类
     *
     * 优点：避免了线程不安全，延迟加载，效率高。
     *
     * 缺点：暂无，最推荐使用。
     *
     * 特点：这种方式跟饿汉式方式采用的机制类似，但又有不同。
     *
     * 两者都是采用了类装载的机制来保证初始化实例时只有一个线程。不同的地方在饿汉式方式是只要Singleton类被装载就会实例化，
     * 没有Lazy-Loading的作用，而静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，
     * 才会装载SingletonInstance类，从而完成Singleton的实例化。 类的静态属性只会在第一次加载类的时候初始化，所以在这里，
     * JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的
     */

public class Singleton {
    private Singleton() {
    }

    private static class SingletonHolder {
        private static final Singleton instance = new Singleton();
    }

    public static final Singleton getInstance() {
        return SingletonHolder.instance;
    }
}

/**
 * Enum
 *
 * 优点：不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 * 缺点：JDK 1.5之后才能使用。
 *
 * public enum Singleton {
 *    INSTANCE;
 *    public void whateverMethod() {
 *    }
 * }
 *
 */

