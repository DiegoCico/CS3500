

## Adapters

#### Our Code
Our code is under `ProviderCode` with the scripts we coded in the previous

While creating the model we were not able to adapt much of our code to theirs, we connected all of our main playable function in a main script `GameModel` However, this code had an unique way to tackle their problems, they coded their whole method in different their main classes, for example, our `GameModel` took care of battleing cards and handling players. In this script the `Card` or `Slot` deals with the battleing while the `BoardModel` took care of the players and the board. As well as the `Card` script took care of hte player scores 