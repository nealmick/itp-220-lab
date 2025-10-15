import java.util.Scanner;
import java.util.ArrayList;

/**
 * Simulates a game of Crazy Eights with support for multiple players.
 * Now uses EightsHand for specialized scoring.
 */
public class Eights {
    private ArrayList<Player> players;
    private EightsHand drawPile;
    private EightsHand discardPile;
    private Scanner in;
    private int currentPlayerIndex;
    private int numPlayers = 3; // Default to 3 players
    
    /**
     * Sets the number of players for the next game.
     */
    public void setNumPlayers(int players) {
        if (players >= 2 && players <= 6) {
            this.numPlayers = players;
        }
    }

    /**
     * Initializes the state of the game.
     */
    public Eights() {
        Deck deck = new Deck("Deck");
        deck.shuffle();

        // Create players with EightsHands
        players = new ArrayList<Player>();
        String[] playerNames = {"Alice", "Bob", "Charlie", "Diana", "Eve", "Frank"};
        
        for (int i = 0; i < numPlayers; i++) {
            String name = (i < playerNames.length) ? playerNames[i] : "Player" + (i + 1);
            players.add(new SmartPlayer(name));
        }

        // Deal cards to each player
        int cardsPerPlayer = Math.min(5, 52 / numPlayers);
        for (Player player : players) {
            deck.deal(player.getHand(), cardsPerPlayer);
        }

        // Turn one card face up - use EightsHand
        discardPile = new EightsHand("Discards");
        deck.deal(discardPile, 1);

        // Put the rest of the deck face down - use EightsHand
        drawPile = new EightsHand("Draw pile");
        deck.dealAll(drawPile);

        // Create the scanner we'll use to wait for the user
        in = new Scanner(System.in);
        
        // Start with the first player
        currentPlayerIndex = 0;
        
        System.out.println("Starting Crazy Eights with " + numPlayers + " players!");
        System.out.println("Press ENTER after each turn to continue...\n");
    }

    /**
     * Returns true if any player's hand is empty.
     */
    public boolean isDone() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves cards from the discard pile to the draw pile and shuffles.
     */
    public void reshuffle() {
        // save the top card
        Card prev = discardPile.popCard();

        // move the rest of the cards
        discardPile.dealAll(drawPile);

        // put the top card back
        discardPile.addCard(prev);

        // shuffle the draw pile
        drawPile.shuffle();
    }

    /**
     * Returns a card from the draw pile.
     */
    public Card drawCard() {
        if (drawPile.isEmpty()) {
            reshuffle();
        }
        return drawPile.popCard();
    }

    /**
     * Returns the next player in turn order.
     */
    public Player nextPlayer(Player current) {
        // Find current player's index
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == current) {
                currentPlayerIndex = (i + 1) % players.size();
                return players.get(currentPlayerIndex);
            }
        }
        // This shouldn't happen, but return first player as fallback
        currentPlayerIndex = 0;
        return players.get(currentPlayerIndex);
    }
    
    /**
     * Returns the current player.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Displays the state of the game.
     */
    public void displayState() {
        for (Player player : players) {
            player.display();
        }
        discardPile.display();
        System.out.print("Draw pile: ");
        System.out.println(drawPile.size() + " cards");
        in.nextLine();
    }

    /**
     * One player takes a turn.
     */
    public void takeTurn(Player player) {
        Card prev = discardPile.lastCard();
        Card next = player.play(this, prev);
        discardPile.addCard(next);

        System.out.println(player.getName() + " plays " + next);
        System.out.println();
    }

    /**
     * Plays the game.
     */
    public void playGame() {
        Player player = players.get(currentPlayerIndex);

        // keep playing until there's a winner
        while (!isDone()) {
            displayState();
            takeTurn(player);
            player = nextPlayer(player);
        }

        // display the final score for all players using EightsHand scoring
        System.out.println("Final Scores:");
        for (Player p : players) {
            if (p.getHand() instanceof EightsHand) {
                EightsHand hand = (EightsHand) p.getHand();
                System.out.println(p.getName() + " has " + hand.scoreHand() + " points");
            } else {
                p.displayScore(); // fallback
            }
        }
    }

    /**
     * Creates the game and runs it.
     */
    public static void main(String[] args) {
        Eights game = new Eights();
        game.playGame();
    }
}