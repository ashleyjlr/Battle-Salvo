### Changes from PA03 to PA04

- Changed Coordinate from a class to a record: CoordJson was similar in structure to our Coordinate class, so to make it
  easier and concise, we "combined" the two.
- Updated "setUp" such that it correctly places ship randomly, without overlap
- Added methods to ship that calculate its size, get its starting coordinate, and its direction. Used in the new class,
  ship adapter, such that we can correctly convert a ship into a ShipJson.
- Updated driver such that it can handle multiple types of games.
- Added AutoGameController which implements GameController, corresponding to an AI game.
- Added corresponding Jsons to help with server communication.