package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator.Counter;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator.FakeValueEvaluator;

import java.util.HashSet;
import java.util.Set;


@NodeEntity
@Component
/**
 * server id = 1
 */
public class Organization implements Entity {
    @GraphId
    private long REGID;

    @Relationship(type = "offer_job", direction = Relationship.OUTGOING)
    private Set<User> subdue = new HashSet<>();

    private String BANKORGNAME;
    private String BANKNAME;
    private String YHZH;
    private String ORGTYPE;
    private String BELONGNET;
    private long PNO;
    private long ZIPCODE;
    private String REGYEAR;
    private String REGTYPE;
    private String REGTYPE2;
    private String EN_NAME;
    private long ORG_ID;
    private String IS_CONFIRM;
    private String FAREN;
    private String TOUCHWG;
    private String CITY;
    private String PROVINCE;
    private String INTERNAL_ORG;
    private String PRINTNO;
    private String TMP_CITY;
    private String TMP;
    private String SEQ_NO;
    private String BANK_SERIAL;
    private String KYBM_ADDRESS;

    public Organization() {
        REGID = FakeValueEvaluator.getInstance().evaluateLongByLengthUni((int)Counter.getOrgInstance().getCount());
        BANKORGNAME = FakeValueEvaluator.getInstance().evaluateStringByLength(8);
        BANKNAME = FakeValueEvaluator.getInstance().evaluateStringByLength(8);
        YHZH =  FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        ORGTYPE = FakeValueEvaluator.getInstance().evaluateStringByLength(5);
        BELONGNET = FakeValueEvaluator.getInstance().evaluateStringByLength(2);
        PNO = FakeValueEvaluator.getInstance().evaluateLongByLength(10);
        ZIPCODE = FakeValueEvaluator.getInstance().evaluateLongByLength(6);
        REGYEAR = FakeValueEvaluator.getInstance().evaluateStringByLength(4);
        REGTYPE = FakeValueEvaluator.getInstance().evaluateStringByLength(2);
        REGTYPE2 = FakeValueEvaluator.getInstance().evaluateStringByLength(2);
        EN_NAME = FakeValueEvaluator.getInstance().evaluateStringByLength(15);
        ORG_ID = FakeValueEvaluator.getInstance().evaluateLongByLengthUni(1);
        IS_CONFIRM = FakeValueEvaluator.getInstance().evaluateStringByLength(1);
        FAREN = FakeValueEvaluator.getInstance().evaluateStringByLength(12);
        TOUCHWG = FakeValueEvaluator.getInstance().evaluateStringByLength(13);
        CITY =  FakeValueEvaluator.getInstance().evaluateStringByLength(8);
        PROVINCE = FakeValueEvaluator.getInstance().evaluateStringByLength(8);
        INTERNAL_ORG = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        PRINTNO = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        TMP_CITY = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        TMP = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        SEQ_NO = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        BANK_SERIAL = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        KYBM_ADDRESS = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        Counter.getOrgInstance().add();

//        System.out.println("生成ORG" + ORG_ID);
    }

    public void addUser(User u) {
        subdue.add(u);
        Counter.getRelaInstance().add();
        u.work_for(this);
        Counter.getRelaInstance().add();
    }

    public long getREGID() {
        return REGID;
    }

    public Set<String> getOfferJobRelationship() {
        long id = this.REGID;
        Set<String> res = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (User r : subdue) {
            sb.append(id).append(",");
            sb.append("提供工作").append(",");
            sb.append(r.getPSN_CODE()).append(",");
            sb.append("offer_job");
            res.add(sb.toString());
            sb = new StringBuilder();
        }
        return res;
    }
    public static String getOfferJobRelationshipSchema() {
        return ":START_ID,提供工作,:END_ID,:TYPE";
    }

}
