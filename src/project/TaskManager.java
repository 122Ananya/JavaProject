package project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Project> projects = new ArrayList<>();
    private static final String TASKS_FILE = "tasks.ser";

    // Adds a project to the project list
    public void addProject(Project project) {
        projects.add(project);
        saveTasksToFile(); // Save after adding a project
    }

    // Removes a project by its name
    public void removeProject(String projectName) {
        projects.removeIf(project -> project.getName().equals(projectName));
        saveTasksToFile(); // Save after removing a project
    }

    // Retrieves all projects
    public List<Project> getProjects() {
        return projects;
    }

    // Gets a project by name
    public Project getProjectByName(String projectName) {
        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                return project;
            }
        }
        return null; // Return null if project not found
    }

    // Loads tasks from a file
    public void loadTasksFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TASKS_FILE))) {
            List<Project> loadedProjects = (List<Project>) ois.readObject();
            projects.clear();
            projects.addAll(loadedProjects);
        } catch (FileNotFoundException e) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Saves tasks to a file
    public void saveTasksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

