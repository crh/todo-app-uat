# GitHub-Themed Todo UAT

This repository hosts a localized GitHub-themed todo sample along with a Serenity BDD Screenplay suite that exercises the UI manually.

## Web app
- `index.html` renders a Primer-styled todo UI with an input, add button, and list of tasks that can be deleted.
- `app.js` manages task storage in `window.localStorage`, ensuring persistence across browser refreshes.

## Screenplay tests
- Maven + Serenity 5.2.2 drive the automation through the Screenplay pattern.
- Actor **Toby** performs `AddTask.called(...)` and `DeleteTask.called(...)`, while questions `TheTodoList.size()` and `TheTodoList.contents()` assert the UI state.
- Tests cover adding a task, deleting a task, and verifying persistence after a browser refresh.
- Serenity is configured to capture screenshots for each action (`serenity.properties`).

## CI/CD
- `.github/workflows/tests.yml` runs `mvn -B clean verify` on every push and uploads the Serenity HTML report as an artifact.

## Running locally
1. Ensure Java 17+ and Maven 3.9+ are installed.
2. Start a HTTP server (e.g., `mvn -q -pl . -DskipTests -Pdemo verify`?)
3. Run `mvn -B clean verify` to execute the Screenplay suite (requires access to `repo.maven.apache.org`).
4. After a successful run, view the Serenity report under `target/site/serenity`.

## Notes
- Maven currently resolves dependencies from the default local repository (`~/.m2/repository`). If you encounter network/DNS issues reaching Maven Central, download the necessary artifacts manually or use a cached mirror.
- The UI is intentionally minimal and uses only CDN assets; open `index.html` in a browser or serve the directory to interact with the todo list.
