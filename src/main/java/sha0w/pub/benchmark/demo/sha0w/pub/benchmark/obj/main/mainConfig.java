package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.main;

import org.neo4j.driver.internal.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Organization;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Project;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.Reward;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.User;
@EnableTransactionManagement
@EnableNeo4jRepositories("sha0w.pub.benchmark.demo.sha0w.pub.benchmark.repo")
@Configuration
public class mainConfig {
    @Bean
    public Organization getOrg() {
        return new Organization();
    }
    @Bean
    public Project getPrj() {
        return new Project();
    }
    @Bean
    public Reward getRwd() {
        return new Reward();
    }
    @Bean
    public User getUser() {
        return new User();
    }

}
