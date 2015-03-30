package dk.itu.ssas.project.errorHandling;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author mikael
 */
public class ClientInputException extends RuntimeException{

	public ClientInputException(String message) {
		super(message);
	}

}
