# Abgabe des p20-Projekts (PiS, SoSe 2017)

**Hinweis**: _Die Datei in in Bearbeitung. Per Moodle-Nachricht wird sie freigegeben._

> * Füllen Sie das Dokument aus
>   * Legen Sie das Dokument in Ihren Repo ab.
>   * Bringen Sie eine ausgedruckte Fassung mit zur Abnahme

Zur Vorbereitung der Abgabe des p20-Projekts laden Sie sich dieses Markdown-Dokument bitte herunter (es ist im [p20-Repository](https://git.thm.de/dhzb87/p20) zu finden), füllen es aus und fügen die ausgefüllte Version bitte Ihrem Projektverzeichnis im Top-Level ebenfalls unter dem Namen `Abgabe.md` hinzu. Ergänzend bringen Sie eine ausgedruckte Fassung des Dokuments zur Abgabe mit.

Was müssen Sie ausfüllen? Die entsprechenden Stellen sind im Dokument gekennzeichnet und im [Markdown-Format](https://git.thm.de/help/user/markdown.md) zu ersetzen.

> Hinweis: An einigen Stellen müssen Sie in diesem Dokument eine Referenz zur P20-Dokumentation auf GitLab einfügen. Wenn Sie z.B. von der Interface-Seite des Master-Branchs auf der [GitLab-Seite](https://git.thm.de/dhzb87/p20/blob/master/InterfaceBoard.md) ein spezielles Interface referenzieren wollen, dann bewegen Sie die Maus auf die entsprechende Überschrift, links am Rand erscheint dann ein Link-Symbol. Sie kopieren den Link,  indem Sie die Maus über dem Symbol positionieren, die rechte Maustaste klicken und “Adresse des Links kopieren” auswählen.

## Projektdaten

> - Geben Sie an, welchen Praktikumsblock Sie besucht haben
> - Geben Sie das Team-Repository an
> - Benennen Sie die beteiligten Team-Mitglieder
> - Geben Sie an, welchen Anteil jedes Team-Mitglied an dem Projekt hatte

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
| 2.Donges | 50%   |

## Basispunkte (20P)

### Programme starten

> - Geben Sie an, wie man das T3-Spiel startet
> - Geben Sie an, wie man das Mühle-Spiel startet

* Das T3-Spiel wird in der Konsole folgt gestartet:
```
Hier den Konsolen-Dialog reinkopieren.
```

* Das Mühle-Spiel wird in der Konsole wie folgt gestartet:
```
Hier den Konsolen-Dialog reinkopieren.
```

### Der gemeinschaftliche Code zum Berechnen von Zügen (5x 2P)

> - Erläutern Sie, woran man erkennt, dass Sie zum Berechnen von Zügen für T3 bzw. Mühle den gleichen Code verwenden! (Beispielhafte Antworten könnten sein: Code taucht nur einmal auf, wir verwenden das Interface als Typ)
> - Geben Sie an, welche Punkte Sie aus der Anforderungtabelle erfüllen
> - Verweisen Sie auf die Code-Dateien und den Codebereich, welche die Anforderungen implementieren
> - Erläutern Sie, inwiefern Ihre Implementierung von den Vorgaben abweicht, was Sie z.B. anders oder nicht implementiert haben

Unser Code zur Berechnung eines T3- bzw. eines Mühle-Zugs ist in der Tat identisch. Das sieht man im Code daran, dass ...

Beide Spiele rufen die Selbe Klasse `AI.java` auf, der einzige Unterschied besteht in der Parametrisierung: TicTacToe nutzt einen Integer, wohingegen Mühle eine Klasse `Move` nutzt in der die drei Zuganteile gespeichert werden.

Beide Implementierungen nutzen für die folgenden Punkte den gleichen Code und

- [X] führen eine iterative Tiefensuche durch (2P)
- [X] und zwar als Alpha-Beta-Suche (2P),
- [X] eine Transpositionstabelle beschleunigt die Suche (2P)
- [X] die Suche bewertet eine Stellung jenseits des Suchhorizonts mit Hilfe der Monte-Carlo-Methode (2P)
- [(X)] die bis dahin beste gefundene Zugfolge wird mit jeder Iteration aktualisiert und kann bei Bedarf ausgegeben werden (2P)

Die Implementierung für die Anforderungen finden sich für

- die Tiefensuche (je nachdem, was Sie implementiert haben):
  - Minimax/Negamax: _Linkangabe_ (Zeilen 29-87)
  - Alpha-Beta:  _Linkangabe_ (Zeilen 29-87)
  - iterative Suche:  _Linkangabe_ (Zeilen 18-27)
- die Transpositionstabelle: _Linkangabe_ (Zeilen 34-38 & 71-83)
- die Monte-Carlo-Methode: _Linkangabe_ (Zeilen 89-138)

Wir möchten folgende Anmerkungen zu unserem Code machen:

_Ihr Text_

### Implementierung der Spielbrett- und Zuglogik (2P)

> - Geben Sie an, wie Sie die Spielbretter implementiert haben; löschen Sie nicht zutreffende Angaben
> - Geben Sie an, welches Interface Ihre Spielbretter implementieren
> - Erläutern Sie, falls Sie von den Vorgaben abweichen!

Wir haben die Spielbretter implementiert für
- [X] T3 (immutabel)
- [X] Mühle (immutabel)

Folgende Interfaces haben wir implementiert:
- Das T3-Board nutzt das Interface: [ImmutableBoard](https://git.thm.de/dhzb87/p20/blob/master/InterfaceBoard.md#interface-immutableboard)
- Das Mühle-Board nutzt das Interface: [ImmutableBoard](https://git.thm.de/dhzb87/p20/blob/master/InterfaceBoard.md#interface-immutableboard)

Wir möchten folgende Anmerkungen zu unserem Code machen:

_Ihr Text_

### Der Programmdialog (2x 2P)

* Wir sind aus folgendem Grund abgewichen vom spezifizierten Dialogverhalten:

* So sieht der Programmdialog mit dem T3-Spiel aus:
```
Hier ca. 200 Zeilen des Konsolen-Dialogs reinkopieren.
```

* Sie sieht der Programmdialog mit dem Mühle-Spiel aus:
```
Hier ca. 200 Zeilen des Konsolen-Dialogs reinkopieren.
```

### Das Laden und Speichern von Spielen (2x 2P)

> - [X] Geben Sie an, ob Ihr Programm Spiele speichern kann
> - [X] Geben Sie an, ob Ihr Programm Spiele laden kann
> - [ ] Haben Sie das Laden fehlerhafter Spieldateien überprüft? - prüfen
> - [X] Geben Sie ein Beispiel für ein gespeichertes T3-Spiel an
> - [X] Geben Sie ein Beispiel für ein gespeichertes Mühle-Spiel an

- Wir speichern einen Spielstand gemäß [Spezifikation](https://git.thm.de/dhzb87/p20/blob/master/LoadSaveSpec.md)
  - [X] für das T3-Spiel
  - [X] für das Mühle-Spiel
- Wir können einen Spielstand gemäß [Spezifikation](https://git.thm.de/dhzb87/p20/blob/master/LoadSaveSpec.md) laden
  - [ ] für das T3-Spiel
  - [ ] für das Mühle-Spiel
  - [ ] Wir haben überprüft, dass ungültige Speicherformate nicht geladen werden und keinen Fehler im Programmablauf erzeugen
 

* So sieht der Dateiinhalt eines gespeicherten T3-Spiels von unserem Programm aus:
```
0,4,7,1,2,3,5,8,6,f
```
 
* So sieht der Dateiinhalt eines gespeicherten Mühle-Spiels von unserem Programm aus:
```
0,8,1,2,4,16,5,23,21,3,18,22-4,20,4-5,7,14,9,6,7-15,3-11,18-17-11,4-5,0-7,2-3
```

## Bonuspunkte (15P)

