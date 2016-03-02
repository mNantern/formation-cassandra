package fr.xebia.training;

import com.datastax.driver.core.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
  public static final String KEYSPACE = "cassandra_iot";

  @Bean(destroyMethod = "close")
  public Session buildCassandraSession(){

    //US00 : créer une session vers Cassandra
    Session session = null;
    createKeyspace(session);
    useKeyspace(session);

    return session;
  }

  private void createKeyspace(Session session) {
    //US00 : créer un keyspace
    LOGGER.info("Create keyspace {} if necessary", KEYSPACE);
  }

  private void useKeyspace(Session session) {
    //US00 : utiliser un keyspace
    LOGGER.info("Use keyspace {}", KEYSPACE);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
