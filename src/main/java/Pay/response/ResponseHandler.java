package Pay.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class ResponseHandler {
 private int code;
 private String message;
 private Object data;
 
public ResponseHandler(int code, String message, Object data) {
	super();
	this.code = code;
	this.message = message;
	this.data = data;
}
public ResponseHandler(int code, String message) {
	super();
	this.code = code;
	this.message = message;
} 
}
