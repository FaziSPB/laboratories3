# Task 1 – Development environment

The IDE used for the Java labs is **Visual Studio Code** (1.137.0) with:

- JDK: OpenJDK 25 (projects are compiled with `--release 21`)
- Maven 3.9.12
- Git 2.53

## VS Code extensions

| Extension                          | Purpose                                         |
|------------------------------------|-------------------------------------------------|
| `vscjava.vscode-java-pack`         | Extension Pack for Java (installs the ones below) |
| `redhat.java`                      | Java language support (editing, compiling, refactoring) |
| `vscjava.vscode-java-debug`        | Debugger                                        |
| `vscjava.vscode-java-test`         | Running JUnit tests                             |
| `vscjava.vscode-maven`             | Maven projects and goals                        |
| `vscjava.vscode-java-dependency`   | Project and dependency view                     |
| `shengchen.vscode-checkstyle`      | Checkstyle (task 5)                             |
| `bierner.markdown-mermaid`         | Mermaid UML diagrams in Markdown (task 6)       |

Installation:

```bash
code --install-extension vscjava.vscode-java-pack
```

Git support is built into VS Code (*Source Control* view, `Ctrl+Shift+G`).
