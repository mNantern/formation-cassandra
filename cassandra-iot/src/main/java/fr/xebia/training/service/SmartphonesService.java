package fr.xebia.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.repository.SmartphonesRepository;

@Service
public class SmartphonesService {

  private SmartphonesRepository smartphonesRepository;

  @Autowired
  public SmartphonesService(SmartphonesRepository smartphonesRepository) {
    this.smartphonesRepository = smartphonesRepository;
  }

  public Smartphone read(UUID id) {
    return smartphonesRepository.read(id);
  }

  public void delete(UUID id, UUID userId) {
    smartphonesRepository.delete(id, userId);
  }

  public void update(UUID id, Smartphone smartphone) {
    smartphone.setId(id);
    smartphonesRepository.update(id, smartphone);
  }

  public Smartphone create(Smartphone smartphone) {
    smartphone.setId(UUID.randomUUID());
    return smartphonesRepository.create(smartphone);
  }
}
