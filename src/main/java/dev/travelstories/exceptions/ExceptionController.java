package dev.travelstories.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

   @ExceptionHandler(value = {RecordNotFoundException.class})
   public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(RecordNotFoundException exception, WebRequest webRequest) {

      ExceptionDetails exceptionDetails = new ExceptionDetails(
         new Date(), exception.getMessage(), webRequest.getDescription(false), HttpStatus.NOT_FOUND.value());

      return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
   }

}
