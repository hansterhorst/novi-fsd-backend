package dev.travelstories.exceptions;


import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

   @ExceptionHandler(value = {RecordNotFoundException.class})
   public ResponseEntity<ExceptionDetails> handleRecordNotFoundException(RecordNotFoundException exception, WebRequest webRequest) {

      ExceptionDetails exceptionDetails = new ExceptionDetails(
              new Date(), exception.getMessage(), webRequest.getDescription(false), HttpStatus.NOT_FOUND.value());

      return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
   }


   @ExceptionHandler(value = {BadRequestException.class})
   public ResponseEntity<Object> handleBadRequestException(BadRequestException exception, WebRequest webRequest) {

      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", new Date());
      body.put("status", HttpStatus.BAD_REQUEST.value());

      ArrayList<String> message = new ArrayList<>();
      message.add(exception.getMessage());
      body.put("message", message);

      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

   }


   @Override
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", new Date());
      body.put("status", status.value());

      // get all errors
      List<String> errors = exception.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.toList());

      body.put("message", errors);

      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
   }
}
