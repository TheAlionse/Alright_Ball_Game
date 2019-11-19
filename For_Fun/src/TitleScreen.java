import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javalib.worldimages.*;

class TitleScreen {
  int height;
  int width;
  TextImage titleName;
  TextImage levelSelImg;
  TextImage optionsIMG;
  int selected;
  int selectedMax = 1; //max number of options
  boolean inLevelSelect;
  boolean inOptions;
  LevelSelect levelSelect;
  Options options;
  
  TitleScreen(int width, int height) {
    this.selected = 0;
    this.height = height;
    this.width = width;
    this.options = new Options(width, height);
    this.levelSelect = new LevelSelect(width, height);
    this.levelSelect.passOptions(options);
  }
  
  //onTick
  void changeSelect(int tickCounter) {
    if(this.inLevelSelect) {
      this.levelSelect.onTick(tickCounter);
    }
    else {
      //do nothing for now
    }
  }
  
  //if the menu is in Level Select or not
  public void updateInLevelSelect() {
    this.inLevelSelect = !this.inLevelSelect;
  }
  
  //if the menu is in the Options or not
  public void updateInOptions() {
    this.inOptions = !this.inOptions;
  }
  
  //draws the Title Screen
  public WorldScene draw() {
    //draws Level Select Screen
    if(inLevelSelect) {
      return levelSelect.draw();
    }
    //draws Option Screen
    else if(inOptions) {
      //draw options
      return this.options.draw();
    }
    //draws Default title screen
    else {
      this.titleName = new TextImage("Crappy Ball Game", 42, Color.black);
      this.optionsIMG = new TextImage("Options", 30, Color.black);
      this.levelSelImg = new TextImage("Select Level", 30, Color.black);
      if(this.selected == 0) {
        this.levelSelImg = new TextImage("Select Level", 31, Color.red);
      }
      else if(this.selected == 1) {
        this.optionsIMG = new TextImage("Options", 31, Color.red);
      }
      
      WorldScene title = new WorldScene(this.width, this.height);
      title.placeImageXY(titleName, this.width / 2, this.height / 5);
      title.placeImageXY(levelSelImg, this.width / 2, this.height / 2);
      title.placeImageXY(optionsIMG, this.width / 2, (int) (this.height / 1.5));
      return title;
    }
  }
  
  //Key Event
  public void titleSelect(String key) {
    //Goes into Level Select for Key Events
    if(inLevelSelect) {
      this.levelSelect.pickLevel(key);
    }
    //Goes into Options for Key Events
    else if(inOptions) {
      this.options.optionsSelect(key);
    }
    //Key Events on Title Screen
    else {
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
        if(selected == 0) {
          this.levelSelect.addTitleScreenAccess(this);
          this.inLevelSelect = true;
        }
        else if(selected == 1) {
          this.options.addTitleScreenAccess(this);
          this.inOptions = true;
        }
      }
    }
  }
}