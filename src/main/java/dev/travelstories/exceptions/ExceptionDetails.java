package dev.travelstories.exceptions;

import java.util.Date;

public class ExceptionDetails {

   private final Date timestamp;
   private final String message;
   private final String details;
   private final Integer status;


   public ExceptionDetails(Date timestamp, String message, String details, Integer status) {
      this.timestamp = timestamp;
      this.message = message;
      this.details = details;
      this.status = status;
   }


   public Date getTimestamp() {
      return timestamp;
   }

   public String getMessage() {
      return message;
   }

   public String getDetails() {
      return details;
   }

   public Integer getStatus() {
      return status;
   }

}
