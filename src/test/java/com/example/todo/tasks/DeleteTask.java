package com.example.todo.tasks;

import com.example.todo.pages.TodoPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

public final class DeleteTask implements Task {

    private final String taskName;

    private DeleteTask(String taskName) {
        this.taskName = taskName;
    }

    public static DeleteTask called(String taskName) {
        return new DeleteTask(taskName);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Target deleteButton = TodoPage.deleteButtonFor(taskName);
        actor.attemptsTo(Click.on(deleteButton));
    }
}
