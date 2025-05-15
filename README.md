# software-testing-project

## Project Description
Weboldal tesztelése, User Story-k és bug report-ok készítése JIRA-ban, feature file létrehozása és implementálása az órákon használt és ismertetett technológiák és tool-ok segítségével. 

A weboldal az EPAM Community-Z (https://wearecommunity.io/) bármelyik oldala, ez szabadon választható, akár az 'About' vagy 'Articles' vagy akár több page tesztelése. FONTOS: Az oldalra belogolni illetve regisztrálni nem lehet! 

A User story-kat a meglévő viselkedés alapján kell létrehozni JIRA-ban, bug reportokat valós, avagy vélt hibákra lehet felvenni. 

A projekt pontozása: 

Maximum 40 pont 

Sikeres védés: 12 pont 

Megfelelő minőségű kód, feature file: 8 pont 

Megfelelő minőségű user story: 3 pont/db – 3-5 db 

Megfelelő minőségű bug report: 3 pont/db – 3-5 db 

A projekteket GitLabon, GitHubon vagy Bitbucketen kell létrehozni. A csapatoknak ezekben a projekteben kell a megoldásokat elérhetővé tenni majd az oktatók számára. 

Hasonlóképpen a JIRA projekteket is a csapatoknak kell létrehozni és elérhetővé tenni az Atlassian oldalán. 

## Getting Started
1. Clone the repo
```shell
git clone https://github.com/Sciencewolf/software-testing-project.git
cd software-testing-project
```
2. Run the tests in the default mode (headless Chrome)
```shell
mvn clean install
```
3. (Optional) Run the tests with visible Chrome browser (non-headless mode)
```shell
mvn clean install -Dheadless=false
```
> ℹ️ By default, the tests run in **headless mode**, meaning the Chrome browser window will not be visible.  
> You can pass the `headless=false` system property to see the browser during test execution.


## Tech Stack
<p align="center">
    <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="java icon" />
    <img src="https://www.vectorlogo.zone/logos/apache_maven/apache_maven-icon.svg" alt="maven icon" />
    <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git icon" />
    <img src="https://www.vectorlogo.zone/logos/jetbrains/jetbrains-icon.svg" alt="JetBrains icon" />
    <img src="https://raw.githubusercontent.com/edent/SuperTinyIcons/master/images/svg/github.svg" alt="github icon" width="64" height="64"/>
</p>