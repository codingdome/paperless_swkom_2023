*©Dominik Englert, if21b151, 2023<br>
©Artjom Moisejev, if21b055, 2023<br>
©Viktor Bartosik, if21b082, 2023<br>
<br>
Technikum Wien<br>
Software Engineering 1<br>
time-spent: ~ 200 h*

# PAPERLESS "DOC" Softwarekomponentensystem
## Protocol
Willkommen bei unserem Paperless System "DOC". Dem wohl beeindruckensten Dokumentenverwaltungssystem aller Zeiten.

![title](src_readme/doc-logo.png)

## Server Architektur
Die folgende Grafik zeigt den recht komplexen Aufbau von DOC. Im Zentrum stehen drei Java-Server, sowie ein React Frontend. 

### Doc REST
Der Doc REST Server übernimmt sämtliche Aufgaben bezogen auf File Upload, Löschen, Updaten (CRUD Operations). 

### Doc SERVICE
Der Doc SERVICE Server enthält einen OCR Worker mittels Tesseract für PNG, JPG, JPEG Dateien, sowie PDFBox für Pdf-Dateien. Andere Datei-Formate werden nicht unterstützt. Pdfs mit Bild-Content werden vom OCR Worker nicht richtig erfasst.

### Doc PROTOCOL
Doc PROTOCOL stellt in einem POC einen Server zum Monitoring, sowie zur Kontroller der 2 Hauptserver dar. Seine Funktionen umfassen unteranderem momentane Daten einzulesen, sowie alle Datenbanken zu löschen. Theoretisch denkbar wären deutlich weitreichendere Funktionalitäten, wie z.B. automatisch integrierte Test.

![title](src_readme/architecture.png)

### RabbitMQ
Als Message Broker liegt der Applikation RabbitMQ zugrunde. In dieser Architektur übernimmt er vor allem das Erfassen von neuen Uploads im Doc REST und der damit folgenden Nachricht an Doc SERVICES zum Start der OCR Worker.

### Daten
Der Applikation liegen 3 Datenbanken zugrunde. Einmal eine PostgreSQL Datenbank zum Speichern der Files nach angegebenen Schema, ElasticSearch zur Indexierung der Ergebnisse der OCR Worker für Suchfunktionalität, sowie MinIO zum speichern der File-Daten selbst als BLOB. Alle drei Speicher agieren unabhängig von einander und werden von den jeweiligen Repositories bzw. Services angesprochen.

### Frontend
![title](src_readme/landing.png)

Das React Frontend ist ebenso wie sämtliche Server von Grund auf neu entwickelt um allen Anforderungen gerecht zu werden. Als CSS Framework kommt TailwindCSS zum Einsatz. Die Vorgestaltung erfolgte in Figma.

### Frontend Dashboard
![title](src_readme/dashboard-1.png)
![title](src_readme/dashboard-2.png)

Über das eigens entwickelte Dashboard (zu erreichen unter localhost:80/dashboard) erhält man Einsicht in die aktuellen Stati der Server, sowie einen allgemeinen Überblick über die Speicherstände der Datenbanken. Darüber hinaus bietet das Dashboard die Möglichkeit einen Integration Test vom Frontend aus zu starten. Dieses durchläuft die wichtigsten Schritte der Applikationen und kontrolliert diese jeweils auf ihre richtige Durchführung. Sämtliche dadurch entstandenen Änderungen werden am Ende des Tests rückgängig gemacht.

## Einrichtung & Start

## Known Issues

### Funktionalität

### Good Practice

### Nice To Have