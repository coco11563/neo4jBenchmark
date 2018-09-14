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

/**
 * User server id = 3
 */
@NodeEntity
@Component
public class User implements Entity{

    @GraphId
    private long PSN_CODE;

    private String NAME;
    private String NAME_EN;
    private String POSITION;
    private String PHONE_NUM;
    private String ID;
    private String ID_TYPE;

    public User() {
        PSN_CODE = FakeValueEvaluator.getInstance().evaluateLongByLengthUni((int)Counter.getUserInstance().getCount());
        NAME = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        NAME_EN = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        POSITION = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        PHONE_NUM  = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        ID = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        ID_TYPE = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        Counter.getUserInstance().add();
//        System.out.println("生成user" + PSN_CODE);
    }

    public long getPSN_CODE() {
        return PSN_CODE;
    }
    @Relationship(type = "工作于")
    private Set<Organization> work_in = new HashSet<>();

    @Relationship(type = "申报项目")
    private Set<Project> apply = new HashSet<>();

    public void work_for(Organization organization) {
        work_in.add(organization);
        Counter.getRelaInstance().add();
    }

    public void apply(Project project) {
        apply.add(project);
        Counter.getRelaInstance().add();
        project.participated(this);
        Counter.getRelaInstance().add();
    }

    public Set<String> getWorkForRelationship() {
        long id = this.PSN_CODE;
        Set<String> res = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (Organization o : work_in) {
            sb.append(id).append(",");
            sb.append("工作于").append(",");
            sb.append(o.getREGID()).append(",");
            sb.append("work_for");
            res.add(sb.toString());
            sb = new StringBuilder();
        }
        return res;
    }
    public static String getWorkForRelationshipSchema() {
        return ":START_ID,工作于,:END_ID,:TYPE";
    }
    public Set<String> getApplyRelationship() {
        long id = this.PSN_CODE;
        Set<String> res = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (Project p : apply) {
            sb.append(id).append(",");
            sb.append("申请").append(",");
            sb.append(p.getID()).append(",");
            sb.append("apply");
            res.add(sb.toString());
            sb = new StringBuilder();
        }
        return res;
    }

    public static String getApplyRelationshipSchema() {
        return ":START_ID,申请,:END_ID,:TYPE";
    }

}
