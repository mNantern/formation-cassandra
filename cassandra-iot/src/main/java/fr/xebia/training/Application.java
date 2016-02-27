package fr.xebia.training;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.StringJoiner;

@SpringBootApplication
public class Application {

  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
  public static final String KEYSPACE = "cassandra_iot";

  @Bean(destroyMethod = "close")
  public Session buildCassandraSession(){

    //US00 : créer une session vers Cassandra
    Session session = Cluster.builder().addContactPoint(LOCALHOST).build().connect();
    createKeyspace(session);
    useKeyspace(session);

    return session;
  }

  private void useKeyspace(Session session) {
    LOGGER.info("Use keyspace {}", KEYSPACE);
    session.execute("USE " + KEYSPACE);
  }

  private void createKeyspace(Session session) {
    LOGGER.info("Create keyspace {} if necessary", KEYSPACE);
    String keyspaceCreation = new StringJoiner("")
        .add("CREATE KEYSPACE IF NOT EXISTS ")
        .add(KEYSPACE)
        .add(" WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1' : 1}")
        .toString();
    session.execute(keyspaceCreation);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
