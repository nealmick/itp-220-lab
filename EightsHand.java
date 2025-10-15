/**
 * A Hand specialized for Crazy Eights game.
 * Extends Hand and adds scoreHand() method for calculating penalty points.
 */
public class EightsHand extends Hand {
    
    /**
     * Constructs an empty EightsHand with the given label.
     */
    public EightsHand(String label) {
        super(label);
    }
    
    /**
     * Calculates the total penalty score for all cards in this hand.
     * Uses the EightsCard scoring rules if cards are EightsCards,
     * otherwise falls back to basic scoring.
     */
    public int scoreHand() {
        int total = 0;
        
        for (int i = 0; i < size(); i++) {
            Card card = getCard(i);
            
            if (card instanceof EightsCard) {
                // Use the specialized EightsCard scoring
                EightsCard eightsCard = (EightsCard) card;
                total += eightsCard.scoreCard();
            } else {
                // Fall back to basic scoring for regular Cards
                int rank = card.getRank();
                if (rank == 8) {
                    total += 20;
                } else if (rank > 10) {
                    total += 10;
                } else {
                    total += rank;
                }
            }
        }
        
        return total;
    }
}