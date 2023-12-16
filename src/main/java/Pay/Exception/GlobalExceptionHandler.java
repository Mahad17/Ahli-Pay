package Pay.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        return new ResponseEntity<String>( "Not Found", HttpStatus.BAD_REQUEST);
        }
    
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException elementException){
//        return new ResponseEntity<String>( "no such id found in database", HttpStatus.NOT_FOUND);
//    }
    
    @ExceptionHandler(org.springframework.web.client.HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<String> internalServerError(ResourceNotFoundException ex){
        return new ResponseEntity<String>( "Not Found", HttpStatus.BAD_REQUEST);
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> resp=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError) error).getField();
            String message=error.getDefaultMessage();
            resp.put(fieldName,message);
        });
        
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<Integer,Integer>>  handleMethodArgNotValidException(MethodArgumentNotValidException ex){
//        Map<String,String> resp=new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error)->{
//            String fieldName=((FieldError) error).getField();
//            String message=error.getDefaultMessage();
//            resp.put(fieldName,message);
//        });
//        
//        return new ResponseEntity<Map<Integer,Integer>>(HttpStatus.BAD_REQUEST);
//    }

}
