import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Levels {
  Character player;
  int lvlHeight;
  int lvlWidth;
  ArrayList<LevelObject> levelObjects;
  boolean paused;
  int selected;
  int selectedMax = 3; //number of options on pause menu
  LevelSelect levelSelect;
  Options options;
  
  Levels(int lvlWidth, int lvlHeight, LevelSelect levelSelect, Options options) {
    this.lvlHeight = lvlHeight;
    this.lvlWidth = lvlWidth;
    levelObjects = new ArrayList<LevelObject>();
    this.levelSelect = levelSelect;
    this.player = new Character(lvlHeight, levelObjects);
    this.options = options;
  }
  
  Levels(int lvlWidth, int lvlHeight, LevelSelect levelSelect, int size, Options options) {
    this.lvlHeight = lvlHeight;
    this.lvlWidth = lvlWidth;
    levelObjects = new ArrayList<LevelObject>();
    this.levelSelect = levelSelect;
    this.player = new Character(lvlHeight, levelObjects, size);
    this.options = options;
  }
  
  //unpauses and resets the player to the starting position
  public void resetLevel() {
    this.paused = false;
    this.player.resetPlayer(lvlHeight);
  }
  
  //draws the objects on the scene
  WorldScene draw(WorldScene bk) {
    for(int i = 0; i < levelObjects.size(); i++) {
      bk = levelObjects.get(i).draw(bk);
    }
    return bk;
  }
  
  //add an object to this levels list of objects
  void addObject(LevelObject that) {
    levelObjects.add(that);
  }
  
  //removes an object from this level's list of objects
  void removeObject(int place) {
    levelObjects.remove(place);
  }
  
  //onTick
  public void moveChar(int tickCounter) {
    if(this.paused) {
      //don't move character
    }
    else {
      this.player.movePlayer(this.lvlWidth, this.lvlHeight);
      if((tickCounter + 1) % 2 == 1) {
        this.player.gravity();
      }
      
      if(tickCounter == 8) {
        //this.player.friction();
        tickCounter = 0;
      }
      tickCounter ++;
    }
  }
  
  //Key Event
  public void accChar(String key) {
    if(key.equals("escape")) {
      this.paused = !this.paused;
    }
    else if(!this.paused){
      //keys to control player
      this.player.accPlayer(key); 
    }
    else {
      //used for pause menu
      if(key.equalsIgnoreCase("w")) {
        if(this.selected == 0) {
          this.selected = this.selectedMax;
        }
        else {
          this.selected --;
        }
      }
      if(key.equalsIgnoreCase("s")) {
        if(this.selected == this.selectedMax) {
          this.selected = 0;
        }
        else {
          this.selected ++;
        }
      }
      if(key.contentEquals("enter")) {
        //resume
        if(selected == 0) {
          this.paused = false;
        }
        //restart
        else if(selected == 1) {
          this.resetLevel();
        }
        //Level Select
        else if(selected == 2) {
          this.resetLevel();
          this.levelSelect.updateOnGame();
        }
        //Options
        else if(selected == 3) {
          //this.options.updateIfInOptions();
        }
      }
    }
  }
  
  //creates the on screen game scene
  public WorldScene makeLevelScene() {
    WorldScene current = new WorldScene(this.lvlWidth, this.lvlHeight);
    
    if(this.paused) {
      WorldImage titleName = new TextImage("Paused", 42, Color.black);
      WorldImage resume = new TextImage("Resume", 30, Color.black);
      WorldImage restart = new TextImage("Restart", 30, Color.black);
      WorldImage options = new TextImage("Options", 30, Color.black);
      WorldImage levelSelImg = new TextImage("Select Level", 30, Color.black);
      
      if(this.selected == 0) {
        resume = new TextImage("Resume", 30, Color.red);
      }
      else if(this.selected == 1) {
        restart = new TextImage("Restart", 31, Color.red);
      }
      else if(this.selected == 2) {
        levelSelImg = new TextImage("Select Level", 31, Color.red);
      }
      else if(this.selected == 3) {
        options = new TextImage("Options", 31, Color.red);
      }
      
      current.placeImageXY(titleName, this.lvlWidth / 2, this.lvlHeight / 5);
      current.placeImageXY(resume, this.lvlWidth / 2, this.lvlHeight / 2 - (this.lvlHeight / 10));
      current.placeImageXY(restart, this.lvlWidth / 2, this.lvlHeight / 2);
      current.placeImageXY(levelSelImg, this.lvlWidth / 2, this.lvlHeight / 2  + (this.lvlHeight / 10));
      current.placeImageXY(options, this.lvlWidth / 2, (int) (this.lvlHeight / 2 + (this.lvlHeight / 5)));
    }
    
    current.placeImageXY(new TextImage("Speed X: " 
      + this.player.xacc, 18, Color.BLACK), 50, 50);
    current.placeImageXY(new TextImage("Speed Y: " + this.player.yacc, 18, Color.black), 50, 100);
    current.placeImageXY(new TextImage("X Pos: " + this.player.pos.x, 18, Color.black), 200, 50);
    current.placeImageXY(new TextImage("Y Pos: " + this.player.pos.y, 18, Color.black), 200, 100);
    
    for(int i = 0; i < this.levelObjects.size(); i++) {
      this.levelObjects.get(i).draw(current);
    }
    this.player.draw(current);
    return current;
  }
}





interface LevelObject {
  WorldScene draw(WorldScene bk);
  //collision function
  ArrayList<Integer> objectCollision(int playerx, int playery, int xacc, int yacc, int size);
}





abstract class LevelAbs implements LevelObject {
  Posn pos;
  
  LevelAbs(int xpos, int ypos) {
    this.pos = new Posn(xpos, ypos);
  }
  
  public WorldScene draw(WorldScene bk) {
    return bk;
  }
  
  public abstract ArrayList<Integer> objectCollision(int playerx, int playery, int xacc, int yacc, int size);
}





class Platform extends LevelAbs {
  int height;
  int width;  
  
  Platform(int width, int height, int xpos, int ypos) {
    super(xpos, ypos);
    this.height = height;
    this.width = width;
  }
  
  public WorldScene draw(WorldScene bk) {
    bk.placeImageXY(new RectangleImage(this.width, this.height, OutlineMode.SOLID, Color.BLUE), this.pos.x, this.pos.y);
    return bk;
  }

  public ArrayList<Integer> objectCollision(int playerx, int playery, int xacc, int yacc, int size) {
    int tempx = playerx + xacc;
    int tempy = playery + yacc;
    int change = 0;
    if(playerx > this.pos.x - (this.width / 2) && playerx < this.pos.x + (this.width / 2)) {
      //above plat
      if(tempy >= this.pos.y - this.height / 2 - size && playery <= this.pos.y) {
        tempy = this.pos.y - size - this.height/2;
        yacc = -1;
        change = 1;
      }
      //below plat
      else if(tempy <= this.pos.y + this.height / 2 + size && playery > this.pos.y) {
        tempy = this.pos.y + size + this.height/2;
        yacc = 0;
        change = 1;
      }
    }
    else if(playery < this.pos.y + this.height / 2 && playery > this.pos.y) {
      //left of plat
      if(tempx >= this.pos.x - this.width / 2 - size && playerx <= this.pos.x) {
        tempx = this.pos.x - size - this.width / 2;
        xacc = 0;
        change = 1;
      }
      //right of plat
      else if(tempx <= this.pos.x + this.width / 2 + size && playerx >= this.pos.x) {
        tempx = this.pos.x + size + this.width / 2;
        xacc = 0;
        change = 1;
      }
    }
    ArrayList<Integer> myReturn = new ArrayList<Integer>();
    myReturn.add(tempx);
    myReturn.add(tempy);
    myReturn.add(xacc);
    myReturn.add(yacc);
    myReturn.add(change);
    return myReturn;
  }
  
}