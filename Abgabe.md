# Abgabe des p20-Projekts (PiS, SoSe 2017)

## Projektdaten

Wir haben das Praktikum bei

* Herrn Herzberg besucht
* im 2. Block

Das Team-Repository auf GitLab: https://git.thm.de/jsnr08/pis-p20

Die Team-Mitglieder sind:

* Schneider, Jonatan: jonatan.schneider@mni.thm.de
* Donges, David Noah: david.noah.donges@mni.thm.de

Die Beitragsanteile in Prozent verteilen sich wie folgt in unserem Team; die Summe der Prozentangaben ergibt 100% (oder 99% bei Drittelung).

| Nachname | Anteil |
| -------- | -------- |
| 1.Schneider | 50%   |
| 2.Donges    | 50%   |

## Basispunkte (20P)

### Was gesagt werden muss

Gleich zu Anfang möchten wir auf Folgendes hinweisen und aufmerksam machen: /

### Programme starten

* Das T3-Spiel wird in der Konsole folgt gestartet:

```
P20 - Projekt
-------------
Bitte wählen Sie aus den folgenden Optionen durch Eingabe der vorangestellten Zahl + <ENTER>.
[1] TicTacToe starten
[2] Mühle starten
[3] Beenden
>> 1

...
...
...

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:


```

* Das Mühle-Spiel wird in der Konsole wie folgt gestartet:

```
P20 - Projekt
-------------
Bitte wählen Sie aus den folgenden Optionen durch Eingabe der vorangestellten Zahl + <ENTER>.
[1] TicTacToe starten
[2] Mühle starten
[3] Beenden
>> 2
.-----.-----.
| .---.---. |
| | .-.-. | |
.-.-.   .-.-.
| | .-.-. | |
| .---.---. |
.-----.-----.
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:

```

### Der gemeinschaftliche Code zum Berechnen von Zügen (5x 2P)

Unser Code zur Berechnung eines T3- bzw. eines Mühle-Zugs ist in der Tat identisch. Das sieht man im Code daran, dass ...

beide Spiele rufen die Selbe Klasse `AI.java` auf, der einzige Unterschied besteht in der Parametrisierung: TicTacToe nutzt einen Integer, wohingegen Mühle eine Klasse `Move` nutzt in der die drei Zuganteile gespeichert werden.

Beide Implementierungen nutzen für die folgenden Punkte den gleichen Code und

- [X] führen eine iterative Tiefensuche durch (2P)
- [X] und zwar als Alpha-Beta-Suche (2P),
- [X] eine Transpositionstabelle beschleunigt die Suche (2P)
- [X] die Suche bewertet eine Stellung jenseits des Suchhorizonts mit Hilfe der Monte-Carlo-Methode (2P)
- [ ] die bis dahin beste gefundene Zugfolge wird mit jeder Iteration aktualisiert und kann bei Bedarf ausgegeben werden (2P)

Die Implementierung für die Anforderungen finden sich für

- die Tiefensuche (je nachdem, was Sie implementiert haben):
  - Alpha-Beta:  [AI.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/AI.java) (Zeilen 29-87)
  - iterative Suche:  [AI.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/AI.java)  (Zeilen 18-27)
- die Transpositionstabelle: [AI.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/AI.java)  (Zeilen 34-38)
- die Monte-Carlo-Methode: [AI.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/AI.java)  (Zeilen 118-132)

Wir möchten folgende Anmerkungen zu unserem Code machen: /

### Implementierung der Spielbrett- und Zuglogik (2P)

Wir haben die Spielbretter implementiert für

- [X] T3 (immutabel)
- [X] Mühle (immutabel)

Folgende Interfaces haben wir implementiert:

- Das T3-Board nutzt das Interface: [Immutable Board](https://git.thm.de/dhzb87/p20/blob/master/InterfaceBoard.md#interface-immutableboard)
- Das Mühle-Board nutzt das Interface: [Immutable Board](https://git.thm.de/dhzb87/p20/blob/master/InterfaceBoard.md#interface-immutableboard)

Wir möchten folgende Anmerkungen zu unserem Code machen:

### Der Programmdialog (2x 2P)

Wir sind aus folgendem Grund abgewichen vom spezifizierten Dialogverhalten: /

So sieht der Programmdialog mit dem T3-Spiel aus:

```
P20 - Projekt
-------------
Bitte wählen Sie aus den folgenden Optionen durch Eingabe der vorangestellten Zahl + <ENTER>.
[1] TicTacToe starten
[2] Mühle starten
[3] Beenden
>> 1

...
...
...

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 5

Ich denke nach ... und setze auf 8.

...
.X.
.O.

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 9

Ich denke nach ... und setze auf 1.

O..
.X.
.OX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 2

Ich denke nach ... und setze auf 4.

OX.
OX.
.OX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>>u

O..
.X.
.OX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 4

Ich denke nach ... und setze auf 6.

O..
XXO
.OX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> f

X..
OOX
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 2

Ich denke nach ... und setze auf 3.

XOX
OOX
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> s
Das Spiel wurde gespeichert.

XOX
OOX
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 7

XOX
OOX
OXO

Das Spiel endet unentschieden.

Gib "new" für eine neues Spiel ein!
[?: Hilfe]:
>> l
Das Spiel wurde geladen.

XOX
OOX
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 0

Ich denke nach ... und setze auf 7.

XOX
OOX
OXO

Das Spiel endet unentschieden.

Gib "new" für eine neues Spiel ein!
[?: Hilfe]:
>> ?
Spielen Sie Tic-Tac-Toe gegen den Computer. Die Positionen des Spielfelds sind von 1-9 nummeriert:

1 2 3
4 5 6
7 8 9

Es wird abwechselnd gezogen. Gewonnen hat, wer zuerst drei Spielsteine (entweder X oder O) in Reihe anordnet.

1-9             Position des Feldes, das man besetzen möchte
0               Der Computer macht für Sie einen Zug
undo            Macht den letzten Spielzug rückgängig
flip            Tauscht Spielsteine: X gegen O und umgekehrt
new             Beginnt ein neues Spiel
exit            Beendet das Programm
save            Speichert aktuelle Spielsituation
load            Lädt letzte gespeicherte Spielsituation
help            Zeigt diese Übersicht an
?               Wie "help"

Schließen Sie die Eingabe mit der <Eingabe>-Taste ab!
Bei Befehlen genügt auch die Eingabe des ersten Buchstabens.

Gib "new" für eine neues Spiel ein!
[?: Hilfe]:
>> u

Ich denke nach ... und setze auf 3.

XOX
OOX
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> u

X..
OOX
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> u

X..
.O.
.XO

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> flip

O..
.X.
.OX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 2

Ich denke nach ... und setze auf 4.

OX.
OX.
.OX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 0

Ich denke nach ... und setze auf 7.

Ich denke nach ... und setze auf 3.

OXO
OX.
XOX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> new

...
...
...

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 4

Ich denke nach ... und setze auf 5.

...
XO.
...

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 2

Ich denke nach ... und setze auf 1.

OX.
XO.
...

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 9

Ich denke nach ... und setze auf 3.

OXO
XO.
..X

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 0

Ich denke nach ... und setze auf 7.

Ich denke nach ... und setze auf 8.

OXO
XO.
XOX

Gib gültigen Spielzug ein (1-9 + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 0

Ich denke nach ... und setze auf 6.

OXO
XOX
XOX

Das Spiel endet unentschieden.

Gib "new" für eine neues Spiel ein!
[?: Hilfe]:
```

So sieht der Programmdialog mit dem Mühle-Spiel aus:

```
P20 - Projekt
-------------
Bitte wählen Sie aus den folgenden Optionen durch Eingabe der vorangestellten Zahl + <ENTER>.
[1] TicTacToe starten
[2] Mühle starten
[3] Beenden
>> 2
.-----.-----.
| .---.---. |
| | .-.-. | |
.-.-.   .-.-.
| | .-.-. | |
| .---.---. |
.-----.-----.
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 1
Ich denke nach ... und setze auf 9.

X-----.-----.
| O---.---. |
| | .-.-. | |
.-.-.   .-.-.
| | .-.-. | |
| .---.---. |
.-----.-----.
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 5
Ich denke nach ... und setze auf 8.

X-----.-----.
| O---.---. |
| | .-.-. | |
O-.-.   .-.-.
| | .-.-. | |
| .---.---. |
.-----.-----X
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 17
Ich denke nach ... und setze auf 3.

X-----.-----O
| O---.---. |
| | X-.-. | |
O-.-.   .-.-.
| | .-.-. | |
| .---.---. |
.-----.-----X
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 18
Ich denke nach ... und setze auf 10.

X-----.-----O
| O---O---. |
| | X-X-. | |
O-.-.   .-.-.
| | .-.-. | |
| .---.---. |
.-----.-----X
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 19-3
Ich denke nach ... und setze auf 4.

X-----.-----.
| O---O---. |
| | X-X-X | |
O-.-.   .-.-O
| | .-.-. | |
| .---.---. |
.-----.-----X
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 0
Ich denke nach ... und setze auf 23.

Ich denke nach ... und setze auf 20.

X-----.-----.
| O---O---. |
| | X-X-X | |
O-.-.   O-.-O
| | X-.-. | |
| .---.---. |
.-----.-----X
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> f
O-----.-----.
| X---X---. |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> s
Das Spiel wurde gespeichert.
O-----.-----.
| X---X---. |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 23
Dieser Zug ist nicht möglich!
O-----.-----.
| X---X---. |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 14
Ich denke nach ... und setze auf 11 und entferne 14.

O-----.-----.
| X---X---X |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> ?
Spielen Sie Mühle gegen den Computer. Die Positionen des Spielfelds sind von 1-24 nummeriert:

01-------02------03
|  09----10---11  |
|  |  17-18-19 |  |
08-16-24    20-12-04
|  |  23-22-21 |  |
|  15----14----13 |
07-------06------05


Einen Zug können Sie auf die folgenden Art und Weisen machen:
[Ziehe zu Position] oder
[Ziehe zu Position]-[Position des zu entfernenden Steins] oder
[Ziehe zu Position]-[Von Position]-[Position des zu entfernenden Steins]


1-24            Position des Feldes, das man besetzten möchte
0               Der Computer macht für Sie einen Zug
undo            Macht den letzten Spielzug rückgängig
flip            Tauscht Spielsteine: X gegen O und umgekehrt
new             Beginnt ein neues Spiel
exit            Beendet das Programm
save            Speichert aktuelle Spielsituation
load            Lädt letzte gespeicherte Spielsituation
help            Zeigt diese Übersicht an
?               Wie "help"

Schließen Sie die Eingabe mit der <Eingabe>-Taste ab!
Bei Befehlen genügt auch die Eingabe des ersten Buchstabens.

Es wird abwechselnd gezogen. Verloren hat, wer nur noch zwei eigene Spielsteine hat.
Eine Mühle schließen Sie indem sie drei ihrer Steine in Reihe bringen (horizontal oder vertikal).
Das Spiel beginnt mit dem abwechselnden Setzen von jeweils 9 Steinen. Sobald jeder Spieler 9x
am Zug war darf jeder Spieler in seinem Zug einen Stein von sich auf ein freies Nachbarfeld bewegen.
Falls Sie in ihrem Zug eine Mühle schließen dürfen Sie einen Stein des Gegners entfernen, welcher nicht in einer Mühle ist,
es sei denn alle seine Steine sind in einer Mühle, dann dürfen Sie auch einen davon auswählen.
Sobald ein Spieler nur noch drei eigene Steine auf dem Feld hat, darf er mit seinen Steinen zu jeder Position springen

O-----.-----.
| X---X---X |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 0
Ich denke nach ... und setze auf 6.

Ich denke nach ... und setze auf 12 und entferne 1.

.-----.-----.
| X---X---X |
| | O-O-O | |
X-.-.   X-X-X
| | O-.-. | |
| .---.---. |
.-----O-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> u
O-----.-----.
| X---X---X |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 20
Dieser Zug ist nicht möglich!
O-----.-----.
| X---X---X |
| | O-O-O | |
X-.-.   X-.-X
| | O-.-. | |
| .---.---. |
.-----.-----O
Gib gültigen Spielzug ein ('1-24'-'1-24'-'1-24' + <ENTER>)
[0: Computer zieht, ?: Hilfe]:
>> 21
Ich denke nach ... und setze auf 12 und entferne 21.
```

### Das Laden und Speichern von Spielen (2x 2P)

Wir speichern einen Spielstand gemäß [Spezifikation](https://git.thm.de/dhzb87/p20/blob/master/LoadSaveSpec.md)

- [X] für das T3-Spiel
- [X] für das Mühle-Spiel

Wir können einen Spielstand gemäß [Spezifikation](https://git.thm.de/dhzb87/p20/blob/master/LoadSaveSpec.md) laden

- [X] für das T3-Spiel
- [X] für das Mühle-Spiel
- [X] Wir haben überprüft, dass ungültige Speicherformate nicht geladen werden und keinen Fehler im Programmablauf erzeugen

So sieht der Dateiinhalt eines gespeicherten T3-Spiels von unserem Programm aus:
```
0,4,7,1,2,3,5,8,6,f
```
 
So sieht der Dateiinhalt eines gespeicherten Mühle-Spiels von unserem Programm aus:
```
0,8,1,2,4,16,5,23,21,3,18,22-4,20,4-5,7,14,9,6,7-15,3-11,18-17-11,4-5,0-7,2-3
```

## Bonuspunkte (15P)

### Transpositionstabelle oder Bitboards (entweder/oder 5P)

Sie können für die Umsetzung eines der beiden folgenden Punkte 5 Bonuspunkte erhalten; es ist ohne Mehrwert, beide Punkte umzusetzen:

- [ ] Die Implementierung der Transpositionstabelle für Mühle nutzt Symmetrien der "Spielmechanik" aus, um symmetrische Stellungen zu erkennen
- [ ] Die Implementierung setzt Bitboards für Mühle um

Die Realisation ist im Code zu finden unter: /

### Immutabilität und Streams mit Lambdas (2x 5P)

#### Immutabilität (5P)

Die Realisation der Immutabilität ist im Code gegeben durch

- [X] Nutzung des entsprechenden immutablen Interfaces der Spezifikation: [Immutable Board](https://git.thm.de/dhzb87/p20/blob/master/InterfaceBoard.md#interface-immutableboard)
- [X] Folgende Klasse implementiert das Interface: [Board.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/Board.java), Zeilen 8-57
- [ ] Dadurch, dass alle Felder als `final` ausgewiesen sind
- [ ] Dadurch, dass alle Felder als `private` ausgewiesen sind 

#### Streams und Lambdas (5P)

Sie nutzen an vielen Stellen Streams und Lambda-Expressions:

- [ ] Wir implementieren das `StreamBoard`
- [X] An folgenden Stellen im Code kommen Streams zum Einsatz:
    - [Mills.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/Mills.java), Zeilen: 55-58, 64-66, 71-74, 79-82, 104, 139-141, 143-145, 192
    - [T3Board.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/T3Board.java), Zeilen: 26-29
    - [MillsPersistenceManager.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/persistence/MillsPersistenceManager.java), Zeilen: 19, 30-31, 38
    - [T3PersistenceManager.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/persistence/T3PersistenceManager.java), Zeilen: 16, 26-27
    - [T3UI.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/ui/T3UI.java), Zeilen: 69
- [X] An folgenden Stellen im Code kommen Lambda-Ausdrücke zum Einsatz
    - [Mills.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/Mills.java), Zeilen: 56, 57, 65, 71-73, 79-81, 104, 140, 144, 192
    - [T3Board.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/T3Board.java), Zeilen: 28
    - [MillsPersistenceManager.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/persistence/MillsPersistenceManager.java), Zeilen: 19
    - [T3PersistenceManager.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/persistence/T3PersistenceManager.java), Zeilen: 16
    - [T3UI.java](https://git.thm.de/jsnr08/pis-p20/blob/master/src/p20/ui/T3UI.java), Zeilen: 69

### Git-Meisterschaft (5P/Person)

- [ ] Aus unserem Team haben sich die folgenden Mitglieder besonders bei der Erstellung, Korrektur und Stabilisierung der Spezifikationen im Git-Repository engagiert:



