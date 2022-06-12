# SiteInspector - Teilaufgabe 1

Bei Ausgrabungen werden meist noch vor Ort Zeichnungen von den Fundstücken angefertigt. Oft sind bestimmte Charakteristiken darauf klarer zu erkennen als auf Fotos. Heute werden wir ein Tool entwickeln, das die Fotos den Zeichnungen gegenüberstellt und z.B. Illustrationen für wissenschaftliche Publikationen erstellen kann. Die Bildbeispiele zeigen altsteinzeitliche Ritzzeichnungen auf Ocker aus der Blombos-Höhle in Südafrika. Die Publikation, welche diese Aufgabe inspiriert hat, finden Sie [hier](https://www.sciencedirect.com/science/article/pii/S0047248409000207).

### ImageSeries: Verwaltung einer Bildreihe

Implementieren Sie eine Klasse ```ImageSeries```, die alle Bilddateien (Frames) aus einer Dateiliste einliest und verwaltet. Lesen Sie bitte zunächst die Aufgabe bis zum Ende: Es ist sehr empfehlenswert, die Implementation nicht stur in dieser Reihenfolge durchzuführen, sonder stückweise ```ImageSeries``` und ```Frame``` parallel zu implementieren, so dass man immer testen kann, ob die bisher implementierte Funktionalität auch korrekt ist.

```ImageSeries``` soll folgende Methoden enthalten:
* Einen Konstruktor ```ImageSeries(File[] files)```: Bekommt die einzulesenden Dateien. Merkt sich die Dateipfade, und liest alle Bilder aus ```files``` in eine interne Liste (am besten eine ```ArrayList``` - wir werden immer wieder über den Index auf die frames zugreifen) von ```Frame``` ein. Dabei besteht ein Datensatz für ein ```Frame``` aus mindestens je einem einzulesenden Foto und einer Zeichnung. Per Konvention endet die dem Foto zugehörige Zeichnung auf dass Suffix *_tracing*. Heisst also das Foto z.B. *engraving-1.png*, so muss der Name der Zeichnung *engraving-1_tracing.png* lauten. Ist zu einem in ```files``` angegebenen Foto keine Zeichnung vorhanden, soll ein Fehler signalisiert werden. Optional können auch Grauwertbilder (mit der Endung *_grayscale*) und halbtransparente Überblendungen (mit der Endung *_overlay*) geladen werden. Da diese jedoch vom Programm erst erzeugt werden, sollen eventuelle Exceptions ignoriert und nur das entsprechende ```BufferedImage``` auf null gesetzt werden.

* ```public void writeFrames(int from, int to, boolean grayscale, boolean overlay, boolean combined)```: Speichert die Bilder aus den frames von ```from``` bis ```to``` in Bilddateien im *.png*-Format. Fall ```grayscale``` ```true``` ist, werden die entsättigten Fotos gespeichert (mit dem gleichen Namensschema wie oben beschrieben). Falls ```overlay``` ```true``` ist, werden die halbtransparente Überblendungen ausgegeben (wieder mit dem oben angegebenen Namensschema). Falls ```combined``` ```true``` ist, wird das Resultatbild mit der Endung *_combined* ausgegeben. Hier empfiehlt es sich sehr, zunächst sowohl in dieser Methode als auch in der ersten Implementation von ```Frame```, auf die Bildverarbeitung zu verzichten, und erst zu überprüfen, ob die eingelesenen frames korrekt als png gespeichert werden.

### Frame: Ein Einzelbild

Implementieren Sie zudem eine Klasse ```Frame```, die ein einzelnes frame verwaltet. Sie soll folgende Methoden enthalten:
* Einen Constructor ```public Frame(BufferedImage image, BufferedImage grayscaleImage, BufferedImage tracing, BufferedImage overlay) {}```: Merkt sich die übergebenen BufferedImages. Wenn ```grayscaleImage``` ```null``` ist, wird ```computeGrayscaleImage()``` aufgerufen. Ist ```overlay``` ```null```, wird ```computeOverlay()``` aufgerufen. In jedem Fall wird am Ende mittels ```computeCombinedImage()``` das Resultatbild berechnet.
* ```public BufferedImage getImage()```: Gibt das Originalfoto zurück
* ```public BufferedImage getTracing()```: Gibt das Originalzeichnung zurück
* ```public BufferedImage getGrayscaleImage()```: Gibt das entsättigte Originalfoto  (im Farbmodus BufferedImage.TYPE_BYTE_GRAY) zurück
* ```public BufferedImage getTracing()```: Gibt die invertierte Originalzeichnung mit als RGB-Bild mit Alpha-Kanal zurück
* ```public BufferedImage getCombinedImage(boolean showTracing)```: Gibt das Resultatbild zurück. Ist ```showTracing``` true, soll das Originalfoto mit dem Overlay überblendet werden, andernfalls wird nur das Originalfoto dem Grauwertbild gegenübergestellt. Hat sich der Wert von ```showTracing``` seit dem letzten Aufruf verändert, so muss ```computeCombinedImage()``` erneut aufgerufen werden.

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
