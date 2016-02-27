package fr.xebia.training.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import fr.xebia.training.domain.model.Data;

@Repository
public class DataRepository {

  public static final String INSERT_DATA =
      "INSERT INTO data (id, smartphone_id, event_time, type, value) VALUES (?, ?, ?, ?, ?); ";

  private Session session;

  private PreparedStatement insertDataStmt;

  @Autowired
  public DataRepository(Session session) {
    this.session = session;
    prepareStatements();
  }

  private void prepareStatements() {
    insertDataStmt = session.prepare(INSERT_DATA);
  }

  public void insert(List<Data> dataCollection){
    // US01 : insérer les données dans Cassandra
    dataCollection.stream().forEach( data -> {
      session.execute(insertDataStmt.bind(data.getId(),
                                          data.getSmartphoneId(),
                                          Date.from(data.getEventTime()),
                                          data.getType().toString(),
                                          data.getValue()));
    });
  }

}
