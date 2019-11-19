import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//class of created levels
class MyLevels {
  int height;
  int width;
  MyLevelObjects myObjects = new MyLevelObjects(); 
  public Levels recLevel;
  public Levels MTLevel;
  LevelSelect levelSelect;
  Options options;
  
  MyLevels(int width, int height, LevelSelect levelSelect) {
    this.height= height;
    this.width = width;
    this.levelSelect = levelSelect;
  }
  
  void addOptions(Options options) {
    this.options = options;
  }
  
  //creates levels that are defined at top of class
  //LATER: move this from hard code to a document to read from
  void createLevels() {
    this.MTLevel = new Levels(this.width, this.height, this.levelSelect, this.options);
    this.recLevel = new Levels(this.width, this.height, this.levelSelect, this.options);
    this.recLevel.levelObjects.add(this.myObjects.smallRec(this.recLevel.lvlWidth / 2, this.recLevel.lvlHeight - 50));
    this.recLevel.levelObjects.add(this.myObjects.ThickRec(this.recLevel.lvlWidth * 3/4, this.recLevel.lvlHeight - 75));
    this.recLevel.levelObjects.add(this.myObjects.wall(this.recLevel.lvlWidth - this.recLevel.lvlWidth / 16,
        this.recLevel.lvlHeight - 51));
  }
  
  //creates an ArrayList of the levels
  public ArrayList<Levels> printLevels(){
    this.createLevels();
    ArrayList<Levels> printMe = new ArrayList<Levels>();
    printMe.add(this.MTLevel);
    printMe.add(this.recLevel);
    return printMe;
  }
}

//class of pre-created objects
class MyLevelObjects {
  
  MyLevelObjects() { 
  }
  
  public LevelObject smallRec(int xpos, int ypos) {
    return new Platform(50, 5, xpos, ypos);
  }
  
  public LevelObject ThickRec(int xpos, int ypos) {
    return new Platform(100, 50, xpos, ypos);
  }
  
  public LevelObject wall(int xpos, int ypos) {
    return new Platform(20, 100, xpos, ypos);
  }
}