package project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final List<Project> projects = new ArrayList<>();
    private static final String TASKS_FILE = "tasks.ser";

    public void addProject(Project project) {
        projects.add(project);
        saveTasksToFile(); 
    }

    public void removeProject(String projectName) {
        projects.removeIf(project -> project.getName().equals(projectName));
        saveTasksToFile();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getProjectByName(String projectName) {
        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                return project;
            }
        }
        return null; 
    }

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

    public void saveTasksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TASKS_FILE))) {
            oos.writeObject(projects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

