/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              Dungeon implements Serializable
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class generates the dungeon.
 *                      It is composed of Room class objects.
 *                      Each Room is composed of 11 x 11 Square class objects.
 */

package main.javafx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Dungeon implements Serializable {
    private int numRoomsVisited; // number rooms visited
    private int highScore; // high score (distance from starting room)
    private int numDungeonExits; // number dungeon exists
    private int numRoomsFromStart; // number rooms from the start
    private Room room; // Room object 
    private final ArrayList<Room> roomsVisited = new ArrayList<Room>(); // List of Room(s) visited
    private final int START_ROOM_X_LOC = 0; // starting room x location
    private final int START_ROOM_Y_LOC = 0; // starting room y location
    private int roomXLoc; // current room x location
    private int roomYLoc; // current room y location
    private final Random random = new Random(); // random number generator
    private boolean goNorth; // can you go North?
    private boolean goSouth; // can you go South?
    private boolean goEast; // can you go East?
    private boolean goWest; // can you go West?
    private String savedGameName; // name of saved game
    private int numSpawns; // number of spawns
    
    // Dungeon images:  Wall block and doors
    private final String wallBlock = "resources/images/Wall_Block.png";
    
    protected Dungeon(int xStart, int yStart) {
        roomXLoc = 0; // room x location
        roomYLoc = 0; // room y location    
        numRoomsVisited = 1; // number of rooms visited
        highScore = 0; // high score (distance from starting room)
        numRoomsFromStart = 0; // number of rooms from the start
        numSpawns = 0; // number of spawns
        
        /*
         * Keep track of the total number of available doors
         * for the dungeon.  If this goes to zero, the game
         * ends.
         *
         * Initialize to 1 because the starting room has 1 exit.
         */
        numDungeonExits = 1;
        
        /*
         * genterate random int from 0-3
         * the initial room will have a door facing
         * a random cardinal direction
         */
        int randomCardinalDir = random.nextInt(4);
        
        if (randomCardinalDir == 0)
            room = new Room(roomXLoc, roomYLoc, true, false, false, false, getNumRoomsFromStart(), 0, "None");    
        else if (randomCardinalDir == 1)
            room = new Room(roomXLoc, roomYLoc, false, true, false, false, getNumRoomsFromStart(), 0, "None"); 
        else if (randomCardinalDir == 2)
            room = new Room(roomXLoc, roomYLoc, false, false, true, false, getNumRoomsFromStart(), 0, "None"); 
        else if (randomCardinalDir == 3)
            room = new Room(roomXLoc, roomYLoc, false, false, false, true, getNumRoomsFromStart(), 0, "None"); 
        
        roomsVisited.add(room); // add starting room
        getSquare(xStart, yStart).setOccupiedByAnimateObject(true); // set starting hero position to occupied
    }
    
    /*
     * getRoom Method - Get reference to current Room object
     */
    protected Room getRoom(int roomX, int roomY) {
        for (Room r : roomsVisited) {
            if (r.getDungeonXLoc() == roomX && r.getDungeonYLoc() == roomY)
                return r;
        }
        return null;
    }
    
    /*
     * getRoomAtIndex Method - Get reference to Room object at passed index
     */
    protected Room getRoom(int index) {
        return roomsVisited.get(index);
    }
    
    /*
     * roomExist Method - Does Room exist?
     */
    protected boolean roomExist() {
        for (Room r : roomsVisited) {
            if (r.getDungeonXLoc() == roomXLoc && r.getDungeonYLoc() == roomYLoc)
                return true;
        }
        return false;
    }
    
    /*
     * getSquare Method - Get reference to square object at x,y arguments
     */
    protected Square getSquare(int x, int y) {
        return getRoom(roomXLoc, roomYLoc).getSquare(x, y);
    }
    
    /*
     * createNewRoom Method - Create a new Room
     *      - Note that the dungeon is procedurally generated,
     *        with a random number of doors at random locations.
     *      - This means that for each new game, the dungeon layout
     *        will be different.
     */
    protected void createNewRoom(String dir) {
        // determine number of doors for room
        while (determineDoors(dir))
            ; // do nothing
        
        int distance = getNumRoomsFromStart();
       
        numSpawns = distance / 2;
        
        if (numSpawns < 1)
            numSpawns = 1;
        
        String spawnType = "GiantRat";
        
        // create room and add to list
        room = new Room(roomXLoc, roomYLoc, goNorth, goEast, goSouth, goWest, distance, numSpawns, spawnType); 
        roomsVisited.add(room);
        numRoomsVisited++;
            
        // is this a new high score?
        if (distance > highScore)
            highScore = distance;
    }
    
    protected int getRoomXLoc() {
        return roomXLoc;
    }
    
    protected void setRoomXLoc(int x) {
        roomXLoc = x;
    }
    
    protected int getRoomYLoc() {
        return roomYLoc;
    }
    
    protected void setRoomYLoc(int y) {
        roomYLoc = y;
    }
    
    protected int getNumRoomsVisited() {
        return numRoomsVisited;
    }
    
    protected int getHighScore() {
        return highScore;
    }
    
    protected int getStartRoomXLoc() {
        return START_ROOM_X_LOC;
    }
    
    protected int getStartRoomYLoc() {
        return START_ROOM_Y_LOC;
    }

    protected boolean getRoomNorth() {
        return getRoom(roomXLoc, roomYLoc).getDoorNorth();
    }
    
    protected void setRoomNorth(boolean n) {
        getRoom(roomXLoc, roomYLoc).setDoorNorth(n);
    }
    
    protected boolean getRoomSouth() {
        return getRoom(roomXLoc, roomYLoc).getDoorSouth();
    }
    
    protected void setRoomSouth(boolean s) {
        getRoom(roomXLoc, roomYLoc).setDoorSouth(s);
    }
    
    protected boolean getRoomEast() {
        return getRoom(roomXLoc, roomYLoc).getDoorEast();
    }
    
    protected void setRoomEast(boolean e) {
        getRoom(roomXLoc, roomYLoc).setDoorEast(e);
    }
    
    protected boolean getRoomWest() {
        return getRoom(roomXLoc, roomYLoc).getDoorWest();
    }
    
    protected void setRoomWest(boolean w) {
        getRoom(roomXLoc, roomYLoc).setDoorWest(w);
    }
    
    /*
     * determineDoors Method - Calculate doors for the room
     *      - Note that the dungeon is procedurally generated,
     *        with a random number of doors at random locations.
     *
     *      - This means that for each new game, the dungeon layout
     *        will be different.
     *
     *      - ALSO NOTE THAT IT IS POSSIBLE FOR THE NUMBER OF
     *        AVAILABLE DUNGEON EXISTS TO REACH ZERO>  IF THAT HAPPENS,
     *        THE GAME WILL EFFECTIVELY END AND THE PLAYER WILL BE NOTIFIED.
     *        THEREFORE, I WILL BE ADDING CODE TO DISPLAY AND TRACK THE NUMBER
     *        OF AVAILABLE DUNGEON EXITS.  
     */
    protected boolean determineDoors(String d) {
        int availableDoors = 0;
        int numDoors = 0;
        
        // if doors leads to an existing room,
        // I need to subtract 2 from the 
        // avaiable exits
        int doorsToSubtract = 0; 
        
        goNorth = false;
        goEast = false;
        goSouth = false; 
        goWest = false;
        
        if (getRoom(roomXLoc, roomYLoc+1) == null) {
            goNorth = random.nextBoolean();
            availableDoors++;
        } else if (getRoom(roomXLoc, roomYLoc+1).getDoorSouth()) {
            goNorth = true;
            availableDoors++;
            doorsToSubtract += 2;
        }
        
        if (getRoom(roomXLoc+1, roomYLoc) == null) {
            goEast = random.nextBoolean();
            availableDoors++;
        } else if (getRoom(roomXLoc+1, roomYLoc).getDoorWest()) {
            goEast = true;            
            availableDoors++;
            doorsToSubtract += 2;
        }
        
        if (getRoom(roomXLoc, roomYLoc-1) == null) {
            goSouth = random.nextBoolean();
            availableDoors++;
        } else if (getRoom(roomXLoc, roomYLoc-1).getDoorNorth()) {
            goSouth = true;
            availableDoors++;
            doorsToSubtract += 2;
        }

        if (getRoom(roomXLoc-1, roomYLoc) == null) {
            goWest = random.nextBoolean();
            availableDoors++;
        } else if (getRoom(roomXLoc-1, roomYLoc).getDoorEast()) {
            goWest = true;
            availableDoors++;
            doorsToSubtract += 2;
        }
        
        if (goNorth)
            numDoors++;
        
        if (goEast)
            numDoors++;
        
        if (goSouth)
            numDoors++;
        
        if (goWest)
            numDoors++;
       
        boolean getDoorsAgain;
        if (numDoors <= 1) {
            if (availableDoors == 1)
                getDoorsAgain = false;
            else
                getDoorsAgain = true;
        } else
            getDoorsAgain = false;
            
        /*
         * The number of dungeon exits will increase by
         * the number of doors in a room, excluding the
         * door entered.
         * 
         * I subtract two because one exit is lost when
         * then room is entered and I also don't count
         * the door the hero entered from.
         */
        if (!getDoorsAgain) {
            numDungeonExits -= doorsToSubtract;
            numDungeonExits += numDoors;
        }
                
        return getDoorsAgain;
    }
    
    protected int getRoomXSize() {
        return room.getXSize();
    }
    
    protected int getRoomYSize() {
        return room.getYSize();
    }

    protected String getWallBlock() {
        return wallBlock;
    }
        
    /*
     * getDistanceFromStart Method - Calculate and return the 
     *      number of rooms 
     *
     *      - Calculation (absolute values are used):
     *          distance = x distance + y distance
     *
     *      - This statistic is the primary score in the game
     *        and is used to determine the high score.
     *
     *      - IT WILL BE USED TO DETERMINE THE NUMBER AND DIFFICULTY OF MOBS.
     *        THE GREATER THE DISTANCE, THE MORE POWERFUL THE MOBS.
     */
    protected int getNumRoomsFromStart() {
        int distance = 0;
        
        if (roomXLoc >= START_ROOM_X_LOC)
            distance = roomXLoc;
        else
            distance = Math.abs(roomXLoc);
        
        if (roomYLoc >= START_ROOM_Y_LOC)
            distance += roomYLoc;
        else
            distance += Math.abs(roomYLoc);
        
        return distance;
    }
    
    protected int getNumDungeonExits() {
        return numDungeonExits;
    }   
    
    /*
     * regenDungeon Method - Regenerate mobs after game restart
     */
    protected void regenDungeon() {
        for (int i = 0; i < roomsVisited.size(); i++) {
            Room r = roomsVisited.get(i);
            
            int x = r.getDungeonXLoc();
            int y = r.getDungeonYLoc();
            
            goNorth = r.getDoorNorth();
            goEast = r.getDoorEast();
            goSouth = r.getDoorSouth();
            goWest = r.getDoorWest();
            
            int distance = r.getNumRoomsFromStart();
            int numSpawns;
            
            if (distance == 0) {
                numSpawns = 0;
            } else {
                numSpawns = distance / 2;
            
                if (numSpawns < 1)
                    numSpawns = 1;
            }
            
            String spawnType = "GiantRat";
            
            roomsVisited.set(i, new Room(x, y, goNorth, goEast, goSouth, goWest, distance, numSpawns, spawnType));
        }
    }
    
    protected String getSavedGameName() {
        return savedGameName;
    }
    
    protected void setSavedGameName(String s) {
        savedGameName = s;
    }
    
    /*
     * calcuate and return number of spawns for current room
     */
    protected int getNumSpawns() {
        int distance = getNumRoomsFromStart();
       
        numSpawns = distance / 2;
        
        if (numSpawns < 1)
            numSpawns = 1;
        
        return numSpawns;
    }
}