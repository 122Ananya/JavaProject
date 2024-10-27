package project;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
// Task Cell Renderer for coloring task list based on status
class TaskCellRenderer extends JLabel implements ListCellRenderer<Task> {
    @Override
    public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
        setText(task.toString());
        setOpaque(true);

        switch (task.getStatus()) {
            case "Completed":
                setBackground(Color.GREEN);
                break;
            case "In Progress":
                setBackground(Color.YELLOW);
                break;
            case "Not Started":
            default:
                setBackground(Color.RED);
                break;
        }

        setForeground(Color.BLACK);
        return this;
    }
}



