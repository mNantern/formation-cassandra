package fr.xebia.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import javax.validation.Valid;

import fr.xebia.training.domain.exceptions.NotFoundException;
import fr.xebia.training.domain.model.Smartphone;
import fr.xebia.training.service.SmartphonesService;

@RestController
@RequestMapping(value="/smartphones")
public class SmartphonesController {

  private SmartphonesService smartphoneService;

  @Autowired
  public SmartphonesController(SmartphonesService smartphoneService) {
    this.smartphoneService = smartphoneService;
  }

  @RequestMapping(value={"/{id}"}, method= RequestMethod.GET)
  public Smartphone getUser(@PathVariable UUID id) {
    Smartphone smartphone = smartphoneService.read(id);
    if (smartphone == null){
      throw new NotFoundException();
    }
    return smartphone;
  }

  @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
  public void deleteUser(@PathVariable UUID id,
                         @RequestParam UUID userId) {
    smartphoneService.delete(id,userId);
  }

  @RequestMapping(value="/{id}", method=RequestMethod.PUT)
  public void updateUser(@PathVariable UUID id, @RequestBody @Valid Smartphone smartphone) {
    smartphoneService.update(id,smartphone);
  }

  @RequestMapping(value={"/",""}, method=RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public Smartphone createUser(@RequestBody @Valid Smartphone smartphone) {
    return smartphoneService.create(smartphone);
  }

}