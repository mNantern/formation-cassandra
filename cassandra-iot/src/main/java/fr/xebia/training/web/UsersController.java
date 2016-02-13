package fr.xebia.training.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.xebia.training.domain.model.User;

@RestController
@RequestMapping(value="/users")
public class UsersController {

  @RequestMapping(value="/{user}", method= RequestMethod.GET)
  public User getUser(@PathVariable Long user) {
    // TODO
    return null;
  }

  @RequestMapping(value="/{user}", method=RequestMethod.DELETE)
  public void deleteUser(@PathVariable Long user) {
    // ...
  }
}
