package com.example.todo.pages;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

import java.nio.file.Paths;

public final class TodoPage {

    public static final Target TASK_INPUT = Target.the("new task field").located(By.id("new-task"));
    public static final Target ADD_BUTTON = Target.the("add button").located(By.id("add-task"));
    public static final Target TODO_ITEMS = Target.the("todo entries").locatedBy("#todo-list li[data-task-id]");

    public static final String LOCAL_URL = Paths.get("index.html").toAbsolutePath().toUri().toString();

    private TodoPage() {
        // utility class
    }

    public static final Target TODO_ITEM_LABELS = Target.the("todo item labels")
            .locatedBy("#todo-list li[data-task-id] span.todo-item");

    public static Target deleteButtonFor(String taskName) {
        String xpath = String.format(
                "//li[.//span[contains(@class,'todo-item') and normalize-space(text())=%s]]//button[@data-action='delete-task']",
                escapeForXPathLiteral(taskName));
        return Target.the("delete button for " + taskName).located(By.xpath(xpath));
    }

    private static String escapeForXPathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"") ) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'");
        StringBuilder builder = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                builder.append(", \"'\", ");
            }
            builder.append("'").append(parts[i]).append("'");
        }
        builder.append(")");
        return builder.toString();
    }
}
