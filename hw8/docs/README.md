# Three Trios Game

## Overview
Three Trios Game is a two-player strategic card game played on a customizable board/grid. Players take turns placing cards on the grid, attempting to flip the opponent's cards by leveraging attack values. The player with the most cards at the end of the game wins.

## Quick Start

### Prerequisites
- **Java Development Kit (JDK)**: Ensure JDK 11+ is installed
- **Java Swing**: Required for the GUI

### Project Structure
The project is organized into packages and classes to support
the model-view-controller (MVC)  for the Three Trios Game.

- `src/cs3500.threetrios`
    - `ai`: Contains classes related to AI strategies for solo play mode.
        - `Flip`: Represents a specific AI strategy focused on maximizing card flips.
        - `GoForCorner`: AI strategy that prioritizes moves targeting corner positions.
        - `HybridStrategy`: Combines multiple strategies to determine the optimal move.
        - `LeastFlippableStrategy`: AI strategy that chooses cards and positions that are least likely to be flipped by the opponent.
        - `MinMaxStrategy`: Implements a Minimax-based decision-making strategy to minimize the maximum flip potential for the opponent.
        - `NoPlay`: Represents a strategy where the AI chooses not to play, or a placeholder for no valid moves.
        - `PosnStrategy`: Interface or abstract class for position-based strategy logic.
        - `StrategyController`: Controls and coordinates the use of different AI strategies.

    - `card`: Manages card-related components.
        - `Card`: Interface representing a general card with attributes and behaviors.
        - `CardModel`: Implements the Card interface, handling specific card properties like attack values.
        - `COLOR`: Enum representing possible colors for the cards.
        - `NUMBER`: Enum representing possible number values on cards.

    - `game`: Contains core game mechanics and grid management.
        - `Cell`: Represents individual cells on the game grid (either playable or non-playable).
        - `Game`: Core game logic, coordinating gameplay flow and state.
        - `GameGrid`: Manages the layout and structure of the game grid, including cells.
        - `GameModel`: Main interface defining the game model’s behaviors and data access.
        - `Grid`: Additional grid-related functionality (potentially a helper or superclass for GameGrid).
        - `ReadOnlyGameModel`: Provides a read-only view of the game model, ensuring the view cannot modify the game state.

    - `GUI`: Implements the graphical user interface for the game.
        - `Features`: Defines various interactive features for the GUI.
        - `GridPanel`: Handles the display and layout of the grid within the GUI.
        - `Main`: Entry point of the program, initializing the model, view, and controller.
        - `ThreeTriosControllerImpl`: Implementation of the game controller, managing interactions between the model and view.
        - `ThreeTriosGameController`: Interface for the game controller, specifying controller behavior.
        - `ThreeTriosGameView`: Interface defining the capabilities of the game’s view component.
        - `ThreeTriosViewImpl`: Implementation of the view interface, managing the visual aspects of the game.

    - `parser`: Handles configuration file parsing.
        - `BoardConfigParser`: Reads and processes configuration files to set up the game grid and card details.

    - `player`: Manages player-specific functionality.
        - `Player`: Interface for general player behaviors.
        - `PlayerModel`: Implementation of the Player interface, managing player attributes such as hands and score.

    - `view`: Contains classes for alternative or auxiliary views of the game.
        - `TextView`: Provides a text-based representation of the game state, useful for debugging or non-GUI implementations.

Configuring the Game via Command-Line
The program accepts a minimum of two to four command-line arguments:

Player 1 Type: "human" or "ai"
Player 2 Type: "human" or "ai"
Player 1 AI Strategy (Optional): "strategy1", "strategy2", or "hybrid"
Player 2 AI Strategy (Optional): "strategy1", "strategy2", or "hybrid"

More tests for GameModel:
isMoveLegal(row, col),
getCurrentPlayerModel(),
getWinner(),
getGridSize(),
getCardAt(row, col)

### Features
- **Visual View**: A GUI built with Java Swing,
  enabling players to interact with the game grid and cards through mouse clicks
- **Game Model**: Divided into mutable and read-only interfaces to maintain
  the integrity of the MVC, preventing the view from modifying the game state
- **Strategies**: Initial AI strategies for single-player mode:
    - **Maximize Flip**: Selects moves to flip the most opponent cards.
    - **Corner Preference**: Prioritizes corner positions on the grid,
      where cards are less likely to be flipped.

## Setup
1. **Download Starter Files**  
   Download the starter code from `code.zip`
   and extract it into your project directory.

2. **Clone the Repository**  
   Clone the repository to your local machine

3. **Compile the Project**  
   Compile using Maven

## Running the Game
1. **Launch the Game**  
   Run the main class to start the game:
   This will open the game window with the initial grid and player hands

2. **Game Controls**
    - **Select a Card**: Click on a card in the player’s hand to highlight it
    - **Place a Card**: Click on a grid cell to place the selected card
    - **Deselect a Card**: Click the same card again or choose a different card
    - **Console Logging**: The game view logs coordinates and player
      actions to the console for debugging.

3. **Choose Game Mode**
    - **Two-Player Mode**
    - **AI Mode**: Play against a simple AI

4. **Gameplay Instructions**
    - Place cards strategically on the grid
    - Flip opponent cards by utilizing attack values
    - The player with the most cards on the grid at the end wins

## Components
- **GameGrid**: Manages the grid and card placement.
- **Cell**: Represents individual grid cells (playable or non-playable)
- **CardModel**: Contains card attack values and determines winning conditions in card battles
- **Player**: Manages player attributes, including each player’s hand of cards
- **BoardConfigParser**: Parses configuration files to set up the game grid and card list
- **TextView**: Provides a user-friendly, text-based representation of the grid

### Configuration Files
Configuration files in `docs/` contain both card and grid settings.
They may be updated to support other game setups

**Example Invariant for Grading**:
```java
// INVARIANT: Each player’s hand is filled with exactly (N+1)/2 cards
// where N is the number of card cells on the grid.
int numCardsEachPlayer = (numCardCells + 1) / 2;
```

## Adapters

#### Our Code
Our adapter code is under `ProviderCode` with the scripts we coded in the previous

While creating the model we were not able to adapt much of our code to theirs, we connected all 
of our main playable function in a main script `GameModel` However, 
this code had an unique way to tackle their problems, 
they coded their whole method in different their main classes,
for example, our `GameModel` took care of battling cards and 
handling players. In this script the `Card` or `Slot` deals with the battling
while the `BoardModel` took care of the players and the board. 
As well as the `Card` script took care of the player scores.

We attempted many different adapters including a `CardAdapter`, `PlayerAdapter`, `ReadOnlyBoardAdapter`, and `ViewFeaturesAdapter`.
The `CardAdapter` tried to convert their Slots (representation of Cards) 
to our representation of `Card` with our `PlayerModel`, but the providers design did not 
have dedicated player management instead they relied on enums and loosely coupled logic which
was difficult to translate with our `PlayerModel`. Also, more complex actions were not easy to adapt because their
Slot lacked the methods to fully integrate our player/card actions.
The `ReadOnlyBoardAdapter` was created to adapt their `ReadOnlyBoard` with our `GameModel`.
However, their `ReadOnlyBoard` had multiple responsibilities like keeping track of score and managing player actions.
Our design separated these so the adapter was only able to handle simple cases.
The `PlayerAdapter` was designed to convert their player logic into our `PlayerModel`. Their player relied on enums 
and interfaces like `PlayerAction`, so we had to adapt to match our more centralized player logic, but this
limited the flexibility of player actions and strategies. 
The `ViewFeaturesAdapter` translated their `PlayerAction` logic into our Features interface to handle 
people playing the game and cell clicks. Though we had to compromise with some logic since we didn't include player specific and index clicks,
we were able to implement working features into our code.

We also attempted converting our `Card` and our `GameModel` into their code, however, due to
us not being able to modify their code there was not a lot we could had done and while going through that route
we would be not Adapting to their code but just coding the whole project again.

However, the providers code had many incomplete interfaces like Slot and PlayerAction that did not
include all functionality that we needed to make the code and adapters work. Also, their code was more class based
than interface focused unlike our code which had more modularity. This problem made it easy for the provider to forget
to place important functions and methods on the code. Because of this flaw we communicated with them
many times, but there was so much that they could do and they could not code their entire project again for this homework.

## Some of the key difference: 

#### Card
In our card model we have only a class and interface strictly for that, it takes care of the card
and holds its information, COLOR and all the values needed. They did not have a card script, they 
implemented the `Slot` which the Slot is used for cards and each cell on the board. Therefore, in their
"Card" script they also battled each card, took care of the player scores, and took care of some player
actions. 

#### Player
In our Player model we have only a class and interface strictly for that, it takes care of the player actions
and holds its information: COLOR, the cards in their hand, their name, and removing and adding cards. However,
the provider created an ENUM for the player, which also was in charge of which player turn it was. They also contained
many others interfaces that took care of the player actions alongside having the player action on the `Slot` interface

#### Game
their `Board` is equivalent to our `Game` where it should take care of the game, making sure everthing
ran smoothly, but how they designed it did not go well with ours, becuase our `Game` took in the `Player` `Card` and `Grid`
interfaces and made them both work together to make the game functional, in their `Board` they take 
create the Grid of the game full of `Slot`, each slot had 2 extra classes that were not provided to us
becuase they were classes, which was to classify if it was a HOLE, or EMPTY. In their `Board` they also take care
of some of the Player actions but not all of them. And lastly in their `Board` they also took care of some of the
AI implementations into it.

## Conclusion
Due to all these issues we faced it was incredibly difficult the adapt our code into theirs, our design 
and their design are very different and not compatible. Our design was more modular which did not adapt well to their tightly coupled.
We kept in contact with them and asking for changes
or for the to explain how their code works before making this conclusion. 


Testing: All test cases were removed due to size constraints.
View: Recreating their view was impossible due to incompatible GUI 
frameworks and the lack of access to their Slot and card construction methods. They used a builder to construct their cards/slots,
however, there was no interface for us to access. 

While the view does not display cards or cells. You can still play the game and all game logic works for human vs human.