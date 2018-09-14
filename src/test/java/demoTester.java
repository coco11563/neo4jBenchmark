import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.cypher.internal.compiler.v2_3.commands.values.TokenType;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import sha0w.pub.benchmark.demo.demoTest;

import static sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function.graphDbOps.DelRelationship;
import static sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function.graphDbOps.buildNode;
import static sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function.graphDbOps.buildRelationship;

class demoTester {
    static sha0w.pub.benchmark.demo.demoTest demoTest;
    static String k = "name";
    static String s1 = "k1";
    static String s2 = "k2";
    static RelationshipType rela = RelationshipType.withName("knows");
    static Node a1;
    static Node a2;
    @BeforeAll
    static void Test() {
        demoTest = new demoTest();
        a1 = buildNode(demoTest.getGraphDb(), "l1", k, s1);
        a2 = buildNode(demoTest.getGraphDb(), "l1", k, s2);
        demoTest.shutdown();
    }

    @Test
    void TestBuildRelationship() {
        buildRelationship(demoTest.getGraphDb(), a1, a2, rela);
        DelRelationship(demoTest.getGraphDb(), "l1", k, s1, rela, Direction.OUTGOING);
    }
}
