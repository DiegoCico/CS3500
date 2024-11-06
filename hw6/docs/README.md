# Three Trios Game
## Overview
Three Trios Game is a two player game built on a custom size board/grid.
Players take turns placing cards on the grid and battle to flip the opponent's card.
The player with the most cards at the end wins.

## Components
GameGrid: Manages the grid and card placement
Cell: Reps grid cells (playable or non)
CardModel: Holds attack values for battle and handles winning conditions
Player: Managers players and their hands (of cards)
BoardConfigParser: Parses configuration files to set up the game grid and card list
TextView: Displays grid in a user-friendly way

config files currently have both cards and grid - may update or not

EXAMPLE INVARIANT FOR GRADING PURPOSES IN MODEL:
//INVARIANT: Each player’s hand is filled with exactly (N+1)/2 cards
// where N is the number of card cells on the grid.
int numCardsEachPlayer = (numCardCells + 1) / 2;

# Next Steps
- GUI and Controller (currently in the dummy folder - not enough time to implement)
- Need to create a features class
- Need make ReadOnlyModel for GUI 
- AI Player: Adding a simple AI to allow solo play.
- Making strategies based on class lecture on strategy design pattern