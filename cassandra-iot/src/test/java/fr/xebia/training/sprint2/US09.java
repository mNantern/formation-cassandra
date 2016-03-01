package fr.xebia.training.sprint2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.domain.model.User;
import fr.xebia.training.repository.SmartphonesRepository;
import fr.xebia.training.repository.UsersRepository;
import fr.xebia.training.service.SmartphonesService;

import static fr.xebia.training.ResourcesBuilder.createSmartphone;
import static fr.xebia.training.ResourcesBuilder.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class US09 extends BaseTest {

  private static final String CQL_US03 = "cql/US03.cql";
  private static final String CQL_US11 = "cql/US11.cql";
  @Mock
  SmartphonesRepository smartphonesRepository;

  @Mock
  UsersRepository usersRepository;

  @InjectMocks
  SmartphonesService smartphonesService;

  @Test
  public void testCallAddSmartphone() {
    //GIVEN
    Smartphone smartphone = createSmartphone(UUID.randomUUID());

    //WHEN
    smartphonesService.create(smartphone);

    //THEN
    verify(usersRepository, times(1)).addSmartphone(eq(smartphone.getOwner()), any(UUID.class),
                                                    eq(smartphone.getName()));
  }

  @Test
  public void testAddSmartphone() {
    //GIVEN
    loadCQL(CQL_US03);
    loadCQL(CQL_US11, false, false);
    UsersRepository usersRepository = new UsersRepository(session);
    String username = "test.integration@xebia.fr";
    UUID newSmartphoneId = UUID.randomUUID();
    String newSmartphoneName = "MyNewSmartphone";
    usersRepository.insert(createUser(username, UUID.randomUUID()));

    //WHEN
    usersRepository.addSmartphone(username, newSmartphoneId, newSmartphoneName);

    //THEN
    User user = usersRepository.read(username);
    assertThat(user.getSmartphonesId().size()).isEqualTo(2);
    assertThat(user.getSmartphonesId()).containsValues("MySmartphone", newSmartphoneName);
    assertThat(user.getSmartphonesId()).containsKey(newSmartphoneId);
  }

}
