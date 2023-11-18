/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              Room implements Serializable
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        Room class  
 *                      Room class stores room information.
 *                      It contains:
 *                          - 11 x 11 Square class objects
 *                          - GiantRat class MOB objects 
 *                          - Methods and fields that operate on a per Room basis.
 */

package main.javafx;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    // ArrayList of ArrayList(s) which contains 11 x 11 Square(s) in Room
    private final ArrayList<ArrayList<Square>> squares = new ArrayList<ArrayList<Square>>(); 
    
    private int xLocation; // room x location
    private int yLocation; // room y location
    private boolean doorNorth; // north door?
    private boolean doorSouth; // south door?
    private boolean doorEast; // east door?
    private boolean doorWest; // west door?
    private final int ROOM_X_SIZE = 11; // number of squares in x direction
    private final int ROOM_Y_SIZE = 11; // number of squares in y direction
    private int numMobSpawners = 0; // number of MOB spawners in room
    private ArrayList<GiantRat> giantRat = new ArrayList<GiantRat>(); // list of GiantRat MOBs
    private int numRoomsFromStart; // number of rooms from starting room
    private static boolean firstTime = true; // first time status variable
    private int numSpawns; // number of spawns for room
    private String spawnType; // spawn type for room
    
    /*
     * Room constructor - Initialize Room object
     */
    public Room(int xLoc, int yLoc, boolean n, boolean e, boolean s, boolean w, int d, int ns, String st) {
        xLocation = xLoc;
        yLocation = yLoc;
        doorNorth = n;
        doorEast = e;
        doorSouth = s;
        doorWest = w;
        numRoomsFromStart = d;
        numSpawns = ns;
        spawnType = st;
        initializeRoom();
    }
    
    /*
     * initializeRoom Method - Initialize Room when object is first created
     */
    private void initializeRoom() { 
        for (int yCount = 0; yCount <= ROOM_Y_SIZE-1; yCount++) {
            squares.add(new ArrayList<Square>());
            
            for (int xCount = 0; xCount <= ROOM_X_SIZE-1; xCount++) {
                boolean north = true;
                boolean south = true;
                boolean east = true;
                boolean west = true;
                boolean occupied = false;
                    
                if (xCount == 0) {
                    if (yCount == 5 && doorWest == true) {
                        north = false;
                        south = false;
                    } else {
                        occupied = true;
                    }
                    west = false;
                }
                
                if (xCount == 1) {
                    if (yCount == 5 && doorWest == true)
                        west = true;
                    else
                        west = false;
                }
                
                if (xCount == ROOM_X_SIZE-1) {
                    if (yCount == 5 && doorEast == true) {
                        north = false;
                        south = false;
                    } else {
                        occupied = true;
                    }
                    east = false;
                }
                
                if (xCount == ROOM_X_SIZE-2) {
                    if (yCount == 5 && doorEast == true)
                        east = true;
                    else
                        east = false;
                }
                
                if (yCount == 0) {
                    if (xCount == 5 && doorNorth == true) {
                        east = false;
                        west = false;
                    } else {
                        occupied = true;
                    }
                    north = false;
                }
                
                if (yCount == 1) {
                    if (xCount == 5 && doorNorth == true)
                        north = true;
                    else
                        north = false;
                }
                
                if (yCount == ROOM_Y_SIZE-1) {
                    if (xCount == 5 && doorSouth == true) {
                        east = false;
                        west = false;
                    } else {
                        occupied = true;
                    }
                    south = false;
                }
                
                if (yCount == ROOM_Y_SIZE-2) {
                    if (xCount == 5 && doorSouth == true)
                        south = true;
                    else
                        south = false;
                }
                
                // add square
                squares.get(yCount).add(new Square(xCount, yCount, north, south, east, west, occupied));
            }
        }
    }
    
    protected Square getSquare(int x, int y) {
        return squares.get(y).get(x);
    }
    
    protected int getDungeonXLoc() {
        return xLocation;
    }
    
    protected int setDungeonXLoc(int x) {
        return xLocation = x;
    }
    
    protected int getDungeonYLoc() {
        return yLocation;
    }
    
    protected int setDungeonYLoc(int y) {
        return yLocation = y;
    }
    
    protected boolean getDoorNorth() {
        return doorNorth;
    }
    
    protected void setDoorNorth(boolean n) {
        doorNorth = n;
    }
    
    protected boolean getDoorSouth() {
        return doorSouth;
    }
    
    protected void setDoorSouth(boolean s) {
        doorSouth = s;
    }
    
    protected boolean getDoorEast() {
        return doorEast;
    }
    
    protected void setDoorEast(boolean e) {
        doorNorth = e;
    }
    
    protected boolean getDoorWest() {
        return doorWest;
    }
    
    protected void setDoorWest(boolean w) {
        doorWest = w;
    }
    
    protected int getXSize() {
        return ROOM_X_SIZE;
    }
    
    protected int getYSize() {
        return ROOM_Y_SIZE;
    }
    
    protected int getNumberMobSpawners() {
        return numMobSpawners;
    }
    
    protected void addMob(GiantRat r) {
        giantRat.add(r);
    }
    
    protected void removeMob(int i) {
        giantRat.remove(i);
    }
    
    /*
     * Get GiantRat MOB based on index position in list
     */
    protected GiantRat getMob(int i) {
        return giantRat.get(i);
    }   
    
    /*
     * Get GiantRat MOB based on XY location passed as arguments
     */
    protected GiantRat getMob(int x, int y) {
        GiantRat r = null;
        for (int i = 0; i <= giantRat.size()-1; i++)
            if (giantRat.get(i).getXLocation() == x && giantRat.get(i).getYLocation() == y)
                r = giantRat.get(i);
        return r;
    }  
    
    /*
     * Get index position of GiantRat MOB based on XY location passed as arguments
     */
    protected int getIndexOfMob(int x, int y) {
        int index = 0;
        for (int i = 0; i <= giantRat.size()-1; i++)
            if (giantRat.get(i).getXLocation() == x && giantRat.get(i).getYLocation() == y)
                index = i;
        return index;
    }   
    
    protected int getNumberMobs() {
        return giantRat.size();
    }
    
    protected void setNumSpawns(int ns) {
        numSpawns = ns;
    }
    
    protected int getNumSpawns() {
        return numSpawns;
    }
    
    protected void setSpawnType(String st) {
        spawnType = st;
    }
    
    protected String getSpawnType() {
        return spawnType;
    }
    
    protected void clearMobs() {
        giantRat.clear();
    }
    
    protected int getNumRoomsFromStart() {
        return numRoomsFromStart;
    } 
    
    protected void initGiantRats() {
        giantRat = new ArrayList<GiantRat>(); // list of GiantRat MOBs
    }
    
    /*
     * initializeStartingRoom Method - Initialize starting room on game restart
     */
    protected void initializeStartingRoom() { 
        for (int yCount = 0; yCount <= ROOM_Y_SIZE-1; yCount++) {
            for (int xCount = 0; xCount <= ROOM_X_SIZE-1; xCount++) {
                boolean north = true;
                boolean south = true;
                boolean east = true;
                boolean west = true;
                    
                if (xCount == 0) {
                    if (yCount == 5 && doorWest == true) {
                        north = false;
                        south = false;
                    } 
                    west = false;
                }
                
                if (xCount == 1) {
                    if (yCount == 5 && doorWest == true)
                        west = true;
                    else
                        west = false;
                }
                
                if (xCount == ROOM_X_SIZE-1) {
                    if (yCount == 5 && doorEast == true) {
                        north = false;
                        south = false;
                    } 
                    east = false;
                }
                
                if (xCount == ROOM_X_SIZE-2) {
                    if (yCount == 5 && doorEast == true)
                        east = true;
                    else
                        east = false;
                }
                
                if (yCount == 0) {
                    if (xCount == 5 && doorNorth == true) {
                        east = false;
                        west = false;
                    } 
                    north = false;
                }
                
                if (yCount == 1) {
                    if (xCount == 5 && doorNorth == true)
                        north = true;
                    else
                        north = false;
                }
                
                if (yCount == ROOM_Y_SIZE-1) {
                    if (xCount == 5 && doorSouth == true) {
                        east = false;
                        west = false;
                    } 
                    south = false;
                }
                
                if (yCount == ROOM_Y_SIZE-2) {
                    if (xCount == 5 && doorSouth == true)
                        south = true;
                    else
                        south = false;
                }
                
                Square s = squares.get(yCount).get(xCount);
                
                s.setGoNorth(north);
                s.setGoEast(east);
                s.setGoSouth(south);
                s.setGoWest(west);
                
                if (xCount != 5 || yCount != 5)
                    s.setOccupiedByAnimateObject(false);
            }
        }
    }
}
