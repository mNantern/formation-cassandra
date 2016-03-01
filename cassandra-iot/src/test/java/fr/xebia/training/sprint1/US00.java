package fr.xebia.training.sprint1;

import com.datastax.driver.core.Session;

import org.junit.Test;

import java.util.Map;

import fr.xebia.training.Application;
import fr.xebia.training.BaseTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class US00 extends BaseTest {

  @Test
  public void testSession() {
    //Given
    String keyspaceName = "cassandra_iot";
    Application application = new Application();

    //When
    Session cassandraSession = application.buildCassandraSession();
    Map<String, String> replicationOptions = cassandraSession.getCluster()
        .getMetadata()
        .getKeyspace(keyspaceName)
        .getReplication();

    //Then
    assertThat(cassandraSession).isNotNull();
    assertThat(replicationOptions.get("class"))
        .isEqualTo("org.apache.cassandra.locator.NetworkTopologyStrategy");
    assertThat(replicationOptions.get("datacenter1")).isEqualTo("1");
    assertThat(cassandraSession.getLoggedKeyspace()).isEqualTo(keyspaceName);
  }
}
