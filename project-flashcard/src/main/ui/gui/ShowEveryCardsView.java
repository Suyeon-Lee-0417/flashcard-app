package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Flashcard;
import ui.gui.ShowEveryCardsController.DeleteBTListener;
import ui.gui.ShowEveryCardsController.EditBTListener;
import ui.gui.ShowEveryCardsController.CheckBoxListener;

/*
 * A GUI class that represents the 'show every cards' pannel of the app.
 */
public class ShowEveryCardsView extends JFrame {
    private JTable cardTable;
    private DefaultTableModel tableModel;
    private JButton deleteBT;
    private JButton editBT;
    private boolean isEditMode;

    /*
     * EFFECTS: sets up the components of the pannel.
     */
    public ShowEveryCardsView() {
        this.setTitle("Every Flashcards");
        this.setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        isEditMode = false;
        String[] columns = { "Term", "Definitions", "Learned", "image" };

        /* Constructs a DefaultTableModel */
        tableModel = new DefaultTableModel(columns, 0) {

            /*
             * EFFECTS: returns the class(data type) of the column.
             */
            @Override
            public Class<?> getColumnClass(int column) {
                // set the 3rd col as Boolean.class
                return column == 2 ? Boolean.class : Object.class;
            }

            /*
             * EFFECTS: Allows a cell that is in the first or second column to be edited.
             */
            @Override
            public boolean isCellEditable(int row, int col) {
                return (col == 1 || col == 2) && isEditMode;
            }
        };

        /* table */
        cardTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cardTable);

        /* buttons */
        deleteBT = new JButton("Delete");
        editBT = new JButton("Start Edit");

        /* add buttons to panel */
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(deleteBT);
        buttonPanel.add(editBT);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: reverse the editing mode
     */
    public boolean flipEditMode() {
        isEditMode = !isEditMode;
        return isEditMode;
    }

    /*
     * EFFECTS: Returns whether the table is currently in editing mode.
     */
    public boolean isEditMode() {
        return isEditMode;
    }

    /*
     * EFFECTS: Returns the edit button in the panel
     */
    public JButton getEditButton() {
        return this.editBT;
    }

    /*
     * MODIFIES: this
     * EFFECTS: Updates the flashcards data and display in a table form
     */
    public void updateFlashcardList(List<Flashcard> flashcards) {
        tableModel.setRowCount(0); /* delete previous data */
        for (Flashcard card : flashcards) {
            Object[] row = {
                    card.getTerm(),
                    card.getDescription(),
                    card.getLearningStatus() // Boolean object
            };
            tableModel.addRow(row);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: add an ActionListener to editBT button
     */
    public void addEditBTListener(EditBTListener listener) {
        listener.initCardTable(cardTable);
        editBT.addActionListener(listener);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add an ActionListener to deleteBT button
     */
    public void addDeleteBTListener(DeleteBTListener listener) {
        listener.initCardTable(cardTable);
        deleteBT.addActionListener(listener);
    }

    /*
     * MODIFIES: this
     * EFFECTS: add an ActionListener to table
     */
    public void addTableModelListener(CheckBoxListener listener) {
        listener.initCardTable(cardTable);
        tableModel.addTableModelListener(listener);
    }

}
