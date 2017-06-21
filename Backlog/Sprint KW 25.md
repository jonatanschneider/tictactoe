# Sprint Backlog KW 25

## David -> Dienstag
- hashCode() ? (testen)
- toString()
- makeMove(Move move)
- isWin
    + Gegner weniger als drei Steine
    + nicht in erster Phase abfragen
    + Gegner kann keinen Stein bewegen
        - wenn listMoves == 0 dann verloren
- isDraw
    + dreimal die gleiche Spielstellung
    + Liste mit Hashcodes
        * wenn ein Stein entfernt, Liste löschen da nicht mehr erreichbar


## Jonatan -> Dienstag
- listMoves:
- Zuglogik
    + Phasen unterscheiden
        1. Phase
            - 9x Stein setzen abwechselnd
        2. Phase
            - Steine auf angrenzende Felder verschieben
            - über 2D Array Mills rausfinden ob Mühle geschlossen wird
                + Wenn Mühle geschlossen wird
                    * Gegnerstein entfernen außer in Mühle
        3. Phase
            - Steine == 3
            - an irgendeinen freien Punkt springen
            - analog zu Phase 2
            - Steine < 3 -> verloren
- Steinenanzahl über Board rausfinden
- history.size() > 18: Phase 2
- in listMoves Phase 3 berücksichtigen
- 2D Array mit Nachbarn
- 2D Array mit Mills