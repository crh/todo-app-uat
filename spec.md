# Design Specification: GitHub-Themed Todo & Serenity BDD Suite

### 1. Application Under Test (AUT)
- **Target:** A localized web application.
- **UI:** GitHub-themed Todo list using **GitHub Primer CSS** CDN.
- **Tech Stack:** HTML5, JavaScript (ES6+).
- **Functionality:** - Input field for task name.
    - "Add" button and "Delete" (trash icon) button.
    - **Persistence:** Use `window.localStorage` so tasks survive a browser refresh.

### 2. Automation Framework: Serenity BDD
- **Environment:** Java 17+, Maven.
- **Design Pattern:** **Screenplay Pattern** (MANDATORY).
- **Core Components:**
    - **Actor:** "Toby" (the user).
    - **Tasks:** `AddTask.called("taskName")`, `DeleteTask.called("item")`.
    - **Actions:** Use `Enter.theValue()`, `Click.on()`, `Open.url()`.
    - **Questions:** `TheTodoList.size()`, `TheTodoList.contents()`.
- **Test Scenarios:**
    1. **Add Task:** Verify a new task appears in the list.
    2. **Delete Task:** Verify a task is removed from the DOM.
    3. **Persistence:** Add task -> Refresh page (via `driver.navigate().refresh()`) -> Verify task still exists.

### 3. CI/CD & Reporting
- **Screenshots:** Configure `serenity.properties` to `serenity.take.screenshots = FOR_EACH_ACTION`.
- **GitHub Actions:** Generate a `.github/workflows/tests.yml` to:
    - Run on `push`.
    - Use `mvn clean verify`.
    - Upload Serenity HTML reports as build artifacts.

### 4. Output Format
- Provide code for: `index.html`, `app.js`, `pom.xml`, `serenity.properties`, and the Java Screenplay classes.