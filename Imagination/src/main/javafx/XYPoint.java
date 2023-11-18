/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              Dungeon
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class is used by the MOBs to determine which
 *                      Square(s) to go to to attack the Hero.
 *                      It stores Room Location information:
 *                          - Room x coordinate
 *                          - Room y coordinate
 *                          - Distance from target
 */

package main.javafx;

public class XYPoint {
    private int xCoord; // Room x coordinate
    private int yCoord; // Room y coordinate
    private int distance; // distance 
    
    /*
     * XYPoint - 2 argument constructor
     *      Pass x, y point coordinates
     */
    protected XYPoint(int x, int y) {
        xCoord = x;
        yCoord = y;
    }
    
    /*
     * XYPoint - 3 argument constructor
     *      Pass x, y point coordinates
     *      Pass distance
     */
    protected XYPoint(int x, int y, int d) {
        xCoord = x;
        yCoord = y;
        distance = d;
    }
    
    protected int getXCoord() {
        return xCoord;
    }
    
    protected void setXCoord(int x) {
        xCoord = x;
    }
    
    protected int getYCoord() {
        return yCoord;
    }
    
    protected void setYCoord(int y) {
        yCoord = y;
    }
    
    protected void setDistance(int d) {
        distance = d;
    }
    
    protected int getDistance() {
        return distance;
    }
}
