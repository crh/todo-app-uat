package com.example.todo.features;

import com.example.todo.pages.TodoPage;
import com.example.todo.questions.TheTodoList;
import com.example.todo.reporting.BrowserConsoleLogReporter;
import com.example.todo.tasks.AddTask;
import com.example.todo.tasks.DeleteTask;
import net.serenitybdd.annotations.Narrative;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Narrative(text = "Toby keeps a GitHub-themed todo board tidy while Serenity captures UI interactions for living documentation.")
@ExtendWith(SerenityJUnit5Extension.class)
class TodoScreenplayTest {

    private final Actor toby = Actor.named("Toby");
    private WebDriver browser;

    @BeforeEach
    void setUp() {
        ensureChromeUserDataDir();
        browser = Serenity.getDriver();
        toby.can(BrowseTheWeb.with(browser));
    }

    @AfterEach
    void tearDown() {
        BrowserConsoleLogReporter.attachLogs(browser);
        Serenity.getWebdriverManager().closeCurrentDrivers();
    }

    @Test
    void should_add_task_to_the_list() {
        openFreshTodoApp();
        toby.attemptsTo(AddTask.called("Review pull request"));

        assertEquals(1, TheTodoList.size().answeredBy(toby));
        assertEquals(List.of("Review pull request"), TheTodoList.contents().answeredBy(toby));
    }

    @Test
    void should_remove_task_from_the_dom() {
        openFreshTodoApp();
        toby.attemptsTo(AddTask.called("Archive backlog"));
        toby.attemptsTo(DeleteTask.called("Archive backlog"));

        assertEquals(0, TheTodoList.size().answeredBy(toby));
    }

    @Test
    void should_preserve_tasks_after_refresh() {
        openFreshTodoApp();
        toby.attemptsTo(AddTask.called("Persist across reload"));
        browser.navigate().refresh();

        assertEquals(List.of("Persist across reload"), TheTodoList.contents().answeredBy(toby));
    }

    private void openFreshTodoApp() {
        toby.attemptsTo(Open.url(TodoPage.LOCAL_URL));
        ((JavascriptExecutor) browser).executeScript("window.localStorage.clear();");
        toby.attemptsTo(Open.url(TodoPage.LOCAL_URL));
    }

    private void ensureChromeUserDataDir() {
        try {
            Files.createDirectories(Path.of("/tmp/chrome"));
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create Chrome user data directory", e);
        }
    }
}
