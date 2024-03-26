package com.example.communityoftravellers2.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class NoCriteriaToSearchException extends RuntimeException{
    private String message;
    private String status;

    public NoCriteriaToSearchException(String message, HttpStatus status) {
        this.message = message;
        this.status = String.valueOf(status);
    }
}
