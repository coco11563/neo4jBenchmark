package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.repo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import sha0w.pub.benchmark.demo.sha0w.pub.benchmark.obj.User;

interface UserRepo extends Neo4jRepository<User, Long> {
    // derived finder
    User findByTitle(String title);
}