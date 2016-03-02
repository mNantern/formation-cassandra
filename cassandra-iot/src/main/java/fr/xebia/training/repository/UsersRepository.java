package fr.xebia.training.repository;

import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.xebia.training.domain.model.User;

@Repository
public class UsersRepository {

  private Session session;

  @Autowired
  public UsersRepository(Session session) {
    this.session = session;
  }

  public User insert(User user) {
    // US03: insertion d'un utilisateur
    return null;
  }

  public User read(String username) {
    // US03: lecture d'un utilisateur
    return null;
  }

  public void delete(String username) {
    // US03: suppression d'un utilisateur
  }

  public void update(String username, User user) {
    // US03: mise à jour d'un utilisateur
  }
}
