public class Card {

    private final int rank;
    private final int suit;

    public Card() {
        rank = 0;
        suit = 0;
    } // end-default constructor

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    } // end-constructor

    public static final String[] RANKS = {
        null, "Ace", "2", "3", "4", "5", "6", "7",
        "8", "9", "10", "Jack", "Queen", "King"};

    public static final String[] SUITS = {
        "Clubs", "Diamonds", "Hearts", "Spades"};

    public String toString() {
            return RANKS[this.rank] + " of " + SUITS[this.suit];
    } // end-toString

    public boolean equals(Card that) {
        return this.rank == that.rank
               && this.suit == that.suit;
    } // end-equals

    public int compareTo(Card that) {
        int thisRank, thatRank;
        thisRank = this.getRank();
        thatRank = that.getRank();
        
        // Make Aces high - convert rank 1 to 14 (higher than King which is 13)
        if (thisRank == 1) thisRank = 14;
        if (thatRank == 1) thatRank = 14;
    
        // Compare by suit first
        if (this.suit < that.suit) {
            return -1;
        } 
        if (this.suit > that.suit) {
            return 1;
        }
        
        // If same suit, compare by rank
        if (thisRank < thatRank) {
            return -1;
        }
        if (thisRank > thatRank) {
            return 1;
        }
        
        // Cards are equal
        return 0;
    } // end-compareTo

    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }

} // end-class Card
