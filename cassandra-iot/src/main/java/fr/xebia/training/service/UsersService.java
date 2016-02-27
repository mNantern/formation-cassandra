package fr.xebia.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.xebia.training.domain.model.User;
import fr.xebia.training.repository.UsersRepository;

@Service
public class UsersService {

  private UsersRepository usersRepository;

  @Autowired
  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public User create(User user) {
    return usersRepository.insert(user);
  }

  public void update(String username, User user) {
    usersRepository.update(username, user);
  }

  public void delete(String username) {
    usersRepository.delete(username);
  }

  public User read(String username) {
    return usersRepository.read(username);
  }
}
