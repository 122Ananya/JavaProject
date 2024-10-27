package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Project class to represent a project
class Project implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final List<Task> tasks = new ArrayList<>();

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
}


