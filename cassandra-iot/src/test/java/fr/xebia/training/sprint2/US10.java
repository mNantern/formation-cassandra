package fr.xebia.training.sprint2;

import org.junit.Test;

import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.exceptions.ConflictException;
import fr.xebia.training.repository.UsersRepository;

import static fr.xebia.training.ResourcesBuilder.createUser;
import static org.assertj.core.api.Assertions.assertThat;

public class US10 extends BaseTest {

  private static final String CQL_US03 = "cql/US03.cql";

  @Test(expected = ConflictException.class)
  public void testDuplicateUser() {
    //GIVEN
    loadCQL(CQL_US03);
    UsersRepository usersRepository = new UsersRepository(session);
    String username = "test.integration@xebia.fr";
    usersRepository.insert(createUser(username, UUID.randomUUID()));

    //WHEN
    usersRepository.insert(createUser(username, UUID.randomUUID()));

    //THEN
    assertThat(true).isFalse();
  }

}
