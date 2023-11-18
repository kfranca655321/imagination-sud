/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              ImaginationController implements Initializable
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class implements the Initializable interface.
 *                      This class is the operational hub of the game.  It contains:
 *                          - JavaFX view code to display the GUI.
 *                          - Handlers for keyboard input.
 *                          - Inner class GameLoop which is an extention of AnimationTimer
 *                              - It controls/updates MOBs after a fixed number of frames per second in game.
 *                          - Hero class object.
 *                          - Dungeon class object.
 *                          - Methods to control game logic and update GUI.
 */

package main.javafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ImaginationController implements Initializable {
    private boolean splashScreenOption; // Accessing options menu from spash screen?
    private boolean inGame; // Are you in game or accessing an in game menu?
    private final double SOLID = 1; // AnchorPane opacity = SOLID
    private final double SEE_THROUGH = 0.35; // AnchorPane opacity = SEE THROUGH (used when game is paused)
    private final GameLoop gameLoop = new GameLoop(); // Game loop inner class
    private boolean inGameMenu = false; // Are you on in game menu?
    private boolean gameRunning = true; // Is the game running?
    private boolean mapScreen = false; // Are you on the map screen?
    private boolean insightsScreen = false; // Are you on the Insights screen? 
    private boolean helpScreen = false; // Are you on the Help screen?     
    private final int STARTING_X_POSITION = 5; // Hero starting room x location
    private final int STARTING_Y_POSITION = 5; // Hero starting room y location
    private final int STARTING_IMAGE_SIZE = 23; // starting image size for wall blocks
    private final int STARTING_HERO_SIZE = 20; // starting image size for Hero  
    private final ImageView heroView = new ImageView(); // ImageView used to store Hero image
    private final ArrayList<ArrayList<ImageView>> wallBlocks = new ArrayList<ArrayList<ImageView>>(); // ArrayList of ArrayList(s) to store wall block ImageView(s)
    private final ArrayList<ArrayList<ImageView>> mobViews = new ArrayList<ArrayList<ImageView>>(); // ArrayList of ArrayList(s) to store MOB ImageView(s)    
    private final Hero hero = new Hero("S", STARTING_X_POSITION, STARTING_Y_POSITION); // Hero object          
    private Dungeon dungeon = new Dungeon(STARTING_X_POSITION, STARTING_Y_POSITION); // Dungeon object    
    private boolean toggle = true; // menu button toggle flag    
    private final ArrayList<XYPoint> mobTargetSquares = new ArrayList<XYPoint>(); // store MOB target squares to travel to (get as close to Hero as possible) 
    private final Random random = new Random(); // Random number generator
    private final Map map = new Map(); // dungeon map
    private final ArrayList<ArrayList<ImageView>> mapViews = new ArrayList<ArrayList<ImageView>>(); // ArrayList of ArrayList(s) to store map room ImageView(s)    
    private final GameData gameData = new GameData(); // save, load and delete games
    private boolean restartGame = false; // is this a previously saved game?
    private final int AGRO_DISTANCE = 5; // agro distance from hero
    private final int MAGE_ARMOR_DC = 14; // mage armor difficulty check
    private final int SECOND_WIND_DC = 14; // second wind difficulty check
    private String previousCommand = ""; // store previous command
    private int imaginationBonus = 0; // imagination bonus (+0 to +3)
    
    // root AnchorPane for the application
    @FXML
    private AnchorPane rootAnchorPane = new AnchorPane();
    
    // splash screen AnchorPane
    @FXML
    private AnchorPane splashScreenAnchorPane = new AnchorPane();
    
    // in game AnchorPane
    @FXML
    private AnchorPane inGameAnchorPane = new AnchorPane();  
    
    // load game AnchorPane
    @FXML
    private AnchorPane startGameAnchorPane = new AnchorPane(); 
    
    // delete game AnchorPane
    @FXML
    private AnchorPane deleteGameAnchorPane = new AnchorPane(); 
    
    // opstions AnchorPane
    @FXML
    private AnchorPane optionsAnchorPane = new AnchorPane(); 
    
    // game menu AnchorPane
    @FXML
    private AnchorPane gameMenuAnchorPane = new AnchorPane(); 
    
    // new game AnchorPane
    @FXML
    private AnchorPane newGameAnchorPane = new AnchorPane(); 
      
    // map screen AnchorPane
    @FXML
    private AnchorPane mapAnchorPane = new AnchorPane(); 
    
    // insights AnchorPane
    @FXML
    private AnchorPane insightsAnchorPane = new AnchorPane();
    
    // game over AnchorPane
    @FXML
    private AnchorPane gameOverAnchorPane = new AnchorPane(); 
    
    // command line TextField
    @FXML
    private TextField commandLine = new TextField(); 
    
    // GridPane used to display 2D dungeon
    @FXML
    private GridPane gridPaneWorld = new GridPane();

    // rooms visited labels
    @FXML
    private Label roomsVisitedLabel = new Label();
    @FXML
    private Label roomsVisitedLabel2 = new Label();
    
    // high score labels
    @FXML
    private Label highScoreLabel = new Label();
    @FXML
    private Label highScoreLabel2 = new Label();
    
    // distance from starting room labels
    @FXML
    private Label distanceFromStartingRoomLabel = new Label();
    @FXML
    private Label distanceFromStartingRoomLabel2 = new Label();
    
    // available exits labels
    @FXML
    private Label availableExitsLabel = new Label();
    @FXML
    private Label availableExitsLabel2 = new Label();
    
    // imagination progress bar
    @FXML
    private ProgressBar imaginationProgressBar = new ProgressBar();
    
    // imagination progress bar text
    @FXML
    private Text imaginationProgressBarText = new Text();
    
    // health progress bar
    @FXML
    private ProgressBar healthProgressBar = new ProgressBar();
    
    // health progress bar text
    @FXML
    private Text healthProgressBarText = new Text();
   
    // map GridPane
    @FXML
    private GridPane mapGridPane = new GridPane();
     
    // new game text input field
    @FXML
    private TextField savedGameNameTextField = new TextField();
    
    // Help display - List of keyboard commands
    @FXML
    private AnchorPane helpAnchorPane = new AnchorPane();
    
    // Combat Dialog - Display combat 
    @FXML
    private TextArea combatDialog = new TextArea();
    
    // enemy level label
    @FXML
    private Label enemyLevelLabel = new Label();
    
    // mage armor icon
    @FXML
    private ImageView mageArmorIconImageView;
    
    // second wind icon
    @FXML
    private ImageView secondWindIconImageView;
    
    // imagination icon
    @FXML
    private ImageView imaginationBonusImageView;
    
    // attack rating label
    @FXML
    private Label attackRatingLabel = new Label();
    
    // insight rating label
    @FXML
    private Label insightRatingLabel = new Label();
    
    // defense rating label
    @FXML
    private Label defenseRatingLabel = new Label();
    
    // "small text" radio button
    @FXML
    private RadioButton smallTextRadioButton = new RadioButton();
    
    // "large text" radio button
    @FXML
    private RadioButton largeTextRadioButton = new RadioButton();
    
    /*
     * initialize Method - Initialize dungeon
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initGame(); // init game
        setDungeonStats(); // set dungeon statistics
    }
    
    /*
     * handleStartGameButtonAction - Start game button event handler
     */
    @FXML
    private void handleStartGameButtonAction(ActionEvent event) {
        splashScreenAnchorPane.setVisible(false);
        startGameAnchorPane.setVisible(true);
        startGameAnchorPane.toFront();
    }
   
    /*
     * handleOptionsButtonAction - Options button event handler
     */
    @FXML
    private void handleOptionsButtonAction(ActionEvent event) {
        splashScreenOption = true;
        splashScreenAnchorPane.setVisible(false);
        optionsAnchorPane.setVisible(true);
        optionsAnchorPane.toFront();
    }
    
    /*
     * handleQuitButtonAction - Quit button event handler
     */
    @FXML
    private void handleQuitButtonAction(ActionEvent event) {
        Platform.exit();
    }
    
    /*
     * handleOptionsDoneButtonAction - Options menu done button event handler
     */
    @FXML
    private void handleOptionsDoneButtonAction(ActionEvent event) {
        if (splashScreenOption) {
            splashScreenAnchorPane.setVisible(true);
            splashScreenAnchorPane.toFront();
        } else {
            inGameAnchorPane.setVisible(true);
            inGameAnchorPane.setOpacity(SEE_THROUGH);
            gameMenuAnchorPane.setVisible(true);
            gameMenuAnchorPane.toFront();
            inGameMenu = true;
        }
        optionsAnchorPane.setVisible(false);
    }
    
    /*
     * handleNewGameButtonAction - New game button event handler
     */
    @FXML
    private void handleNewGameButtonAction(ActionEvent event) {
        //newGameAnchorPane.setVisible(true);
        //newGameAnchorPane.toFront();
        //newGameAnchorPane.setVisible(false);
        startGameAnchorPane.setVisible(false);
        inGame = true;
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.toFront();
        gameRunning = true;
        gameLoop.start(); // start game
        restartGame = false; // is this a previously saved game?
        dungeon = new Dungeon(STARTING_X_POSITION, STARTING_Y_POSITION); // create new dungeon object
        initGame(); // init game
        setDungeonStats(); // set dungeon statistics
        resizeImages(); // ensure GridPane images are at correct size 
    }
    
    /*
     * handleCancelButtonAction - Cancel button event handler
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        startGameAnchorPane.setVisible(false);
        splashScreenAnchorPane.setVisible(true);
        splashScreenAnchorPane.toFront();
    }
    
    /*
     * handleOptionsButton2Action - Second options button event handler
     */
    @FXML
    private void handleOptionsButton2Action(ActionEvent event) {
        splashScreenOption = false;
        gameMenuAnchorPane.setVisible(false);
        optionsAnchorPane.setVisible(true);
        optionsAnchorPane.toFront();
        inGameMenu = false;
    }
    
    /*
     * handleQuitToTitleButtonAction - Quit-to-title button event handler
     */
    @FXML
    private void handleQuitToTitleButtonAction(ActionEvent event) {
        inGame = false;
        gameMenuAnchorPane.setVisible(false);
        inGameAnchorPane.setOpacity(SOLID);
        inGameAnchorPane.setVisible(false);
        splashScreenAnchorPane.setVisible(true);
        splashScreenAnchorPane.toFront();
        inGameMenu = false;
        gameRunning = false;
        saveGame();
    } 
    
    /*
     * handleQuitToTitleButton2Action - Quit the game from the gameOverAnchorPane
     */
    @FXML
    private void handleQuitToTitleButton2Action(ActionEvent event) {
        gameOverAnchorPane.setVisible(false);
        handleQuitToTitleButtonAction(event);
    }
    
    /*
     * handleRestartGameButtonAction - Restart the game
     */
    @FXML
    private void handleRestartGameButtonAction(ActionEvent event) {
        inGame = true;
        gameRunning = true; 
        restartGame = true;
        gameOverAnchorPane.setVisible(false);
        inGameAnchorPane.setOpacity(SOLID);
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.toFront();
        dungeon.regenDungeon(); 
        initGame();
        regenMobs();
        dungeon.getRoom(0).initializeStartingRoom(); 
        resizeImages(); // ensure GridPane images are at correct size 
        combatDialog.setText(""); // clear combat dialog
        gameLoop.start(); // start game        
    }
    
    /*
     * gameOver - Brings up "Game Over" AnchorPane when Hero dies (health = 0)
     *      Also resets Hero collision detection
     */
    private void gameOver() {
        inGameAnchorPane.setOpacity(SEE_THROUGH);
        gameOverAnchorPane.setVisible(true);
        gameOverAnchorPane.toFront();
        gameLoop.stop(); // stop game
        gameRunning = false;
        unsetHero();
        heroView.setImage(hero.getDeadHero()); // getDeadHero
    }
    
    /*
     * handleLoadSavedGameButtonAction - Load game button event handler
     */
    @FXML
    private void handleLoadSavedGameButtonAction(ActionEvent event) { 
        if (!gameData.doesSavedGameExist())
            return;
               
        dungeon.regenDungeon(); // regenerate dungeon
        initGame(); // init game
        regenMobs(); // regenerate mobs
        dungeon.getRoom(0).initializeStartingRoom(); // init starting room 
        resizeImages(); // ensure GridPane images are at correct size
        loadGame(); // load saved game
        setDungeonStats(); // set dungeon statistics
        
        inGame = true;
        startGameAnchorPane.setVisible(false);
        splashScreenAnchorPane.setVisible(false);
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.toFront();
        gameRunning = true;
        gameLoop.start(); // start game
        restartGame = true; // previously saved game
    }
    
    /*
     * handleDeleteSelectedGameButtonAction - Delete selected game button event handler
     */
    @FXML
    private void handleDeleteSelectedGameButtonAction(ActionEvent event) {
        deleteGameAnchorPane.setVisible(true);
        startGameAnchorPane.setVisible(false);
        deleteGameAnchorPane.toFront();
    }
    /*
     * handleDeleteGameButtonAction - Delete game button event handler
     */
    @FXML
    private void handleDeleteGameButtonAction(ActionEvent event) {
        System.out.println("TO DO:  DELETE GAME!!!");
    }
    
    /*
     * handleCancelDeleteGameButtonAction - Cancel delete game button event handler
     */
    @FXML
    private void handleCancelDeleteGameButtonAction(ActionEvent event) {
        deleteGameAnchorPane.setVisible(false);
        startGameAnchorPane.setVisible(true);
        startGameAnchorPane.toFront();
    }
    
    /*
     * handleCreateNewGameButtonAction - Create new game button event handler
     */
    @FXML
    private void handleCreateNewGameButtonAction(ActionEvent event) {
        inGame = true;
        newGameAnchorPane.setVisible(false);
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.toFront();
        gameRunning = true;
        gameLoop.start(); // start game
        restartGame = false; // is this a previously saved game?
        dungeon = new Dungeon(STARTING_X_POSITION, STARTING_Y_POSITION); // create new dungeon object
        initGame(); // init game
        setDungeonStats(); // set dungeon statistics
        resizeImages(); // ensure GridPane images are at correct size 
    }
    
    /*
     * handleCreateNewGameCancelButtonAction - Create new game screen cancel button event handler
     */
    @FXML
    private void handleCreateNewGameCancelButtonAction(ActionEvent event) {
        savedGameNameTextField.setText(""); // cleared save game text field        
        newGameAnchorPane.setVisible(false);
        startGameAnchorPane.setVisible(true);
        startGameAnchorPane.toFront();
    }
    
    /*
     * handleMapCloseButtonAction - Map close button event handler
     */
    @FXML
    private void handleMapCloseButtonAction(ActionEvent event) {
        mapAnchorPane.setVisible(false);
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.toFront();
        gameLoop.start();
        gameRunning = true;
        mapScreen = false;
    }
    
    /*
     * handleInsightsCloseButtonAction - Insights screen close button event handler
     */
    @FXML
    private void handleInsightsCloseButtonAction(ActionEvent event) {
        insightsAnchorPane.setVisible(false);
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.toFront();
        gameLoop.start();
        gameRunning = true;
        insightsScreen = false;
    }
    
    /*
     * handleBackToGameButtonAction - Back to game button event handler
     */
    @FXML
    private void handleBackToGameButtonAction(ActionEvent event) {
        gameMenuAnchorPane.setVisible(false);
        inGameAnchorPane.setVisible(true);
        inGameAnchorPane.setOpacity(SOLID);
        inGameAnchorPane.toFront();
        inGameMenu = false;
        gameRunning = true;
        gameLoop.start(); // start game
    }
    
    /*
     * handleEscKeyPressedAction - ESC key event handler
     */
    protected void handleEscKeyPressedAction() {
        if (inGame) {
            if (gameRunning) {
                inGameMenu = true;
                inGameAnchorPane.setOpacity(SEE_THROUGH);
                gameMenuAnchorPane.setVisible(true);
                gameMenuAnchorPane.toFront();
                gameLoop.stop(); // stop game
                gameRunning = false;
            } else if (inGameMenu) {
                inGameMenu = false;
                inGameAnchorPane.setOpacity(SOLID);
                gameMenuAnchorPane.setVisible(false);
                inGameAnchorPane.toFront();
                gameLoop.start(); // start game
                gameRunning = true;
            }
        }
    }
    
    /*
     * handleF1KeyPressedAction - F1 key event handler
     */
    protected void handleF1KeyPressedAction() {
        if (inGame) {
            if (gameRunning) {
                inGameAnchorPane.setVisible(false);
                gameLoop.stop();
                gameRunning = false;
                mapScreen = true;
                mapAnchorPane.setVisible(true);
                mapAnchorPane.toFront();
                generateMap();
            } else {
                if (mapScreen) {
                    mapScreen = false;
                    gameRunning = true;
                    mapAnchorPane.setVisible(false);
                    inGameAnchorPane.setVisible(true);
                    inGameAnchorPane.toFront();
                    gameLoop.start();
                }
            }
        }
    }

    /*
     * handleF2KeyPressedAction - F2 key event handler
     */
    protected void handleF2KeyPressedAction() {
        if (inGame) {
            if (gameRunning) {
                inGameAnchorPane.setVisible(false);
                gameLoop.stop();
                gameRunning = false;
                insightsScreen = true;
                insightsAnchorPane.setVisible(true);
                insightsAnchorPane.toFront();
            } else {
                if (insightsScreen) {
                    insightsScreen = false;
                    gameRunning = true;
                    insightsAnchorPane.setVisible(false);
                    inGameAnchorPane.setVisible(true);
                    inGameAnchorPane.toFront();
                    gameLoop.start();
                }
            }
        }
    }
    
    /*
     * handleHelpButtonAction - Splash screen help button event handler
     */
    @FXML
    private void handleHelpButtonAction(ActionEvent event) {
        splashScreenAnchorPane.setVisible(false);
        helpAnchorPane.setVisible(true);
        helpAnchorPane.toFront();
    }
       
    /*
     * handleF3KeyPressedAction - F3 key event handler
     */
    protected void handleF3KeyPressedAction() {   
       if (inGame) {
            if (gameRunning) {
                inGameAnchorPane.setVisible(false);
                gameLoop.stop();
                gameRunning = false;
                helpScreen = true;
                helpAnchorPane.setVisible(true);
                helpAnchorPane.toFront();
            } else {
                helpScreen = false;
                gameRunning = true;
                helpAnchorPane.setVisible(false);
                inGameAnchorPane.setVisible(true);
                inGameAnchorPane.toFront();
                gameLoop.start();
            }
        }
    }
    
    /*
     * handleHelpCloseButtonAction - Help close button event handler
     */
    @FXML
    private void handleHelpCloseButtonAction(ActionEvent event) {
        if (inGame) {
           helpAnchorPane.setVisible(false);
           inGameAnchorPane.setVisible(true);
           inGameAnchorPane.toFront();
           gameLoop.start();
           gameRunning = true;
           helpScreen = false;
        } else {
            splashScreenAnchorPane.setVisible(true);
            helpAnchorPane.setVisible(false);
            splashScreenAnchorPane.toFront();
        }
    }
    
    /*
     * handleSpaceKeyPressedAction - Space key event handler
     *      This is the Hero attack key.
     */
    protected void handleSpaceKeyPressedAction() {
        // do not attack if command line is in focus
        if (inGame && gameRunning && commandLine.isFocused())
            return;
        
        attack();
    }
    
    /*
     * attack - Hero attacks mob
     */
    protected void attack() {
        /*
         * if heroAttack = false, exit attack method
         * else, set heroAttack to false and 
         * execute method
         */
        if (!gameLoop.getHeroAttack())
            return;
        else
            gameLoop.setHeroAttack(false);
        
        String dir = hero.getDirFacing();
        int x = hero.getXLocation();
        int y = hero.getYLocation();
        
        if (dir == "N") {
            x = hero.getXLocation();
            y = hero.getYLocation()-1;
        } else if (dir == "E") {
            x = hero.getXLocation()+1;
            y = hero.getYLocation();
        } else if (dir == "S") {
            x = hero.getXLocation();
            y = hero.getYLocation()+1;
        } else if (dir == "W") {
            x = hero.getXLocation()-1;
            y = hero.getYLocation();
        }

        Room r = getRoom();
        GiantRat rat = r.getMob(x, y);
        String text; // combat dialog
        
        if (rat == null) {
            text = combatDialog.getText() + "\nYou swing your SWORD!\n";
            combatDialog.setText(text);
            
            // scroll text area to bottom
            combatDialog.setScrollTop(Double.MAX_VALUE);
            combatDialog.appendText("");
            
            return;
        }
            
        int index = r.getIndexOfMob(x, y);
        
        int dieTwentyRoll = random.nextInt(20) + 1;
        int attack = hero.getAttack() + dieTwentyRoll;
        text = combatDialog.getText() + "\nYou swing your SWORD at a GIANT RAT!" +
               "\n AR vs. DR: " + dieTwentyRoll + " + " + hero.getAttack() + " = " + (dieTwentyRoll + hero.getAttack()) + 
               " vs " + rat.getDefenseRating();
        combatDialog.setText(text);
        
        if (attack >= rat.getDefenseRating())  {
            int damage = random.nextInt(hero.getDamage()) + 1;
            int health = rat.getHealth() - damage;
            
            text = combatDialog.getText() + "\nYou slash the RAT for " + damage + " damage!";
            combatDialog.setText(text);
            
            if (health <= 0) {
                text = combatDialog.getText() + "\nYou KILLED the RAT!";
                combatDialog.setText(text);
                
                r.removeMob(index);
                r.setNumSpawns(r.getNumSpawns()-1);
                gridPaneWorld.getChildren().remove(mobViews.get(y).get(x));
                mobViews.get(y).set(x, new ImageView());
                r.getSquare(x, y).setOccupiedByAnimateObject(false);
                r.getSquare(x, y-1).setGoSouth(true);
                r.getSquare(x+1, y).setGoWest(true);
                r.getSquare(x, y+1).setGoNorth(true);
                r.getSquare(x-1, y).setGoEast(true);
                
                incrementImagination();
            } else {
                rat.setHealth(health);
            }
            
            // add newline
            text = combatDialog.getText() + "\n";
            combatDialog.setText(text);
        } else {
            text = combatDialog.getText() + "\nYou miss!\n";
            combatDialog.setText(text);
        }
        
        // scroll text area to bottom
        combatDialog.setScrollTop(Double.MAX_VALUE);
        combatDialog.appendText("");
    }
    
    /*
     * handleDirectionKeyPressedAction - Arrow key event handler
     */
    protected void handleDirectionKeyPressedAction(String direction) {
        if (inGame && gameRunning) {
            if (direction == hero.getDirFacing()) {
                validMove(hero.getDirFacing());
            } else {
                switch (direction) {
                    case "N":
                        hero.setDirFacing("N");
                        heroView.setImage(hero.getHeroNorth());          
                        break;
                    case "S":
                        hero.setDirFacing("S");
                        heroView.setImage(hero.getHeroSouth()); 
                        break;
                    case "E":
                        hero.setDirFacing("E");
                        heroView.setImage(hero.getHeroEast()); 
                        break;
                    case "W":
                        hero.setDirFacing("W");
                        heroView.setImage(hero.getHeroWest()); 
                        break;  
                }
            }
        }
    }
    
    /*
     * handleStrafeAction method - Method to handle strafe movement (CONTROL + arrow key) 
     */
    protected void handleStrafeAction(String direction) {
        validMove(direction);
    }
    
    /*
     * handleAltKeyPressedAction - ALT key event handler
     */
    protected void handleAltKeyPressedAction() {
        if (inGame && gameRunning) {
            if (toggle) {
                commandLine.requestFocus();
            } else {
                gridPaneWorld.requestFocus();
                commandLine.clear();
            }
            toggle = !toggle;
        }
    }
    
    /*
     * handleEnterKeyPressedAction - Enter key event handler
     *      Process command line (insights)
     */
    protected void handleEnterKeyPressedAction() {
        // only process command if command line is in focus
        if (!commandLine.isFocused()) {
            commandLine.requestFocus();
            return;
        }
        
        String command = commandLine.getText(); // put command line contents into string
        commandLine.clear(); // clear command line
        command = command.trim(); // trim white space
        command = command.toLowerCase(); // force lower case to make case insensitive
        String text = ""; // combat dialog text
        
        // if blank command line entered, execute previous command
        if (command.equals("")) {
            command = previousCommand;
        } else {
            previousCommand = command;
        }
            
        
        int dieTwentyRoll = random.nextInt(20) + 1;
        int insightRoll = dieTwentyRoll + hero.getAttack();
        
        if (command.equals("m") || command.equals("mage armor")) {
            if (gameLoop.getMageArmor()) {
                text = "\nMAGE ARMOR is already ACTIVE!\n";
            } else {
                if (insightRoll >= MAGE_ARMOR_DC) {
                    incrementImagination();
                    gameLoop.mageArmor = true;
                    text = "\nYou conjure a MAGICAL AURA!";
                    hero.setDefenseRating(hero.getDefenseRating() + 4);
                    defenseRatingLabel.setText(Integer.toString(hero.getDefenseRating()));
                    mageArmorIconImageView.setVisible(true); // display icon
                } else {
                    text = "\nMAGE ARMOR FAILS!";
                }
                text += "\n IR vs. DC: " + dieTwentyRoll + " + " + hero.getAttack() + " = " + insightRoll + " vs " + MAGE_ARMOR_DC + "\n"; 
            }
        } else if (command.equals("s") || command.equals("second wind")) {
            if (gameLoop.getSecondWind()) {
                text = "\nSECOND WIND is already ACTIVE!\n";
            } else {
                if (insightRoll >= SECOND_WIND_DC) {
                    incrementImagination();
                    gameLoop.secondWind = true;
                    text = "\nYou got a SECOND WIND!";
                    secondWindIconImageView.setVisible(true); // display icon
                } else {
                    text = "\nSECOND WIND FAILS!";
                }
                text += "\n IR vs. DC: " + dieTwentyRoll + " + " + hero.getAttack() + " = " + insightRoll + " vs " + SECOND_WIND_DC + "\n"; 
            }
        } else if (command.equals("a") || command.equals("attack")) {
            attack();
        } else {
            text = "\nThis is not a recognized ACTION!\n";
        }
        
        combatDialog.setText(combatDialog.getText() + text);
        
        // scroll text area to bottom
        combatDialog.setScrollTop(Double.MAX_VALUE);
        combatDialog.appendText("");
    }

    /*
     * validMove Method - Move Hero in the cardinal direction passed as an 
     *      if square is valid
     */
    private void validMove(String dir) {
        int x = hero.getXLocation();
        int y = hero.getYLocation();
       
        /*
         * check if square is occupied
         * this is required to prevent hero
         * from occupying MOB square
         */
        boolean valid = true;
        if ((dir == "N" && dungeon.getSquare(x, y-1).getOccupiedByAnimateObject() == true) ||
            (dir == "E" && dungeon.getSquare(x+1, y).getOccupiedByAnimateObject() == true) ||
            (dir == "S" && dungeon.getSquare(x, y+1).getOccupiedByAnimateObject() == true) ||     
            (dir == "W" && dungeon.getSquare(x-1, y).getOccupiedByAnimateObject() == true))
            valid = false;
        
        // if direction is valid and square is not occupied
        if (dungeon.getSquare(x, y).validDirection(dir) && valid) {      
            dungeon.getSquare(x+1, y).setGoWest(true);
            dungeon.getSquare(x-1, y).setGoEast(true);
            dungeon.getSquare(x, y+1).setGoNorth(true);
            dungeon.getSquare(x, y-1).setGoSouth(true);
            dungeon.getSquare(x, y).setOccupiedByAnimateObject(false);
            
            // move hero
            hero.move(dir);
            
            // get new location
            x = hero.getXLocation();
            y = hero.getYLocation();
            
            // does hero leave room?
            String nextRoom = "";
            if (x == 5 && y == 0) //go north to next room
                nextRoom = "N";
            else if (x == 0 && y == 5) //go west to next room
                nextRoom = "W";
            else if (x == 10 && y == 5) //go east to next room
                nextRoom = "E";
            else if (x == 5 && y == 10) //go south to next room
                nextRoom = "S";
            
            // hero still in current room
            if (nextRoom == "") { 
                dungeon.getSquare(x+1, y).setGoWest(false);
                dungeon.getSquare(x-1, y).setGoEast(false);
                dungeon.getSquare(x, y+1).setGoNorth(false);
                dungeon.getSquare(x, y-1).setGoSouth(false);
                dungeon.getSquare(x, y).setOccupiedByAnimateObject(true);
               
                gridPaneWorld.getChildren().remove(heroView);
                gridPaneWorld.add(heroView, x, y);
            // hero goes to next room
            } else {
                // update X Y location of room
                if ("N".equals(nextRoom)) 
                    dungeon.setRoomYLoc(dungeon.getRoomYLoc() + 1);
                else if ("E".equals(nextRoom))
                    dungeon.setRoomXLoc(dungeon.getRoomXLoc() + 1);
                else if ("S".equals(nextRoom))
                    dungeon.setRoomYLoc(dungeon.getRoomYLoc() - 1);
                else if ("W".equals(nextRoom))
                    dungeon.setRoomXLoc(dungeon.getRoomXLoc() - 1);
                
                // if room doesn't exist, create it and update scores
                if (!dungeon.roomExist()) {
                    dungeon.createNewRoom(nextRoom); 
                    
                    // update number of dungeon exits
                    availableExitsLabel.setText(Integer.toString(dungeon.getNumDungeonExits()));
                    availableExitsLabel2.setText(Integer.toString(dungeon.getNumDungeonExits()));
                    
                    for (int i = 0; i <= getRoom().getNumSpawns()-1; i++)
                        spawnMobs(i, getRoom());
                    
                    roomsVisitedLabel.setText(Integer.toString(dungeon.getNumRoomsVisited()));
                    roomsVisitedLabel2.setText(Integer.toString(dungeon.getNumRoomsVisited()));
                    highScoreLabel.setText(Integer.toString(dungeon.getHighScore()));
                    highScoreLabel2.setText(Integer.toString(dungeon.getHighScore()));
                } 
                    
                // get distance from starting room
                distanceFromStartingRoomLabel.setText(Integer.toString(dungeon.getNumRoomsFromStart()));
                distanceFromStartingRoomLabel2.setText(Integer.toString(dungeon.getNumRoomsFromStart()));
                
                // display enemy level
                
                int enemyLevel = dungeon.getNumSpawns();
                if (enemyLevel < 1)
                    enemyLevel = 1;
                enemyLevelLabel.setText(Integer.toString(enemyLevel));
                    
                
                // redraw room
                wallBlocks.clear();
                gridPaneWorld.getChildren().clear();
                addWalls();
                addMobs();
                resizeImages(); 
                
                // place hero next to the door hero entered
                if (nextRoom == "N") {
                    hero.setXLocation(5);
                    hero.setYLocation(9);
                } else if (nextRoom == "E") {
                    hero.setXLocation(1);
                    hero.setYLocation(5);
                } else if (nextRoom == "S") {
                    hero.setXLocation(5);
                    hero.setYLocation(1);
                } else if (nextRoom == "W") {
                    hero.setXLocation(9);
                    hero.setYLocation(5);
                } 
                
                x = hero.getXLocation();
                y = hero.getYLocation();
                
                dungeon.getSquare(x+1, y).setGoWest(false);
                dungeon.getSquare(x-1, y).setGoEast(false);
                dungeon.getSquare(x, y+1).setGoNorth(false);
                dungeon.getSquare(x, y-1).setGoSouth(false);
                
                dungeon.getSquare(x, y).setOccupiedByAnimateObject(true);
                gridPaneWorld.add(heroView, x, y);
            }
        }   
    }
    
    /*
     * addWalls Method - Add Room Walls 
     */
    private void addWalls() {
        Room r = getRoom();
        Image wallBlock = new Image(ImaginationMain.class.getResource(dungeon.getWallBlock()).toString());
        
        for (int y = 0; y <= dungeon.getRoomYSize()-1; y++) {
            wallBlocks.add(new ArrayList<ImageView>()); 
            
            for (int x = 0; x <= dungeon.getRoomXSize()-1; x++) {
                wallBlocks.get(y).add(new ImageView(wallBlock)); 
                wallBlocks.get(y).get(x).setFitWidth(STARTING_IMAGE_SIZE);
                wallBlocks.get(y).get(x).setFitHeight(STARTING_IMAGE_SIZE);
               
                if (dungeon.getSquare(x, y).getOccupiedByInanimateObject())
                    gridPaneWorld.add(wallBlocks.get(y).get(x), x, y);         
            }   
        } 
    }
    
    /*
     * initMobViews Method - initialize MOB ImageViews
     */
    private void initMobViews() {
        for (int y = 0; y <= dungeon.getRoomYSize()-1; y++) {
            mobViews.add(new ArrayList<ImageView>()); 
            for (int x = 0; x <= dungeon.getRoomXSize()-1; x++)
                mobViews.get(y).add(new ImageView());
        }
    }
    
    /*
     * addMobs Method - Add MOB images to dungeon display
     */
    private void addMobs() {
        for (int y = 0; y <= dungeon.getRoomYSize()-1; y++) {
            for (int x = 0; x <= dungeon.getRoomXSize()-1; x++)
                if (dungeon.getSquare(x, y).getOccupiedByAnimateObject())
                    if (hero.getXLocation() != x || hero.getYLocation() != y)
                        gridPaneWorld.add(mobViews.get(y).get(x), x, y);  
        }
    }
    
    /*
     * resizeImages Method - Resize images to match application size
     */
    protected void resizeImages() {
        double width = getWidth();
        double height = getHeight();
        
        // resize hero
        heroView.setFitWidth(width);
        heroView.setFitHeight(height); 

        // resize other images
        for (int y = 0; y <= dungeon.getRoomYSize()-1; y++) {
            for (int x = 0; x <= dungeon.getRoomXSize()-1; x++) {
                // resize wall block images
                if (dungeon.getSquare(x, y).getOccupiedByInanimateObject()) {
                    wallBlocks.get(y).get(x).setFitWidth(width);
                    wallBlocks.get(y).get(x).setFitHeight(height);
                }
                
                // resize MOB images
                if (dungeon.getSquare(x, y).getOccupiedByAnimateObject()) {
                    mobViews.get(y).get(x).setFitWidth(width);
                    mobViews.get(y).get(x).setFitHeight(height);
                }
            }
        }
        
        for (int y = 0; y <= map.getMapYSize()-1; y++) {
            for (int x = 0; x <= map.getMapXSize()-1; x++) {   
                mapViews.get(y).get(x).setFitWidth(mapGridPane.getWidth() / map.getMapXSize());
                mapViews.get(y).get(x).setFitHeight(mapGridPane.getHeight() / map.getMapYSize());
            }
        }
        
        //recreate map on resize
        generateMap();
    } 

    protected boolean getCommandLineFocused() {
        return commandLine.isFocused();
    }
    
    protected boolean getInGame() {
        return inGame;
    }
    
    protected boolean getGameRunning() {
        return gameRunning;
    }
    
    /*
     * GameLoop inner class - Infinite loop, update MOBs after a fixed number of frames
     */
    private class GameLoop extends AnimationTimer {
        private int loopCounter = 0; // GameLoop counter set to number of frames to wait before MOB update 
        private int loopCounter2 = 0; // GameLoop counter for second wind insight
        private int xDelta = 0; // x delta between current MOB square and target square next to Hero
        private int yDelta = 0; // y delta between current MOB square and target square next to Hero
        private boolean heroAttack = true; // can hero attack?
        private int mageArmorTimer = 1000; // "Mage Armor" insight timer
        private boolean mageArmor = false; // is mage armor active?
        private int secondWindTimer = 500; // "Second Wind" insight timer
        private boolean secondWind = false; // is second wind still active?
        
        // Default constructor
        GameLoop() { }

        /*
         * handle method - this function is called in each frame
         *      It controls the MOBs 
         */
        @Override
        public void handle(long now) {
            if (hero.getHealth() == 0)
                gameOver();
            
            Room r = getRoom();
            int numSpawns = r.getNumSpawns();
                     
            loopCounter++;
            loopCounter2++;
            
            if (mageArmor){
                mageArmorTimer--;
                
                if (mageArmorTimer == 0) {
                    mageArmor = false;
                    mageArmorTimer = 1000;
                    hero.setDefenseRating(hero.getDefenseRating() - 4);
                    defenseRatingLabel.setText(Integer.toString(hero.getDefenseRating()));
                    
                    combatDialog.setText(combatDialog.getText() + "\nYour MAGE ARMOR disappears!\n");
                    mageArmorIconImageView.setVisible(false); // hide icon
                    
                    // scroll text area to bottom
                    combatDialog.setScrollTop(Double.MAX_VALUE);
                    combatDialog.appendText("");
                }        
            }
            
            if (secondWind){
                secondWindTimer--;
                
                // regenerate 1 health every 30 frames
                if (loopCounter2 == 29 && hero.getHealth() < 20) {
                    hero.setHealth(hero.getHealth() + 1);
                    healthProgressBarText.setText(hero.getHealth() + " / 20");
                    healthProgressBar.setProgress(hero.getHealth() / 20d);
                }
                
                if (secondWindTimer == 0) {
                    secondWind = false;
                    secondWindTimer = 500;
                    
                    combatDialog.setText(combatDialog.getText() + "\nYou lose your SECOND WIND!\n");
                    secondWindIconImageView.setVisible(false); // hide icon
                    
                    // scroll text area to bottom
                    combatDialog.setScrollTop(Double.MAX_VALUE);
                    combatDialog.appendText("");
                }        
            }
            
            
            // execute below if block once every 15 frames
            if (loopCounter == 15) {
                loopCounter = 0;
                heroAttack = !heroAttack; // limit hero attack to once every 15 frames
            }
            
            // reset second wind loop counter
            if (loopCounter2 == 29)
                loopCounter2 = 0;
            
            // move MOBs once every 15 frames
            if (numSpawns > 0 && loopCounter == 14 && !isDoor(hero.getXLocation(), hero.getYLocation())) {
                attackHero();
                
                for (int n = 0; n <= numSpawns-1; n++) {
                    GiantRat rat = r.getMob(n);
                    
                    // get distance of mob from hero
                    int distance = rat.getDistanceFromHero(hero.getXLocation(), hero.getYLocation());
                    
                    // if hero gets within agro distance from mob, mob will persue hero and attack
                    // note that once a mob is agroed, it will stay agroed, regardless of distance
                    // from the hero
                    if (distance < AGRO_DISTANCE)
                        rat.setMobAgroed(true);
                    
                    // redraw room
                    wallBlocks.clear();
                    gridPaneWorld.getChildren().clear();
                    addWalls();
                    addMobs();
                    resizeImages(); 
                    gridPaneWorld.add(heroView, hero.getXLocation(), hero.getYLocation());

                    int x;
                    int y;
                    int targetX;
                    int targetY;
                    boolean getTargetAgain = false;
                    Image image = null;
                    
                    /*
                     * there is the possibility that MOBs will either
                     * occupy the square of other MOBs, or the hero
                     * 
                     * do / while checks for this scenario and will regenerate
                     * ordered list of MOB target squares once
                     *
                     * This isn't perfect, but it works most of the time
                     */
                    do {
                        getListMobTargetSquares(n); // get list of target squares for the MOB next to the Hero  

                        targetX = 0; // target x
                        targetY = 0; // target y 
                        x = rat.getXLocation(); // current x
                        y = rat.getYLocation(); // current y

                        // get valid target x, y location
                        for (int i = 0; i <= mobTargetSquares.size()-1; i++) {
                            targetX = mobTargetSquares.get(i).getXCoord();
                            targetY = mobTargetSquares.get(i).getYCoord();

                            // if hero is at target XY, skip
                            if (x == hero.getXLocation() && y == hero.getYLocation())
                                continue;
                            
                            // verify target XY is not occupied, or
                            // MOB is already at target
                            if ((dungeon.getSquare(targetX, targetY).getOccupiedByInanimateObject() == false) &&
                                (dungeon.getSquare(targetX, targetY).getOccupiedByAnimateObject() == false) &&
                                !isDoor(targetX, targetY) ||
                                (x == targetX && y == targetY))
                                    break;
                        }

                        // check if MOB square is occupied once
                        for (int m = 0; m <= numSpawns-1; m++) {
                            if (n != m) {
                                if ((r.getMob(n).getXLocation() == r.getMob(m).getXLocation()) &&
                                    (r.getMob(n).getYLocation() == r.getMob(m).getYLocation()) ||                           
                                    ((r.getMob(n).getXLocation() == hero.getXLocation()) &&
                                    (r.getMob(n).getYLocation() == hero.getYLocation()))) {                          
                                    if (getTargetAgain == true) {
                                        getTargetAgain = false;
                                        break;
                                    } else {
                                        getTargetAgain = true;
                                        break;
                                    }
                                }
                            }
                        }    
                    } while (getTargetAgain);
                    
                    String dir = "";
                    // if at target square, do not move MOB
                    // if MOB is not facing the Hero, update
                    // to face MOB
                    if (x == targetX && y == targetY) {
                        if (hero.getYLocation() == y - 1)
                            dir = "N";
                        else if (hero.getXLocation() == x + 1)
                            dir = "E";
                        else if (hero.getYLocation() == y + 1)
                            dir = "S";
                        else if (hero.getXLocation() == x - 1)
                            dir = "W";
                        // keep MOB direction if not directly adjacent to hero
                        else 
                            dir = r.getMob(n).getDirFacing();
                        
                        Image wallBlock = new Image(ImaginationMain.class.getResource(dungeon.getWallBlock()).toString());

                        if (dir == "N")
                            image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatNorth()).toString());
                        else if (dir == "E")
                            image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatEast()).toString());
                        else if (dir == "S")
                            image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatSouth()).toString());
                        else if (dir == "W")
                            image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatWest()).toString());
                        
                        // set direction
                        r.getMob(n).setDirFacing(dir);

                        gridPaneWorld.getChildren().remove(mobViews.get(y).get(x));
                        mobViews.get(y).set(x, new ImageView(image));
                        mobViews.get(y).get(x).setFitWidth(getWidth());
                        mobViews.get(y).get(x).setFitHeight(getHeight());
                        gridPaneWorld.add(mobViews.get(y).get(x), x, y);
                    // move MOB to a valid square
                    } else {                   
                        gridPaneWorld.getChildren().remove(mobViews.get(y).get(x));
                        mobViews.get(y).set(x, new ImageView());
                        
                        xDelta = targetX - x; // get x distance
                        yDelta = targetY - y; // get y distance
                        
                        /*
                         * move in the direction that is the largest distance
                         * from the Hero
                         *
                         * if square in desired direction is occupied,
                         * change direction
                         * i.e., if north is occupied, 
                         * move MOB randomly east or west
                         */
                        boolean coin = random.nextBoolean(); // coin flip to determine direction
                        if (Math.abs(xDelta) >= Math.abs(yDelta)) {
                            if (xDelta > 0) {
                                dir = "E";
                                if (r.getSquare(rat.getXLocation()+1, rat.getYLocation()).getOccupiedByAnimateObject() == true) {
                                    if (coin)
                                        dir = "N";
                                    else
                                        dir = "S";              
                                } 
                            } else if (xDelta < 0) {
                                dir = "W";
                                if (r.getSquare(rat.getXLocation()-1, rat.getYLocation()).getOccupiedByAnimateObject() == true) {
                                    if (coin)
                                        dir = "N";
                                    else
                                        dir = "S";              
                                } 
                            }
                        } else {
                            if (yDelta > 0) {
                                dir = "S";
                                if (r.getSquare(rat.getXLocation(), rat.getYLocation()+1).getOccupiedByAnimateObject() == true) {
                                    if (coin)
                                        dir = "E";
                                    else
                                        dir = "W";              
                                }  
                            } else if (yDelta < 0) {
                                dir = "N";
                                if (r.getSquare(rat.getXLocation(), rat.getYLocation()-1).getOccupiedByAnimateObject() == true) {
                                    if (coin)
                                        dir = "E";
                                    else
                                        dir = "W";              
                                }  
                            }    
                        }
                        
                        /******************************************************/
                        /* if mob argroed hero, use agro movement algorithm
                        /******************************************************/
                        if (rat.getMobAgroed()) {
                            // if MOB is not facing the hero, update
                            // to face Hero
                            if (dir != r.getMob(n).getDirFacing()) {
                                r.getMob(n).setDirFacing(dir);

                                if (dir == "N")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatNorth()).toString());
                                else if (dir == "E")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatEast()).toString());
                                else if (dir == "S")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatSouth()).toString());
                                else if (dir == "W")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatWest()).toString());

                                gridPaneWorld.getChildren().remove(mobViews.get(y).get(x));
                                mobViews.get(y).set(x, new ImageView(image));
                                mobViews.get(y).get(x).setFitWidth(getWidth());
                                mobViews.get(y).get(x).setFitHeight(getHeight());
                                gridPaneWorld.add(mobViews.get(y).get(x), x, y);   
                            //otherwise, move MOB to valid square
                            } else {
                                moveMob(n, dir);
                            } 
                        /******************************************************
                         * mob is not in agro range, use random move algorithm
                         ******************************************************/
                        } else {
                            coin = random.nextBoolean(); // coin flip to determine whether mob turns or moves
                            
                            // turn in a random direction 50% of the time
                            if (coin) {
                                int randomDir = random.nextInt(4);
                                if (randomDir == 0)
                                    dir = "N";
                                else if (randomDir == 1)
                                    dir = "E";
                                else if (randomDir == 2)
                                    dir = "S";
                                else if (randomDir == 3)
                                    dir = "W";
                                
                                rat.setDirFacing(dir);
                                
                                if (dir == "N")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatNorth()).toString());
                                else if (dir == "E")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatEast()).toString());
                                else if (dir == "S")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatSouth()).toString());
                                else if (dir == "W")
                                    image = new Image(ImaginationMain.class.getResource(r.getMob(n).getRatWest()).toString()); 
                                
                                gridPaneWorld.getChildren().remove(mobViews.get(y).get(x));
                                mobViews.get(y).set(x, new ImageView(image));
                                mobViews.get(y).get(x).setFitWidth(getWidth());
                                mobViews.get(y).get(x).setFitHeight(getHeight());
                                gridPaneWorld.add(mobViews.get(y).get(x), x, y); 
                            // move in current direction faced 50% of the time
                            } else {
                                dir = rat.getDirFacing();
                                moveMob(n, dir); 
                            } 
                        }
                    }   
                }
            }
        }

        // start handle method
        @Override
        public void start() {
            super.start();
        }

        // stop handle method
        @Override
        public void stop() {
            super.stop();
        }
               
        protected int getLoopCounter() {
            return loopCounter;
        }
        
        protected void setHeroAttack(boolean b) {
            heroAttack = b;
        }
        
        protected boolean getHeroAttack() {
            return heroAttack;
        }
        
        protected void setMageArmorTimer(int m) {
            mageArmorTimer = m;
        }
        
        protected int getMageArmorTimer() {
            return mageArmorTimer;
        }
        
        protected void setMageArmor(boolean m) {
            mageArmor = m;
        }
        
        protected boolean getMageArmor() {
            return mageArmor;
        }
        
        protected void setSecondWindTimer(int s) {
            secondWindTimer = s;
        }
        
        protected int getSecondWindTimer() {
            return secondWindTimer;
        }
        
        protected void setSecondWind(boolean s) {
            secondWind = s;
        }
        
        protected boolean getSecondWind() {
            return secondWind;
        }
    }
   
    /*
     * moveMob method - Move MOB to a valid square
     *      Accepts the index of the GiantRat MOB list and direction
     *      to move the MOB.
     */
    private void moveMob(int i, String dir) {
        Room r = getRoom();
        GiantRat rat = r.getMob(i);
        int x = rat.getXLocation();
        int y = rat.getYLocation();
        Square s = dungeon.getSquare(x, y);
        
        boolean validSquare = false;
   
        if (dir == "N") {
            if (s.getGoNorth() && !isDoor(x, y-1) && dungeon.getSquare(x, y-1).getOccupiedByAnimateObject() == false) {
                validSquare = true;
                s.setGoNorth(false);
            }
        } else if (dir == "E") {
            if (s.getGoEast() && !isDoor(x+1, y) && dungeon.getSquare(x+1, y).getOccupiedByAnimateObject() == false) {
                validSquare = true;
                s.setGoEast(false);
            }
        } else if (dir == "S") {
            if (s.getGoSouth() && !isDoor(x, y+1) && dungeon.getSquare(x, y+1).getOccupiedByAnimateObject() == false) {
                validSquare = true;
                s.setGoSouth(false);
            }
        } else if (dir == "W") {
            if (s.getGoWest() && !isDoor(x-1, y) && dungeon.getSquare(x-1, y).getOccupiedByAnimateObject() == false) {
                validSquare = true;
                s.setGoWest(false);
            }
        } else {
            System.out.printf("Invalid direction \"%s\"!!!\n", dir);
            System.out.printf("Invalid direction \"%s\"!!!\n", dir);
            System.out.printf("Invalid direction \"%s\"!!!\n", dir);
        }
        
        if (validSquare) {
            dungeon.getSquare(x, y+1).setGoNorth(true);
            dungeon.getSquare(x-1, y).setGoEast(true);
            dungeon.getSquare(x, y-1).setGoSouth(true);
            dungeon.getSquare(x+1, y).setGoWest(true);
            mobViews.get(y).set(x, new ImageView()); 
            dungeon.getSquare(x, y).setOccupiedByAnimateObject(false);
 
            rat.move(dir);
            x = rat.getXLocation();
            y = rat.getYLocation();
            
            dungeon.getSquare(x, y).setOccupiedByAnimateObject(true);
        } 
        
        Image image = null;
        
        if (dir == "N")
            image = new Image(ImaginationMain.class.getResource(rat.getRatNorth()).toString());
        else if (dir == "E")
            image = new Image(ImaginationMain.class.getResource(rat.getRatEast()).toString());
        else if (dir == "S")
            image = new Image(ImaginationMain.class.getResource(rat.getRatSouth()).toString());
        else if (dir == "W")
            image = new Image(ImaginationMain.class.getResource(rat.getRatWest()).toString());
        
        gridPaneWorld.getChildren().remove(mobViews.get(y).get(x));
        mobViews.get(y).set(x, new ImageView(image));
        mobViews.get(y).get(x).setFitWidth(getWidth());
        mobViews.get(y).get(x).setFitHeight(getHeight());
        gridPaneWorld.add(mobViews.get(y).get(x), x, y); 
    }
    
    /*
     * getRoom method - Get reference to current Room
     */
    private Room getRoom() {
        return dungeon.getRoom(dungeon.getRoomXLoc(), dungeon.getRoomYLoc());
    }
    
    /*
     * getWidth method - Get width for GridPane image
     */
    private double getWidth() {
         return gridPaneWorld.getWidth() / dungeon.getRoomXSize();
    }
    
    /*
     * getHeight method - Get height for GridPane image
     */
    private double getHeight() {
         return gridPaneWorld.getHeight() / dungeon.getRoomYSize();
    }
    
    /*
     * spawnMobs method - Spawn MOB at a random x, y location in the room
     *      that is not occupied by another MOB.
     *      Index argument used to set starting direction of image
     */
    private void spawnMobs(int index, Room r) {            
        int dirNum = random.nextInt(4);
        
        // get a random x y location in the room for MOB to spawn in
        int x;
        int y;
        do {
            // get random x and y room location from 1 - 9
            x = random.nextInt(9) + 1;
            y = random.nextInt(9) + 1;
        } while (r.getSquare(x, y).getOccupiedByAnimateObject() == true);
                
        r.addMob(new GiantRat("", x, y, 6, 0, dungeon.getNumSpawns(), 12, 3));
        initMobViews();
        
        // random starting direction
        Image image = null;
       
        if (dirNum == 0) {
            image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatNorth()).toString());
            r.getMob(index).setDirFacing("N");
        } else if (dirNum == 1) {
            image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatEast()).toString());
            r.getMob(index).setDirFacing("E");
        } else if (dirNum == 2) {
            image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatSouth()).toString());
            r.getMob(index).setDirFacing("S");
        } else if (dirNum == 3) {
            image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatWest()).toString());
            r.getMob(index).setDirFacing("W");
        }
            
        mobViews.get(y).set(x, new ImageView(image)); 
        dungeon.getSquare(x-1, y).setGoEast(false);
        dungeon.getSquare(x+1, y).setGoWest(false);
        dungeon.getSquare(x, y+1).setGoNorth(false);
        dungeon.getSquare(x, y-1).setGoSouth(false);
        dungeon.getSquare(x, y).setOccupiedByAnimateObject(true);
        gridPaneWorld.add(mobViews.get(y).get(x), x, y);
        mobViews.get(y).get(x).setFitWidth(getWidth());
        mobViews.get(y).get(x).setFitHeight(getHeight());
    }
    
    /*
     * isDoor method - Is Room x, y location a door?
     */
    private boolean isDoor(int x, int y) {
        if ((x == 5 && y == 10) ||
            (x == 0 && y == 5) ||
            (x == 5 && y == 0) ||
            (x == 10 && y == 5))
            return true;
        else
            return false;
    }
    
    /*
     * getDistance method - Get distance from MOB to target square next to Hero
     */
    private int getDistance(int curX, int curY, int tarX, int tarY) {
       return Math.abs(curX - tarX) + Math.abs(curY - tarY);
    }
    
    /*
     * getListMobTargetSquares method - Populate list of target squares for MOB
     *      Order list from closest to farthest square from Hero.
     */
    private void getListMobTargetSquares(int n) {
        Room r = getRoom();
        ArrayList<XYPoint> tempPoints = new ArrayList<XYPoint>();
        mobTargetSquares.clear();
        
        int d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation(), hero.getYLocation()-1);
        tempPoints.add(new XYPoint(hero.getXLocation(), hero.getYLocation()-1, d));
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation()+1, hero.getYLocation());
        tempPoints.add(new XYPoint(hero.getXLocation()+1, hero.getYLocation(), d));
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation(), hero.getYLocation()+1);
        tempPoints.add(new XYPoint(hero.getXLocation(), hero.getYLocation()+1, d));
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation()-1, hero.getYLocation());
        tempPoints.add(new XYPoint(hero.getXLocation()-1, hero.getYLocation(), d));
        
        int tempIndex = 0;
        int tempDistance = tempPoints.get(tempIndex).getDistance();
      
        // Place MOB target XY location in mobTargetSquares array in order of closest to farthest away
        while (tempPoints.size() >= 1) {
            for (int i = 0; i <= tempPoints.size()-1; i++) {
                if (i == 0) {
                    tempDistance = tempPoints.get(i).getDistance();
                    tempIndex = i;
                } else {
                    if (tempPoints.get(i).getDistance() < tempDistance) {
                        tempDistance = tempPoints.get(i).getDistance();
                        tempIndex = i;
                    }
                }
            }
            mobTargetSquares.add(tempPoints.get(tempIndex));
            tempPoints.remove(tempIndex);
            
            if (tempPoints.isEmpty())
                break;
        }
        
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation()+1, hero.getYLocation()+1);
        tempPoints.add(new XYPoint(hero.getXLocation()+1, hero.getYLocation()+1, d));
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation()+1, hero.getYLocation()-1);
        tempPoints.add(new XYPoint(hero.getXLocation()+1, hero.getYLocation()-1, d));
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation()-1, hero.getYLocation()+1);
        tempPoints.add(new XYPoint(hero.getXLocation()-1, hero.getYLocation()+1, d));
        d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), hero.getXLocation()-1, hero.getYLocation()-1);
        tempPoints.add(new XYPoint(hero.getXLocation()-1, hero.getYLocation()-1, d));
        
        // Place MOB target XY location in mobTargetSquares array in order of closest to farthest away
        while (tempPoints.size() >= 1) {
            for (int i = 0; i <= tempPoints.size()-1; i++) {
                if (i == 0) {
                    tempDistance = tempPoints.get(i).getDistance();
                    tempIndex = i;
                } else {
                    if (tempPoints.get(i).getDistance() < tempDistance) {
                        tempDistance = tempPoints.get(i).getDistance();
                        tempIndex = i;
                    }
                }
            }
            mobTargetSquares.add(tempPoints.get(tempIndex));
            tempPoints.remove(tempIndex);
            
            if (tempPoints.isEmpty())
                break;
        }
        
        int xMin = hero.getXLocation() - 2;
        int xMax = hero.getXLocation() + 2;
        int yMin = hero.getYLocation() - 2;
        int yMax = hero.getYLocation() + 2;
        
        if (hero.getXLocation() < 2)
            xMin = 0;
        if (hero.getXLocation() > 8)
            xMax = 10;
        if (hero.getYLocation() < 2)
            yMin = 0;
        if (hero.getYLocation() > 8)
            yMax = 10;
        
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (y == yMin || y == yMax || x == xMin || x == xMax) {
                    d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), x, y);
                    tempPoints.add(new XYPoint(x, y, d));
                }        
            }
        }
        
        // Place MOB target XY location in mobTargetSquares array in order of closest to farthest away
        while (tempPoints.size() >= 1) {
            for (int i = 0; i <= tempPoints.size()-1; i++) {
                if (i == 0) {
                    tempDistance = tempPoints.get(i).getDistance();
                    tempIndex = i;
                } else {
                    if (tempPoints.get(i).getDistance() < tempDistance) {
                        tempDistance = tempPoints.get(i).getDistance();
                        tempIndex = i;
                    }
                }
            }
            mobTargetSquares.add(tempPoints.get(tempIndex));
            tempPoints.remove(tempIndex);
            
            if (tempPoints.isEmpty())
                break;
        } 
        
        xMin = hero.getXLocation() - 3;
        xMax = hero.getXLocation() + 3;
        yMin = hero.getYLocation() - 3;
        yMax = hero.getYLocation() + 3;
        
        if (hero.getXLocation() < 3)
            xMin = 0;
        if (hero.getXLocation() > 7)
            xMax = 10;
        if (hero.getYLocation() < 3)
            yMin = 0;
        if (hero.getYLocation() > 7)
            yMax = 10;
        
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (y == yMin || y == yMax || x == xMin || x == xMax) {
                    d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), x, y);
                    tempPoints.add(new XYPoint(x, y, d));
                }        
            }
        }
        
        // Place MOB target XY location in mobTargetSquares array in order of closest to farthest away
        while (tempPoints.size() >= 1) {
            for (int i = 0; i <= tempPoints.size()-1; i++) {
                if (i == 0) {
                    tempDistance = tempPoints.get(i).getDistance();
                    tempIndex = i;
                } else {
                    if (tempPoints.get(i).getDistance() < tempDistance) {
                        tempDistance = tempPoints.get(i).getDistance();
                        tempIndex = i;
                    }
                }
            }
            mobTargetSquares.add(tempPoints.get(tempIndex));
            tempPoints.remove(tempIndex);
            
            if (tempPoints.isEmpty())
                break;
        }
        
        xMin = hero.getXLocation() - 4;
        xMax = hero.getXLocation() + 4;
        yMin = hero.getYLocation() - 4;
        yMax = hero.getYLocation() + 4;
        
        if (hero.getXLocation() < 4)
            xMin = 0;
        if (hero.getXLocation() > 6)
            xMax = 10;
        if (hero.getYLocation() < 4)
            yMin = 0;
        if (hero.getYLocation() > 6)
            yMax = 10;
        
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (y == yMin || y == yMax || x == xMin || x == xMax) {
                    d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), x, y);
                    tempPoints.add(new XYPoint(x, y, d));
                }        
            }
        }
        
        // Place MOB target XY location in mobTargetSquares array in order of closest to farthest away
        while (tempPoints.size() >= 1) {
            for (int i = 0; i <= tempPoints.size()-1; i++) {
                if (i == 0) {
                    tempDistance = tempPoints.get(i).getDistance();
                    tempIndex = i;
                } else {
                    if (tempPoints.get(i).getDistance() < tempDistance) {
                        tempDistance = tempPoints.get(i).getDistance();
                        tempIndex = i;
                    }
                }
            }
            mobTargetSquares.add(tempPoints.get(tempIndex));
            tempPoints.remove(tempIndex);
            
            if (tempPoints.isEmpty())
                break;
        } 
        
        xMin = hero.getXLocation() - 5;
        xMax = hero.getXLocation() + 5;
        yMin = hero.getYLocation() - 5;
        yMax = hero.getYLocation() + 5;
        
        if (hero.getXLocation() < 5)
            xMin = 0;
        if (hero.getXLocation() > 5)
            xMax = 10;
        if (hero.getYLocation() < 5)
            yMin = 0;
        if (hero.getYLocation() > 5)
            yMax = 10;
        
        for (int y = yMin; y <= yMax; y++) {
            for (int x = xMin; x <= xMax; x++) {
                if (y == yMin || y == yMax || x == xMin || x == xMax) {
                    d = getDistance(r.getMob(n).getXLocation(), r.getMob(n).getYLocation(), x, y);
                    tempPoints.add(new XYPoint(x, y, d));
                }        
            }
        }
        
        // Place MOB target XY location in mobTargetSquares array in order of closest to farthest away
        while (tempPoints.size() >= 1) {
            for (int i = 0; i <= tempPoints.size()-1; i++) {
                if (i == 0) {
                    tempDistance = tempPoints.get(i).getDistance();
                    tempIndex = i;
                } else {
                    if (tempPoints.get(i).getDistance() < tempDistance) {
                        tempDistance = tempPoints.get(i).getDistance();
                        tempIndex = i;
                    }
                }
            }
            mobTargetSquares.add(tempPoints.get(tempIndex));
            tempPoints.remove(tempIndex);
            
            if (tempPoints.isEmpty())
                break;
        } 
    }
    
    /*
     * attackHero method - mobs adjacent to hero attack. 
     */
    private void attackHero() {
        Room r = getRoom();
        int numSpawns = r.getNumSpawns();
        
        int x = hero.getXLocation();
        int y = hero.getYLocation();
        
        for (int i = 0; i <= r.getNumSpawns()-1; i++) {
            GiantRat rat = r.getMob(i);
            
            //System.out.printf("\n Rat x = %d, y = %d\n", rat.getXLocation(), rat.getYLocation());
            //System.out.printf(" Rat direction facing = %s\n", rat.getDirFacing());
            //System.out.printf(" Hero x = %d, y = %d\n", hero.getXLocation(), hero.getYLocation());
            
            // MOB needs to be next to Hero and facing Hero for it to attack
            boolean validAttack = false;
            if ((rat.getDirFacing() == "N" && rat.getXLocation() == x && rat.getYLocation() == y+1) ||
                (rat.getDirFacing() == "E" && rat.getXLocation() == x-1 && rat.getYLocation() == y) || 
                (rat.getDirFacing() == "S" && rat.getXLocation() == x && rat.getYLocation() == y-1) ||
                (rat.getDirFacing() == "W" && rat.getXLocation() == x+1 && rat.getYLocation() == y))
                validAttack = true;
                    
            if (validAttack) {
                int dieTwentyRoll = random.nextInt(20) + 1;
                int attack = rat.getAttack() + dieTwentyRoll;
                String text = combatDialog.getText() + "\nA GIANT RAT tries to BITE you!" +
                              "\n AR vs. DR: " + dieTwentyRoll + " + " + rat.getAttack() + " = " + (dieTwentyRoll + rat.getAttack()) + 
                              " vs " + hero.getDefenseRating();                
                combatDialog.setText(text);
                
                if (attack >= hero.getDefenseRating())  {
                    int damage = random.nextInt(rat.getDamage()) + 1;
                    int health = hero.getHealth() - damage;
                    
                    if (health < 0)
                        hero.setHealth(0);
                    else
                        hero.setHealth(health);
                    
                    text = combatDialog.getText() + "\nYou are BITTEN for " + damage + " damage!\n";
                    combatDialog.setText(text);
                    
                    healthProgressBarText.setText(Integer.toString(hero.getHealth()) + " / 20");
                    healthProgressBar.setProgress(hero.getHealth() / 20d);
                    
                    // decrement Hero imagination by 1 on successful hit
                    decrementImagination();
                } else {
                    text = combatDialog.getText() + "\nThe RAT MISSES!\n";
                    combatDialog.setText(text);
                }
                
                // scroll text area to bottom
                combatDialog.setScrollTop(Double.MAX_VALUE);
                combatDialog.appendText("");
            }
        }
    }
    
    /*
     * initMap - Initialize mapViews ImageView array 
     */
    private void initMap() {
        mapViews.clear();
        for (int y = 0; y <= map.getMapYSize()-1; y++) {
            mapViews.add(new ArrayList<ImageView>()); 
            for (int x = 0; x <= map.getMapXSize()-1; x++)
                mapViews.get(y).add(new ImageView());
        }
    }
    
    /*
     * generateMap - Create the dungeon map for the map screen 
     */
    private void generateMap() {
        initMap();
        mapGridPane.getChildren().clear();
       
        int heroXRoom = dungeon.getRoomXLoc(); // get hero (current) room x location
        int heroYRoom = dungeon.getRoomYLoc(); // get hero (current) room y location
        int x; // map x location
        int y; // map y location
        
        // hero image view
        ImageView h = new ImageView(map.getHeroPicture());
        h.setFitWidth(mapGridPane.getWidth() / map.getMapXSize());
        h.setFitHeight(mapGridPane.getHeight() / map.getMapYSize()); 
        
        for (int i = 0; i <= dungeon.getNumRoomsVisited()-1; i++) {
            Room r = dungeon.getRoom(i);
            Image image = map.getRoomImage(r);
             
            /*
             * get x and y for map display
             *
             * note:  need to get negative of y coordinate because
             *        the map display is the inversion of the
             *        array position.
             */
            x = r.getDungeonXLoc() + 10 + (-1 * heroXRoom);
            y = (-1 * r.getDungeonYLoc()) + 10 - (-1 * heroYRoom);
            
            /*
             * skip if past limit of map display
             */
            if (x < 0 || x > 20 || y < 0 || y > 20)
                continue;

            // add room image view to map grid
            ImageView v = mapViews.get(y).get(x);
            v.setImage(image);
            v.setFitWidth(mapGridPane.getWidth() / map.getMapXSize());
            v.setFitHeight(mapGridPane.getHeight() / map.getMapYSize());        
            mapGridPane.add(v, x, y);
            
            // add hero to current room
            if (r.getDungeonXLoc() == getRoom().getDungeonXLoc() &&
                r.getDungeonYLoc() == getRoom().getDungeonYLoc()) {
                mapGridPane.add(h, x, y);
            }
            
            // add start image to starting room
            if (r.getDungeonXLoc() == 0 && r.getDungeonYLoc() == 0) {
                ImageView start = new ImageView(map.getStart());
                start.setFitWidth(mapGridPane.getWidth() / map.getMapXSize());
                start.setFitHeight(mapGridPane.getHeight() / map.getMapYSize());  
                mapGridPane.add(start, x, y);
            }
        }
    }
    
    /*
     * saveGame - save the game to file on hard drive 
     */
    private void saveGame() {
        unsetHero(); // unset Hero collision detection
        combatDialog.setText(""); // clear combat dialog
        gameData.openFileForOutput(); // open file
        gameData.saveGame(dungeon); // save game to file
        gameData.closeOutputFile(); // close file
    }
    
    /*
     * loadGame - load the game from file on hard drive 
     */
    private void loadGame() {
        gameData.openFileForInput(); // open saved game file
        dungeon = gameData.getSavedGame(); // get saved game  
        gameData.closeInputFile(); // close file        
        dungeon.regenDungeon(); // regenerate dungeon
        initGame(); // init game
        regenMobs(); // regenerate mobs
        dungeon.getRoom(0).initializeStartingRoom(); // init starting room 
        resizeImages(); // ensure GridPane images are at correct size
    }
    
    /*
     * initGame - Initialize the game
     */
    private void initGame() {               
        gridPaneWorld.getChildren().clear(); // clear GridPane
        
        // reset distance from starting room
        distanceFromStartingRoomLabel.setText("0"); 
        distanceFromStartingRoomLabel2.setText("0"); 
        
        // set to starting dungeon room
        dungeon.setRoomXLoc(0);
        dungeon.setRoomYLoc(0);
        
        initMobViews(); // initialize MOB views
        
        addWalls(); // add wall images
        heroView.setImage(hero.getHeroSouth()); // starting Hero image - face South
        
        // set state data for Hero stating square
        dungeon.getSquare(STARTING_X_POSITION-1, STARTING_Y_POSITION).setGoEast(false);
        dungeon.getSquare(STARTING_X_POSITION+1, STARTING_Y_POSITION).setGoWest(false);
        dungeon.getSquare(STARTING_X_POSITION, STARTING_Y_POSITION+1).setGoNorth(false);
        dungeon.getSquare(STARTING_X_POSITION, STARTING_Y_POSITION-1).setGoSouth(false);
        dungeon.getSquare(STARTING_X_POSITION, STARTING_Y_POSITION).setOccupiedByAnimateObject(true);
        
        gridPaneWorld.add(heroView, STARTING_X_POSITION, STARTING_Y_POSITION); // add Hero to GridPane
        
        // set image height and width
        heroView.setFitWidth(STARTING_HERO_SIZE); 
        heroView.setFitHeight(STARTING_HERO_SIZE);
        
        // init map
        initMap();
        
        // init hero starting position
        hero.setXLocation(STARTING_X_POSITION);
        hero.setYLocation(STARTING_Y_POSITION);
        hero.setDirFacing("S");
        
        // init health
        hero.setHealth(20);
        healthProgressBar.setProgress(1d);
        healthProgressBarText.setText("20 / 20");
        
        // clear command line and focus
        commandLine.clear();
        commandLine.requestFocus();
        
        // reset previous command
        previousCommand = "";
        
        // Insight icons - init to off
        mageArmorIconImageView.setVisible(false);
        secondWindIconImageView.setVisible(false);
        
        // init enemy level to 1
        enemyLevelLabel.setText("1");
        
        // init imagination
        hero.setImagination(0);
        imaginationProgressBarText.setText(hero.getImagination() + " / 20");
        imaginationProgressBar.setProgress(hero.getImagination() / 20d);
        
        // Imagination icon - init to off
        imaginationBonusImageView.setVisible(false);
        
        // init AR, IR and DR
        hero.setAttack(5);
        hero.setDefenseRating(16);
        attackRatingLabel.setText(Integer.toString(hero.getAttack()));
        insightRatingLabel.setText(Integer.toString(hero.getAttack()));
        defenseRatingLabel.setText(Integer.toString(hero.getDefenseRating()));
        
        // init insights
        gameLoop.setSecondWindTimer(500);
        gameLoop.setMageArmorTimer(1000);
        gameLoop.setMageArmor(false);
        gameLoop.setSecondWind(false);
        
        // init imagination bonus
        imaginationBonus = 0;
    }
    
    /*
     * setDungeonStats - Set Dungeon statistics
     */
    private void setDungeonStats() {
        roomsVisitedLabel.setText(Integer.toString(dungeon.getNumRoomsVisited()));
        roomsVisitedLabel2.setText(Integer.toString(dungeon.getNumRoomsVisited()));
        highScoreLabel.setText(Integer.toString(dungeon.getHighScore()));
        highScoreLabel2.setText(Integer.toString(dungeon.getHighScore()));
        availableExitsLabel.setText(Integer.toString(dungeon.getNumDungeonExits()));
        availableExitsLabel2.setText(Integer.toString(dungeon.getNumDungeonExits()));  
    }
    
    /*
     * regenMobs - Regeneration mobs in dungeon after game restart
     */
    protected void regenMobs() {
        for (int i = 0; i < dungeon.getNumRoomsVisited(); i++) { 
            Room r = dungeon.getRoom(i);
            
            int numSpawns = r.getNumSpawns();
                 
            for (int index = 0; index < numSpawns; index++) { 
                int dirNum = random.nextInt(4);

                // get a random x y location in the room for MOB to spawn in
                int x;
                int y;
                do {
                    // get random x and y room location from 1 - 9
                    x = random.nextInt(9) + 1;
                    y = random.nextInt(9) + 1;
                } while (r.getSquare(x, y).getOccupiedByAnimateObject() == true);

                r.addMob(new GiantRat("", x, y, 6, 0, numSpawns, 12, 3));

                // random starting direction
                Image image = null;
      
                if (dirNum == 0) {
                    image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatNorth()).toString());
                    r.getMob(index).setDirFacing("N");
                } else if (dirNum == 1) {
                    image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatEast()).toString());
                    r.getMob(index).setDirFacing("E");
                } else if (dirNum == 2) {
                    image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatSouth()).toString());
                    r.getMob(index).setDirFacing("S");
                } else if (dirNum == 3) {
                    image = new Image(ImaginationMain.class.getResource(r.getMob(index).getRatWest()).toString());
                    r.getMob(index).setDirFacing("W");
                }

                dungeon.getSquare(x-1, y).setGoEast(false);
                dungeon.getSquare(x+1, y).setGoWest(false);
                dungeon.getSquare(x, y+1).setGoNorth(false);
                dungeon.getSquare(x, y-1).setGoSouth(false);
                dungeon.getSquare(x, y).setOccupiedByAnimateObject(true);
            }
        }
    }
    
    /*
     * unsetHero - Unset Hero collision detection
     */
    private void unsetHero() {
        int x = hero.getXLocation();
        int y = hero.getYLocation();
        dungeon.getSquare(x+1, y).setGoWest(true);
        dungeon.getSquare(x-1, y).setGoEast(true);
        dungeon.getSquare(x, y+1).setGoNorth(true);
        dungeon.getSquare(x, y-1).setGoSouth(true);        
        dungeon.getSquare(x, y).setOccupiedByAnimateObject(false);
    }
    
    /*
     * incrementImagination - Increment Hero imagination by 1
     *      activate imagination bonus based on imagination level - from +0 to +3
     */
    private void incrementImagination() {          
        if (hero.getImagination() < 20)
            hero.setImagination(hero.getImagination() + 1);

        setImagination();
    }
    
    /*
     * decrementImagination - Decrenebt imaginaion by 1 when mob hits Hero
     *      activate imagination bonus based on imagination level - from +0 to +3
     */
    private void decrementImagination() {
        String text = combatDialog.getText() + "You LOSE " + 1 + " IMAGINATION!\n";
        combatDialog.setText(text);
            
        // scroll text area to bottom
        combatDialog.setScrollTop(Double.MAX_VALUE);
        combatDialog.appendText("");

        int imagination = hero.getImagination() - 1;
        if (imagination < 0)
            hero.setImagination(0);
        else
            hero.setImagination(imagination);
        
        setImagination();
    }
    
    /*
     * setImagination - Set imagination and related stats / icon
     *      activate imagination bonus based on imagination level - from +0 to +3
     */
    private void setImagination() {
        imaginationProgressBarText.setText(hero.getImagination() + " / 20");
        imaginationProgressBar.setProgress(hero.getImagination() / 20d);
        
        int imagination = hero.getImagination();
        
        int previousBonus = imaginationBonus;
        
        if (imagination < 5) {
            imaginationBonusImageView.setVisible(false);
            imaginationBonus = 0;
        } else if (imagination >= 5 && imagination <= 10) {
            imaginationBonusImageView.setVisible(true);
            imaginationBonusImageView.setImage(new Image(ImaginationMain.class.getResource("resources/images/brainIconPlusOne.png").toString()));
            imaginationBonus = 1;
        } else if (imagination >= 11 && imagination <= 15) {            
            imaginationBonusImageView.setVisible(true);
            imaginationBonusImageView.setImage(new Image(ImaginationMain.class.getResource("resources/images/brainIconPlusTwo.png").toString()));
            imaginationBonus = 2;
        } else if (imagination > 15) {
            imaginationBonusImageView.setVisible(true);
            imaginationBonusImageView.setImage(new Image(ImaginationMain.class.getResource("resources/images/brainIconPlusThree.png").toString()));
            imaginationBonus = 3;
        }
        
        if (previousBonus < imaginationBonus) {
            String text = combatDialog.getText() + "\nYour IMAGINATION increases!\n";
            combatDialog.setText(text);
            
            // scroll text area to bottom
            combatDialog.setScrollTop(Double.MAX_VALUE);
            combatDialog.appendText("");
            
            hero.setAttack(hero.getAttack() + 1);
            hero.setDefenseRating(hero.getDefenseRating() + 1);
        } else if (previousBonus > imaginationBonus) {
            String text = combatDialog.getText() + "\nYour IMAGINATION fades!\n";
            combatDialog.setText(text);
            
            hero.setAttack(hero.getAttack() - 1);
            hero.setDefenseRating(hero.getDefenseRating() - 1);
            
            // scroll text area to bottom
            combatDialog.setScrollTop(Double.MAX_VALUE);
            combatDialog.appendText("");
        }
             
        attackRatingLabel.setText(Integer.toString(hero.getAttack()));
        insightRatingLabel.setText(Integer.toString(hero.getAttack()));
        defenseRatingLabel.setText(Integer.toString(hero.getDefenseRating()));
    }
}
