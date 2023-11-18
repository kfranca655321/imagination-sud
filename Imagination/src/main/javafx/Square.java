/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              Square implements Serializable
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class contains data and methods for the
 *                      Square class objects.
 *                      Each room contains 11 x 11 Square(s).
 */

package main.javafx;

import java.io.Serializable;

public class Square implements Serializable {
    private int xLocation; // Room x location of square
    private int yLocation; // Room y location of square
    private boolean goNorth; // Can you go north?
    private boolean goSouth; // Can you go south?
    private boolean goEast; // Can you go east?
    private boolean goWest; // Can you go west?
    private boolean occupiedByInanimateObject; // Is the square occupied by an inanimate object (i.e. wall block)?
    private boolean occupiedByAnimateObject; // Is the square occupied by an animate object (i.e. MOB, Hero)?
    //private int indexArea;
    //private int indexObject;
    //private boolean visited;
    //private int indexSquareType;
    
    /*
     * Square constructor - initialize status fields
     */
    protected Square(int x, int y, boolean n, boolean s, boolean e, boolean w, boolean o) {
        xLocation = x;
        yLocation = y;
        goNorth = n;
        goSouth = s;
        goEast = e;
        goWest = w;
        occupiedByInanimateObject = o;
        occupiedByAnimateObject = false;
    }
    
    protected int getXLocation() {
        return xLocation;
    }
    
    protected void setXLocation(int xLoc) {
        xLocation = xLoc;
    }
    
    protected int getYLocation() {
        return yLocation;
    }
    
    protected void setYLocation(int yLoc) {
        yLocation = yLoc;
    }
    
    protected boolean getGoNorth() {
        return goNorth;
    }
    
    protected void setGoNorth(boolean n) {
        goNorth = n;
    }
    
    protected boolean getGoSouth() {
        return goSouth;
    }
    
    protected void setGoSouth(boolean s) {
        goSouth = s;
    }
    
    protected boolean getGoEast() {
        return goEast;
    }
    
    protected void setGoEast(boolean e) {
        goEast = e;
    }
    
    protected boolean getGoWest() {
        return goWest;
    }
    
    protected void setGoWest(boolean w) {
        goWest = w;
    }  
    
    /*
     * validDirection method - Is direction passed as an argument valid?  Returns T or F.
     */
    protected boolean validDirection(String dir) {
        boolean valid = false;
        
        switch (dir) {
            case "N":
                if (goNorth)
                    valid = true;
                break;
            case "S":
                if (goSouth)
                    valid = true;
                break;
            case "E":
                if (goEast)
                    valid = true;
               break;
            case "W":
                if (goWest)
                    valid = true;
                break;
        }
        
        return valid;
    }
    
    protected boolean getOccupiedByInanimateObject() {
        return occupiedByInanimateObject;
    }
    
    protected void setOccupiedByInanimateObject(boolean o) {
        occupiedByInanimateObject = o;
    }
    
    protected boolean getOccupiedByAnimateObject() {
        return occupiedByAnimateObject;
    }
    
    protected void setOccupiedByAnimateObject(boolean o) {
        occupiedByAnimateObject = o;
    } 
}
