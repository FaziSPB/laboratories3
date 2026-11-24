# Ściąga – Lista 3 (wzorce projektowe)

Pokazywanie zmian: `lista3/rozwiazania/zadN-*.diff` albo `git diff 678a393 a5125a0 -- lista3/<projekt>`.
Uruchomienie: `cd lista3/<projekt> && mvn test`.
pom.xml: mockito 3.2.4, source/target 1.8 (stare wersje nie działały na nowym JDK).

---

## 1. Adapter
**Wzorzec:** dopasowuje cudzą klasę do interfejsu, którego oczekuje klient.
**Problem:** `ThirdPartyDoor` ma inne metody (`unlock`, `setState`, `lock`) niż nasz interfejs `Door` (`open`, `close`, `changeCode`).
**Zrobiłem:**
- `ThirdPartyDoorAdaper` – adapter klasowy: `extends ThirdPartyDoor implements Door`.
- `ThirdPartyDoorObjectAdapter` – adapter obiektowy: `implements Door`, w środku pole `ThirdPartyDoor door`.
- Wyjątki third-party zamieniam na nasze (`CannotUnlockDoorException` → `IncorrectDoorCodeException`).
**Pytanie: klasowy czy obiektowy?** Obiektowy: kompozycja, można podmienić obiekt, nie wystawia metod `ThirdPartyDoor` na zewnątrz. Klasowy: mniej kodu, ale dziedziczy wszystko i wiąże się z jedną klasą.

## 2. Builder
**Wzorzec:** oddziela kroki budowania obiektu od jego reprezentacji.
**Problem:** `ReportAssembler` miał `if (type.equals("JSON")) ... else if ("XML") ...` – łamał OCP (nowy format = zmiana klasy) i DIP (zależał od konkretnych klas).
**Zrobiłem:**
- interfejs `ReportBodyBuilder`: `buildCustomer`, `buildItems`, `getReportBody`;
- `JSONReportBodyBuilder`, `XMLReportBodyBuilder`, `HTMLReportBodyBuilder` – osobna klasa na każdy format;
- `ReportAssembler` = dyrektor: `getReport(ReportBodyBuilder)` woła kroki w kolejności, nie zna formatu.
**Pytanie: nowy format?** Nowa klasa buildera, `ReportAssembler` bez zmian.

## 3. Singleton
**Wzorzec:** jedna instancja klasy, globalny dostęp przez `getInstance()`.
**Problem:** `testMultiThreading` padał – kilka wątków naraz widziało `instance == null` i każdy tworzył swój obiekt (wyścig).
**Zrobiłem:**
- konstruktor `private`;
- double-checked locking: sprawdzenie bez blokady → `synchronized (ReportBuilder.class)` → drugie sprawdzenie;
- pole `volatile`.
**Pytanie: po co `volatile`?** Bez niego JVM może przestawić instrukcje i inny wątek zobaczy referencję do obiektu jeszcze nie w pełni zbudowanego.
**Pytanie: czemu nie `synchronized` na metodzie?** Działa, ale blokuje przy każdym wywołaniu. Pomiar (8 wątków × 20 mln wywołań): synchronized ~11–46 s, DCL ~17–96 ms.

## 4. Factory Method
**Wzorzec:** klasa bazowa definiuje algorytm, a podklasy decydują, jaki obiekt utworzyć.
**Problem:** `ReportGenerator` miał `if`-y po typie raportu (OCP, DIP).
**Zrobiłem:**
- `ReportGenerator` abstrakcyjny: `generateReport(data)` woła `createReport()` (metoda fabrykująca);
- podklasy `JSONReportGenerator`, `XMLReportGenerator`, `HTMLReportGenerator`, `PDFReportGenerator`.
**Pytanie: różnica z Abstract Factory?** Factory Method tworzy jeden produkt i opiera się na dziedziczeniu. Abstract Factory tworzy rodzinę produktów i jest przekazywana jako obiekt (kompozycja).

## 5. Abstract Factory
**Wzorzec:** interfejs do tworzenia rodziny powiązanych obiektów bez podawania ich konkretnych klas.
**Problem:** konstruktor `Report` miał `if ("JSON") ... else ...` i importował konkretne klasy JSON/XML.
**Zrobiłem:**
- interfejs `ReportFactory`: `createHeader`, `createBody`, `createFooter`;
- `JSONReportFactory`, `XMLReportFactory`;
- `Report(ReportFactory factory)` – bez `if`-ów.
**Zaleta:** nie da się pomieszać nagłówka JSON ze stopką XML, bo jedna fabryka daje spójną rodzinę.

## 6. Composite
**Wzorzec:** drzewo obiektów, w którym liść i grupa mają ten sam interfejs.
**Problem:** metody w `Shape` i `CompositeShape` były puste (TODO), wszystkie testy czerwone.
**Zrobiłem:**
- `Shape.asComposite()` zwraca `null`, a `CompositeShape` nadpisuje ją i zwraca `this` – polimorfizm zamiast `instanceof`;
- `Shape.move()` przesuwa siebie i, jeśli jest kompozytem, rekurencyjnie dzieci;
- `CompositeShape`: lista dzieci, `addShape`, `removeShape`, `getShapeCount`, `getShapesByType`, `getLeafShapes` (rekurencyjnie zbiera liście).
**Pytanie: co jest liściem?** `Line` (`LeafShape`). `Circle`/`Rectangle`/`Triangle` to kompozyty, nawet gdy nie mają dzieci.

## 7. State
**Wzorzec:** obiekt zmienia zachowanie zależnie od stanu, a każdy stan to osobna klasa.
**Problem:** TODO w `ApplianceState`, `Toaster`, `OffState`, `OnState`, `StartedState`.
**Zrobiłem:**
- enum `ApplianceState`: każda stała zwraca swój obiekt stanu (`getStateBehavior()`);
- `Toaster` deleguje: `applianceStateBehavior = applianceStateBehavior.turnOn();` (analogicznie start/stop/turnOff);
- stany zwracają `this`, gdy przejście jest niedozwolone, albo następny stan (OFF→ON→STARTED→STOPPED→OFF).
**Zaleta:** zamiast `switch` w `Toaster` reguły przejść siedzą w klasach stanów.

## 8. Decorator
**Wzorzec:** dokłada zachowanie obiektowi przez opakowanie go, bez zmiany jego klasy.
**Zadanie:** nowy dekorator cenzurujący słowa, bez ruszania istniejącego kodu.
**Zrobiłem:**
- `WordCensor extends SocialChannelDecorator` – zamienia słowa z listy na `###` i przekazuje dalej (`delegate.deliverMessage`);
- `WordCensorTest`: „Microsoft Windows is great!” → „### Windows is great!”;
- `ChainCensorDecoratorTest`: cenzor + `MessageTruncator(20)` + `URLAppender` w łańcuchu.
**Nie zrobiłem:** raportu Cobertury, bo stary plugin nie działa na obecnym JDK.
**Pytanie: czemu nie dziedziczenie?** Dekoratory można dowolnie łączyć w czasie działania. Przy dziedziczeniu potrzebna byłaby klasa na każdą kombinację.

## 9. Facade
**Wzorzec:** jeden prosty interfejs zamiast kilku podsystemów.
**Zrobiłem:** `DefaultBooktoreFacade implements BookstoreFacade`, a w nim `placeOrder(customerId, isbn)`:
klient → książka → zamówienie → `warehouse.dispatch(order)` → `notifyClient(receipt)`.
Serwisy wstrzykiwane setterami (w teście to mocki Mockito).
**Wskazówka z `verify`:** test sprawdza, że `dispatch(order)` i `notifyClient(dispatchReceipt)` są wołane dokładnie raz, z potwierdzeniem wysyłki, a nie z zamówieniem.

## 10. Dodatkowe
**Chain of Responsibility** (`chainofresponsability/`) – żądanie przechodzi przez łańcuch obiektów, a każdy coś robi i przekazuje dalej.
- `FakeLoggingFilter`, `FakeAuthorizationFilter`: wypisują prefiks i wołają `chain.doFilter(...)`;
- `FakeFormatFilter`: najpierw `chain.doFilter(...)`, potem dopisuje sufiks (działa „w drodze powrotnej”);
- wynik całego łańcucha: `Logging: Authorized: Hello Joao :Formated`.

**Iterator** (`iterator/`) – przechodzenie po kolekcji bez znajomości jej budowy.
- `CommandJob implements Iterable<Command>`, `iterator()` zwraca iterator po niemodyfikowalnym widoku listy;
- odkomentowany test: pętla `while (it.hasNext())` liczy 3 komendy;
- działa też `for (Command c : job)`.

---

**Zasady, które często padają:**
- **OCP** – otwarte na rozszerzenia, zamknięte na modyfikacje (nowy typ = nowa klasa, bez `if`-ów);
- **DIP** – wysokopoziomowe klasy zależą od abstrakcji, a nie od konkretnych implementacji.
