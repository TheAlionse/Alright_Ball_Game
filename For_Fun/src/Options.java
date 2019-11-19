import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Options {
  int width;
  int height;
  int selected;
  int selectedMax = 1;  //max number of options (change as options are added) - 1
  TitleScreen titleScreen;
  
  Options(int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  WorldScene draw() {
    TextImage titleName;
    TextImage option0;
    TextImage option1;
    
    titleName = new TextImage("[Insert Title]", 42, Color.black);
    option0 = new TextImage("option1", 30, Color.black);
    option1 = new TextImage("option2", 30, Color.black);
    if(this.selected == 0) {
      option0 = new TextImage("option1", 31, Color.red);
    }
    else if(this.selected == 1) {
      option1 = new TextImage("option2", 31, Color.red);
    }
    
    WorldScene title = new WorldScene(this.width, this.height);
    title.placeImageXY(titleName, this.width / 2, this.height / 5);
    title.placeImageXY(option0, this.width / 2, this.height / 2);
    title.placeImageXY(option1, this.width / 2, (int) (this.height / 1.5));
    return title;
  }
  
  public void updateIfInOptions() {
    this.titleScreen.updateInOptions();
  }
  
  public void addTitleScreenAccess(TitleScreen titleScreen) {
    this.titleScreen = titleScreen;
  }
  
  public void optionsSelect(String key) {
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
        //option 1
      }
      else if(selected == 1) {
        //option 2
      }
    }
    if(key.equalsIgnoreCase("escape")) {
      this.updateIfInOptions();
    } 
  }
  
}