/**
 * A smarter player strategy for Crazy Eights.
 * Extends Player and overrides searchForMatch to implement better strategy.
 */
public class SmartPlayer extends Player {
    
    public SmartPlayer(String name) {
        super(name);
    }
    
    /**
     * Overrides searchForMatch to implement a smarter strategy:
     * 1. Avoid playing eights unless necessary (save them as wild cards)
     * 2. Play highest-ranking cards first to minimize penalty points
     * 3. Prefer face cards (Jacks, Queens, Kings) since they're worth 10 points
     */
    @Override
    public Card searchForMatch(Card prev) {
        Hand hand = getHand();
        Card bestCard = null;
        int bestRank = 0;
        boolean foundNonEight = false;
        
        // First pass: look for non-eight matches, preferring high ranks
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            
            // Skip eights for now (save as wild cards)
            if (card.getRank() == 8) {
                continue;
            }
            
            if (cardMatches(card, prev)) {
                foundNonEight = true;
                
                // Prefer higher-ranking cards to minimize penalty points
                if (card.getRank() > bestRank) {
                    bestCard = card;
                    bestRank = card.getRank();
                }
            }
        }
        
        // If we found a non-eight match, play the highest one
        if (foundNonEight && bestCard != null) {
            // Find the card's index and remove it
            for (int i = 0; i < hand.size(); i++) {
                Card card = hand.getCard(i);
                if (card.getRank() == bestCard.getRank() && 
                    card.getSuit() == bestCard.getSuit()) {
                    return hand.popCard(i);
                }
            }
        }
        
        // Second pass: if no other matches, look for eights
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (card.getRank() == 8) {
                return hand.popCard(i);  // Play the eight as last resort
            }
        }
        
        return null;  // No matches found
    }
}