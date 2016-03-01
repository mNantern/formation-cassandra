package fr.xebia.training.sprint1;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.core.UDTValue;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.User;
import fr.xebia.training.repository.UsersRepository;

import static fr.xebia.training.ResourcesBuilder.createUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class US03 extends BaseTest {

  public static final String CQL_US03 = "cql/US03.cql";
  public static final String CQL_US11 = "cql/US11.cql";

  @Test
  public void testTableData() {
    //GIVEN
    loadCQL(CQL_US03);
    //Don't create the table if it already exists
    loadCQL(CQL_US03, false, false);

    //WHEN
    TableMetadata
        table =
        session.getCluster().getMetadata().getKeyspace(KEYSPACE).getTable("users");
    List<ColumnMetadata> pk = table.getPartitionKey();
    ColumnMetadata addresses = table.getColumn("addresses");

    //THEN
    assertThat(table).isNotNull();
    assertThat(pk.size()).isEqualTo(1);
    assertThat(pk.get(0).getName()).isEqualTo("username");
    assertThat(pk.get(0).getType()).isEqualTo(DataType.text());

    assertThat(addresses).isNotNull();
    assertThat(addresses.getType().getTypeArguments().size()).isEqualTo(2);
    assertThat(addresses.getType().getTypeArguments().get(1).getName().toString()).isEqualTo("udt");
  }


  @Test
  public void testInsert() {
    //GIVEN
    loadCQL(CQL_US03);
    loadCQL(CQL_US11, false, false);
    UsersRepository usersRepository = new UsersRepository(session);

    //WHEN
    String username = "test.integration@xebia.fr";
    usersRepository.insert(createUser(username, UUID.randomUUID()));

    //THEN
    Statement select = QueryBuilder.select().all().from("users");
    List<Row> results = session.execute(select).all();

    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getString("username")).isEqualTo(username);
    assertThat(results.get(0)
                   .getMap("addresses", String.class, UDTValue.class)
                   .get("Xebia")
                   .getString("city"))
        .isEqualTo("PARIS");
  }

  @Test
  public void testRead() {
    //GIVEN
    loadCQL(CQL_US03);
    loadCQL(CQL_US11, false, false);
    UsersRepository usersRepository = new UsersRepository(session);
    String username = "test.integration@xebia.fr";
    UUID smartphoneId = UUID.randomUUID();
    User user = createUser(username, smartphoneId);
    usersRepository.insert(user);

    //WHEN
    User result = usersRepository.read(username);

    //THEN
    assertThat(result).isEqualToComparingFieldByField(user);
  }

  @Test
  public void testUpdate() {
    //GIVEN
    loadCQL(CQL_US03);
    loadCQL(CQL_US11, false, false);
    UsersRepository usersRepository = new UsersRepository(session);
    String username = "test.integration@xebia.fr";
    UUID smartphoneId = UUID.randomUUID();
    User user = createUser(username, smartphoneId);
    usersRepository.insert(user);

    //WHEN
    user.setLastname("unitaire");
    usersRepository.update(username, user);

    //THEN
    User result = usersRepository.read(username);
    assertThat(result.getLastname()).isEqualTo("unitaire");
  }

  @Test
  public void testDelete() {
    //GIVEN
    loadCQL(CQL_US03);
    loadCQL(CQL_US11, false, false);
    UsersRepository usersRepository = new UsersRepository(session);
    String username = "test.integration@xebia.fr";
    usersRepository.insert(createUser(username, UUID.randomUUID()));

    //WHEN
    usersRepository.delete(username);

    //THEN
    Statement select = QueryBuilder.select().all().from("users");
    List<Row> results = session.execute(select).all();

    assertThat(results.size()).isEqualTo(0);
  }

}
