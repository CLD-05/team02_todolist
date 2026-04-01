package com.team.todolist.common.exception;
 
import com.team.todolist.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
 
@RestControllerAdvice
public class GlobalExceptionHandler {
 

    // 커스텀 예외 클래스
 
    // 이미 존재하는 유저
    public static class DuplicateUserException extends RuntimeException {
        public DuplicateUserException(String message) {
            super(message);
        }
    }
 
    // 존재하지 않는 유저
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
 
    // 존재하지 않는 Todo
    public static class TodoNotFoundException extends RuntimeException {
        public TodoNotFoundException(String message) {
            super(message);
        }
    }
 
    
    // 유저 관련 예외 처리
 
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateUser(DuplicateUserException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)          // 409
                .body(ApiResponse.fail(e.getMessage()));
    }
 
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)          // 404
                .body(ApiResponse.fail(e.getMessage()));
    }
 
    // Todo 관련 예외 처리

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleTodoNotFound(TodoNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)          // 404
                .body(ApiResponse.fail(e.getMessage()));
    }
}