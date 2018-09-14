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
 * reward server id = 4
 */
@NodeEntity
@Component
public class Reward implements Entity{
    @GraphId
    long reward_id;

    String zh_title;
    String en_title;
    String authors;
    String publish_date;

    public Reward() {
        reward_id = FakeValueEvaluator.getInstance().evaluateLongByLengthUni((int)Counter.getRewardInstance().getCount());
        zh_title = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        en_title = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        authors = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        publish_date = FakeValueEvaluator.getInstance().evaluateStringByLength(20);
        Counter.getRewardInstance().add();

//        System.out.println("生成reward" + reward_id);
    }

    @Relationship(type = "gain_from")
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    public void gain(Project project) {
        projects.add(project);
        Counter.getRelaInstance().add();
    }

    public Set<String> getGainFromRelationship() {
        long id = this.reward_id;
        Set<String> res = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (Project p : projects) {
            sb.append(id).append(",");
            sb.append("成果依托于").append(",");
            sb.append(p.getID()).append(",");
            sb.append("gain_from");
            res.add(sb.toString());
            sb = new StringBuilder();
        }
        return res;
    }
    public static String getGainFromRelationshipSchema() {
        return ":START_ID,成果依托于,:END_ID,:TYPE";
    }

    public long getReward_id() {
        return reward_id;
    }

}
