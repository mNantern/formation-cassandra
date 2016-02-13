package fr.xebia.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import javax.validation.Valid;

import fr.xebia.training.domain.model.Data;
import fr.xebia.training.domain.model.InputData;
import fr.xebia.training.service.DataService;

@RestController
@RequestMapping("/data")
public class DataController {

  private DataService dataService;

  @Autowired
  public DataController(DataService dataService) {
    this.dataService = dataService;
  }

  @RequestMapping(value={"/", ""}, method= RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public Collection<Data> createData(@RequestBody @Valid Collection<InputData> inputData) {
    Collection<Data> data = dataService.insertInputData(inputData);
    data.stream().forEach(System.out::println);
    return data;
  }
}