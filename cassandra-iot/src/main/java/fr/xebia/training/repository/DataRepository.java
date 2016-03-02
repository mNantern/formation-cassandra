package fr.xebia.training.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Type;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.google.common.base.Preconditions.checkNotNull;

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

  public List<Data> getBySmartphoneId(UUID smartphoneId) {
    checkNotNull(smartphoneId);
    // US02: récupérer l'ensemble des données liées à un smartphone
    Statement select = QueryBuilder
        .select()
        .all()
        .from("data")
        .where(eq("smartphone_id", smartphoneId));
    //US07: trier par date

    return getDataListFromResultSet(session.execute(select));
  }

  private List<Data> getDataListFromResultSet(ResultSet results) {
    List<Data> output = new ArrayList<>();
    results.forEach(row -> output.add(rowToData(row)));
    return output;
  }

  private Data rowToData(Row row){
    return new Data.Builder()
        .id(row.getUUID("id"))
        .smartphoneId(row.getUUID("smartphone_id"))
        .eventTime(row.getDate("event_time").toInstant())
        .type(Type.valueOf(row.getString("type")))
        .value(row.getString("value"))
        .build();
  }

  public List<Data> getBySmartphoneIdStartDate(UUID smartphoneId, Instant startDate) {
    //US06 : recherche avec date début
    return null;
  }

  public List<Data> getBySmartphoneIdEndDate(UUID smartphoneId, Instant endDate) {
    //US06 : recherche avec date de fin
    return null;
  }

  public List<Data> getBySmartphoneIdStartEndDate(UUID smartphoneId, Instant startDate,
                                                  Instant endDate) {
    //US06 : recherche avec date début + dateFin
    return null;
  }
}
