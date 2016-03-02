package fr.xebia.training.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import fr.xebia.training.domain.model.Smartphone;

@Repository
public class SmartphonesRepository {

  //US08: seul le propriétaire peut supprimer le smartphone
  public static final String DELETE_SMARTPHONE = "DELETE FROM smartphones WHERE id=?;";
  private Session session;

  private Mapper<Smartphone> mapper;

  private PreparedStatement deleteSmartphoneStmt;

  @Autowired
  public SmartphonesRepository(Session session) {
    this.session = session;
    mapper = new MappingManager(session).mapper(Smartphone.class);
    prepareStatements();
  }

  private void prepareStatements() {
    deleteSmartphoneStmt = session.prepare(DELETE_SMARTPHONE);
  }

  public SmartphonesRepository(Session session, Mapper<Smartphone> mapper) {
    this.session = session;
    this.mapper = mapper;
  }

  public Smartphone read(UUID id) {
    // US04: lecture d'un smartphone
    return mapper.get(id);
  }

  public void delete(UUID id, String userId) {
    // US04: suppression d'un smartphone
    ResultSet result = session.execute(deleteSmartphoneStmt.bind(id, userId));
    // US08: seul le propriétaire peut supprimer le smartphone
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
}
