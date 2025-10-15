# Crazy Eights

A Java implementation of Crazy Eights with smart AI players.

## Running the Game

Download the latest JAR from [Releases](../../releases) or build it yourself:

```bash
./build.sh
java -jar CrazyEights.jar
```

Just press ENTER to advance each turn. First player to empty their hand wins.

## Files

- `Card.java` - Basic card representation
- `Deck.java`, `Hand.java`, `CardCollection.java` - Card container classes
- `Player.java` - Base player with basic strategy
- `SmartPlayer.java` - Improved AI that plays high cards first and saves eights
- `Eights*.java` - Game-specific implementations for Crazy Eights rules
- `build.sh` - Compiles everything and packages into a JAR

## CI/CD

GitHub Actions automatically builds and releases new versions on every push to main. The workflow compiles the source, packages the JAR, and creates a timestamped release. Check the Actions tab to see builds in progress.

## Requirements

Java 8 or higher
