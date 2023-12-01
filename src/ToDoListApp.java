import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoListApp extends JFrame {
    private JTextArea taskTextArea;
    private JTextField taskTextField;
    private JButton addButton;
    private int userId;  // User ID for the current user

    public ToDoListApp(int userId) {
        super("To-Do List");
        this.userId = userId;

        taskTextArea = new JTextArea(10, 30);
        taskTextField = new JTextField(20);
        addButton = new JButton("Add Task");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(taskTextArea));
        panel.add(taskTextField);
        panel.add(addButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addTask() {
        String description = taskTextField.getText();
        Task task = new Task();
        task.setDescription(description);
        task.setCompleted(false);

        DatabaseHelper.addTask(task, userId);

        taskTextArea.append(description + "\n");
        taskTextField.setText("");
    }

    public static void main(String[] args) {
        UserDatabaseHelper.createTable();
        DatabaseHelper.createTable();

        // Assuming a user is created in the database
        User user = UserDatabaseHelper.getUser("username", "password");

        if (user != null) {
            SwingUtilities.invokeLater(() -> new ToDoListApp(user.getId()));
        } else {
            System.out.println("User not found.");
        }
    }
}
