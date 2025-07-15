package ui.cui;

import model.FCardCollection;
import model.Flashcard;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * FlashCardApp that enables user to add / edit / review / delete 
 * the flashcards.
 * 
 * @version phase 1
 * @author Sydney Lee
 */

public class FlashCardApp {

    private static final String JSON_STORE = "./data/FCardList.json";
    private FCardCollection cardList;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private boolean keepGoing;
    private int currentIndex;
    private int numOfCards;
    private boolean backToMain;

    /*
     * constructor
     * EFFECTS: create an empty list of Flashcards
     */
    public FlashCardApp() {
        cardList = new FCardCollection("Sydney's Flashcard list");
        scanner = new Scanner(System.in);
        keepGoing = true; // a flag for running the program
        currentIndex = 0; 
        numOfCards = cardList.getNumFCards();
        backToMain = false; // a flag for returning to main menu
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    /*
     * EFFECTS: starts the app; 
     * displays the starting menu and process user choice
     * keeps the app running while keepGoing is true; Otherwise quit the app.
     */
    public void run() {
        System.out.println("Starting the FlashCard app...");
        while (keepGoing) {
            mainMenu();
            processMainMenu();          
            //reset backToMain flag
            if (backToMain) {
                backToMain = false;
            }
        }
        System.out.println("Exit the FlashCard app...\n");
    }

    /*
     * EFFECTS: displays main menu; Process user's selection;
     */
    @SuppressWarnings("methodlength")
    public void processMainMenu() {
        char inputChar = getUserInput();
        boolean invalidInput = false;
        do {
            switch (inputChar) {
                case '1': 
                    invalidInput = false;
                    addNewCard();
                    break;
                case '2':
                    invalidInput = false;
                    displayAllCards();
                    if (backToMain) {
                        return;
                    }
                    break;
                case '3':
                    review();
                    invalidInput = false;
                    if (backToMain) {
                        return;
                    }
                    break;
                case '4':
                    System.out.println("\n----<Quitting the app>----\n");
                    System.out.println("        Bye Bye!        ");
                    keepGoing = false;
                    break;
                case '5': //save
                    // TODO
                    saveFCardCollection();
                    break;
                case '6': //load
                    // TODO
                    loadFCardCollection();
                    break;
                default:
                    System.out.println("\nInvalid input!\n");
                    invalidInput = true;
                    inputChar = getUserInput();
            }
        } while (invalidInput && keepGoing);
    }

    /*
    * EFFECTS: prints out main menu.
    */
    Menu() {
        printLine();
        System.out.println("Main menu: \n"
                            + "1: create a new vocabulary card\n"
                            + "2: show every vocabularies\n"
                            + "3: start vocab review for still in learning\n"
                            + "4: quit\n"
                            + "5: save Flashcard List to file\n"
                            + "6: load Flashcard List from file"
        );
    }

    /*
     * REQUIRES: prompt and description should has length > 0
     * MODIFIES: this
     * EFFECTS: gets term and description from user;
     * Creates a new Flashcard and add it to the cardList 
     */
    public void addNewCard() {
        System.out.println("\n----<Create a new FlashCard>----\n");
        String term = "";
        String description = "";
        while (term.length() <= 0 || description.length() <= 0) {
            System.out.println("Type the term for a new flashcard : ");
            term = scanner.nextLine();
            System.out.println("\nType the description(answer) for a new flashcard : ");
            description = scanner.nextLine();
            if (term.length() <= 0 || description.length() <= 0) {
                System.out.println("Invalid input. Please type it again");
            }
        }
        numOfCards++;
        System.out.println("\nNew flashcard is created!");
        cardList.addNewCard(new Flashcard(term, description));
    }


    /*
     * EFFECTS: Prints out every flashcards in the cardList with options for each card;
     * Warn the user if the cardList is empty.
     */
    public void displayAllCards() {
        char inputChar;
        System.out.println("\n----<Show every flashcards>----\n");
        while (currentIndex >= 0 && currentIndex < numOfCards && !backToMain) {
            int count = currentIndex + 1;
            Flashcard card = cardList.getFlashCard(currentIndex);
            System.out.println("No. " + count + "\n");
            System.out.println("Term: " + card.getTerm() + "\n");
            System.out.println("Description: " + card.getDescription() + "\n");
            System.out.println("Learned? : " + card.getLearningStatus());
            printLine();
            menuForDisplayAll();
            inputChar = getUserInput();
            processMenuInDisplayAll(inputChar);
        }
        if (currentIndex < 0) {
            System.out.println("This is the first card! \n"
                            + "Back to main menu");
        }
        if (currentIndex >= numOfCards) {
            System.out.println("This is the last card! \n"
                            + "Back to main menu");
        }
        currentIndex = 0;
    }

    /*
     * EFFECTS: prints out the menu for each cards.
     */
    public void menuForDisplayAll() {
        System.out.println("a: edit card\n"
                        + "b: next card\n"
                        + "c: previous card\n"
                        + "d: delete this card\n"
                        + "e: mark as still learning\n"
                        + "f: back to main menu"
        );
    }
    
    /*
     * MODIFIES: this
     * EFFECTS: process selection of user;
     * 
     * helper method for displayAllCards()
     */
    @SuppressWarnings("methodlength")
    public void processMenuInDisplayAll(char userInput) {
        boolean invalidInput = false;
        do {
            switch (userInput) {
                case 'a':
                    // method for editing card
                    editDescription();
                    break;
                case 'b':
                    // next card
                    currentIndex++;
                    return;
                case'c':
                    // previous card
                    currentIndex--;
                    return;
                case'd':
                    // delete this card
                    deleteCard();
                    return;
                case'e':
                    // mark as still learning
                    if (cardList.getFlashCard(currentIndex).getLearningStatus()) {
                        System.out.println("Status updated!");
                    } else {
                        System.out.println("Already 'still in Learning' !");
                    }
                    printLine();
                    return;
                case'f':
                    // back to main menu
                    backToMain = true;
                    return;
                default:
                    System.out.println("Invalid input\n");
                    userInput = getUserInput();
                    invalidInput = true;
            }
        } while (invalidInput);
    }

    /*
     * MODIFIES: this
     * EFFECTS: get new description from user and set it as 
     * a description of the current card.
     * 
     * Helper method for processMenuInDisplayAll()
     */
    public void editDescription() {
        boolean isValid = false;
        String newDescription = "";
        System.out.println("Type new definition: ");

        while (!isValid) {
            newDescription = scanner.nextLine();
            if (newDescription.length() >= 0) {
                isValid = true;
            } else {
                System.out.println("invalid input.\nType new definition: ");
            }
        }
        cardList.getFlashCard(currentIndex).editDescription(newDescription);
        System.out.println("\nDescription edited!");
    }

    /*
     * MODIFIES: this
     * EFFECTS: delete the current flashcard and notify user. 
     * Decrement numOfCards
     * 
     * Helper method for processMenuInDisplayAll()
     */
    public void deleteCard() {
        cardList.deleteCard(currentIndex);
        numOfCards = cardList.getNumFCards();
        System.out.println("\nDeleted the card");

    }

    /*
     * MODIFIES: this
     * EFFECTS: starts review session for list of cards in 
     * stillInLearning status;
     * Displays the menu for each reviewing card; 
     * Process the selection of user;
     * Prints warning sign if there is no cards to review 
     * and go back to main menu.
     */
    public void review() {
        System.out.println("----<Review Session starts>----");
        ArrayList<Flashcard> reviewList = cardList.gatherFlashcardsToReview();
        while (currentIndex >= 0 && currentIndex < reviewList.size() && !backToMain) {
            String term = reviewList.get(currentIndex).getTerm();
            System.out.println(term + "\n");
            menuForReview();
            char userInput = getUserInput();
            processMenuInReview(userInput);
        }
        if (currentIndex < 0) {
            System.out.println("This is the first card! \n"
                            + "Back to main menu");
        }
        if (currentIndex >= reviewList.size()) {
            System.out.println("This is the last card! \n"
                            + "Back to main menu");
        }
        currentIndex = 0;
    }

    /* 
     * EFFECTS: displays the menu for each card during the review session 
     * for user.
     * 
     * helper method for review()
     */
    public void menuForReview() {
        System.out.println("a: check my answer\n"
                            + "b: next card\n"
                            + "c: previous card\n"
                            + "d: back to main\n"
        );
    }

    /*
     * MODIFIES: this
     * EFFECTS: process the selection of user.
     * check user's answer OR Previous card OR Next card OR back to main menu.
     * 
     * helper method of review()
     */
    public void processMenuInReview(char userInput) {
        boolean invalidInput = false;
        do {
            switch (userInput) {
                case 'a':
                    checkUserAnswer();
                    currentIndex++;
                    break;
                case 'b':
                    currentIndex++;
                    return; 
                case'c':
                    currentIndex--;
                    return;
                case 'd':
                    backToMain = true;
                    return;
                default:
                    System.out.println("Invalid input\n");
                    userInput = getUserInput();
                    invalidInput = true;
            }
        } while (invalidInput);
    }

    /*
     * REQUIRES: user answer has length > 0
     * EFFECTS: get user answer; Check if 
     * the user answer is correct; 
     * Gets user answer again if user select option, 
     * 'try again'; Displays the options for each 
     * cases- correct or incorrect. Processes selection 
     * of user.
     * 
     * helper method of processMenuInReview()
     */
    public void checkUserAnswer() {
        printLine();
        boolean tryAgain = false;
        Flashcard currentCard = cardList.getFlashCard(currentIndex);
        do {
            System.out.println("Type your answer for " + currentCard.getTerm() + " : ");
            String userAnswer = scanner.nextLine();
            if (currentCard.getDescription().equals(userAnswer)) {
                menuAnswerCorrect();
                tryAgain = false;
            } else {
                menuAnswerIncorrect(); 
                tryAgain = processIncorrectAnsMenu();
            }
        } while (tryAgain);
    }

    /*
    * EFFECTS: either try again or show answer 
    * depends on the selection by user.
    * 
    * helper for checkUserAnswer()
    */
    public boolean processIncorrectAnsMenu() {
        boolean invalidInput = false; 
        do {
            char userInput = getUserInput();
            switch (userInput) {
                case 'a':
                    invalidInput = false;
                    break;
                case 'b':
                    showAnswer();
                    return false;
                default:
                    System.out.println("invalid input!\nTry again");
                    invalidInput = true;
            }
        } while (invalidInput);
        return true;
    }

    /*
    * EFFECTS: displays the options for incorrect answer;
    * option a) try again
    * option b) see answer
    * 
    * helper method for checkUserAnswer()
    */
    public void menuAnswerIncorrect() {
        printLine();
        System.out.println("Wrong answer\n");
        System.out.println("a: Try again");
        System.out.println("b: See answer");
        printLine();   
    }
        
    /*
    * EFFECTS: show the answer for the current flashcard
    * 
    * helper method for userAnswerIncorrect()
    */
    public void showAnswer() {
        printLine();
        System.out.println("\nThe answer for " + cardList.getFlashCard(currentIndex).getTerm() + " is :");
        System.out.println("\"" + cardList.getFlashCard(currentIndex).getDescription() + "\"");
        printLine();
    }

    /*
    * MODIRIES: this
    * EFFECTS: notify user's answer is correct; mark current card as "Learned".
    *
    * helper method for checkUserAnswer()
    */
    public void menuAnswerCorrect() {
        printLine();
        System.out.println("Correct answer!\n");
        cardList.markThisCardAsLearned(currentIndex);
        printLine();
    }

    public void printLine() {
        System.out.println("");
        System.out.println("-------------------");
        System.out.println("");
    }

    /*
     * EFFECTS: Gets selection input from user
     */
    public char getUserInput() {
        String inputString = "";
        System.out.println("\nType the option chosen");
        inputString = scanner.nextLine();
        while (inputString.length() <= 0) {
            System.out.println("Please type your option again");
            inputString = scanner.nextLine();
        }
        char inputChar = inputString.charAt(0);
        System.out.println("");
        return inputChar;
    }

    // EFFECTS: saves the Flash card collection to file
    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void saveFCardCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(cardList);
            jsonWriter.close();
            System.out.println("Saved " + cardList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Flash Card Collection from file
    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private void loadFCardCollection() {
        try {
            cardList = jsonReader.read();
            numOfCards = cardList.getNumFCards();
            System.out.println("Loaded " + cardList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}