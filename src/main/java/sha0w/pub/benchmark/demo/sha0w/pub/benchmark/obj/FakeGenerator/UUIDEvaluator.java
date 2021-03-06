package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator;

public class UUIDEvaluator {
    private static UUIDEvaluator instance=new UUIDEvaluator();
    public static UUIDEvaluator getInstance(){
        return instance;
    }
    private UUIDEvaluator(){

    }
    private int id = 0;

    private static final long SIDMASK = 1000000000000000l;
    private static final long TIMEMASK = 100000;
    /**
     * 可读性更好 每秒生成十万条时 性能与位操作无明显差异  程序与ID的可读性更强
     * 有密集生成ID的时候 需要注意  单服每秒生成不超过9W个ID否则会出现重复
     * @param serverId
     * @return
     */
    public long getId(int serverId){
        synchronized (UUIDEvaluator.class) {
            id=id>=TIMEMASK?0:++id;
            return serverId*SIDMASK+System.currentTimeMillis()/1000*TIMEMASK+id;
        }
    }
}
