package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator;

public class Counter {

    private final static Counter counter = new Counter();
    private final static Counter org = new Counter();
    private final static Counter user = new Counter();
    private final static Counter project = new Counter();
    private final static Counter reward = new Counter();
    private long count = 0;

    private Counter() {
    }

    public static Counter getRelaInstance() {
        return counter;
    }
    public static Counter getOrgInstance() {
        return org;
    }
    public static Counter getUserInstance() {
        return user;
    }
    public static Counter getProjectInstance() {
        return project;
    }
    public static Counter getRewardInstance() {
        return reward;
    }
    public void add() {
        synchronized (Counter.class) {
            count ++;
        }
    }

    public long getCount() {
        synchronized (Counter.class) {
            return count;
        }
    }

    public void report() {
        System.out.println("build user num = " + user.getCount());
        System.out.println("build org num = " + org.getCount());
        System.out.println("build prj num = " + project.getCount());
        System.out.println("build reward num = " + reward.getCount());
        System.out.println("build rela num = " + counter.getCount());
    }
}
