/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              Hero
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class stores Hero (player) state information.
 *                      It is used to store Hero state information.
 *                      It contains Hero action methods.         
 */

package main.javafx;

import javafx.scene.image.Image;

public class Hero {
    private String dirFacing; // direction facing
    private int xLocation; // Hero x location
    private int yLocation; // Hero y location
    private final Image heroNorth = new Image(ImaginationMain.class.getResource("resources/images/Hero_N.png").toString()); // image of hero facing north
    private final Image heroEast = new Image(ImaginationMain.class.getResource("resources/images/Hero_E.png").toString()); // image of hero facing east
    private final Image heroSouth = new Image(ImaginationMain.class.getResource("resources/images/Hero_S.png").toString()); // image of hero facing south
    private final Image heroWest = new Image(ImaginationMain.class.getResource("resources/images/Hero_W.png").toString()); // image of hero facing west
    private final Image deadHero = new Image(ImaginationMain.class.getResource("resources/images/Dead_Hero.png").toString()); // image of dead hero
    private int imagination; // Hero imagination
    private int health; // Hero health
    private int attack; // Hero attack stat
    private int defenseRating; // Hero defense rating
    private int damage; // hero damage die
    //private int block; // Hero block stat
    //private int blockLevel;
    //private int attackLevel;
    //private ArrayList<int> insightsKnown = new ArrayList<int>();
    //private boolean intemPriHand;
    //private boolean intemSecHand;
    //private boolean armorWorn;
    //private boolean ringWorn;
    //private indexItemPriHand;
    //private indexItemSecHand;
    //private int indexArmorWorn;
    //private int indexRingWorn;
    //private ArrayList<Image> heroImage = new ArrayList<Image>(); 
    
    /*
     * Constructor
     */
    protected Hero(String d, int x, int y) {
        dirFacing = d; // directing facing
        xLocation = x; // room x location
        yLocation = y; // room y location
        imagination = 0; // starting imagination = 0
        health = 20; // starting health = 20
        attack = 5; // attack rating
        defenseRating = 16; // defense rating
        damage = 6; // max damage
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
    
    /*
     * move Method - Move the Hero based on cardinal direction argument
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
    
    protected Image getHeroNorth() {
        return heroNorth;
    }
    
    protected Image getHeroEast() {
        return heroEast;
    }
    
    protected Image getHeroSouth() {
        return heroSouth;
    }
    
    protected Image getHeroWest() {
        return heroWest;
    }
    
    protected void attack(String dir) {
           
    }
    
    protected void equiptItem(int i) {
           
    }
    
    protected void unequiptItem(int i) {
           
    }
    
    protected void haveInsight(int i) {
           
    }
    
    protected void look(String s) {
           
    }
    
    protected void open(String s) {
           
    }
    
    protected void close(String s) {
           
    }
    
    protected int getImagination() {
        return imagination;
    }
    
    protected void setImagination(int i) {
        imagination = i;
    }
    
    protected int getHealth() {
        return health;
    }
    
    protected void setHealth(int h) {
        health = h;
    }
    
    protected Image getDeadHero() {
        return deadHero;
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
    
    protected int getDamage() {
        return damage;
    }
    
    protected void setDamage(int dam) {
        damage = dam;
    }
}
