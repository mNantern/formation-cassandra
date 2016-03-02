package fr.xebia.training.sprint3;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.repository.UsersRepository;

import static fr.xebia.training.ResourcesBuilder.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class US11 extends BaseTest {

  private static final String CQL_US03 = "cql/US03.cql";
  private static final String CQL_US11 = "cql/US11.cql";

  @Test
  public void addSmartphoneName() {
    //GIVEN
    loadCQL(CQL_US03);
    loadCQL(CQL_US11, false, false);
    UsersRepository usersRepository = new UsersRepository(session);
    String username = "jdoe@gmail.com";
    UUID smartphoneId = UUID.randomUUID();
    usersRepository.insert(createUser(username, smartphoneId));

    //WHEN
    UUID newSmartphoneId = UUID.randomUUID();
    String newSmartphoneName = "myNewSmartphone";
    usersRepository.addSmartphone(username, newSmartphoneId, newSmartphoneName);

    //THEN
    Statement select = QueryBuilder.select().all().from("users");
    List<Row> results = session.execute(select).all();

    assertThat(results.size()).isEqualTo(1);
    assertThat(results.get(0).getString("username")).isEqualTo(username);
    Map<UUID, String> smartphones = results.get(0).getMap("smartphones", UUID.class, String.class);
    assertThat(smartphones).isNotEmpty().hasSize(2);
    assertThat(smartphones).containsOnly(entry(smartphoneId, "MySmartphone"),
                                         entry(newSmartphoneId, newSmartphoneName));
  }
}
