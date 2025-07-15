package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */
import javax.swing.SwingUtilities;
import model.FCardCollection;

/*
 * Starting point of the Flashcard App. Runs the app.
 */
public class FlashcardAppStarter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FCardCollection model = new FCardCollection("Sydney's Flashcard list");
            MainMenuView view = new MainMenuView();
            MainMenuController controller = new MainMenuController(model, view);
            view.setVisible(true);
        });
    }
}
