package fr.xebia.training.repository;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;

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
    //US04: creation d'un mapper
  }

  public SmartphonesRepository(Session session, Mapper<Smartphone> mapper) {
    this.session = session;
    this.mapper = mapper;
  }

  public Smartphone read(UUID id) {
    // US04: lecture d'un smartphone
    return null;
  }

  public void delete(UUID id) {
    // US04: suppression d'un smartphone
  }

  public void update(UUID id, Smartphone smartphone) {
    // US04: mise à jour d'un smartphone
  }

  public Smartphone create(Smartphone smartphone) {
    // US04: insertion d'un smartphone
    return null;
  }
}
