package org.example;

public enum ActionType {
    ADD_EVENT(1, "Добавить событие"),
    VIEW_EVENTS(2, "Просмотреть все события"),
    FILTER_BY_DATE(3, "Фильтр по дате"),
    DELETE_EVENT(4, "Удалить событие"),
    EXIT(5, "Выйти");

    private final int code;
    private final String description;

    ActionType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ActionType getByCode(int code) {
        for (ActionType actionType : values()) {
            if (actionType.code == code) {
                return actionType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code + ". " + description;
    }
}
