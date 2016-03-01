package fr.xebia.training.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.InputData;
import fr.xebia.training.domain.model.ResultPage;
import fr.xebia.training.service.DataService;

@RestController
@RequestMapping("/data")
public class DataController {

  private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

  private DataService dataService;

  @Autowired
  public DataController(DataService dataService) {
    this.dataService = dataService;
  }

  @RequestMapping(value={"/", ""}, method= RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public List<Data> createData(@RequestBody @Valid List<InputData> inputData) {
    List<Data> data = dataService.insertInputData(inputData);
    data.stream().forEach(d -> LOGGER.info(d.toString()));
    return data;
  }

  @RequestMapping(value={"/", ""}, method= RequestMethod.GET)
  public ResultPage<Data> getData(@RequestParam UUID smartphoneId,
                                  @RequestParam(required = false) Instant startDate,
                                  @RequestParam(required = false) Instant endDate,
                                  @RequestParam(required = false) String pagingState){
    return dataService.getData(smartphoneId, startDate, endDate, pagingState);
  }

}