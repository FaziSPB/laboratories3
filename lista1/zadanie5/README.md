# Task 5 – Checkstyle

Checkstyle checks Java source code against a coding standard: whitespace, braces,
naming conventions, imports, line length and so on. Unlike PMD (task 4), it looks at
how the code is written, not at what it does.

## Installation in VS Code

1. Extension **Checkstyle for Java** (`shengchen.vscode-checkstyle`):

   ```bash
   code --install-extension shengchen.vscode-checkstyle
   ```

   `.vscode/settings.json` points it to `checkstyle.xml` and Checkstyle 14.3.0.
   Violations show up as squiggles in the editor and in the Problems panel.
   You can also right-click a file and choose **Checkstyle: Check Code**.
2. **maven-checkstyle-plugin** 3.6.0 in `pom.xml`, using Checkstyle 14.3.0 (the plugin
   bundles 9.x by default) and the same `checkstyle.xml`.

## Files

| File | Purpose |
|------|---------|
| `checkstyle.xml` | Rule set: whitespace, braces, naming, imports, misc |
| `src/main/java/test/checkstyle/StyleTest.java` | Class with deliberate style mistakes (21 violations) |
| `.vscode/settings.json` | Extension configuration |
| `.vscode/tasks.json` | Tasks that run Checkstyle through Maven |

## Usage

In VS Code, open the `zadanie5` folder, then *Terminal → Run Task...*:

- **Checkstyle: check code (Maven)**: `mvn checkstyle:check`; violations go to the Problems panel and the build fails.
- **Checkstyle: generate report (Maven)**: `mvn checkstyle:checkstyle`; HTML report in `target/reports/checkstyle.html`.

From the command line:

```bash
mvn checkstyle:check
mvn checkstyle:checkstyle
```
