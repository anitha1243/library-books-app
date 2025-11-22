# library-books-app

Spring Boot (Gradle, Groovy DSL) application that exposes a  REST API to fetch books from an in-memory repository.

Quick start

1. Open a terminal and change into the project folder:

```powershell
cd C:\Users\anith\Documents\repos\library-app\library-books-app
```

2a. If you have Gradle installed, run:

```powershell
gradle bootRun
```

2b. Or, generate the Gradle wrapper locally and use it (recommended for reproducibility):

```powershell
gradle wrapper
.\gradlew.bat bootRun
```

The API endpoints:

- `GET /api/books` — list all books
- `GET /api/books/{id}` — get book by id
- `POST /api/books` — create a book (JSON body with `title` and `author`)

curl http://localhost:8080/api/books

curl http://localhost:8080/api/books/1

$body = @{ title = 'My Book'; author = 'AK' } | ConvertTo-Json
Invoke-RestMethod -Uri 'http://localhost:8080/api/books' -Method POST -Body $body -ContentType 'application/json' -Verbose

Tests
gradle test

Notes

- Java 17 is used. Install JDK 17 (or later) to build and run.
- If you want, I can add the Gradle wrapper files now so you can run `./gradlew` immediately.
