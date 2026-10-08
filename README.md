# Лабораторна робота №1. Data-Oriented Programming у Java

**Дисципліна:** Технології розробки на платформі Java
**Тема:** Сучасні можливості Java та підхід Data-Oriented Programming (DOP)

## Опис проєкту

Проєкт реалізує систему обробки повідомлень (Notification) у стилі DOP:

- **Дані** змодельовано незмінними `record`-ами, об'єднаними в закриту ієрархію `sealed interface`.
- **Логіку** винесено в окремий клас-обробник `NotificationProcessor`.
- Обробка виконується через **switch expression з pattern matching** (record patterns), що покриває всі варіанти ієрархії **без `default`** — компілятор сам перевіряє вичерпність.
- Результат формується за допомогою **Text Blocks** (`"""`).

### Ієрархія даних (пакет `ua.kpi.comsys.dop`)

```
+ Notification (sealed interface)
|--- TextMsg(String sender, String text)
|--- ImageMsg(String sender, String url, int sizeKb)
|--- AudioMsg(String sender, int durationSeconds)
```

Валідація в компактних конструкторах record-ів (у разі порушення кидається `IllegalArgumentException`):

- `sender`, `text`, `url` не можуть бути `null`, порожніми чи пробільними;
- `sizeKb` і `durationSeconds` мають бути `> 0`.

### Формат результату `NotificationProcessor.process(...)`

| Тип | Результат |
|---|---|
| `TextMsg` | `Notification: TEXT`<br>`From: <sender>`<br>`Message: <text>` |
| `ImageMsg` | `Notification: IMAGE`<br>`From: <sender>`<br>`URL: <url> (<sizeKb> KB)` |
| `AudioMsg` | `Notification: AUDIO`<br>`From: <sender>`<br>`Duration: <durationSeconds> seconds` |

Якщо передано `null`, метод кидає `IllegalArgumentException("Notification cannot be null")`.

## Розрахунок варіанта

Залікова книжка відсутня, тому використано порядковий номер у списку групи: **N = 8**.

```
V = N mod 3 = 8 mod 3 = 2
```

**Варіант 2** — система повідомлень (`Notification` / `NotificationProcessor`).

## Структура проєкту

```
src/main/java/ua/kpi/comsys/dop/
    Notification.java            sealed interface
    TextMsg.java                 record
    ImageMsg.java                record
    AudioMsg.java                record
    Validation.java              допоміжні перевірки (package-private)
    NotificationProcessor.java   клас-обробник
src/test/java/ua/kpi/comsys/dop/
    NotificationProcessorTest.java    тести всіх гілок switch та null
    NotificationValidationTest.java   тести валідації record-ів
```

## Вимоги

- JDK 21+
- Maven 3.8+

## Запуск тестів

```bash
mvn clean test
```

Звіт про покриття коду (JaCoCo) генерується автоматично після тестів:

```
target/site/jacoco/index.html
```

Запуск окремого тестового класу:

```bash
mvn test -Dtest=NotificationProcessorTest
```
