package fr.xebia.training.repository;

import com.datastax.driver.core.PagingState;
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
import fr.xebia.training.domain.model.ResultPage;
import fr.xebia.training.domain.model.Type;

import static com.datastax.driver.core.querybuilder.QueryBuilder.desc;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

@Repository
public class DataRepository {

  //US13: add a TTL
  public static final String INSERT_DATA =
      "INSERT INTO data (id, smartphone_id, event_time, type, value) VALUES (?, ?, ?, ?, ?) "
      + "USING TTL 7776000;";
  public static final String SELECT_DATA_START_DATE =
      "select * from data "
      + "WHERE smartphone_id=? AND event_time > ? "
      + "ORDER BY event_time DESC;";
  public static final String SELECT_DATA_END_DATE =
      "select * from data "
      + "WHERE smartphone_id=? AND event_time <= ? "
      + "ORDER BY event_time DESC;";
  public static final String SELECT_DATA_START_END_DATE =
      "select * from data "
      + "WHERE smartphone_id=? AND event_time > ? AND event_time <= ? "
      + "ORDER BY event_time DESC;";
  public static final int FETCH_SIZE = 20;
  public static final String UPDATE_COUNTER =
      "UPDATE number_data_by_smartphones SET data = data + 1 WHERE smartphone_id= ?;";

  private Session session;

  private PreparedStatement insertDataStmt;
  private PreparedStatement selectDataStartDateStmt;
  private PreparedStatement selectDataEndDateStmt;
  private PreparedStatement selectDataStartEndDateStmt;
  private PreparedStatement updateCounterStmt;

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
    updateCounterStmt = session.prepare(UPDATE_COUNTER);
  }

  public void insert(List<Data> dataCollection){
    // US01 : insérer les données dans Cassandra
    dataCollection.stream().forEach( data -> {
      session.execute(insertDataStmt.bind(data.getId(),
                                          data.getSmartphoneId(),
                                          Date.from(data.getEventTime()),
                                          data.getType().toString(),
                                          data.getValue()));
      //US15 : compter le nombre de données par smartphone
      session.execute(updateCounterStmt.bind(data.getSmartphoneId()));
    });
  }

  public ResultPage<Data> getBySmartphoneId(UUID smartphoneId, String pagingState) {
    // US02: récupérer l'ensemble des données liées à un smartphone
    Statement select = QueryBuilder
        .select()
        .all()
        .from("data")
        .where(eq("smartphone_id",smartphoneId))
        .orderBy(desc("event_time"))
        .setFetchSize(FETCH_SIZE)
        .setPagingState(getPagingState(pagingState));
    //US07: trier par date
    //US12: paginer les résultats

    return getCollectionFromResultSet(session.execute(select));
  }

  private PagingState getPagingState(String pagingState) {
    PagingState ps;
    if(pagingState == null){
      ps = null;
    } else {
      ps = PagingState.fromString(pagingState);
    }
    return ps;
  }

  private ResultPage<Data> getCollectionFromResultSet(ResultSet results) {
    int nbrResult = results.getAvailableWithoutFetching();
    List<Data> output = new ArrayList<>();
    for (int i = 0; i < nbrResult; i++) {
      Row row = results.one();
      output.add(rowToData(row));
    }

    PagingState pagingState = results.getExecutionInfo().getPagingState();
    if(pagingState != null){
      return new ResultPage<>(output, pagingState.toString());
    } else {
      return new ResultPage<>(output, null);
    }
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

  public ResultPage<Data> getBySmartphoneIdStartDate(UUID smartphoneId, Instant startDate,
                                                     String pagingState) {
    //US06 : recherche avec date début
    //US12: paginer les résultats
    Statement statement = selectDataStartDateStmt
        .bind(smartphoneId, Date.from(startDate))
        .setFetchSize(FETCH_SIZE)
        .setPagingState(getPagingState(pagingState));

    return getCollectionFromResultSet(session.execute(statement));
  }

  public ResultPage<Data> getBySmartphoneIdEndDate(UUID smartphoneId, Instant endDate,
                                                   String pagingState) {
    //US06 : recherche avec date de fin
    //US12: paginer les résultats
    Statement statement = selectDataEndDateStmt
        .bind(smartphoneId, Date.from(endDate))
        .setFetchSize(FETCH_SIZE)
        .setPagingState(getPagingState(pagingState));

    return getCollectionFromResultSet(session.execute(statement));
  }

  public ResultPage<Data> getBySmartphoneIdStartEndDate(UUID smartphoneId, Instant startDate,
                                                        Instant endDate, String pagingState) {
    //US06 : recherche avec date début + dateFin
    //US12: paginer les résultats
    Statement statement = selectDataStartEndDateStmt
        .bind(smartphoneId, Date.from(startDate), Date.from(endDate))
        .setFetchSize(FETCH_SIZE)
        .setPagingState(getPagingState(pagingState));

    return getCollectionFromResultSet(session.execute(statement));
  }
}
