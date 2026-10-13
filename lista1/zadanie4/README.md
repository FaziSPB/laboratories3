# Task 4 – PMD

PMD (Programming Mistake Detector) is a static code analyzer that finds bad practices
(unused code, empty catch blocks, naming conventions, unnecessary object creation, ...).

This project follows the [PMD Eclipse tutorial](https://www.javatips.net/blog/pmd-in-eclipse-tutorial),
adapted to VS Code + Maven.

| Tutorial step (Eclipse)                        | Here (VS Code)                                             |
|------------------------------------------------|------------------------------------------------------------|
| Install PMD plugin                             | PMD CLI 7.28.0 in `~/opt`, linked as `/usr/local/bin/pmd` + `maven-pmd-plugin` |
| Window → Preferences → PMD → Rules             | `pmd-ruleset.xml`                                          |
| Create class `test.pmd.PMDTest`                | `src/main/java/test/pmd/PMDTest.java`                      |
| Right click → PMD → Check Code, Violations view | Task **PMD: check code** → Problems panel + squiggles     |
| Generate reports                               | Task **PMD: generate report (Maven)** → `target/reports/pmd.html` |

## Usage

Open the `zadanie4` folder in VS Code, then *Terminal → Run Task...*:

- **PMD: check code** – runs the PMD CLI; violations appear in the Problems panel.
- **PMD: generate report (Maven)** – `mvn pmd:pmd`, HTML report in `target/reports/pmd.html`.
- **PMD: check build (Maven)** – `mvn pmd:check`, the build fails if there are violations.

From the command line:

```bash
pmd check -d src/main/java -R pmd-ruleset.xml -f text
mvn pmd:pmd pmd:check
```
