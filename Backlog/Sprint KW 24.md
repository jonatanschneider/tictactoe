# Sprint Backlog KW 24

## Allgemeines
- Interfaces einbinden

## Projektstruktur
- Board Oberklasse implements ImmutableBoard
    + T3 Board
    + Mühle Board
- AI
- Move
- ImmutableBoard Interface

## David - > Montag
- Oberklasse abstract Board implements Interface
    + parent
    + isFlipped()
    + hashCode()
    + getHistory()
        + bei move in History schreiben
        + bei undo wegnehmen
    + undoMove()

## Absprache - > Dienstag
- T3 an Oberklasse anpassen
    + Parametrisierung
    + moves auf Board Rückgabe
    + getHistory einbauen
    + isBeginnersTurn
    + flip einbuen
    + toString mit isFlipped

## Jonatan - > Dienstag
- KI umstellen auf Parametrisierung
    - mit T3 testen
- Move Klasse
    - 3 Konstruktoren
