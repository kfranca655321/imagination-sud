/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              GameData
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class is used to:
 *                          - start a new game
 *                          - save the game
 *                          - load a saved game
 */

package main.javafx;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class GameData {
    private String saveData = "dungeon.ser"; // save data file
    private ObjectOutputStream output; // object output stream
    private ObjectInputStream input; // object input stream
    private Dungeon savedDungeon; // saved dungeon
    
    protected GameData() {
        
    }
    
    protected void setFileName(TextField t) {
        saveData = t.getText();
    }
    
    /*
     * openFileForOutput - Open save game file for output
     */
    protected void openFileForOutput() {
        try {
            output = new ObjectOutputStream(new FileOutputStream( saveData ) );
        } catch ( IOException ioException ) {
            System.err.println("Unable to open file stream " + saveData + "!");            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("File Error");
            alert.setContentText("Unable to open file stream " + saveData + "!");
            alert.showAndWait();
        }
    }
    
    /*
     * saveGame - Save the game to file
     */
    protected void saveGame(Dungeon d) {
        savedDungeon = d;
        
        try {
            output.writeObject(d);
        } catch (IOException ex) {
            Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);   
            System.err.println("Error saving file!");  
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("File Error");
            alert.setContentText("Error saving file!");
            alert.showAndWait();
        }
    }
    
    /*
     * closeOutputFile - Close the output file
     */
    protected void closeOutputFile() {
        try {
            if ( output != null )
                output.close();
        } catch ( IOException ioException ) {
            System.err.println("Error closing output file " + saveData + "!");
            System.exit( 1 );
        }
    }
    
    /*
     * openFileForInput - Open file for input
     */
    protected void openFileForInput() {
        try {
            input = new ObjectInputStream(new FileInputStream( saveData ) );
        } catch ( IOException ioException ) {
            System.err.println("Error opening file " + saveData + " for input!");      
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("File Error");
            alert.setContentText("Error opening file " + saveData + " for input!");
            alert.showAndWait();
        }
    }
    
    /*
     * getSavedGame - Get saved game Dungeon object from file on disk
     */
    protected Dungeon getSavedGame() {
        try {
            while (true) {
                savedDungeon = ( Dungeon ) input.readObject();
            }   
        } catch ( EOFException endOfFileException ) {
            return savedDungeon;
        } catch ( ClassNotFoundException classNotFoundException ) {
            System.err.println( "Unable to create object." );           
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Class Not Found Error");
            alert.setContentText("Unable to create object when getting saved game " + saveData + ".");
            alert.showAndWait();
        } catch ( IOException ioException ) {
            System.err.println("Error during read from file " + saveData + "!");           
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("File Error");
            alert.setContentText("Error during read from file " + saveData + "!");
            alert.showAndWait();
        }
        
        return null;
    }
    
    /*
     * closeInputFile - Close input file
     */
    protected void closeInputFile() {
        try {
            if ( input != null )
                input.close();
        } catch ( IOException ioException ) {
            System.err.println( "Error closing input file." );
            System.exit( 1 );
        }
    }
    
    /*
     * doesSavedGameExist Method - Check if there is a game to load
     */
    protected boolean doesSavedGameExist() {
        File file = new File(saveData);
         boolean exists = file.exists();
        if (exists)
            return true;
        else {
            System.err.println("Saved game does not exist.  Please select \"New Game\" or \"Cancel\".");
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Information Dialog");
            alert.setContentText("Saved game does not exist.  Please select \"New Game\" or \"Cancel\".");
            alert.showAndWait();
            
            return false;
        }
    }
}
