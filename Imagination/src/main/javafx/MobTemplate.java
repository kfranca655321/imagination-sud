/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              MobTemplate implements Serializable
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This is an abstract class which contains fields and methods
 *                      common to all MOBs.  
 */

package main.javafx;

import java.io.Serializable;

public abstract class MobTemplate implements Serializable {
    private String dirFacing; // direction facing
    private int xLocation; // room x location
    private int yLocation; // room y location
    private int distanceFromHero; // distance from hero
    private boolean mobAgroed; // is mob agroed?
    private int health; // mob health
    private int block; // mob block stat
    private int attack; // mob attack stat
    private int defenseRating; // mob defense rating stat
    private int damage; // mob damage die
    //private int addDamage;
    //private int addMobs;
    //private boolean speaks;
    //private ArrayList<String> whatMobSays = new ArrayList<String>();
    //private ArrayList<Image> mobImage = new ArrayList<Image>;
            
    /*
     * Constructor
     */
    protected MobTemplate(String d, int x, int y, int h, int b, int a, int dr, int dam) {
        dirFacing = d; // direction facing
        xLocation = x; // room x location
        yLocation = y; // room y location
        health = h; // health
        block = b; // block
        attack = a; // attack
        defenseRating = dr; // defense rating
        damage = dam; // max damage to deal
        setMobAgroed(false); // begin with a passive mob
    }
    
    protected String getDirFacing() {
        return dirFacing;
    }
    
    protected void setDirFacing(String df) {
        dirFacing = df;
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
    
    protected int getHealth() {
        return health;
    }
    
    protected void setHealth(int h) {
        health = h;
    }
    
    protected int getBlock() {
        return block;
    }
    
    protected void setBlock(int b) {
        block = b;
    }
    
    protected int getAttack() {
        return attack;
    }
    
    protected void setAttack(int a) {
        attack = a;
    }
    
    protected int getDefenseRating() {
        return defenseRating;
    }
    
    protected void setDefenseRating(int d) {
        defenseRating = d;
    }
    
    /*
     * move method - move MOB based on cardinal direction argument
     */
    protected void move(String dir) {
        switch (dir) {
            case "N":
                yLocation--;     
                break;
            case "S":
                yLocation++;
                break;
            case "E":
                xLocation++; 
                break;
            case "W":
                xLocation--;
                break;    
        }
    }
    
    /*
     * getDistanceFromHero method - return the distance of the mob from the hero
     *      distance = total number of squares in the x and y direction from the hero
     */
    protected int getDistanceFromHero(int heroX, int heroY) {
        if (xLocation >= heroX)
            distanceFromHero = xLocation - heroX;
        else
            distanceFromHero = heroX - xLocation;
        
        if (yLocation >= heroY)
            distanceFromHero += yLocation - heroY;
        else
            distanceFromHero += heroY - yLocation;
        
        return distanceFromHero;
    }
    
    protected void setDistanceFromHero(int d) {
        distanceFromHero = d;
    }
    
    protected boolean getMobAgroed() {
        return mobAgroed;
    }
    
    protected void setMobAgroed(boolean a) {
        mobAgroed = a;
    }
    
    protected int getDamage() {
        return damage;
    }
    
    protected void setDamage(int dam) {
        damage = dam;
    }
}
