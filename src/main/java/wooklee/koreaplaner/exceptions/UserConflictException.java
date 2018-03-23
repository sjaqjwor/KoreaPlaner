package wooklee.koreaplaner.exceptions;

public class UserConflictException extends RuntimeException{
    public UserConflictException(String msg){
        super(msg);
    }
}
