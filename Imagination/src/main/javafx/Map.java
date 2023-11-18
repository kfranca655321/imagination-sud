/*
 * @Course:             SDEV-435: Applied Software Practice I
 * @class:              ImaginationController implements Initializable
 * @Author Name:        Keith Francalangia 
 * @Assignment Name:    Software Project:  "Imagination" Dungeon Crawler Game
 * @Date:               December 12, 2015
 * @Description:        This class dungeon map data and methods.
 */

package main.javafx;

import javafx.scene.image.Image;

public class Map {
    private final int MAP_X_SIZE = 21; // map grid pane x size 
    private final int MAP_Y_SIZE = 21; // map grid pane y size
    
    // images for map
    private final Image mapRoomE = new Image(ImaginationMain.class.getResource("resources/images/mapRoomE.png").toString());
    private final Image mapRoomES = new Image(ImaginationMain.class.getResource("resources/images/mapRoomES.png").toString());
    private final Image mapRoomESW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomESW.png").toString());
    private final Image mapRoomEW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomEW.png").toString());
    private final Image mapRoomN = new Image(ImaginationMain.class.getResource("resources/images/mapRoomN.png").toString());
    private final Image mapRoomNE = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNE.png").toString());
    private final Image mapRoomNES = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNES.png").toString());
    private final Image mapRoomNESW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNESW.png").toString());
    private final Image mapRoomNEW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNEW.png").toString());
    private final Image mapRoomNS = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNS.png").toString());
    private final Image mapRoomNSW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNSW.png").toString());
    private final Image mapRoomNW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomNW.png").toString());
    private final Image mapRoomS = new Image(ImaginationMain.class.getResource("resources/images/mapRoomS.png").toString());
    private final Image mapRoomSW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomSW.png").toString());
    private final Image mapRoomW = new Image(ImaginationMain.class.getResource("resources/images/mapRoomW.png").toString());
    private final Image start = new Image(ImaginationMain.class.getResource("resources/images/start.png").toString());
    private final Image heroPicture = new Image(ImaginationMain.class.getResource("resources/images/Hero_S_Map.png").toString());
    
    Map() { 
        
    }
    
    protected int getMapXSize() {
        return MAP_X_SIZE;
    }
    
    protected int getMapYSize() {
        return MAP_Y_SIZE;
    }
    
    protected Image getMapRoomE() {
        return mapRoomE;
    }
    
    protected Image getMapRoomES() {
        return mapRoomES;
    }
    
    protected Image getMapRoomESW() {
        return mapRoomESW;
    }
    
    protected Image getMapRoomEW() {
        return mapRoomEW;
    }
    
    protected Image getMapRoomN() {
        return mapRoomN;
    }
    
    protected Image getMapRoomNE() {
        return mapRoomNE;
    }
    
    protected Image getMapRoomNES() {
        return mapRoomNES;
    }
    
    protected Image getMapRoomNESW() {
        return mapRoomNESW;
    }
    
    protected Image getMapRoomNEW() {
        return mapRoomNEW;
    }
    
    protected Image getMapRoomNS() {
        return mapRoomNS;
    }
    
    protected Image getMapRoomNSW() {
        return mapRoomNSW;
    }
    
    protected Image getMapRoomNW() {
        return mapRoomNW;
    }
    
    protected Image getMapRoomS() {
        return mapRoomS;
    }
    
    protected Image getMapRoomSW() {
        return mapRoomSW;
    }
    protected Image getMapRoomW() {
        return mapRoomW;
    }
    
    protected Image getStart() {
        return start;
    }
    
    protected Image getHeroPicture() {
        return heroPicture;
    }
    
    /*
     * getRoomImage method - Get Image of Room passed as argument for dungeon map.
     *      There are 15 combinations of doors (1 to 4 doors per room).
     */
    protected Image getRoomImage(Room r) {
        boolean north = r.getDoorNorth();
        boolean east = r.getDoorEast();
        boolean south = r.getDoorSouth();
        boolean west = r.getDoorWest();
        Image v = null;
        
        if (!north && east && !south && !west)
            v = mapRoomE;
        else if (!north && east && south && !west)
            v = mapRoomES;   
        else if (!north && east && south && west)
            v = mapRoomESW;   
        else if (!north && east && !south && west)
            v = mapRoomEW;  
        else if (north && !east && !south && !west)
            v = mapRoomN;   
        else if (north && east && !south && !west)
            v = mapRoomNE;  
        else if (north && east && south && !west)
            v = mapRoomNES;  
        else if (north && east && south && west)
            v = mapRoomNESW;   
        else if (north && east && !south && west)
            v = mapRoomNEW;   
        else if (north && !east && south && !west)
            v = mapRoomNS; 
        else if (north && !east && south && west)
            v = mapRoomNSW;  
        else if (north && !east && !south && west)
            v = mapRoomNW;
        else if (!north && !east && south && !west)
            v = mapRoomS;  
        else if (!north && !east && south && west)
            v = mapRoomSW;   
        else if (!north && !east && !south && west)
            v = mapRoomW;   
            
        return v;
    }
}
