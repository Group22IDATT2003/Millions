# Millions

Et askjemarkedssimuleringspill utviklet i Java som en del av Programmering 2 (IDATT2003) ved NTNU. Applikasjonen lar spillere kjøpr og selge akjser, administrere en portefølje, spore transaskjoner, analysere markedsstatistikk og gå gjennom simulerte handelsuker.

## Spillutviklere
* Cathrine Kristiansen, Kandidatnummer: 10101
* Meenakshi Jayachandran, Kandidatnummer: 10068

## Funksjoner
- Lage et nytt spill med tilpasset startkapital
- Laste inn aksjedata fra CSV filer
- Kjøp og selg aksjer
- Porteføljehåndtering
- Transaksjonshistorikk 
- Ukentlig aksjekursssimulering
- Markedstatestikk
  * Vinnere og tapere
  * kurshistorikk
  * Høyeste og laveste aksjekurser
- System for spillerformue og status
- Grafisk brukergrensesnitt bygget med JavaFX
- MVC arkitektur med Observer-mønster
- Fabrikkmønster for transaksjnosoppretting
- Enhetstesting med JUnit

## Verktøy brukt
- Java 25
- Maven
- JavaFX
- JUnit 5
- GitHub
- Checkstyle

## Prosjekt Struktur
```text
src
├── main
│   ├── java
│   │   └── no
│   │       └── ntnu
│   │           └── idatt2003
│   │               └── group22
│   │                   └── millions
│   │                       ├── controller
│   │                       ├── model
│   │                       ├── transaction
│   │                       ├── market
│   │                       ├── io
│   │                       ├── observer
│   │                       ├── factory
│   │                       └── view
│   └── resources
│
└── test
    └── java
```

## Design
Prosjektet bruker flere designmønstre:

### MVC (Model-View-Controller)
Det grafiske brukergrensesnittet er strukturert med MVC for å skille:
- forretnignslogikk
- presentasjon
- brukerinteraksjon

### Observer-mønster
Visninger observerer endringer i modelen og oppdateres automatisk når data endres. 

### Factory-mønster
Transaksjoner opprettes gjennom en transaksjonsfabrikk for å forenkle objektoppretting og redusere kobling. 

## Hvordan kjøre spillet:
1. Klon Repository eller last ned ZIP-fil
2. Bygg prosjektet:
   mvn clean package
4. Kjør applikasjonen:
   mvn javafx:run

## CSV fil formaat
Aksje data filen må følge denne formaten:

| Symbol | Navn | Pris |
|--------|----------|------|
| AAPL | Apple Inc. | 276.43 |
| MSFT | Microsoft | 404.68 |
| NVDA | Nvidia | 191.27 |

Format: *symbol,navn,pris*

## Testing
Prosjektet inkluderer både positive og negative enhetstester for forretningskritisk funskjonalitet som:
- transaksjoner
- portefljehåndtering
- aksjestatistikk
- statusberegninger
- filhåndtering

Kjør tester med: 
*mvn test*

## Status system
Spilere går gjennom ulike statusnivåer avhengig av ytelse:

| Status      | Krav |
|-------------|------|
| Nybegynner  | Startnivå |
| Investor    | 10+ uker og 20 % profitt |
| Spekulant   | 20+ uker og doblet nettoformue |

## Fremtidige forbedringer
- system for lagring/innlasting av spill
- liste over topscore
- aksjevarsler
- støtte for flerspillere

## KI bruk:
KI-verktøy ble brukt som en støttende ressurs for:
- diskusjon av arkitektur
- feilsøking
- forbedring av kodekvalitet
- generering av ideer og dokumentasjon

Alt som ble generert av KI ble gjennomgått, modifisert og testet før bruk

## Lisens
Dette prosjektet ble utviklet for utdanningsformål som en del av IDATT2003 ved NTNU


