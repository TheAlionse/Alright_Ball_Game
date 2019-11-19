import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class LevelSelect {
  int height;
  int width;
  TextImage titleName;
  ArrayList<Levels> levels;
  int selected;
  int maxLevels;
  int levelsPerRow = 5; //default is 5
  TitleScreen titleScreen;
  MyLevels myLevels;
  Color boxColor;
  boolean onGame; //states whether the game is paused or going
  
  LevelSelect(int width, int height) {
    this.height = height;
    this.width = width;
    this.titleName = new TextImage("Crappy Ball Game", 42, Color.black);
    this.myLevels = new MyLevels(width, height, this);
    this.levels = myLevels.printLevels();
    this.boxColor = Color.black;
    this.maxLevels = levels.size() - 1;
  }
  
  public void passOptions(Options options) {
    this.myLevels.addOptions(options);
  }
  
  public void addTitleScreenAccess(TitleScreen titleScreen) {
    this.titleScreen = titleScreen;
  }
  
  public WorldImage drawLevelIcon(int i) {
    return new OverlayImage(new RectangleImage(this.width / 6, this.width / 6, OutlineMode.OUTLINE, boxColor),
        new TextImage("" + (i + 1), 18, Color.black));
  }
  
  //draw WorldScene 
  public WorldScene draw() {
    if(this.onGame) {
      return this.levels.get(this.selected).makeLevelScene();
    }
    else {
      int h = -1;
      WorldScene title = new WorldScene(this.width, this.height);
      title.placeImageXY(titleName, this.width / 2, this.height / 5);
      for(int i = 0; i < levels.size(); i++) {
        if(i % this.levelsPerRow == 0) {
          h++;
        }
        if(i == selected) {
          boxColor = Color.red;
        }
        else {
          boxColor = Color.black;
        }
        title.placeImageXY(this.drawLevelIcon(i),
            this.width / (this.levelsPerRow + 3) + (i * this.width / (this.levelsPerRow + 1))
            - (h * this.levelsPerRow * this.width/(this.levelsPerRow + 1)),
            this.height / (this.levelsPerRow - 1) + ((1 + h) * this.width/ (this.levelsPerRow - 1)));
      }
      return title;
    }
  }
  
  //onTick
  public void onTick(int tickCounter) {
    this.levels.get(this.selected).moveChar(tickCounter);
  }
    
  //Key Event
  public void pickLevel(String key) {
    if(this.onGame) {
      this.levels.get(this.selected).accChar(key);
    }
    else {
      if(key.equalsIgnoreCase("w")) {
        if(this.selected < this.levelsPerRow) {
          this.selected = this.selected + (this.maxLevels - 5);
        }
        else {
          this.selected = this.selected - this.levelsPerRow;
        }
      }
      if(key.equalsIgnoreCase("a")) {
        if(this.selected == 0) {
          this.selected = this.maxLevels;
        }
        else {
          this.selected --;
        }
      }    
      if(key.equalsIgnoreCase("d")) {
        if(this.selected == this.maxLevels) {
          this.selected = 0;
        }
        else {
          this.selected ++;
        }
      }
      if(key.equalsIgnoreCase("s")) {
        if(this.maxLevels - this.levelsPerRow < this.selected && this.selected <= this.maxLevels) {
          this.selected = this.selected % this.levelsPerRow;
        }
        else {
          this.selected = this.selected + this.levelsPerRow;
        }
      }
      if(key.equalsIgnoreCase("enter")) {
        this.onGame = true;
      }
      if(key.equalsIgnoreCase("escape")) {
        this.titleScreen.updateInLevelSelect();
      } 
    }
  }
  
  public void updateOnGame() {
    this.onGame = !this.onGame;
  }
  
}