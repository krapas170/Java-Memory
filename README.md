# Java-Memory

Das klassische Memory-Spiel als Desktop-Anwendung in Java mit Swing-Oberfläche.
Ursprünglich als Schulprojekt entstanden.

Decke zwei Karten mit demselben Symbol auf, bevor die Zeit abläuft. Feldgröße und
Zeitlimit stellst du vor jeder Runde selbst ein.

## Spielen

### Fertiges Spiel herunterladen

Den Installer bzw. das JAR gibt es unter
[Releases](https://github.com/krapas170/Java-Memory/releases/latest).

**Voraussetzung: Java 17 oder neuer.** Falls noch nicht vorhanden, gibt es ein
kostenloses JRE bei [Adoptium](https://adoptium.net/de/temurin/releases/?version=17).
Ob Java schon installiert ist, verrät `java -version` in der Eingabeaufforderung.

Die CI baut, testet und startet das Spiel bei jedem Push gegen **JDK 17, 21 und
25** – die neueren LTS-Stände laufen also nachweislich mit.

Das JAR startet per Doppelklick oder über:

```
java -jar java-memory.jar
```

### Aus den Quellen bauen

Benötigt werden ein **JDK 17+** und **Maven 3.8+**:

```
git clone https://github.com/krapas170/Java-Memory.git
cd Java-Memory
mvn verify
java -jar target/java-memory.jar
```

`mvn verify` baut das Spiel, führt die Tests aus und legt unter `target/` ein
startfähiges JAR mit allen Abhängigkeiten ab.

## Spielregeln und Grenzen

| Einstellung | Bedeutung |
|---|---|
| Höhe × Breite | Anzahl der Karten. **Das Produkt muss gerade sein**, sonst bliebe eine Karte ohne Partner. |
| Zeitlimit | In Minuten. Läuft die Uhr ab, ist die Runde verloren. |

Es sind höchstens **68 Felder** möglich – für jedes verfügbare Kartensymbol
genau ein Paar. Über die Vorlagen *Leicht* (4×4), *Mittel* (6×6) und *Schwer*
(8×8) geht es schneller als über die Zahlenfelder.

### Bedienung

- **Maus**: Karte anklicken zum Aufdecken.
- **Tastatur**: Mit den Pfeiltasten durch das Gitter wandern, mit Leertaste oder
  Enter aufdecken.
- **Pause**: Hält die Uhr an und verdeckt das Spielfeld.

Die letzten zehn Sekunden werden farbig hervorgehoben und akustisch begleitet.
Gefundene Paare erkennst du an Farbe, Rahmen und dem abgeschalteten Zustand –
also nicht allein an Rot/Grün.

### Bestwerte

Die wenigsten Züge und die schnellste Zeit werden je Feldgröße lokal gespeichert
(Windows-Registry bzw. `~/.java` unter Linux und macOS) und oben rechts angezeigt.

## Update-Prüfung

Beim Start fragt das Spiel im Hintergrund bei GitHub nach, ob es eine neuere
Version gibt, und liest dafür
[`version.json`](https://github.com/krapas170/Java-Memory/blob/main/version.json).
Es werden keine Daten übertragen. Ohne Internetverbindung läuft die Abfrage nach
wenigen Sekunden ins Leere und das Spiel startet ganz normal – gefragt wird nur,
wenn die Serverversion tatsächlich höher ist als die installierte.

## Projektaufbau

```
src/main/java/de/krapas170/memory/   Quellcode
src/main/resources/assets/           Bilder und Klänge (landen im JAR)
src/test/java/                       JUnit-5-Tests
version.json                         Die auf dem Server veröffentlichte Version
```

Die Spiellogik (`SpielLogik`, `SpielDaten`, `Kartenblatt`, `Einstellungen`)
kennt kein Swing und ist deshalb ohne Oberfläche testbar. Die Verbindung zur
Anzeige läuft über das Interface `SpielAnzeige`.

## Eine neue Version veröffentlichen

1. `<version>` in der `pom.xml` erhöhen.
2. `mvn verify` ausführen und `target/java-memory.jar` an ein GitHub-Release hängen.
3. **Erst danach** `version.json` auf dieselbe Nummer setzen und nach `main`
   pushen – vorher würden vorhandene Installationen auf ein Release verweisen,
   das es noch nicht gibt.

## Lizenz

[MIT](LICENSE)
