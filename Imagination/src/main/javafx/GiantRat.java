/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              GiantRat extends MobTemplate
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class is an extention of the MobTemplate abstract class,
 *                      which contains fields and methods common to all MOBs.
 *                      It is used to store Giant Rat MOB specific state information.
 *                      It contains Giant Rat action methods. 
 */

package main.javafx;

public class GiantRat extends MobTemplate {
    // Giant Rat images - one for each cardinal direction (N, E, S, W).
    private final String ratNorth = "resources/images/Rat_N.png";
    private final String ratEast = "resources/images/Rat_E.png";
    private final String ratSouth = "resources/images/Rat_S.png";
    private final String ratWest = "resources/images/Rat_W.png";

    /*
     * Constructor
     */
    protected GiantRat(String d, int x, int y, int h, int b, int a, int dr, int dam) {
        super(d, x, y, h, b, a, dr, dam);
    }
    
    protected String getRatNorth() {
        return ratNorth;
    }
    
    protected String getRatEast() {
        return ratEast;
    }
    
    protected String getRatSouth() {
        return ratSouth;
    }
    
    protected String getRatWest() {
        return ratWest;
    }
}