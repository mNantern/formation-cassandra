package fr.xebia.training.sprint1;

import com.datastax.driver.core.ColumnMetadata;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;
import com.datastax.driver.mapping.Mapper;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import fr.xebia.training.BaseTest;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.SmartphonesRepository;

import static fr.xebia.training.ResourcesBuilder.createSmartphone;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class US04 extends BaseTest {

  private static final String CQL_US04 = "cql/US04.cql";

  @Mock
  private Session sessionMock;
  @Mock
  private Mapper<Smartphone> mapper;

  @InjectMocks
  private SmartphonesRepository smartphonesRepository;

  @Test
  public void testTableData() {
    //GIVEN
    loadCQL(CQL_US04);
    //Don't create the table if it already exists
    loadCQL(CQL_US04, false, false);

    //WHEN
    TableMetadata table = session.getCluster()
        .getMetadata()
        .getKeyspace(KEYSPACE)
        .getTable("smartphones");
    List<ColumnMetadata> pk = table.getPartitionKey();

    //THEN
    assertThat(table).isNotNull();
    assertThat(pk.size()).isEqualTo(1);
    assertThat(pk.get(0).getName()).isEqualTo("id");
    assertThat(pk.get(0).getType()).isEqualTo(DataType.uuid());
  }

  @Test
  public void testRead() throws Exception {
    //GIVEN
    UUID id = UUID.randomUUID();

    //WHEN
    smartphonesRepository.read(id);

    //THEN
    verify(mapper, times(1)).get(id);
  }

  @Test
  @Ignore(value = "Tested in US15")
  public void testUpdate() throws Exception {
    //GIVEN
    UUID id = UUID.randomUUID();
    Smartphone smartphone = createSmartphone(id);

    //WHEN
    smartphonesRepository.update(id, smartphone);

    //THEN
    verify(mapper, times(1)).save(smartphone);
  }

  @Test
  @Ignore(value = "Tested in US15")
  public void testCreate() throws Exception {
    //GIVEN
    UUID id = UUID.randomUUID();
    Smartphone smartphone = createSmartphone(id);

    //WHEN
    Smartphone result = smartphonesRepository.create(smartphone);

    //THEN
    verify(mapper, times(1)).save(smartphone);
    assertThat(result).isEqualTo(smartphone);
  }
}
