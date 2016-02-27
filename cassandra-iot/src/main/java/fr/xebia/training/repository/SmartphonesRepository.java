package fr.xebia.training.repository;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import fr.xebia.training.domain.model.Smartphone;

@Repository
public class SmartphonesRepository {

  private Session session;

  private Mapper<Smartphone> mapper;

  @Autowired
  public SmartphonesRepository(Session session) {
    this.session = session;
    mapper = new MappingManager(session).mapper(Smartphone.class);
  }

  public Smartphone read(UUID id) {
    // US04: lecture d'un smartphone
    return mapper.get(id);
  }

  public void delete(UUID id) {
    // US04: suppression d'un smartphone
    mapper.delete(id);
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
