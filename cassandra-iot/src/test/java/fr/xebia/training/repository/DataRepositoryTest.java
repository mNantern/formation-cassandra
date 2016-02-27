package fr.xebia.training.repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Type;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataRepositoryTest {

  private DataRepository dataRepository;

  @Mock
  private Session session;

  @Mock
  PreparedStatement insertDataStmt;

  @Before
  public void setup() {
    when(session.prepare(anyString())).thenReturn(insertDataStmt);
    dataRepository = new DataRepository(session);
  }

  @Test
  public void testInsertUS01() throws Exception {
    //Given
    List<Data> dataCollection = createDataCollection();
    when(session.execute(any(Statement.class))).thenReturn(null);

    //When
    dataRepository.insert(dataCollection);

    //Then
    verify(session, times(2)).execute(any(BoundStatement.class));
  }

  private List<Data> createDataCollection() {
    List<Data> dataCollection = new ArrayList<>();
    UUID smartphoneId = UUID.randomUUID();

    Data data1 = new Data.Builder()
        .id(UUID.randomUUID())
        .smartphoneId(smartphoneId)
        .type(Type.BRIGHTNESS)
        .eventTime(Instant.now())
        .value("66.0")
        .build();

    Data data2 = new Data.Builder()
        .id(UUID.randomUUID())
        .smartphoneId(smartphoneId)
        .type(Type.ACCELEROMETER)
        .eventTime(Instant.now())
        .value("-1.0591182;2.6281822;9.728196")
        .build();

    dataCollection.add(data1);
    dataCollection.add(data2);

    return dataCollection;
  }
}