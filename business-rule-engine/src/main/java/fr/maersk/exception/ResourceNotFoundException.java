package fr.maersk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6412585376786677530L;
	private static final String MESSAGE = "Resource not found for this id ";

	    public ResourceNotFoundException(Long customerId){
	        super(MESSAGE + customerId);
	    }
}
