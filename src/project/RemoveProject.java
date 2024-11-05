package project;

import javax.swing.*;

public class RemoveProject {
    private final JComboBox<String> projectComboBox;
    private final TaskManager taskManager;

    public RemoveProject(JComboBox<String> projectComboBox, TaskManager taskManager) {
        this.projectComboBox = projectComboBox;
        this.taskManager = taskManager;
    }

    public void removeSelectedProject() 
    {
        String selectedProject = (String) projectComboBox.getSelectedItem();
        if (selectedProject != null && !selectedProject.equals("All")) {
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to remove the project \"" + selectedProject + "\"?",
                    "Confirm Removal", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                taskManager.removeProject(selectedProject);
                projectComboBox.removeItem(selectedProject);
                projectComboBox.setSelectedItem("All");
                JOptionPane.showMessageDialog(null, "Project removed successfully.");
            }
        } 
        else {
            JOptionPane.showMessageDialog(null, "Please select a valid project to remove.");
        }
    }
}
