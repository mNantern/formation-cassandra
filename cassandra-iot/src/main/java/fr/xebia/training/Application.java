package fr.xebia.training;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

@SpringBootApplication
public class Application {

  public static final String LOCALHOST = "127.0.0.1";
  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
  public static final String KEYSPACE = "cassandra_iot";
  private static final ObjectMapper MAPPER = initObjectMapper();

  @Bean(destroyMethod = "close")
  public Session buildCassandraSession(){

    //US00 : créer une session vers Cassandra
    Session session = Cluster.builder()
        .addContactPoint(LOCALHOST)
        .withLoadBalancingPolicy(
            new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build())
        )
        .build().connect();
    //US05 : appeler une machine locale

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

  @Bean
  public MappingJackson2HttpMessageConverter converter() {

    MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));

    List<MediaType> mediaTypes = new LinkedList<>();
    mediaTypes.add(mediaType);

    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(MAPPER);
    converter.setSupportedMediaTypes(mediaTypes);
    return converter;
  }

  private static ObjectMapper initObjectMapper() {
    final ObjectMapper objectMapper = new ObjectMapper();
    objectMapper
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
        .setDateFormat(new ISO8601DateFormat())
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return objectMapper;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
