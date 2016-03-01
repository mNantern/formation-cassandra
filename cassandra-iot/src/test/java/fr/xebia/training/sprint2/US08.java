package fr.xebia.training.sprint2;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.exceptions.ForbiddenException;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.SmartphonesRepository;

import static fr.xebia.training.ResourcesBuilder.createSmartphone;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class US08 extends BaseTest {

  private static final String CQL_US04 = "cql/US04.cql";
  private static final String CQL_US08 = "cql/US08.cql";
  public static final String TABLE_SMARTPHONES = "smartphones";
  private static final String CQL_US15 = "cql/US15.cql";
  private static final String CQL_US16 = "cql/US16.cql";
  private SmartphonesRepository smartphonesRepository;
  private UUID smartphoneId;

  @Before
  public void setup() {
    loadCQL(CQL_US04);
    loadCQL(CQL_US08, false, false);
    loadCQL(CQL_US15, false, false);
    loadCQL(CQL_US16, false, false);
    smartphonesRepository = new SmartphonesRepository(session);
    smartphoneId = UUID.randomUUID();
    Smartphone smartphone = createSmartphone(smartphoneId);
    Statement insert = QueryBuilder.insertInto(TABLE_SMARTPHONES)
        .value("id", smartphone.getId())
        .value("constructor", smartphone.getConstructor())
        .value("model", smartphone.getModel())
        .value("name", smartphone.getName())
        .value("owner", smartphone.getOwner());
    session.execute(insert);
  }

  @Test
  public void testTableData() {
    //GIVEN

    //WHEN
    TableMetadata table = session.getCluster().getMetadata()
        .getKeyspace(KEYSPACE).getTable(TABLE_SMARTPHONES);

    //THEN
    assertThat(table.getColumn("owner")).isNotNull();
    assertThat(table.getColumn("owner").getType()).isEqualTo(DataType.text());
  }


  @Test
  public void testDeleteBadUser() {
    //GIVEN
    String badUser = "unknown@bad.com";

    //WHEN
    try {
      smartphonesRepository.delete(smartphoneId, badUser);
      fail("Delete Smartphone must have thrown a ForbiddenException !");
    } catch (ForbiddenException ex) {
      //THEN
      Statement count = QueryBuilder.select().countAll().from(TABLE_SMARTPHONES);
      ResultSet resultSet = session.execute(count);
      assertThat(resultSet.one().getLong("count")).isEqualTo(1);
    }
  }

  @Test
  public void testDeleteGoodUser() {
    //GIVEN
    String badUser = "jdoe@gmail.com";

    //WHEN
    try {
      smartphonesRepository.delete(smartphoneId, badUser);
    } catch (ForbiddenException ex) {
      fail("Why an exception ? It's my smartphone !");
    }

    //THEN
    Statement count = QueryBuilder.select().countAll().from(TABLE_SMARTPHONES);
    ResultSet resultSet = session.execute(count);
    assertThat(resultSet.one().getLong("count")).isEqualTo(0);
  }

}
