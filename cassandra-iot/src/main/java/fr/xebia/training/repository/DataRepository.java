package fr.xebia.training.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.Type;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

@Repository
public class DataRepository {

  public static final String INSERT_DATA =
      "INSERT INTO data (id, smartphone_id, event_time, type, value) VALUES (?, ?, ?, ?, ?); ";
  public static final String SELECT_DATA_START_DATE =
      "select * from data WHERE smartphone_id=? AND event_time > ?;";
  public static final String SELECT_DATA_END_DATE =
      "select * from data WHERE smartphone_id=? AND event_time <= ?;";
  public static final String SELECT_DATA_START_END_DATE =
      "select * from data WHERE smartphone_id=? AND event_time > ? AND event_time <= ?;";

  private Session session;

  private PreparedStatement insertDataStmt;
  private PreparedStatement selectDataStartDateStmt;
  private PreparedStatement selectDataEndDateStmt;
  private PreparedStatement selectDataStartEndDateStmt;

  @Autowired
  public DataRepository(Session session) {
    this.session = session;
    prepareStatements();
  }

  private void prepareStatements() {
    insertDataStmt = session.prepare(INSERT_DATA);
    selectDataStartDateStmt = session.prepare(SELECT_DATA_START_DATE);
    selectDataEndDateStmt = session.prepare(SELECT_DATA_END_DATE);
    selectDataStartEndDateStmt = session.prepare(SELECT_DATA_START_END_DATE);
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

  public Collection<Data> getBySmartphoneId(UUID smartphoneId) {
    // US02: récupérer l'ensemble des données liées à un smartphone
    Statement select = QueryBuilder
        .select()
        .all()
        .from("data")
        .where(eq("smartphone_id",smartphoneId));

    List<Row> results = session.execute(select).all();

    return getCollectionFromRows(results);
  }

  private List<Data> getCollectionFromRows(List<Row> results) {
    return results.stream()
        .map(this::rowToData)
        .collect(Collectors.toList());
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

  public Collection<Data> getBySmartphoneIdStartDate(UUID smartphoneId, Instant startDate) {
    //US06 : recherche avec date début
    List<Row> results =
        session.execute(selectDataStartDateStmt.bind(smartphoneId, Date.from(startDate))).all();
    return getCollectionFromRows(results);
  }

  public Collection<Data> getBySmartphoneIdEndDate(UUID smartphoneId, Instant endDate) {
    //US06 : recherche avec date de fin
    List<Row> results =
        session.execute(selectDataEndDateStmt.bind(smartphoneId, Date.from(endDate))).all();
    return getCollectionFromRows(results);
  }

  public Collection<Data> getBySmartphoneIdStartEndDate(UUID smartphoneId, Instant startDate,
                                                        Instant endDate) {
    //US06 : recherche avec date début + dateFin
    List<Row> results = session.execute(
            selectDataStartEndDateStmt.bind(smartphoneId, Date.from(startDate), Date.from(endDate)))
            .all();
    return getCollectionFromRows(results);
  }
}
