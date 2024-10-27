package project;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjectTaskManagerApp app = new ProjectTaskManagerApp();
            app.setVisible(true);
        });
    }
}
