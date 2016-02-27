package fr.xebia.training;

import com.datastax.driver.core.Host;
import com.datastax.driver.core.Session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetSocketAddress;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTest {

  @Autowired
  private Session session;

  @Test
  public void testBuildCassandraSessionUS00() throws Exception {
    assertThat(session).isNotNull();
    assertThat(session.getLoggedKeyspace()).isEqualTo(Application.KEYSPACE);
    Set<Host> hosts = session.getCluster().getMetadata().getAllHosts();
    assertThat(hosts.size()).isEqualTo(1);
    InetSocketAddress firstHostAddress = hosts.stream().findFirst().get().getSocketAddress();
    assertThat(firstHostAddress.getHostName()).isEqualTo("localhost");
    assertThat(firstHostAddress.getPort()).isEqualTo(9042);
  }
}