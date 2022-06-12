# SiteInspector - Teilaufgabe 1

Bei Ausgrabungen werden meist noch vor Ort Zeichnungen von den Fundstücken angefertigt. Oft sind bestimmte Charakteristiken darauf klarer zu erkennen als auf Fotos. Heute werden wir ein Tool entwickeln, das die Fotos den Zeichnungen gegenüberstellt und z.B. Illustrationen für wissenschaftliche Publikationen erstellen kann. Die Bildbeispiele zeigen altsteinzeitliche Ritzzeichnungen auf Ocker aus der Blombos-Höhle in Südafrika. Die Publikation, welche diese Aufgabe inspiriert hat, finden Sie [hier](https://www.sciencedirect.com/science/article/pii/S0047248409000207).

### ImageSeries: Verwaltung einer Bildreihe

Implementieren Sie eine Klasse ```ImageSeries```, die alle Bilddateien (Frames) aus einer Dateiliste einliest und verwaltet. Lesen Sie bitte zunächst die Aufgabe bis zum Ende: Es ist sehr empfehlenswert, die Implementation nicht stur in dieser Reihenfolge durchzuführen, sonder stückweise ```ImageSeries``` und ```Frame``` parallel zu implementieren, so dass man immer testen kann, ob die bisher implementierte Funktionalität auch korrekt ist.

```ImageSeries``` soll folgende Methoden enthalten:
* Einen Konstruktor ```ImageSeries(File[] files)```: Bekommt die einzulesenden Dateien. Merkt sich die Dateipfade, und liest alle Bilddaten aus ```files``` in eine interne Liste (am besten eine ```ArrayList``` - wir werden immer wieder über den Index auf die frames zugreifen) von ```Frame``` ein. Per Konvention besteht ein Datensatz aus mindestens je einem einzulesenden Foto und einer Zeichnung. Per Konvention endet die dem Foto zugehörige Zeichnung auf dass Suffix ``_tracing.png``. Heisst also das Foto z.B. ```engraving-1.png```, so muss der Name der Zeichnung ```engraving-1_tracing.png``` lauten. Ist zu einem in ```files``` angegebenen Foto keine Zeichnung vorhanden, soll ein Fehler signalisiert werden. Optional können auch Grauwertbilder (mit der Endung ```_grayscale```) und halbtransparente Überblendungen (mit der Endung ```_overlay```) geladen werden. Da diese jedoch vom Programm erst erzeugt werden, sollen eventuelle Exceptions ignoriert und die entsprechenden BufferedImages auf null gesetzt werden.

* ```public void writeFrames(int from, int to, boolean original, boolean edges, double edgeLightnessCutoff)```: Speichert die Bilder aus den frames von ```from``` bis ```to``` in Bilddateien. Fall ```original``` ```true``` ist, werden die Originalbilder gespeichert (das Namensschema lautet: <name>_<frame-Nummer>.png). Falls ```edges``` ```true``` ist, werden die Ergebnisse der Kantendetektion mit dem Cutoff ```edgeLightnessCutoff``` ausgegeben (das Namensschema lautet: <name>_<frame-Nummer>_edges.png). Hier empfiehlt es sich sehr, zunächst sowohl in dieser Methode als auch in der ersten Implementation von ```DICOMFrame``` ohne die Kantendetektion zu arbeiten, sondern erst zu überprüfen, ob die eingelesenen frames korrekt als png gespeichert werden.

### DICOMFrame: Ein einzelner frame

Implementieren Sie zudem eine Klasse ```DICOMFrame```, die ein einzelnes frame verwaltet. Sie soll folgende Methoden enthalten:
* Einen Constructor ```public DICOMFrame(BufferedImage image)```: Merkt sich das übergebene BufferedImage.
* ```public BufferedImage getImage()```: Gibt das Bild des frames zurück
* ```public BufferedImage getEdges(double brightness)```: Gibt ein ```BufferedImage``` (im Farbmodus ```BufferedImage.TYPE_BYTE_GRAY```) mit den Ergebnissen der Kantendetektion mit dem Skalierungsfaktor ```brightness``` auf dem Bild des frames aus. Falls noch keine Katendetektion durchgeführt wurde oder die letzte Kantendetektion mit einem anderen Skalierungsfaktor durchgeführt wurde, wird zunächst die Kantendetektion mit dem Skalierungsfaktor ```brightness``` durchgeführt. Sonst wird das Ergebnis der letzten Kantendetektion zurückgegeben (die Kantendetektion dauert einen Moment, sie sollte also nicht unnötig mehrmals durchgeführt werden).

Der Skalierungsfaktor bei der Kantendetektion ist ein Faktor, mit dem die berechneten Kantenwerte multipliziert werden sollen (nachdem die im Theorieteil beschriebene Skalierung auf Werte von 0-255 durchgeführt wurde). Alle Werte, die danach über 255 liegen, werden wieder auf 255 gesetzt. Dadurch wird es ermöglicht, auch schwache Kanten hervorzuheben.

Es bietet sich zudem an, mindestens die folgenden Hilfsmethoden zu verwenden:
* ```private void detectEdges()```: Führt die Kantendetektion mit dem letzten angegebenen Skalierungsfaktor durch (den Sie sich ja in ```getEdges``` sowieso merken müssen) und speichert das Ergebnis in einem ```BufferedImage``` (welches Sie dann in ```getEdges``` zurückgeben können).
* ```private int getGrayscalePixel(BufferedImage image, int x, int y)```: Gibt den Pixel an den Koordinaten ```x``` und ```y``` im Bild ```image``` als Grauwert zurück. Der empfundene Grauwert eines RGB-Wertes kann nach der Formel: ```Grauwert = 0.2126*Rotwert + 0.7152*Grünwert + 0.0722*Blauwert``` berechnet werden. Hintegrund ist das durch die unterschiedlichen Rezeptoren im menschlichen Auge hervorgerufene Helligkeitsempfinden.

### DICOMDiagnostics

Implementieren Sie zuletzt eine Main-Klasse ```DICOMDiagnostics```, die nur die Datei ```data/angiogram1.DCM``` als ein ```DICOMImage``` einliest und von einem frame Ihrer Wahl sowohl das Originalbild als auch das Ergebnis der Kantendetektion mit einer brightness Ihrer Wahl (es sollten aber Kanten darauf erkennbar sein - und zwar von den Blutgefäßen, nicht einfach nur die umlaufende Kante des Bildes) ausgibt.

Da automatische Tests mit nativen Bibliotheken schwer umzusetzen sind, wenn die Entwicklung mit unterschiedlichen Systemen stattfindet, und ich Ihnen wieder lauter Testfehler ersparen möchte, ersetzen Sie bitte wieder einfach die zwei Bilder ```Aufgabe1_frame.png``` sowie ```Aufgabe1_edge.png``` im Verzeichnis ```Bilder``` durch die Ausgaben Ihres Programms. Sie sollten dann hier erscheinen:

Frame:
![Frame](Bilder/Aufgabe1_frame.png)

Edge:
![Edge](Bilder/Aufgabe1_edge.png)
