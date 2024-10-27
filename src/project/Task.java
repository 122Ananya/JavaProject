package project;

import java.io.Serializable;

// Task class to represent individual tasks
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String member;
    private final String deadline;
    private String status;
    private String projectName; // Add this field

    public Task(String name, String member, String deadline, String status, String projectName) {
        this.name = name;
        this.member = member;
        this.deadline = deadline;
        this.status = status;
        this.projectName = projectName; // Initialize project name
    }

    // Getter for project name
    public String getProjectName() {
        return projectName;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " - " + member + " - Due: " + deadline + " - Status: " + status;
    }
}
