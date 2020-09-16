/**
 * 
 */
package fr.maersk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author supra
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RuleNotFoundException extends Exception{
	
	private static final long serialVersionUID = -6412585376786677530L;
	private static final String MESSAGE = "Rule not found for this orderType ";

	    public RuleNotFoundException(int i){
	        super(MESSAGE + i);
	    }

}
