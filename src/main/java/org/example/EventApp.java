package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EventApp {
    private final EventScheduler eventScheduler;
    private final Scanner scanner;
    private DateTimeFormatter formatter;
    private boolean exit;

    public EventApp() {
        this.eventScheduler = new EventScheduler();
        this.scanner = new Scanner(System.in);
        this.exit = false;
    }

    public void start() {
        System.out.println("Добро пожаловать в приложение \"Планировщик событий\"!");
        while (!exit) {
            displayMenu();
            ActionType actionType = getActionTypeChoice();
            performAction(actionType);
        }
    }

    private void displayMenu() {
        System.out.println("Меню: ");
        for (ActionType actionType : ActionType.values()) {
            System.out.println(actionType.getCode() + ". " + actionType.getDescription());
        }
    }

    private ActionType getActionTypeChoice() {
        int choice;
        while (true) {
            System.out.println("Выберите действие (введите номер): ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                ActionType actionType = ActionType.getByCode(choice);
                if (actionType != null) {
                    return actionType;
                } else {
                    System.out.println("Некорректный ввод. Номер действия должен быть не меньше 1" +
                            " и не больше общего количества доступных действий");
                }
            } catch (InputMismatchException e) {
                System.out.println("Некорректный ввод. Попробуйте снова.");
                scanner.nextLine();
            }
        }
    }

    private void performAction(ActionType actionType) {
        switch (actionType) {
            case ADD_EVENT:
                addEvent();
                break;
            case VIEW_EVENTS:
                viewEvents();
                break;
            case FILTER_BY_DATE:
                filterByDate();
                break;
            case DELETE_EVENT:
                deleteEvent();
                break;
            case EXIT:
                exit();
                break;
            default:
                System.out.println("Некорректное действие. Попробуйте снова.");
        }
    }

    private void addEvent() {
        System.out.println("Введите описание события:");
        String description = scanner.nextLine();
        LocalDateTime startTime;

        while (true) {
            System.out.println("Введите дату и время начала события (в формате \"ГГГГ-ММ-ДД ЧЧ:ММ\"):");
            try {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                startTime = LocalDateTime.parse(scanner.nextLine(), formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты.");
            }
        }
        Event event = new Event(description, startTime);
        eventScheduler.addEvent(event);
        System.out.println("Событие успешно добавлено.");
    }

    private void viewEvents() {
        System.out.print("Список событий: ");
        List<Event> events = eventScheduler.getEvents();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        printEvents(events);
    }

    private void filterByDate() {
        LocalDate filterDate;
        while (true) {
            System.out.println("Введите дату для фильтрации (в формате \"ГГГГ-ММ-ДД\"):");
            try {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                filterDate = LocalDate.parse(scanner.nextLine(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты.");
                continue;
            }
            break;
        }
        List<Event> events = eventScheduler.filterByDate(filterDate);
        printEvents(events);
    }

    private void printEvents(List<Event> events) {
        if (events.isEmpty()) System.out.println("(пусто)");
        else {
            System.out.println();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);
                sb.append(i + 1)
                        .append(". [ ] ")
                        .append(event.getDescription())
                        .append(" ")
                        .append(event.getTime().format(formatter));
                System.out.println(sb);
                sb.setLength(0);
            }
        }
    }

    private void deleteEvent() {
        while (true) {
            if (eventScheduler.getEvents().isEmpty()) {
                System.out.println("В планировщике нет событий.");
            } else {
                System.out.println("Введите номер события для удаления: ");
                int index;
                try {
                    index = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Некорректный ввод. Попробуйте снова.");
                    continue;
                }

                if (index < 1 || index - 1 >= eventScheduler.getEvents().size()) {
                    System.out.println("Некорректный ввод. Номер события должен быть не меньше 1" +
                            " и не больше общего количества событий");
                    continue;
                }

                Event deletedEvent = eventScheduler.deleteEvent(index - 1);
                System.out.println("Событие " + deletedEvent.getDescription() + " успешно удалено.");
            }
            break;
        }
    }

    private void exit() {
        exit = true;
    }
}
