package wooklee.koreaplaner.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import wooklee.koreaplaner.controllers.responses.ScheduleResponse;
import wooklee.koreaplaner.controllers.responses.UserResponse;
import wooklee.koreaplaner.controllers.responses.status.StatusCode;
import wooklee.koreaplaner.exceptions.ScheduleNotFoundException;
import wooklee.koreaplaner.exceptions.UserConflictException;
import wooklee.koreaplaner.exceptions.UserNotFoundException;
import wooklee.koreaplaner.utiles.ErrorStrings;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<?> scheduleNotFound(ScheduleNotFoundException nsae) {
        ScheduleResponse sr =ScheduleResponse.builder().status(StatusCode.SCHEDULENOTFOUND).build();
        return new ResponseEntity<>(sr, HttpStatus.OK);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserResponse> userNotFoundException(UserNotFoundException unfe) {
        UserResponse ur = UserResponse.builder().msg(ErrorStrings.USER_NOT_FOUND).status(StatusCode.USERNOTFOUND).build();
        return new ResponseEntity<UserResponse>(ur, HttpStatus.OK);
    }

    @ExceptionHandler(UserConflictException.class)
    public ResponseEntity<?> userConflictException(UserConflictException uce) {
        UserResponse ur = UserResponse.builder().msg(uce.getMessage()).status(StatusCode.CONFLICATE).build();
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> awsError(IOException i) {
        UserResponse ur = UserResponse.builder().status(StatusCode.AWSERROR).build();
        return new ResponseEntity<>(ur, HttpStatus.OK);
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<?> noSuchAlgp(NoSuchAlgorithmException nsae) {
        UserResponse ur = UserResponse.builder().status(StatusCode.ENCRIPTORError).build();
        return new ResponseEntity<>(ur,HttpStatus.OK);
    }
}
