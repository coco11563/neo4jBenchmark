package sha0w.pub.benchmark.demo;

import org.neo4j.driver.v1.*;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.security.URLAccessRule;

import static org.neo4j.driver.v1.Values.parameters;

public class pcTest {
    private static Driver driver;
    public pcTest() {
        driver = GraphDatabase.driver( "bolt://127.0.0.1:7687", AuthTokens.basic( "admin", "1234" ));
    }
    public static void main(String ars[]) {
        pcTest pc = new pcTest();
        Session session = driver.session();
        session.run( "CREATE (a:Person {name: {name}, title: {title}})",
                parameters( "name", "Arthur001", "title", "King001" ) );

        StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = {name} " +
                        "RETURN a.name AS name, a.title AS title",
                parameters( "name", "Arthur001" ) );
        while ( result.hasNext() )
        {
            Record record = result.next();
            System.out.println( record.get( "title" ).asString() + " " + record.get( "name" ).asString() );
        }
        session.close();
        driver.close();
    }
}
