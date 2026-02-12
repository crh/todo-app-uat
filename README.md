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
- `Jenkinsfile` can run the same `mvn -B clean verify` build locally (archive artifacts, publish JUnit/XML results, and keep the Serenity site) when your Jenkins controller points to this repository.

## Running locally
1. Ensure Java 17+ and Maven 3.9+ are installed.
2. Start a HTTP server (e.g., `mvn -q -pl . -DskipTests -Pdemo verify`?)
3. Run `mvn -B clean verify -Djdk.attach.allowAttachSelf=true` to execute the Screenplay suite (requires access to `repo.maven.apache.org`).
4. After a successful run, view the Serenity report under `target/site/serenity`.

## Jenkins (local)
1. Start Jenkins on your machine (`docker run -d --name jenkins-local -p 8080:8080 -p 50000:50000 -v jenkins-home:/var/jenkins_home jenkins/jenkins:lts`), then unlock and install the reusable Plugin sets (Pipeline, Git, HTML Publisher, JUnit, Maven Integration if you want tool auto-install).
2. Ensure the Jenkins node has Maven 3.9+ (either install it under **Global Tool Configuration** and name it `Maven 3.9`, or install it on the OS and add it to `$PATH` so `mvn` works inside builds).
3. Create a **Pipeline** job pointed at this repository and select “Pipeline script from SCM” (or “Jenkinsfile” from your SCM branch). Jenkins will use `Jenkinsfile` at the repo root, which invokes `mvn -B clean verify` with `-Djdk.attach.allowAttachSelf=true`, archives `target/*.jar`, publishes `target/failsafe-reports/*.xml` as JUnit results, and snapshots `target/site/serenity`.
4. Trigger the job manually to build artifacts, execute the Serenity tests, and confirm the HTML report is available in `target/site/serenity`. Archive entries and Serenity screenshots can be downloaded from the Jenkins build page under **Artifacts** or **HTML Reports**.
5. If you need to debug quickly, click **Console Output** on the build, and rerun with `Rebuild` once any Maven dependency/cache issues are resolved.

## Notes
- Maven currently resolves dependencies from the default local repository (`~/.m2/repository`). If you encounter network/DNS issues reaching Maven Central, download the necessary artifacts manually or use a cached mirror.
- The UI is intentionally minimal and uses only CDN assets; open `index.html` in a browser or serve the directory to interact with the todo list.
