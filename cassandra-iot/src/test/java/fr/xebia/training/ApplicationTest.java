package fr.xebia.training;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest extends BaseTest {

  @Test
  public void testBuildCassandraSessionUS00() throws Exception {
    assertThat(session).isNotNull();
    assertThat(session.getLoggedKeyspace()).isEqualTo(KEYSPACE);
    Set<Host> hosts = session.getCluster().getMetadata().getAllHosts();
    assertThat(hosts.size()).isEqualTo(1);
    InetSocketAddress firstHostAddress = hosts.stream().findFirst().get().getSocketAddress();
    assertThat(firstHostAddress.getHostName()).isEqualTo("localhost");
    assertThat(firstHostAddress.getPort()).isEqualTo(9042);
  }

  @Test
  public void testLoadCQL() {
    assertThat(dataLoader).isNotNull();
    loadCQL("cql/Application.cql");
    Statement select = QueryBuilder.select().all().from("test");
    List<Row> results = session.execute(select).all();
    assertThat(results.size()).isEqualTo(0);
  }
}