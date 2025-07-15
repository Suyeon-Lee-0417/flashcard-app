# FlashCard App for language learners


A *bulleted* list:
- item 1: Flashcard 
- item 2: FCardCollection 



## WHAT (Functionalities)
- The user should be able to `add flashcards to a collection` and specify the content/description/etc.
- The user should be able to `have a review session` of the unlearned flashCard collection.
- The user should be able to `edit the description of individual flashcards` in the collection. 
- The user should be able to `view the list of flashcards` in the collection.
- The user should be able to `mark flashcards` in the collection as `Still in Learning`.
- The user should be able to `delete` flashcards from the collection.


## WHO (Expected Users)
- Language Learners
- people who wants a memorization tool for learning terms and descriptions of it.

## WHY
- As a Language Learner, I have always found that the vocabulary apps are often unneccesarily complicated and doesn't have review functionality.
- As a person with ADHD, I am motivated to build an application that is simple and intuitional, but yet has all the functionalities which boosts language learning.

## User Stories

`Phase 0 & 1`
- I want to `add` a new flashcard to the flashcard collection and specify the definition, example usages, translation in my first language.
- I want to `review` the vocabularies *still in learning*.
- I want to `edit` the description of a flashcard. 
- I want to `see` the entire list of vocabularies in this app. 
- I want to `delete` this flashcard from a collection
- I want to `check` my answer for description of the card during the review session
- I want to `mark` the flashcard as *Stil in Learning*.

`Phase 2`
- I want to be able to save a flashcard list that I’ve created so I can continue updating or reviewing it later.
- I'd like an option to save any updates, such as:
    - The updated state after a review.
    - Any changes made to the descriptions.
- I’d like an option to load the saved flashcard list file.

## Instructions for End User

### - You can add a Flashcard to a FCardList by clicking `create new card` -> fill in the text box with new `term` and `description` -> click `save` button -> a new card is created!
    
- Note: text field shouldn't be empty when you hit the 'save' button

### - Two additional required actions that are related to the required user story *add multiple Xs to a Y*
    
1. **Editing flashcards**

    - You can generate `Edit description` of a card by clicking  `See every vocabularies` in main menu after creating some flashcards or loading a saved file -> click `Start Edit` button -> double click the definition cell and type a new definition -> click `Quit Edit`
    
    - You can generate `Edit Learning Status` of a card by clicking `See every vocabularies` in main menu after creating some flashcards or loading the saved file -> click `Start Edit` button -> click the check box of which you want to edit its learning status -> click `Quit Edit`

2. **Deleting Flashcards** 
    - You can generate `Delete`on a flashcard by navigating to the Main Menu and click `See Every Vocabularies` (after creating flashcards or loading a saved file) ->
    
    clicking `See every vocabularies` in main menu after creating some flashcards or loading the saved file -> If you are in Edit mode, click `Quit Edit` button first, before deleting a card -> Ensure the entire row of the flashcard you want to delete is selected by clicking anywhere on that row. -> click the `Delete` button to remove the selected flashcard.
    - Note: Selecting only specific cells will not work; the full row must be selected. 

### - You can locate my visual component by opening the app -> A proress bar will be appeared on the main menu panel. It indicates the percentage of how many flashcards you have been `Learned` out of all flashcards. Once you create some cards, edit a card's learning status, delete a card the progress bar will reflect the updates that you made.

### - You can save the state of my application by opening the app -> after you creates some flashcards or updates flashcards, click `Save data` button. 

### - You can reload the state of my application by opening the app -> click the `Load data` button -> you can check the reloaded file by clicking `See every vocabularies` button.


### Phase 4: Task 3

If I had more time to work on the project, I would focus on making the class relationships more concise and intuitive. Currently, the number of classes makes it challenging to determine where specific tasks are performed. Simplifying these relationships would enhance the overall readability and maintainability of the code.

Additionally, I would consider creating a class that consolidates all the existing classes into one central location, leading to a more organized design. This central class would act as a coordinator, improving the structure of the application. Lastly, I would address the issue of code duplication by implementing the Composite Pattern, which would help eliminate redundant code and promote better code reuse.


## example log
Mon Dec 02 16:50:12 PST 2024
Added a new card, Card: Apple
Mon Dec 02 16:50:23 PST 2024
Added a new card, Card: Banana
Mon Dec 02 16:50:25 PST 2024
Displaying all cards in your flashcards
Mon Dec 02 16:50:28 PST 2024
Marked a card, Card: Apple, as Learned
Mon Dec 02 16:50:31 PST 2024
Failed to delete. Invalid card selected
Mon Dec 02 16:50:33 PST 2024
Marked a card, Card: Banana, as Learned
Mon Dec 02 16:50:35 PST 2024
Marked a card, Card: Apple, as Still Learning
Mon Dec 02 16:50:38 PST 2024
Deleted the card, Card: Banana
