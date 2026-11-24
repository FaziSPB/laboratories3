# Technologie programowania – Lista 3 (design patterns)

Projects come from J. Pereira's `javatrainings` repository (Google Code Archive,
`designpatterns/trunk`). Exercise sheets linked from `TPLab03.pdf` are in `zadania/`
(`zad1.pdf` … `zad9.pdf`).

In every `pom.xml`, as the sheet requires: Mockito `3.2.4`, `source`/`target` `1.8`.

## Run

```bash
cd adapter && mvn test
```

Every project is green (`mvn test`): adapter 31, builder 3, singleton 3, factorymethod 4,
abstractfactory 2, composite 6, state 9, decorator 16, facade 1, chainofresponsability 5,
iterator 7.

## Code changes

Commit `678a393` holds the original sources (with the `pom.xml` fix already applied),
commit `a5125a0` holds the solutions. List of changed files:

```bash
git show a5125a0 --stat
```

Full diff:

```bash
git diff 678a393 a5125a0 -- lista3
```

## Tasks

### 1. Adapter (`adapter/`)
- `ThirdPartyDoorAdaper` – **class adapter**: `extends ThirdPartyDoor implements Door`.
  It calls the inherited methods (`unlock`, `setState`, `lock`, `setNewLockCode`).
- `ThirdPartyDoorObjectAdapter` – **object adapter**: `implements Door` only and holds a
  `ThirdPartyDoor` field it delegates to (composition instead of inheritance).
- Translating the interface: `open` = `unlock` + `setState(OPEN)`; `close` = `setState(CLOSED)` + `lock`;
  `testCode` tries `unlock` and restores the previous lock state; `changeCode` unlocks with
  the old code, checks the confirmation, then calls `setNewLockCode`. Third-party exceptions
  are mapped to `IncorrectDoorCodeException` / `CodeMismatchException`.
- Tests `ThirdPartyAdapterTest` and `ThirdPartyDoorObjectAdapterTest` now create the adapters
  and use `ThirdPartyDoor.DEFAULT_CODE`.

### 2. Builder (`builder/`)
- New `model/ReportBodyBuilder` (`buildCustomer`, `buildItems`, `getReportBody`) and one
  builder per format: `JSONReportBodyBuilder`, `XMLReportBodyBuilder`, `HTMLReportBodyBuilder`.
- `ReportAssembler` is now the **director**: `getReport(ReportBodyBuilder)` runs the steps
  without knowing the format. No `if (type.equals(...))`, so a new format means a new builder
  and no change to `ReportAssembler` (OCP). `ReportAssembler` depends only on the abstraction (DIP).
- Tests pass a builder instead of the string `"JSON"`/`"XML"`/`"HTML"`.

### 3. Singleton (`singleton/`)
- Constructor of `ReportBuilder` is `private`, so nobody can bypass `getInstance()`.
- `testMultiThreading` failed because several threads saw `instance == null` at the same time
  and each created its own object (race condition).
- Fix: **double-checked locking** – first check without a lock, then `synchronized (ReportBuilder.class)`,
  second check inside. The field is `volatile`, otherwise another thread could see a reference
  to a not fully constructed object (instruction reordering).
- Measured (8 threads × 20M `getInstance()` on a trivial singleton, JDK 25):
  `synchronized` method ≈ 11–46 s, DCL ≈ 17–96 ms. A synchronized method takes the lock on
  every call, while DCL locks only until the instance exists.

### 4. Factory Method (`factorymethod/`)
- `ReportGenerator` is abstract: `generateReport(ReportData)` calls the **factory method**
  `protected abstract Report createReport()`.
- Subclasses `JSONReportGenerator`, `XMLReportGenerator`, `HTMLReportGenerator`, `PDFReportGenerator`
  decide which `Report` to create. The `if` chain is gone (OCP, DIP).
- Tests create the matching generator subclass.

### 5. Abstract Factory (`abstractfactory/`)
- New `ReportFactory` interface (`createHeader`, `createBody`, `createFooter`) and two
  families: `json/JSONReportFactory`, `xml/XMLReportFactory`.
- `Report(ReportFactory)` builds itself with the factory, with no `if` and no import of
  concrete JSON/XML classes. It also cannot mix elements from different formats.

### 6. Composite (`composite/`)
- `Shape.asComposite()` returns `null` by default; `CompositeShape` overrides it to return `this`
  (polymorphism instead of `instanceof`).
- `Shape.move()` moves itself and, if it is a composite, all children recursively.
- `CompositeShape`: list created in `createShapesList()`, `addShape`, `removeShape`,
  `getShapeCount`, `getShapes`, `getShapesByType` (direct children),
  `getLeafShapes` (recursively collects shapes that are not composites).

### 7. State (`state/`)
- `ApplianceState` enum: every constant returns its behaviour object (`getStateBehavior()`).
- `Toaster` delegates `turnOn/turnOff/start/stop` to the current `ApplianceStateBehavior` and
  stores the state it returns. The state objects decide which transitions are allowed.
- `OffState`, `OnState`, `StartedState` – TODOs completed (return `this` when the transition
  is not allowed, otherwise the behaviour of the next state).

### 8. Decorator (`decorator/`)
- New decorator `WordCensor` (extends `SocialChannelDecorator`) replaces every censored word with
  `###` and passes the message on to the wrapped channel. No existing code was changed.
- `WordCensorTest` – `"Microsoft Windows is great!"` → `"### Windows is great!"`.
- `ChainCensorDecoratorTest` – censor + `MessageTruncator(20)` + `URLAppender` chained together.
- The Cobertura report step from the PDF was skipped: the old Cobertura plugin does not work on
  current JDKs.

### 9. Facade (`facade/`)
- `DefaultBooktoreFacade implements BookstoreFacade`: `placeOrder` finds the customer and the book,
  creates an order, dispatches it in the warehouse, then notifies the customer.
  The client calls one method instead of five services.
- The `verify` lines in `BookStoreFacadeTest` require `dispatch(order)` and
  `notifyClient(dispatchReceipt)` (the receipt, not the order) to be called exactly once.
- Services are injected with setters in `createFacade()` (here: Mockito mocks).

### 10. Extra tasks
**Chain of Responsibility (`chainofresponsability/`)** – three filters extending `AbstractFilter`
(in test `fakes/`): `FakeLoggingFilter` and `FakeAuthorizationFilter` write a prefix and then pass
the request on with `chain.doFilter(...)`; `FakeFormatFilter` passes it on first and writes the
suffix after the servlet answers. Each filter knows only "the next one", so the order and set of
filters can change without touching the servlet.

**Iterator (`iterator/`)** – `CommandJob implements Iterable<Command>`; `iterator()` returns
an iterator over an unmodifiable view of the commands, so the client can go through the job
without seeing its internal `List`. The commented part of `CommandJobIteratorTest` is uncommented
and passes (3 commands).
