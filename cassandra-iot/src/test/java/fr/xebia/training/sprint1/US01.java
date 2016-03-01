package fr.xebia.training.sprint1;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Type;
import fr.xebia.training.repository.DataRepository;

import static fr.xebia.training.ResourcesBuilder.createListData;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class US01 extends BaseTest {

  public static final String CQL_US01 = "cql/US01.cql";

  @Test
  public void testTableData() {
    //GIVEN
    loadCQL(CQL_US01);
    //Don't create the table if it already exists
    loadCQL(CQL_US01, false, false);

    //WHEN
    TableMetadata table = session.getCluster().getMetadata().getKeyspace(KEYSPACE).getTable("data");
    List<ColumnMetadata> pk = table.getPartitionKey();
    List<ColumnMetadata> cc = table.getClusteringColumns();

    //THEN
    assertThat(table).isNotNull();
    assertThat(pk.size()).isEqualTo(1);
    assertThat(pk.get(0).getName()).isEqualTo("smartphone_id");
    assertThat(pk.get(0).getType()).isEqualTo(DataType.uuid());
    assertThat(cc.size()).isEqualTo(2);
    assertThat(cc.get(0).getName()).isEqualTo("event_time");
    assertThat(cc.get(0).getType()).isEqualTo(DataType.timestamp());
    assertThat(cc.get(1).getName()).isEqualTo("type");
    assertThat(cc.get(1).getType()).isEqualTo(DataType.text());
  }

  @Test
  public void testInsertEmptyList() {
    //GIVEN
    loadCQL(CQL_US01);
    DataRepository dataRepository = new DataRepository(session);

    //WHEN
    dataRepository.insert(Collections.emptyList());

    //THEN
    Statement select = QueryBuilder.select().all().from("data");
    List<Row> results = session.execute(select).all();
    assertThat(results).isEmpty();
  }

  @Test
  public void testInsertList() {
    //GIVEN
    loadCQL(CQL_US01);
    DataRepository dataRepository = new DataRepository(session);
    UUID smartphoneId = UUID.randomUUID();
    List<Data> input = createListData(smartphoneId);

    //WHEN
    dataRepository.insert(input);

    //THEN
    Statement select = QueryBuilder.select().all().from("data");
    List<Row> results = session.execute(select).all();
    assertThat(results.size()).isEqualTo(2);
    assertThat(results.get(0).getString("type")).isEqualTo(Type.ACCELEROMETER.toString());
    assertThat(results.get(0).getUUID("id")).isNotNull();
    assertThat(results.get(0).getDate("event_time")).isInThePast();
    assertThat(results.get(1).getUUID("smartphone_id")).isEqualTo(smartphoneId);
    assertThat(results.get(1).getString("type")).isEqualTo(Type.BRIGHTNESS.toString());
    assertThat(results.get(1).getString("value")).isEqualTo("34");
  }
}
