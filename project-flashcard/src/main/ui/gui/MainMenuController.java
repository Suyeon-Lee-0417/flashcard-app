package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JProgressBar;

import model.FCardCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

/*
 * Controller class that process a chosen functionality option given in the main menu panel.
 *  opens each panel of the functionality. 
 */
public class MainMenuController {
    private FCardCollection model;
    private MainMenuView view;
    CreateCardController createCardController;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/FCardList.json";

    /*
     * MODIFIES: view
     * EFFECTS: add listeners to every Buttons in the main menu panel
     */
    public MainMenuController(FCardCollection model, MainMenuView view) {
        this.model = model;
        this.view = view;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        this.view.addCreateCardListener(new NewCardBTlistener()); /* add event listener */
        this.view.addShowEveryCardsListener(new SeeEveryCardsBTListener());
        this.view.addSaveListener(new SaveButtonListener());
        this.view.addLoadListener(new LoadButtonListener());
    }

    /*
     * Event listener class for Create new card Button in MainMenuView
     */
    class NewCardBTlistener implements ActionListener {

        /*
         * EFFECTS: Generates a panel for creating a new card when the button has
         * clicked.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            startCreateCardView();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: opens the panel for creating new flashcard
     */
    private void startCreateCardView() {
        CreateCardView createCardView = new CreateCardView(view);
        createCardController = new CreateCardController(model, createCardView, view.getProgressBar());
        createCardView.setVisible(true); /* Don't do this in the CreateCardDialog class */
    }

    /*
     * Inner class.
     * EventListener class for 'See all cards' Button in MainMenuView.
     * Generates a panel of all lashcards.
     */
    class SeeEveryCardsBTListener implements ActionListener {

        /*
         * EFFECTS: Generates a panel of all flashcards when the button has clicked.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            startShowAveryCardView();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: Generates a panel of all flashcards.
     * updates the list of flashcards when it's modified.
     * If there's no flashcard, generates an empty table.
     */
    private void startShowAveryCardView() {
        ShowEveryCardsView everyCardsView = new ShowEveryCardsView();
        ShowEveryCardsController everyCardsCT = new ShowEveryCardsController(model, everyCardsView,
                view.getProgressBar());
        everyCardsView.updateFlashcardList(model.getFCards(false));
        everyCardsView.setVisible(true);
    }

    /*
     * Inner class.
     * EventListener class for 'Save' Button in MainMenuView.
     * saves the current state of app into a file.
     */
    class SaveButtonListener implements ActionListener {

        /*
         * EFFECTS: saves the current state of app into a file when the button has
         * clicked.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(model);
                jsonWriter.close();
                // System.out.println("Saved " + model.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e1) {
                // System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    /*
     * Inner class.
     * EventListener class for 'Load' Button in MainMenuView.
     * Load the data from the saved file.
     */
    class LoadButtonListener implements ActionListener {

        /*
         * MODIFIES: model
         * EFFECTS: Load the data from the saved file when the button has clicked.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model = jsonReader.read();
                JProgressBar bar = view.getProgressBar();
                bar.setMaximum(model.getNumFCards());
                bar.setValue(model.getNumOfLearned());
                bar.setIndeterminate(false);
                // System.out.println("Loaded " + model.getName() + " from " + JSON_STORE);
            } catch (IOException e1) {
                // System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }
}
