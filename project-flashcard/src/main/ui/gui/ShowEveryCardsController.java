package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import model.FCardCollection;
import model.Flashcard;

/*
 * A Controller class that updates and displays the table containing 
 * every card in the App;
 * Processes edit or deletion on each of the cards;
 * Modifies the App's data to reflect changes.
 */
public class ShowEveryCardsController {
    FCardCollection model;
    ShowEveryCardsView view;
    JTable cardTable;
    JProgressBar progressBar;

    /*
     * EFFECTS: add event listener to each of the Buttons
     */
    public ShowEveryCardsController(FCardCollection model, ShowEveryCardsView view, JProgressBar bar) {
        this.model = model;
        this.view = view;
        this.view.addEditBTListener(new EditBTListener());
        this.view.addDeleteBTListener(new DeleteBTListener());
        this.view.addTableModelListener(new CheckBoxListener());
        this.progressBar = bar;
    }

    /*
     * Inner class that is an Event listener class for the Edit Button.
     * Modifies definition of the selected card
     */
    class EditBTListener implements ActionListener {
        JTable cardTable;

        /*
         * MODIFIES: this (EditBTListener)
         * EFFECTS: setter for the cardTable.
         */
        public void initCardTable(JTable cardTable) {
            this.cardTable = cardTable;
        }

        /*
         * MODIFIES: model
         * EFFECTS: when user click the edit button, ENTER EDIT mode OR EXIT EDIT mode.
         * process editing the description and update the description of flashcard.
         * Do nothing if it's not in editing mode.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // edit the description of selected row's flashcard
            cardTable.clearSelection();
            boolean isEditMode = view.flipEditMode(); // flip the Editable flag and get the current flag
            // System.out.println("EditBT Listener"); /* debugger */

            if (!isEditMode) { // in non-edit mode
                view.getEditButton().setText("Start Edit");
                for (int i = 0; i < model.getNumFCards(); i++) {
                    Flashcard card = model.getFlashCard(i);
                    card.editDescription((cardTable.getValueAt(i, 1).toString()));
                    // System.out.println(cardTable.getValueAt(i, 1).toString()); /* debugger */
                }
                progressBar.setMaximum(model.getNumFCards());
                progressBar.setValue(model.getNumOfLearned());

                if (progressBar.getMaximum() <= 0) {
                    progressBar.setIndeterminate(true);
                } else {
                    progressBar.setIndeterminate(false);
                }
            } else { // in edit mode
                view.getEditButton().setText("Quit Edit");
            }
        }
    }

    /*
     * Inner class that is an Event listener class for the 'save' Button
     */
    class DeleteBTListener implements ActionListener {
        JTable cardTable;

        /*
         * MODIFIES: this (DeleteBTListener)
         * EFFECTS: setter for the cardTable.
         */
        public void initCardTable(JTable cardTable) {
            this.cardTable = cardTable;
        }

        /*
         * MODIFIES: this
         * EFFECTS: edit the description of selected row's flashcard
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // System.out.println("deleteBTListener clicked"); /* debugger */
            model.deleteCard(cardTable.getSelectedRow());
            // System.out.println(cardTable.getSelectedRow()); /* debugger */
            view.updateFlashcardList(model.getFCards(true));
            progressBar.setMaximum(model.getNumFCards());
            progressBar.setValue(model.getNumOfLearned());

            if (progressBar.getMaximum() <= 0) {
                progressBar.setIndeterminate(true);
            } else {
                progressBar.setIndeterminate(false);
            }
            view.setVisible(true);
        }
    }

    /*
     * Inner class that is an Event listener class for a change of the Table
     */
    class CheckBoxListener implements TableModelListener {
        JTable cardTable;

        /*
         * REQUIRES: cardTable != null
         * MODIFIES: this (DeleteBTListener)
         * EFFECTS: setter for the cardTable.
         */
        public void initCardTable(JTable cardTable) {
            this.cardTable = cardTable;
        }

        /*
         * MODIFIES: model
         * EFFECTS: Modifies the learning state data of selected card if it's in editing
         * mode.
         * Do nothing otherwise.
         */
        @Override
        public void tableChanged(TableModelEvent e) {
            boolean isEditMode = view.isEditMode(); // flip the Editable flag and get the current flag
            // System.out.println("debugger: entered tableChanged()"); /* debugger */

            int row = cardTable.getSelectedRow();
            int col = e.getColumn();

            if (isEditMode && (row >= 0 & col >= 0)) {
                // System.out.println(cardTable.getValueAt(row, col).toString()); /* debugger */
                if (col == 2) {
                    if (cardTable.getValueAt(row, col).equals(true)) {
                        model.markThisCardAsLearned(row);
                    } else {
                        model.markThisCardStillLearning(row);
                    }
                }
            }
        }
    }

}
