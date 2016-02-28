package fr.xebia.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import fr.xebia.extras.selma.Selma;
import fr.xebia.training.domain.mapping.InputDataMapper;
import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.InputData;
import fr.xebia.training.domain.model.ResultPage;
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
    dataRepository.insert(dataCollection);
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

  public ResultPage<Data> getData(UUID smartphoneId, Instant startDate, Instant endDate,
                                  String pagingState) {
    boolean startDateNull = (startDate == null);
    boolean endDateNull = (endDate == null);
    boolean allNull = startDateNull && endDateNull;

    if(allNull){
      return dataRepository.getBySmartphoneId(smartphoneId, pagingState);
    } else if(startDateNull){
      return dataRepository.getBySmartphoneIdEndDate(smartphoneId, endDate, pagingState);
    } else if (endDateNull) {
      return dataRepository.getBySmartphoneIdStartDate(smartphoneId, startDate, pagingState);
    } else {
      return dataRepository.getBySmartphoneIdStartEndDate(smartphoneId,
                                                          startDate,
                                                          endDate,
                                                          pagingState);
    }

  }
}
