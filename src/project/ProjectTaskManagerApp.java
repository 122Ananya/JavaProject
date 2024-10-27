package project;

import javax.swing.*;
import java.awt.*;

// Main GUI Frame for Project Manager
public class ProjectTaskManagerApp extends JFrame {
    private final DefaultListModel<Task> taskListModel = new DefaultListModel<>();
    private final JTextField projectNameField = new JTextField(15);
    private final JTextField taskNameField = new JTextField(15);
    private final JTextField memberNameField = new JTextField(15);
    private final JTextField deadlineField = new JTextField(10);
    private final JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
    private final JComboBox<String> projectComboBox = new JComboBox<>();
    private final TaskManager taskManager = new TaskManager();
    private final JList<Task> taskList = new JList<>(taskListModel);
    private RemoveTask removeTaskHandler;
    private RemoveProject removeProjectHandler;

    public ProjectTaskManagerApp() {
        setTitle("Project Task Management Application");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for adding projects and tasks
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(180, 220, 200)); // Light blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

        // Project Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Project Name:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(projectNameField, gbc);
        
        gbc.gridx = 2; gbc.gridy = 0;
        JButton addProjectButton = new JButton("Add Project");
        addProjectButton.addActionListener(e -> addProject());
        topPanel.add(addProjectButton, gbc);

        // Task Details
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Task Name:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(taskNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Member Name:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(memberNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Deadline (YYYY-MM-DD):"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(deadlineField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(new JLabel("Status:"), gbc);
        
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(statusComboBox, gbc);

        // Create a panel for Add Task, Remove Task, Update Status, and Remove Project buttons
        JPanel taskButtonsPanel = new JPanel(new FlowLayout());
        taskButtonsPanel.setBackground(new Color(180, 200, 220)); // Light blue-gray background

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(e -> addTask());
        taskButtonsPanel.add(addTaskButton);

        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.addActionListener(e -> removeTask());
        taskButtonsPanel.add(removeTaskButton);

        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(e -> updateTaskStatus());
        taskButtonsPanel.add(updateStatusButton);

        JButton removeProjectButton = new JButton("Remove Project");
        removeProjectButton.addActionListener(e -> removeProject());
        taskButtonsPanel.add(removeProjectButton);

        // Add task buttons panel to topPanel
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; // Make it span across columns
        topPanel.add(taskButtonsPanel, gbc);

        // Center panel for displaying tasks
        taskList.setCellRenderer(new TaskCellRenderer());
        JScrollPane taskScrollPane = new JScrollPane(taskList);
        taskScrollPane.getViewport().setBackground(new Color(1,100,200)); // Lightest blue background for the list

        // Project filter dropdown
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBackground(new Color(1,100,200)); // Light blue background for the filter panel
        filterPanel.add(new JLabel("Select Project:"));
        projectComboBox.addItem("All");
        projectComboBox.addActionListener(e -> filterTasks());
        filterPanel.add(projectComboBox);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(taskScrollPane, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.SOUTH);

        // Load saved tasks and projects from file on startup
        taskManager.loadTasksFromFile();
        updateProjectList();

        removeTaskHandler = new RemoveTask(taskList, taskListModel, taskManager);
        removeProjectHandler = new RemoveProject(projectComboBox, taskManager);
    }

    private void removeTask() {
        removeTaskHandler.removeSelectedTask();
    }

    private void removeProject() {
        removeProjectHandler.removeSelectedProject();
    }

    private void addProject() {
        String projectName = projectNameField.getText();
        if (!projectName.isEmpty()) {
            taskManager.addProject(new Project(projectName));
            projectComboBox.addItem(projectName);
            projectNameField.setText("");
        }
    }

    private void addTask() {
        String projectName = (String) projectComboBox.getSelectedItem();
        if (projectName == null || projectName.equals("All")) {
            JOptionPane.showMessageDialog(this, "Please select a specific project.");
            return;
        }

        String taskName = taskNameField.getText();
        String memberName = memberNameField.getText();
        String deadline = deadlineField.getText();
        String status = (String) statusComboBox.getSelectedItem();

        if (taskName.isEmpty() || memberName.isEmpty() || deadline.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all task details.");
            return;
        }

        Project project = taskManager.getProjectByName(projectName);
        if (project != null) {
            project.addTask(new Task(taskName, memberName, deadline, status, projectName));
            taskManager.saveTasksToFile();
            filterTasks();
            clearTaskFields();
        }
    }

    private void updateTaskStatus() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task selectedTask = taskListModel.get(selectedIndex);
            String newStatus = (String) statusComboBox.getSelectedItem();
            selectedTask.setStatus(newStatus);
            taskManager.saveTasksToFile();
            filterTasks();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to update.");
        }
    }

    private void updateProjectList() {
        projectComboBox.removeAllItems();
        projectComboBox.addItem("All");
        for (Project project : taskManager.getProjects()) {
            projectComboBox.addItem(project.getName());
        }
    }

    private void filterTasks() {
        taskListModel.clear();
        String selectedProject = (String) projectComboBox.getSelectedItem();
        if (selectedProject == null || selectedProject.equals("All")) {
            for (Project project : taskManager.getProjects()) {
                for (Task task : project.getTasks()) {
                    taskListModel.addElement(task);
                }
            }
        } else {
            Project project = taskManager.getProjectByName(selectedProject);
            if (project != null) {
                for (Task task : project.getTasks()) {
                    taskListModel.addElement(task);
                }
            }
        }
    }

    private void clearTaskFields() {
        taskNameField.setText("");
        memberNameField.setText("");
        deadlineField.setText("");
        statusComboBox.setSelectedIndex(0);
    }
}


