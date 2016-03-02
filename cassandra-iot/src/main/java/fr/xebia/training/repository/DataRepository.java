package fr.xebia.training.repository;

import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import fr.xebia.training.domain.model.Data;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
public class DataRepository {

  private Session session;

  @Autowired
  public DataRepository(Session session) {
    this.session = session;
  }

  public void insert(List<Data> dataCollection){
    // US01 : insérer les données dans Cassandra

  }

  public List<Data> getBySmartphoneId(UUID smartphoneId) {
    checkNotNull(smartphoneId);
    // US02: récupérer l'ensemble des données liées à un smartphone
    return null;
  }
}
