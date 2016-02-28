package fr.xebia.training.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import fr.xebia.training.domain.exceptions.ForbiddenException;
import fr.xebia.training.domain.model.Smartphone;

@Repository
public class SmartphonesRepository {

  public static final String DELETE_SMARTPHONE = "DELETE FROM smartphones WHERE id=? IF owner=?;";
  public static final String SELECT_ALL = "SELECT * FROM smartphones;";
  private Session session;

  private Mapper<Smartphone> mapper;

  private PreparedStatement deleteSmartphoneStmt;
  private PreparedStatement readAllSmartphoneStmt;

  @Autowired
  public SmartphonesRepository(Session session) {
    this.session = session;
    mapper = new MappingManager(session).mapper(Smartphone.class);
    prepareStatements();
  }

  private void prepareStatements() {
    deleteSmartphoneStmt = session.prepare(DELETE_SMARTPHONE);
    readAllSmartphoneStmt = session.prepare(SELECT_ALL);
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
  }

  public Smartphone create(Smartphone smartphone) {
    // US04: insertion d'un smartphone
    mapper.save(smartphone);
    return smartphone;
  }

  public List<Smartphone> readAll() {
    return null;
  }
}
