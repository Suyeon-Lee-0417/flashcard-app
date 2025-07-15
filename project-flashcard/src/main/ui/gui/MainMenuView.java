package ui.gui;

/**
 * @author Sydney Lee
 * @version phase 3
 */

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import model.EventLog;

/*
 * A GUI class that represents the main menu pannel of the app.
 */
public class MainMenuView extends JFrame {

    private JButton createCardBT;
    private JButton showAllCardsBT;
    private JButton startReviewBT;
    private JButton quitBT;
    private JButton saveFileBT;
    private JButton loadFileBT;
    private JProgressBar progressBar;
    JTextField fileTxtField;

    /*
     * EFFECTS: sets up all the components in main menu panel
     */
    public MainMenuView() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setTitle("Flashcard App");
        this.setSize(600, 520);

        JPanel panel = new JPanel(); /* base pannel */
        this.add(panel);
        fileTxtField = new JTextField("New file"); /* show the current file */
        fileTxtField.setPreferredSize(new Dimension(50, 30));

        /* buttons */
        createCardBT = new JButton("Create new card");
        showAllCardsBT = new JButton("See every vocabularies");
        startReviewBT = new JButton("Start review");
        quitBT = new JButton("Quit App");
        saveFileBT = new JButton("Save data");
        loadFileBT = new JButton("Load data");

        /* pannels */
        JPanel outerCenterPanel = new JPanel(new BorderLayout());
        JPanel outerBottomPanel = new JPanel(new BorderLayout());
        JPanel bottomCenter = new JPanel();
        JPanel westPanel = new JPanel();
        JPanel eastPanel = new JPanel();

        /* pannel styling */
        outerCenterPanel.setBackground(Color.WHITE);
        outerCenterPanel.setPreferredSize(new Dimension(600, 50));
        outerBottomPanel.setBackground(Color.WHITE);
        outerBottomPanel.setPreferredSize(new Dimension(600, 420));
        bottomCenter.setBackground(Color.LIGHT_GRAY);
        bottomCenter.setLayout(new GridLayout(6, 1, 5, 15));
        westPanel.setBackground(Color.DARK_GRAY);
        westPanel.setPreferredSize(new Dimension(100, 0));
        eastPanel.setBackground(Color.DARK_GRAY);
        eastPanel.setPreferredSize(new Dimension(100, 0));

        /* add panels to outerBottomPannel */
        outerBottomPanel.add(westPanel, BorderLayout.WEST);
        outerBottomPanel.add(eastPanel, BorderLayout.EAST);
        outerBottomPanel.add(bottomCenter, BorderLayout.CENTER);

        /* add outer pannels to base pannel */
        panel.add(outerCenterPanel, BorderLayout.CENTER);
        panel.add(outerBottomPanel, BorderLayout.SOUTH);

        bottomCenter.add(createCardBT);
        bottomCenter.add(showAllCardsBT);
        bottomCenter.add(startReviewBT);
        bottomCenter.add(quitBT);
        bottomCenter.add(saveFileBT);
        bottomCenter.add(loadFileBT);

        /* progress bar setup */
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMinimum(0);
        progressBar.setStringPainted(true);
        outerCenterPanel.add(progressBar, BorderLayout.CENTER);

        // place this frame in the middle of screen
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // trigger an event on closing main menu
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (model.Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
                MainMenuView.this.dispose();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: assign ActionListener to createCardBT button
     */
    public void addCreateCardListener(ActionListener listener) {
        createCardBT.addActionListener(listener);
    }

    /*
     * MODIFIES: this
     * EFFECTS: assign ActionListener to showAllCardsBT button
     */
    public void addShowEveryCardsListener(ActionListener listener) {
        showAllCardsBT.addActionListener(listener);
    }

    /*
     * MODIFIES: this
     * EFFECTS: assign ActionListener to saveFileBT button
     */
    public void addSaveListener(ActionListener listener) {
        saveFileBT.addActionListener(listener);
    }

    /*
     * MODIFIES: this
     * EFFECTS: assign ActionListener to loadFileBT button
     */
    public void addLoadListener(ActionListener listener) {
        loadFileBT.addActionListener(listener);
    }

    /*
     * EFFECTS: Returns the progress bar
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }
}
