/**
 * A player specialized for Crazy Eights.
 * Uses EightsHand and EightsCard matching logic.
 */
public class EightsPlayer extends Player {
    
    private EightsHand hand;
    
    public EightsPlayer(String name) {
        super(name);
        // Replace the regular Hand with an EightsHand
        this.hand = new EightsHand(name);
    }
    
    @Override
    public EightsHand getHand() {
        return hand;
    }
    
    /**
     * Displays the player's name and score using EightsHand scoring.
     */
    @Override
    public void displayScore() {
        System.out.println(getName() + " has " + hand.scoreHand() + " points");
    }
}