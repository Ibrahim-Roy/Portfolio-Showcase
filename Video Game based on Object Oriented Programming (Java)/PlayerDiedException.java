/*Exception that's throwed if the player dies and
 the game needs to come to a halt*/
public class PlayerDiedException extends Exception{
    public PlayerDiedException(String message){
        super(message);
    }
}