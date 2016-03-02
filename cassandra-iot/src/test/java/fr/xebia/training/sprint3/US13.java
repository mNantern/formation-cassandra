package fr.xebia.training.sprint3;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.repository.DataRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static org.assertj.core.api.Assertions.assertThat;

public class US13 extends BaseTest {

  private static final String CQL_US01 = "cql/US01.cql";
  private static final String CQL_US16 = "cql/US16.cql";

  @Test
  public void testTTL() {
    //GIVEN
    loadCQL(CQL_US01);
    loadCQL(CQL_US16, false, false);
    DataRepository dataRepository = new DataRepository(session);
    UUID smartphoneId = UUID.randomUUID();
    List<Data> input = createListData(smartphoneId);

    //WHEN
    dataRepository.insert(input);

    //THEN
    Statement selectTTL = QueryBuilder.select().ttl("id").from("data");
    ResultSet resultSet = session.execute(selectTTL);
    assertThat(resultSet.one().getInt("ttl(id)")).isGreaterThan(0);
  }

}
