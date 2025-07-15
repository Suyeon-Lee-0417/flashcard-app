package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * A GUI class that represents a dialog box for creating a new flashcard.
 */
public class CreateCardView extends JDialog {
    private JTextField term;
    private JTextField description;
    private JButton saveButton;
    private JLabel promptLabel;
    private JLabel termLabel;
    private JLabel descriptionLabel;

    private JPanel panel;
    private JPanel buttonPanel;

    /*
     * EFFECTS: Sets up all the components in the dialog box.
     */
    public CreateCardView(JFrame parentFrame) {
        super(parentFrame, "New Flashcard", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        this.setSize(300, 200);
        this.setLocationRelativeTo(parentFrame);

        /* layouts */
        panel = new JPanel(); /* base layout */
        buttonPanel = new JPanel(); /* button layout */
        panel.setLayout(new GridLayout(5, 1));

        /* components */
        term = new JTextField();
        description = new JTextField();
        // term.setPreferredSize(new Dimension(100, 50));
        // term.setBounds(70, 150, 60, 10);
        saveButton = new JButton("Save");
        promptLabel = new JLabel("Create a new card");
        termLabel = new JLabel("Type term: ");
        descriptionLabel = new JLabel("Type Description: ");

        /* add to panel */
        panel.add(promptLabel);
        panel.add(termLabel);
        panel.add(term);
        panel.add(descriptionLabel);
        panel.add(description);
        buttonPanel.add(saveButton);
        this.add(panel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getTerm() {
        return term.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    /*
     * MODIFIES: this
     * EFFECTS: assigns an event listener to Save Button
     */
    public void makeSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    /* main method for visual checking */

    // public static void main(String[] args) {
    // JFrame frame = new JFrame();
    // CreateCardDialog dialog = new CreateCardDialog(frame);
    // dialog.setVisible(true);
    // }
}
