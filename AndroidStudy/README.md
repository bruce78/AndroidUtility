AndroidStudy
============

github上的开源工程很多，但是例子都不太详细。所以把自己学习开源项目写的例子记录下来，便于积累、回顾

依赖工程：
1.EventBus
https://github.com/greenrobot/EventBus/trunk/EventBus


一：EventBus中单例模式
volatile保证了对defaultInstance的读操作是最新的，synchronized保证了对defaultInstance的写操作是最新的
//EventBus类
public class EventBus {
	//静态成员变量 volatile保证了对defaultInstance的读操作是最新的
	private static volatile EventBus defaultInstance;
	
	//双重判断获取实例
	/** Convenience singleton for apps using a process-wide EventBus instance. */
    public static EventBus getDefault() {
        if (defaultInstance == null) {
			//synchronized保证了对defaultInstance的写操作是原子的
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventBus();
                }
            }
        }
        return defaultInstance;
    }
	
	//方法为public的。意思是可以有多个实例，每个实例是相对独立的。不过一般不需要多个实例，所以这个方法一般不用
	/**
     * Creates a new EventBus instance; each instance is a separate scope in which events are delivered. To use a
     * central bus, consider {@link #getDefault()}.
     */
    public EventBus() {
        subscriptionsByEventType = new HashMap<Class<?>, CopyOnWriteArrayList<Subscription>>();
        typesBySubscriber = new HashMap<Object, List<Class<?>>>();
        stickyEvents = new ConcurrentHashMap<Class<?>, Object>();
        mainThreadPoster = new HandlerPoster(this, Looper.getMainLooper(), 10);
        backgroundPoster = new BackgroundPoster(this);
        asyncPoster = new AsyncPoster(this);
        subscriberMethodFinder = new SubscriberMethodFinder();
        logSubscriberExceptions = true;
    }
}
二：PendingPost对象池
PendingPost对象的创建开销不大，不太理解为什么用对象池。
final class PendingPost {
	//对象池，对象创建的开销并不大。不太理解为什么要用对象池
    private final static List<PendingPost> pendingPostPool = new ArrayList<PendingPost>();

    Object event;
    Subscription subscription;
    PendingPost next;

    private PendingPost(Object event, Subscription subscription) {
        this.event = event;
        this.subscription = subscription;
    }
	
	//从对象池中取出PendingPost
    static PendingPost obtainPendingPost(Subscription subscription, Object event) {
        synchronized (pendingPostPool) {
            int size = pendingPostPool.size();
			//如果对象池中有，从池中末尾位置取出pendingPost，初始化并返回
            if (size > 0) {
                PendingPost pendingPost = pendingPostPool.remove(size - 1);
                pendingPost.event = event;
                pendingPost.subscription = subscription;
                pendingPost.next = null;
                return pendingPost;
            }
        }
		//如果对象池中没有，new一个对象并返回
        return new PendingPost(event, subscription);
    }
	
	//将pendingPost放回对象池中
    static void releasePendingPost(PendingPost pendingPost) {
		//钝化pendingPost
        pendingPost.event = null;
        pendingPost.subscription = null;
        pendingPost.next = null;
        synchronized (pendingPostPool) {
			//如果对象池的大小未达到10000，才将pendingPost放到对象池中末尾位置
            // Don't let the pool grow indefinitely
            if (pendingPostPool.size() < 10000) {
                pendingPostPool.add(pendingPost);
            }
        }
    }

}
三：PendingPostQueue链表
//链表
final class PendingPostQueue {
	//头指针
    private PendingPost head;
	//尾指针
    private PendingPost tail;

	//向链表中增加元素
    synchronized void enqueue(PendingPost pendingPost) {
        if (pendingPost == null) {
            throw new NullPointerException("null cannot be enqueued");
        }
        if (tail != null) {
			//存在内容时 尾指针指向下一个元素
            tail.next = pendingPost;
            tail = pendingPost;
        } else if (head == null) {
			//初始化时
            head = tail = pendingPost;
        } else {
            throw new IllegalStateException("Head present, but no tail");
        }
		//通知其他线程链表中已有数据
        notifyAll();
    }
	
	//从链表中取出下一个元素
    synchronized PendingPost poll() {
        PendingPost pendingPost = head;
        if (head != null) {
			//头指针移向下一个元素
            head = head.next;
			//如果没有元素，全部置为null
            if (head == null) {
                tail = null;
            }
        }
        return pendingPost;
    }

    synchronized PendingPost poll(int maxMillisToWait) throws InterruptedException {
        if (head == null) {
			//当前线程睡眠一段时间
            wait(maxMillisToWait);
        }
        return poll();
    }

}







