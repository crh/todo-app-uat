package com.example.todo.tasks;

import com.example.todo.pages.TodoPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public final class AddTask implements Task {

    private final String taskName;

    private AddTask(String taskName) {
        this.taskName = taskName;
    }

    public static AddTask called(String taskName) {
        return new AddTask(taskName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(taskName).into(TodoPage.TASK_INPUT),
                Click.on(TodoPage.ADD_BUTTON)
        );
    }
}
