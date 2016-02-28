package fr.xebia.training.repository;

import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.xebia.training.domain.exceptions.ForbiddenException;
import fr.xebia.training.domain.model.ResultPage;
import fr.xebia.training.domain.model.Smartphone;

@Repository
public class SmartphonesRepository {

  public static final String DELETE_SMARTPHONE = "DELETE FROM smartphones WHERE id=? IF owner=?;";
  public static final String SELECT_ALL = "SELECT * FROM smartphones;";
  private static final int FETCH_SIZE = 20;
  public static final String SELECT_BY_CONSTRUCTOR =
      "SELECT * FROM smartphones_by_constructor WHERE constructor = ?;";
  public static final String INSERT_SMARTPHONES_BY_CONSTRUCTOR =
      "INSERT INTO smartphones_by_constructor (id, name, constructor, model, owner) VALUES (?, ?, ?, ?, ?)";
  private Session session;

  private Mapper<Smartphone> mapper;

  private PreparedStatement deleteSmartphoneStmt;
  private PreparedStatement readAllSmartphoneStmt;
  private PreparedStatement readByConstructorStmt;
  private PreparedStatement saveByConstructorStmt;

  @Autowired
  public SmartphonesRepository(Session session) {
    this.session = session;
    mapper = new MappingManager(session).mapper(Smartphone.class);
    prepareStatements();
  }

  private void prepareStatements() {
    deleteSmartphoneStmt = session.prepare(DELETE_SMARTPHONE);
    readAllSmartphoneStmt = session.prepare(SELECT_ALL);
    readByConstructorStmt = session.prepare(SELECT_BY_CONSTRUCTOR);
    saveByConstructorStmt = session.prepare(INSERT_SMARTPHONES_BY_CONSTRUCTOR);
  }

  public Smartphone read(UUID id) {
    // US04: lecture d'un smartphone
    return mapper.get(id);
  }

  public void delete(UUID id, String userId) {
    // US04: suppression d'un smartphone
    // US08: seul le propriétaire peut supprimer le smartphone
    ResultSet result = session.execute(deleteSmartphoneStmt.bind(id, userId));
    if(!result.wasApplied()){
      throw new ForbiddenException();
    }
  }

  public void update(UUID id, Smartphone smartphone) {
    // US04: mise à jour d'un smartphone
    mapper.save(smartphone);
    //US15: insertion dans la table de recherche par constructor
    session.execute(saveByConstructorStmt.bind(smartphone.getId(),
                                               smartphone.getName(),
                                               smartphone.getConstructor(),
                                               smartphone.getModel(),
                                               smartphone.getOwner()));
  }

  public Smartphone create(Smartphone smartphone) {
    // US04: insertion d'un smartphone
    mapper.save(smartphone);
    //US15: insertion dans la table de recherche par constructor
    session.execute(saveByConstructorStmt.bind(smartphone.getId(),
                                               smartphone.getName(),
                                               smartphone.getConstructor(),
                                               smartphone.getModel(),
                                               smartphone.getOwner()));
    return smartphone;
  }

  public ResultPage<Smartphone> readAll(String pagingState) {
    //US14: lister de manière paginée l'ensemble des smartphones
    Statement statement = readAllSmartphoneStmt
        .bind()
        .setFetchSize(FETCH_SIZE);

    if( pagingState != null){
      statement.setPagingState(PagingState.fromString(pagingState));
    }

    ResultSet results = session.execute(statement);
    int nbrResult = results.getAvailableWithoutFetching();
    List<Smartphone> output = new ArrayList<>();
    for (int i = 0; i < nbrResult; i++) {
      Smartphone smartphone = mapper.map(results).one();
      output.add(smartphone);
    }

    PagingState nextPagingState = results.getExecutionInfo().getPagingState();
    if(nextPagingState != null){
      return new ResultPage<>(output, nextPagingState.toString());
    } else {
      return new ResultPage<>(output, null);
    }
  }

  public List<Smartphone> readByConstructor(String constructor) {
    //US15 : Recherche par nom de constructeur
    List<Smartphone> output = new ArrayList<>();
    Statement statement = readByConstructorStmt.bind(constructor);
    Result<Smartphone> results = mapper.map(session.execute(statement));
    results.forEach(smartphone -> output.add(smartphone));
    return output;
  }
}
