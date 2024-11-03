package project;

import javax.swing.*;

public class RemoveTask {
    private final JList<Task> taskList;
    private final DefaultListModel<Task> taskListModel;
    private final TaskManager taskManager;

    public RemoveTask(JList<Task> taskList, DefaultListModel<Task> taskListModel, TaskManager taskManager) {
        this.taskList = taskList;
        this.taskListModel = taskListModel;
        this.taskManager = taskManager;
    }

    public void removeSelectedTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task selectedTask = taskListModel.get(selectedIndex);
            Project project = taskManager.getProjectByName(selectedTask.getProjectName());
            if (project != null) {
                project.getTasks().remove(selectedTask);
            }
            taskListModel.remove(selectedIndex);
            taskManager.saveTasksToFile(); 
        } else {
            JOptionPane.showMessageDialog(null, "Please select a task to remove.");
        }
    }
}
