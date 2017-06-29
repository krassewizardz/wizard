# Technische Dokumentation

# Inhalt
1. Komponentenübersicht
2. Projektdaten
- 1. MySQL Datenbank
- 2. sqlite Daten
- 3. Desktopanwendung
- - 1. Übersicht
- - 2. Softwarearchitektur
- - 3. Geschäftslogik
- - 4. Entitäten
- - 5. Repositories
- - 6. Services
- - 7. GUI

# Komponentenübersicht

Die Anwendung "Export Wizard" setzt sich aus drei Komponenten zusammen die alle lokal vorhanden sein müssen. Die auf MySQL basierende Datenbank des DidaktWizards, aus der die Daten für die PDF-Generierung stammen. Die Anwendung hat nur lesenden Zugriff auf diese Datenbank. Zweite Komponente ist eine SQLite-Datenbank, die für die Datenhaltung von Userdaten und Konfigurationstemplates zuständig ist. Die dritte Komponente ist die Desktop-Anwendung, über die Benutzer sich einloggen und PDFs zu einer Beruf/Ausbildungsjahr Kombination exportieren können. Die Desktopanwendung verbindet dabei die zwei Datenbanken mit der Geschäftslogik und stellt dafür ein GUI bereit.

# Projektdaten

Das Projekt wurde in einer Cross-Platform Umgebung entwickelt und ist Platformunabhängig. Zum Einsatz kamen Windows, Linux und macOS Betriebssysteme.

## MySQL Datenbank des Didakt-Wizard
MySQL ist ein Open-Source RDBMS das unter dem Schirm der Oracle-Corporation weiterentwickelt wird. Für die Datenabfragen wird die SQL-Syntax verwendet. Die MySQL-Datenbank wurde in einem Dockercontainer repliziert oder lokal installiert. Für die Anbindung über Dockercontainer wird die aktuelle Docker Version 17-CE benötigt. Für eine lokale Installation der mySQL-Datenbank wird die aktuelle Version der MySQL-Datenbank bzw. MariaDB benötigt.

## SQLite Datenbank

SQLite ist eine freie „Mini-SQL-Datenbank“, die im Gegensatz zu MySQL oder PostgreSQL keinen laufenden Dienst benötigt. Klassische DBMS (Database Management Services) kommunizieren über Sockets oder IP mit Anwendungen und koordinieren deren Zugriffe auf die Datenbanken. Bei SQLite greifen Anwendungen direkt auf die Dateien mit den Datenbanken zu. Da es sich um eine Einzelplatzanwendung handelt ist SQLite als Datenbank ausreichend. SQLite wird in Version >=3.19.3 benötigt.

## Desktopanwendung

### Übersicht

Die Desktopanwendung wurde in Java 8 entwickelt und wird als ausführbare .jar Datei ausgeliefert. Es handelt sich um eine JavaFX-Anwendung. Sie benötigt keine Installation. Verbindungen und Datenbankabfragen werden über die SQL2O-Library hergestellt. Die Konfiguration der Connectionstrings wird über ein JSON-Dokument('config.json') im root-Folder der Anwendung verwaltet. Für das Parsen von JSON-Dokumenten wird die Library org.json verwendet. Das Generieren und Exportieren von PDFs wird mit Hilfe der IText-Library implementiert.

### Softwarearchitektur

Die Architektur wurde zuerst in zwei Teile aufgeteilt. Die Geschäftslogik bildet einen Teil und ist unabhängig von der GUI. Dies ermöglicht eine spätere Migration der Geschäftslogik zu einer Webanwendung.
Die Geschäftslogik wurde in kleinere unabhängige Services unterteilt. Diese Kommunizieren über vorher definierte Interfaces.

#### Geschäftslogik

Die Geschäftslogik wurde in Repositories, ServiceProvider und Models unterteilt und enthält die eigentliche Logik. Die ServiceProvider bieten Dienste für Datenbankconnections, PDF-Export, Authentifizierung und Authorisierung, Verwalten von Templates. Diese ServiceProvider bieten Interfaces und die konkreten Klassen implementieren die Funktionalität abhängig von den Anforderungen. Die ServiceProvider werden in den aufrufenden Klassen über Interfaces angesprochen. Die Repositories greifen über die ServiceProvider auf die Datenbanken zu und liefer Models oder Listen von Models zurück.

Um sich vor SQL-Injection zu schützen, werden sämtliche Queries in den Repositories nur als prepared-Statement aufgerufen.

Über den SQLiteAuthenticationService wird sichergestellt, dass sämtliche Passwörter nur gehashed in die lokale sqlite-Datenbank abgelegt werden.

#### Entitäten

**Report**
*Generelle Information über Report*
- String department
- Profession profession
- String teachingForm
- String director

**Profession**
*Kombination von Beruf und Ausbildungsjahr*
- int id *Primary Key aus der Datenbank*
- String name *Berufname*
- int yearOfTraining *Ausbildungsjahr*
- List subjects *Liste der Lernbereiche*

**Subject**
*Lernbereich*
- int id *Primary Key aus der Datenbank*
- String name *Berufname*
- List fields *Liste der Lernfelder*

**Field**
*Lernfeld*
- int id *Primary Key aus der Datenbank*
- String name *Berufname*
- int duration *Dauer des Lernfeldes*
- List situations *Liste der Situations*

**Situation**
*Lernsituation*
- int id *Primary Key aus der Datenbank*
- String name *Berufname*
- int duration *Dauer der Situation*
- int start *Anfangszeit*
- int end *Endzeit*
- String scenario *Lernszenario*
- String outcome *Handlungsprodukt*
- String competences *Lernkompetenzen*
- String content *Inhalt*
- String materials *Unterrichtsmaterialien*
- comments *Kommentar*
- List techniques *Liste der Lerntechniken*
- List achievments *Liste der Erfolge*

**Technique**
*Lerntechnik*
- int id *Primary Key aus der Datenbank*
- String name *Berufname*

**Achievment**
*Erworbene Kompetenz*
- int id *Primary Key aus der Datenbank*
- String name *Berufname*

**Configuration**
*Konfigurationstemplate*
- int id *Primary Key aus der Datenbank*
- String name *Name des Templates*
- int userId *id des Users*
- bool scenario *Flag*
- bool outcome *Flag*
- bool competence *Flag*
- bool content *Flag*
- bool materials *Flag*
- bool comments *Flag*
- bool techniques *Flag*
- bool achievements *Flag*

#### Repositories

**ReportSQLRepository**
*Für Datentransfer der ReportEntitäten*
- DBServiceProvider dbServiceProvider *Interface fuer DBService*
- ReportSQLRepository (DBServiceProvider dbsp) *CTOR mit DI*
- Report get(Profession p, int yearOfTraining)

**ProfessionSQLRepository**
*Für Datentransfer der ProfessionEntitäten*
- DBServiceProvider dbServiceProvider *Interface fuer DBService*
- ProfessionSQLRepository (DBServiceProvider dbsp) *CTOR mit DI*
- List getAllProfessionsWithId()

**SubjectSQLRepository**
*Für Datentransfer der SubjectEntitäten*
- DBServiceProvider dbServiceProvider *Interface fuer DBService*
- SubjectSQLRepository (DBServiceProvider dbsp) *CTOR mit DI*
- List getAllSubjectsForProfession(Profession p)

**FieldSQLRepository**
*Für Datentransfer der FieldEntitäten*
- DBServiceProvider dbServiceProvider *Interface fuer DBService*
- FieldSQLRepository (DBServiceProvider dbsp) *CTOR mit DI*
- List getAllFieldsForSubject(Subject s)

**SituationSQLRepository**
*Für Datentransfer der SituationEntitäten*
- DBServiceProvider dbServiceProvider *Interface fuer DBService*
- SituationSQLRepository (DBServiceProvider dbsp) *CTOR mit DI*
- List getAllSitutationsForField(Field f)
- List getAllAchievments(Situation s)
- List getAllTechniques(Situation s)

#### Services

**DBServiceProvider T**
*Generisches Interface, das Connections zu einer Datenbank bereitstellt*
- T open() - T ist Connection

**SQL2ODBServiceProvider**
*Konkrete Implementierung um über die SQL2O-Library Connections zu erstellen*
- Connection open()

#### GUI

**ViewManager**
*Für Wechsel zwischen den Views*
- ViewManager getInstance *ViewManager als Singleton*
- Scene loadSceneFromFile *erstellt neue Scene aus fxml und css Datei*
- display(View view) *Wechselt zur angeforderten View*

**LoginController**
*Controller für Login.fxml*
- onLogin *Loggt den User ein und navigiert zu Overview oder Registrierung*
- onGuestLogin *Loggt den Gast ein und navigiert zu Overview*
- boolean formIsValid *prüft die Form*
- updateFormValidationState *setzt Fehlermeldung zurück*

**OverviewController**
*Controller für Overview.fxml*
- savePDF *öffnet Dialog zum abspeichern der PDF*
- back *loggt den User aus und navigiert zum Login*
- saveConfiguration *speichert aktuelle Konfiguration*
- loadConfiguration *lädt ausgewählte Konfiguration*

**RegistrationController**
*Controller für Registration*
- back *navigiert zum Login*
- register *legt neuen Benutzer an*
