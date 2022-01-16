package dev.travelstories.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

   private final HttpStatus status;
   private final String message;

   public ValidationException(HttpStatus status, String message) {
      this.status = status;
      this.message = message;
   }

   public ValidationException(String message, HttpStatus status, String message1) {
      super(message);
      this.status = status;
      this.message = message1;
   }

   public HttpStatus getStatus() {
      return status;
   }

   @Override
   public String getMessage() {
      return message;
   }
}
