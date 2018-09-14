package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator;

import java.util.Random;

public class FakeValueEvaluator {
    private FakeValueEvaluator() {}
    private static final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();
    private static final String long_str = "1234567890";

    private static FakeValueEvaluator instance=new FakeValueEvaluator();
    public static FakeValueEvaluator getInstance(){
        return instance;
    }

    public String evaluateStringByLength(int length) {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public long evaluateLongByLength(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(10);
            sb.append(long_str.charAt(number));
        }
        return Long.parseUnsignedLong(sb.toString());
    }

    public long evaluateLongByLengthUni(int serverId) {
        return UUIDEvaluator.getInstance().getId(serverId);
    }

}
