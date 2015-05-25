package ball.game;

/**
 * Exception class to write out game specific exceptions. 
 * 
 * @author ranieth
 *
 */
public class BallGameException extends RuntimeException{
	
	/**
	 * Constructs the exception with the wanted message.
	 * 
	 * @param message the message will be printed out
	 */
	public BallGameException(String message){
		
		super(message);
		
	}

}
