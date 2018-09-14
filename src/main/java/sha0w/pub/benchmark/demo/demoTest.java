package sha0w.pub.benchmark.demo;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.io.IOException;

import static sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function.graphDbOps.registerShutdownHook;

public class demoTest {
    private static final File DB_PATH = new File("target/neo4j.db");
    private static GraphDatabaseService graphDb;
    public demoTest() {
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
    }
    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }
    public void shutdown() {
        registerShutdownHook(graphDb);
    }
}
