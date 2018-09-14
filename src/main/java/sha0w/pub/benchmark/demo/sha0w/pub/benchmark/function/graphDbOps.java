package sha0w.pub.benchmark.demo.sha0w.pub.benchmark.function;

import org.neo4j.cypher.internal.compiler.v2_3.commands.values.TokenType;
import org.neo4j.graphdb.*;

import javax.xml.crypto.dsig.TransformService;

public class graphDbOps {

    public static void registerShutdownHook(final GraphDatabaseService graphDatabaseService) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDatabaseService.shutdown();
            }
        });
    }


    public static Node buildNode(final GraphDatabaseService graphDatabaseService, String label, String nodeK, String nodeP) {
        Node n;
        try (Transaction tx = graphDatabaseService.beginTx()) {
            n = graphDatabaseService.createNode(Label.label(label));
            n.setProperty(nodeK, nodeP);
            tx.success();
            System.out.println("create node " + nodeK + " which name is " + nodeP);
        }
        return n;
    }

    public static Relationship buildRelationship (GraphDatabaseService graphDatabaseService, Node source, Node target, RelationshipType rela) {
        Relationship rel;
        try (Transaction tx = graphDatabaseService.beginTx()) {
            rel = source.createRelationshipTo(target, rela);
            System.out.println("create relationship which name is " + rela.name());
            tx.success();
        }
        return rel;
    }


    public static boolean DelRelationship(GraphDatabaseService graphDatabaseService, String label, String name, String value, RelationshipType rel, Direction way) {
        boolean result = false;
        try (Transaction tx = graphDatabaseService.beginTx()) {
            Label ll = Label.label(label);
            Node source = graphDatabaseService.findNode(ll, name, value);
            Relationship relationship = source.getSingleRelationship(rel, way);
            relationship.delete();
            relationship.getEndNode().delete();
            tx.success();
            result = true;
        }
       return result;
    }
}
