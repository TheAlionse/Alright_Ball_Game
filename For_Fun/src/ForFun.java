import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javalib.worldimages.*;

class Game extends World {
  TitleScreen titleScreen;
  int height;
  int width;
  double tick = .01; //tick rate of program
  int tickCounter;
  
  //creates a default game
  /*
    Game() {
    super();
    this.player = new Character();
    this.height = 250;
    this.width = 250;
    this.myLevels = new MyLevels();
    this.levels = myLevels.printLevels();
    this.levelSelect = new LevelSelect(height, width, levels);
    this.titleScreen = new TitleScreen(250, 250, levelSelect);
    this.levelSelect.addTitleScreenAccess(this.titleScreen);
  }
  */
  
  //creates a game with set screen width and height
  Game(int width, int height) {
    super();
    this.height = height;
    this.width = width;
    this.titleScreen  = new TitleScreen(width, height);
  }
  
  //runs on KeyEvents
  public void onKeyEvent(String key) {
      this.titleScreen.titleSelect(key);
  }
  
  //runs on tick
  public void onTick() {
      this.titleScreen.changeSelect(this.tickCounter);
  }
  
  //creates the Title screen
  public WorldScene makeScene() {
      return this.titleScreen.draw();
  }
  
}