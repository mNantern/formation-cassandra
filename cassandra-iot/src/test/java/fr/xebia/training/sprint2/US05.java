package fr.xebia.training.sprint2;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.LoadBalancingPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;

import org.junit.Test;

import fr.xebia.training.Application;
import fr.xebia.training.BaseTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class US05 extends BaseTest {

  public static final String TOKEN_AWARE_POLICY =
      "com.datastax.driver.core.policies.TokenAwarePolicy";
  public static final String ROUND_ROBIN_POLICY =
      "com.datastax.driver.core.policies.DCAwareRoundRobinPolicy";

  @Test
  public void testLoadBalancingPolicy() {
    //GIVEN
    String keyspaceName = "cassandra_iot";
    Application application = new Application();

    //WHEN
    Session cassandraSession = application.buildCassandraSession();
    LoadBalancingPolicy lbPolicy = cassandraSession.getCluster().getConfiguration()
        .getPolicies().getLoadBalancingPolicy();

    //THEN
    assertThat(lbPolicy.getClass().getName()).isEqualTo(TOKEN_AWARE_POLICY);
    String rrPolicy = ((TokenAwarePolicy) lbPolicy).getChildPolicy().getClass().getName();
    assertThat(rrPolicy).isEqualTo(ROUND_ROBIN_POLICY);
  }

}
