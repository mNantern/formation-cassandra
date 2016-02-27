package fr.xebia.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import fr.xebia.training.domain.model.User;
import fr.xebia.training.service.UsersService;

@RestController
@RequestMapping(value="/users")
public class UsersController {

  private UsersService usersService;

  @Autowired
  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @RequestMapping(value={"/{username:.+}"}, method= RequestMethod.GET)
  public User getUser(@PathVariable String username) {
    return usersService.read(username);
  }

  @RequestMapping(value="/{username:.+}", method=RequestMethod.DELETE)
  public void deleteUser(@PathVariable String username) {
    usersService.delete(username);
  }

  @RequestMapping(value="/{username:.+}", method=RequestMethod.PUT)
  public void updateUser(@PathVariable String username, @RequestBody @Valid User user) {
    usersService.update(username,user);
  }

  @RequestMapping(value={"/",""}, method=RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public User createUser(@RequestBody @Valid User user) {
    return usersService.create(user);
  }

}