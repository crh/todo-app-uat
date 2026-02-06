package com.example.todo.questions;

import com.example.todo.pages.TodoPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.stream.Collectors;

public final class TheTodoList {

    private TheTodoList() {
        // utility
    }

    public static Question<Integer> size() {
        return actor -> TodoPage.TODO_ITEMS.resolveAllFor(actor).size();
    }

    public static Question<List<String>> contents() {
        return actor -> TodoPage.TODO_ITEM_LABELS.resolveAllFor(actor)
                .stream()
                .map(WebElementFacade::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
