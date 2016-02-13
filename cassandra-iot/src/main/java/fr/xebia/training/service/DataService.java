package fr.xebia.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.xebia.extras.selma.Selma;
import fr.xebia.training.domain.mapping.InputDataMapper;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.InputData;
import fr.xebia.training.repository.DataRepository;

@Service
public class DataService {

  private DataRepository dataRepository;

  @Autowired
  public DataService(DataRepository dataRepository) {
    this.dataRepository = dataRepository;
  }

  public Collection<Data> insertInputData(Collection<InputData> inputDataCollection) {
    List<Data> dataCollection = convertToDataList(inputDataCollection);

    return dataCollection;
  }

  private List<Data> convertToDataList(Collection<InputData> inputDataCollection) {
    InputDataMapper mapper = Selma.builder(InputDataMapper.class).build();
    return inputDataCollection.stream()
        .map(inputData -> {
          Data data = mapper.asData(inputData);
          data.setId(UUID.randomUUID());
          return data;
        })
        .collect(Collectors.toList());
  }
}
