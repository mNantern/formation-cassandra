package fr.xebia.training.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Forbidden !")
public class ForbiddenException extends RuntimeException{

}
