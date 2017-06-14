# Product Backlog

## Allgemein
- Interfaces einbinden
- Speichern/Laden
- Oberklasse abstract Board implements Interface
    + parent
    + isFlipped()
    + hashCode()
    + getHistory()
        + bei move in History schreiben
        + bei undo wegnehmen
    + undoMove()
- Streams und Lamba nutzen, am Ende überprüfen

### KI
- eventl. umstellen auf Parametrisierung
- iterative Tiefensuche
    + Alpha-Beta
- Transpositiontable
    + Symmetrien
    + Spiegelungen
    + Rotationen
- Monte-Carlo
- "Suchabbruch" 
    + Ausgabe?
    + Timer?

## Mühle
- Turn als isBeginnersTurn()
- Immutable Boards
- makeMove Parametrisieren
- Move Klasse für Mühle
    + normalen Move  - 1. Phase
    + von, zu - 2./3. Phase
    + von, zu, Stein entfernen - 2./3.Phase
- listMoves
    + Board als Rückgabewert
- isWin
    + Gegner weniger als drei Steine
    + nicht in erster Phase abfragen
    + Gegner kann keinen Stein bewegen
        - wenn listMoves == 0 dann verloren
- isDraw
    + dreimal die gleiche Spielstellung
    + Liste mit Hashcodes
        * wenn ein Stein entfernt, Liste löschen da nicht mehr erreichbar- 
- toString
    + isFlipped beachten
- Konsoleninteraktion
- Zuglogik
    + Phasen unterscheiden
        1. Phase
            - 9x Stein setzen abwechselnd
        2. Phase
            - Steine auf angrenzende Felder verschieben
            - Wenn Mühle geschlossen wird
                + Gegnerstein entfernen außer in Mühle
        3. Phase
            - Steine == 3
            - an irgendeinen freien Punkt springen
            - analog zu Phase 2
            - Steine < 3 -> verloren
- 2D Array mit Nachbarn

## T3
- Konsoleninteraktion
- an Interface anpassen (Parametrisierung)
- (turn durch isBeginnersTurn ersetzen)