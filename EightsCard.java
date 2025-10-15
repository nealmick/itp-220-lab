/**
 * A Card specialized for Crazy Eights game rules.
 * Extends Card and adds match() and scoreCard() methods.
 */
public class EightsCard extends Card {
    
    /**
     * Constructs an EightsCard with the given rank and suit.
     */
    public EightsCard(int rank, int suit) {
        super(rank, suit);
    }
    
    /**
     * Checks whether this card matches another card according to Crazy Eights rules.
     * Cards match if they have the same suit, same rank, or if this card is an 8.
     */
    public boolean match(EightsCard other) {
        return this.getSuit() == other.getSuit()
            || this.getRank() == other.getRank()
            || this.getRank() == 8;
    }
    
    /**
     * Returns the penalty score for this card in Crazy Eights.
     * Eights are worth 20 points, face cards (J, Q, K) are worth 10 points,
     * all other cards are worth their rank value.
     */
    public int scoreCard() {
        int rank = this.getRank();
        if (rank == 8) {
            return 20;
        } else if (rank > 10) { // Jack, Queen, King
            return 10;
        } else {
            return rank;
        }
    }
}