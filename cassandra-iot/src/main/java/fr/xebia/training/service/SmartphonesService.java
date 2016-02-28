package fr.xebia.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.SmartphonesRepository;
import fr.xebia.training.repository.UsersRepository;

@Service
public class SmartphonesService {

  private SmartphonesRepository smartphonesRepository;
  private UsersRepository usersRepository;

  @Autowired
  public SmartphonesService(SmartphonesRepository smartphonesRepository,
                            UsersRepository usersRepository) {
    this.smartphonesRepository = smartphonesRepository;
    this.usersRepository = usersRepository;
  }

  public Smartphone read(UUID id) {
    return smartphonesRepository.read(id);
  }

  public void delete(UUID id, String userId) {
    smartphonesRepository.delete(id, userId);
  }

  public void update(UUID id, Smartphone smartphone) {
    smartphone.setId(id);
    smartphonesRepository.update(id, smartphone);
  }

  public Smartphone create(Smartphone smartphone) {
    smartphone.setId(UUID.randomUUID());
    smartphonesRepository.create(smartphone);
    //US09 : ajouter le smartphone créé dans l'utilisateur correspondant
    usersRepository.addSmartphone(smartphone.getOwner(), smartphone.getId(), smartphone.getName());
    return smartphone;
  }
}
