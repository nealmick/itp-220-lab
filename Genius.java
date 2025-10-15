/**
 * A genius player strategy for Crazy Eights.
 * Extends Player and implements multiple strategic approaches.
 */
public class Genius extends Player {
    
    private String strategy;
    
    public Genius(String name) {
        super(name);
        this.strategy = "aggressive"; // default strategy
    }
    
    public Genius(String name, String strategy) {
        super(name);
        this.strategy = strategy;
    }
    
    /**
     * Overrides play to choose between different strategies.
     */
    @Override
    public Card play(Eights eights, Card prev) {
        Card card = null;
        
        switch (strategy) {
            case "conservative":
                card = conservativeSearch(prev);
                break;
            case "aggressive":
                card = aggressiveSearch(prev);
                break;
            case "adaptive":
                card = adaptiveSearch(prev);
                break;
            default:
                card = searchForMatch(prev); // fallback to original
        }
        
        if (card == null) {
            card = drawForMatch(eights, prev);
        }
        return card;
    }
    
    /**
     * Conservative strategy: Save high cards AND eights, play low cards first
     */
    private Card conservativeSearch(Card prev) {
        Hand hand = getHand();
        Card bestCard = null;
        int lowestRank = 14; // Start higher than King
        
        // Look for the LOWEST ranking match (except eights)
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            
            if (card.getRank() == 8) continue; // Never play eights conservatively
            
            if (cardMatches(card, prev)) {
                if (card.getRank() < lowestRank) {
                    bestCard = card;
                    lowestRank = card.getRank();
                }
            }
        }
        
        if (bestCard != null) {
            return removeCardFromHand(bestCard);
        }
        
        // Only play eight if absolutely no other choice
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (card.getRank() == 8) {
                return hand.popCard(i);
            }
        }
        
        return null;
    }
    
    /**
     * Aggressive strategy: Get rid of high penalty cards ASAP
     */
    private Card aggressiveSearch(Card prev) {
        Hand hand = getHand();
        Card bestCard = null;
        int highestRank = 0;
        
        // First pass: look for face cards and high ranks (but not eights)
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            
            if (card.getRank() == 8) continue; // Save eights for emergencies
            
            if (cardMatches(card, prev)) {
                // Prioritize face cards (11, 12, 13) and 10s
                if (card.getRank() >= 10) {
                    if (card.getRank() > highestRank) {
                        bestCard = card;
                        highestRank = card.getRank();
                    }
                } else if (bestCard == null || (bestCard.getRank() < 10 && card.getRank() > bestCard.getRank())) {
                    // If no face cards, pick highest available
                    bestCard = card;
                    highestRank = card.getRank();
                }
            }
        }
        
        if (bestCard != null) {
            return removeCardFromHand(bestCard);
        }
        
        // Second pass: play eights if needed
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (card.getRank() == 8) {
                return hand.popCard(i);
            }
        }
        
        return null;
    }
    
    /**
     * Adaptive strategy: Changes approach based on hand size
     */
    private Card adaptiveSearch(Card prev) {
        Hand hand = getHand();
        
        // Early game (many cards): play conservatively
        if (hand.size() >= 7) {
            return conservativeSearch(prev);
        }
        // Mid game (medium cards): play aggressively  
        else if (hand.size() >= 3) {
            return aggressiveSearch(prev);
        }
        // End game (few cards): play anything that works
        else {
            return desperateSearch(prev);
        }
    }
    
    /**
     * Desperate end-game strategy: play any match, including eights
     */
    private Card desperateSearch(Card prev) {
        Hand hand = getHand();
        
        // Play ANY match, even eights
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (cardMatches(card, prev)) {
                return hand.popCard(i);
            }
        }
        
        return null;
    }
    
    /**
     * Helper method to remove a specific card from hand.
     */
    private Card removeCardFromHand(Card targetCard) {
        Hand hand = getHand();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            if (card.getRank() == targetCard.getRank() && 
                card.getSuit() == targetCard.getSuit()) {
                return hand.popCard(i);
            }
        }
        return null;
    }
}