package fr.xebia.training;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.StringJoiner;

@RunWith(MockitoJUnitRunner.class)
public class BaseTest {

  public static final String LOCALHOST = "127.0.0.1";
  public static final String KEYSPACE = "cassandra_iot_test";
  public static final int CASSANDRA_PORT = 9042;
  public Session session = buildCassandraSession();
  protected CQLDataLoader dataLoader = new CQLDataLoader(session);

  public Session buildCassandraSession() {
    Session session = Cluster.builder()
        .addContactPoint(LOCALHOST)
        .withPort(CASSANDRA_PORT)
        .build().connect();

    createKeyspace(session);
    useKeyspace(session);

    return session;
  }

  private void createKeyspace(Session session) {
    String keyspaceCreation = new StringJoiner("")
        .add("CREATE KEYSPACE IF NOT EXISTS ")
        .add(KEYSPACE)
        .add(" WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1' : 1}")
        .toString();
    session.execute(keyspaceCreation);
  }

  private void useKeyspace(Session session) {
    session.execute("USE " + KEYSPACE);
  }

  protected void loadCQL(String filepath) {
    dataLoader.load(new ClassPathCQLDataSet(filepath, false, false, KEYSPACE));
  }

}