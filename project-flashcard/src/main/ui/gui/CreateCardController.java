package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import model.FCardCollection;
import model.Flashcard;

/*
 * Controller class that processes adding a new card to the app.
 * Add a new card if the input from the dialog box is valid.
 * Accesses to CreateCardView(view) and modifies FCardCollection(model).
 */
public class CreateCardController {
    private FCardCollection model;
    private CreateCardView view;
    private JProgressBar progressBar;

    /*
     * MODIFIES: dialog
     * EFFECTS: make a event listener to the 'Save' button.
     */
    public CreateCardController(FCardCollection model, CreateCardView dialog, JProgressBar progressBar) {
        this.model = model;
        this.view = dialog;
        this.progressBar = progressBar;
        this.view.makeSaveButtonListener(new SaveButtonListener());
    }

    /*
     * Inner class that is an Event listener for 'Save' button for creating a
     * new flashcard.
     */
    class SaveButtonListener implements ActionListener {
        /*
         * MODIFIES: model
         * EFFECTS: Adds a new card to the app when user hit the 'Save' button.
         * Display an warning message when the input is invalid.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // System.out.println("Save button event detected");
            String term = view.getTerm();
            String description = view.getDescription();

            if (term.isEmpty() || description.isEmpty()) {
                // System.out.println("invalid input"); /* debugging */
                JOptionPane.showMessageDialog(view, "Invalid input. Please type it again", "Input error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Flashcard newCard = new Flashcard(term, description);
            model.addNewCard(newCard);

            progressBar.setMaximum(progressBar.getMaximum() + 1);
            progressBar.setIndeterminate(false);

            JOptionPane.showMessageDialog(view, "Created a new Flashcard!", "successful",
                    JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
        }
    }

}
