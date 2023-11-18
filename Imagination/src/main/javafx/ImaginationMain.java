/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              Dungeon
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class extends Application
 *                      It contains:
 *                          - main method application entry point.
 *                          - Keyboard detection which is passed to ImaginationController handlers
 *                          - Code to launch JavaFX app.            
 */

package main.javafx;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ImaginationMain extends Application {
    private ImaginationController controller;  // controller object
    private final Screen screen = Screen.getPrimary(); // screen
    private final Rectangle2D bounds = screen.getVisualBounds(); // rectangle for screen resize
    private boolean toggle = true; // display toggle
    private final BooleanProperty cntlPressed = new SimpleBooleanProperty(false); // is CNTL pressed?
    private final BooleanProperty upPressed = new SimpleBooleanProperty(false); // is up arrow or "w" pressed?
    private final BooleanProperty downPressed = new SimpleBooleanProperty(false); // is down arrow or "s" pressed?
    private final BooleanProperty rightPressed = new SimpleBooleanProperty(false); // is right arrow or "d" pressed?
    private final BooleanProperty leftPressed = new SimpleBooleanProperty(false); // is left arrow or "a" pressed?
    
    @Override
    public void start(Stage stage) throws Exception {
        // load JavaFX GUI XML configuration file
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("resources/Imagination.fxml"));
        Parent root = (Parent) loader.load();
        
        // load css
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("resources/Imagination.css").toExternalForm());

        // ImaginationController 
        controller = loader.getController();
        
        // Keyboard event filter
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()  {
            @Override
            public void handle(KeyEvent ke) {                
                if (ke.getCode() == KeyCode.ESCAPE) {
                    ke.consume();
                    controller.handleEscKeyPressedAction();
                } else if (ke.getCode() == KeyCode.F1) {
                    ke.consume();
                    controller.handleF1KeyPressedAction();
                } else if (ke.getCode() == KeyCode.F2) {
                    ke.consume();
                    controller.handleF2KeyPressedAction();
                } else if (ke.getCode() == KeyCode.F3) {
                    ke.consume();
                    controller.handleF3KeyPressedAction();
                }else if (ke.getCode() == KeyCode.F4) {
                    ke.consume();
                    if (toggle) {
                        stage.setHeight(bounds.getHeight());
                        stage.setWidth(bounds.getHeight() * 1.33);
                        //stage.setHeight(750);
                        //stage.setWidth(1000);
                        stage.centerOnScreen();
                        controller.resizeImages();
                    } else {
                        stage.setHeight(600);
                        stage.setWidth(800);
                        stage.centerOnScreen();
                        controller.resizeImages();
                    }
                    toggle = !toggle;
                } else if (ke.getCode() == KeyCode.SPACE) {
                    ke.consume();
                    controller.handleSpaceKeyPressedAction();
                } else if (ke.getCode() == KeyCode.UP) {
                    upPressed.set(true);
                    ke.consume();
                    if (cntlPressed.getValue())
                        controller.handleStrafeAction("N");    
                    else
                        controller.handleDirectionKeyPressedAction("N");
                } else if (ke.getCode() == KeyCode.DOWN) {
                    downPressed.set(true);
                    ke.consume();
                    if (cntlPressed.getValue())
                        controller.handleStrafeAction("S");
                    else
                        controller.handleDirectionKeyPressedAction("S");                      
                } else if (ke.getCode() == KeyCode.LEFT) {
                    leftPressed.set(true);
                    ke.consume();
                    if (cntlPressed.getValue())
                        controller.handleStrafeAction("W");
                    else
                        controller.handleDirectionKeyPressedAction("W");
                } else if (ke.getCode() == KeyCode.RIGHT) {
                    rightPressed.set(true);
                    ke.consume();
                    if (cntlPressed.getValue())
                        controller.handleStrafeAction("E");
                    else
                        controller.handleDirectionKeyPressedAction("E");    
                }  else if (ke.getCode() == KeyCode.W) {
                    if (wasdKeyValid()) {
                        upPressed.set(true);
                            ke.consume();
                        if (cntlPressed.getValue())
                            controller.handleStrafeAction("N");
                        else
                            controller.handleDirectionKeyPressedAction("N");
                    }
                } else if (ke.getCode() == KeyCode.S) {
                    if (wasdKeyValid()) {
                        downPressed.set(true);
                            ke.consume();
                        if (cntlPressed.getValue())
                            controller.handleStrafeAction("S");
                        else
                            controller.handleDirectionKeyPressedAction("S");
                    }
                } else if (ke.getCode() == KeyCode.A) {
                    if (wasdKeyValid()) {
                        leftPressed.set(true);
                            ke.consume();
                        if (cntlPressed.getValue())
                            controller.handleStrafeAction("W");
                        else
                            controller.handleDirectionKeyPressedAction("W");
                    }
                } else if (ke.getCode() == KeyCode.D) {
                    if (wasdKeyValid()) {
                        rightPressed.set(true);
                            ke.consume();
                        if (cntlPressed.getValue())
                            controller.handleStrafeAction("E");                            
                        else
                            controller.handleDirectionKeyPressedAction("E");
                    }
                } else if (ke.getCode() == KeyCode.CONTROL || ke.getCode() == KeyCode.CONTEXT_MENU) {
                    if (controller.getInGame() && controller.getGameRunning()) {
                        ke.consume();
                        cntlPressed.set(true);
                    }
                } else if (ke.getCode() == KeyCode.ALT || ke.getCode() == KeyCode.ALT_GRAPH) {
                    if (controller.getInGame() && controller.getGameRunning()) {
                        ke.consume();
                        controller.handleAltKeyPressedAction();
                    }
                } else if (ke.getCode() == KeyCode.ENTER) {
                    if (controller.getInGame() && controller.getGameRunning()) {
                        ke.consume();
                        controller.handleEnterKeyPressedAction();
                    }
                }  
            }   
        });
                      
        // CONTROL key event filter (for Hero strafe movement)
        scene.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>()  {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.UP) {
                    upPressed.set(false);
                } else if (ke.getCode() == KeyCode.CONTROL) {
                    cntlPressed.set(false);
                }
            }
        });
        
        // init stage
        stage.setScene(scene);
        stage.setHeight(600);
        stage.setWidth(800);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Imagination 0.0.0");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args); // launch GUI
    }
    
    /*
     * wasdKeyValid method - check if direction keys are valid
     *      - must be in game
     *      - game must be running
     *      - command line must not be in focus
     */
    private boolean wasdKeyValid() {
        return controller.getInGame() && controller.getGameRunning() && !controller.getCommandLineFocused();
    }
}
