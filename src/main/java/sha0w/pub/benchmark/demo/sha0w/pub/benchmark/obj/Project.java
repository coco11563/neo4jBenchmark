package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator.Counter;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.FakeGenerator.FakeValueEvaluator;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@Component
/**
 * project server id = 2
 */
public class Project implements Entity{
    @GraphId
    private long PRJ_CODE;

    public Project() {
        PRJ_CODE = FakeValueEvaluator.getInstance().evaluateLongByLengthUni((int)Counter.getProjectInstance().getCount());
        ZH_TITLE = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        EN_TITLE = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        ORG_CODE = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        GRANT_CODE = FakeValueEvaluator.getInstance().evaluateStringByLength(5);
        GRANT_NAME = FakeValueEvaluator.getInstance().evaluateStringByLength(5);
        SUBJECT_CODE1= FakeValueEvaluator.getInstance().evaluateStringByLength(4);
        TOTAL_AMT = FakeValueEvaluator.getInstance().evaluateStringByLength(8);
        HIGH_TECH_NO = FakeValueEvaluator.getInstance().evaluateStringByLength(10);
        PROJNATURE = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        Counter.getProjectInstance().add();

//        System.out.println("生成prj" + PRJ_CODE);
    }

    private String ZH_TITLE;
    private String EN_TITLE;
    private String ORG_CODE;
    private String GRANT_CODE;
    private String GRANT_NAME;
    private String SUBJECT_CODE1;
    private String TOTAL_AMT;
    private String HIGH_TECH_NO;
    private String PROJNATURE;

    @Relationship(type = "take_part_in")
    private Set<User> attain = new HashSet<>();

    public void participated(User u) {
        attain.add(u);
        Counter.getRelaInstance().add();
    }


    @Relationship(type = "output")
    @JsonIgnore
    private Set<Reward> rewards = new HashSet<>();

    public void output(Reward reward) {
        rewards.add(reward);
        Counter.getRelaInstance().add();
    }
    public Set<String> getTakePartInRelationship() {
        long id = this.PRJ_CODE;
        Set<String> res = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (User p : attain) {
            sb.append(id).append(",");
            sb.append("被参与").append(",");
            sb.append(p.getPSN_CODE()).append(",");
            sb.append("take_part_in");
            res.add(sb.toString());
            sb = new StringBuilder();
        }
        return res;
    }
    public static String getTakePartInRelationshipSchema() {
        return ":START_ID,被参与,:END_ID,:TYPE";
    }
    public Set<String> getOutputFromRelationship() {
        long id = this.PRJ_CODE;
        Set<String> res = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (Reward r : rewards) {
            sb.append(id).append(",");
            sb.append("产出").append(",");
            sb.append(r.getReward_id()).append(",");
            sb.append("output");
            res.add(sb.toString());
            sb = new StringBuilder();
        }
        return res;
    }
    public static String getOutputFromRelationshipSchema() {
        return ":START_ID,产出,:END_ID,:TYPE";
    }

    public long getID() {
        return PRJ_CODE;
    }
}
