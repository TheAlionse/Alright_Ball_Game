import java.util.ArrayList;
import java.util.Random;
import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

class Character {
  Posn pos;
  int xacc;
  int yacc;
  int size;
  int speeds = 2;
  int jump = 8; //Jump height (multiply by speed)
  int maxSpeed = 10; //sets the max speed of character
  ArrayList<LevelObject> levelObjects;
  
  //creates default character
  /*
  Character() {
    this.pos = new Posn(5,5);
    this.xacc = 0;
    this.yacc = 0;
    this.size = 5;
  }
  */
  
  //created character so that it starts in the bottom left of the screen
  Character(int height, ArrayList<LevelObject> levelObjects) {
    this.pos = new Posn(5,height - 5);
    this.xacc = 0;
    this.yacc = 0;
    this.size = 5;
    this.levelObjects = levelObjects;
  }
  
  //create a character that starts in the bottom left with a set size
  Character(int height, ArrayList<LevelObject> levelObjects, int size) {
    this.pos = new Posn(size,height - size);
    this.xacc = 0;
    this.yacc = 0;
    this.size = size;
    this.levelObjects = levelObjects;
  }
  
  //draws the character on the given scene
  WorldScene draw(WorldScene bk) {
    bk.placeImageXY(new CircleImage(this.size, OutlineMode.SOLID, Color.RED), this.pos.x, this.pos.y);
    return bk;
  }
  
  //reset player to starting position
  public void resetPlayer(int height) {
    this.xacc = 0;
    this.yacc = 0;
    this.pos = new Posn(this.size, height - this.size);
  }
  
  //moves the player so that it stays within the specified height and width of the screen
  //onTick
  void movePlayer(int width, int height) {
    int tempx = this.pos.x + this.xacc;
    int tempy = this.pos.y + this.yacc;
    //insert collision function
    for(int i = 0; i < this.levelObjects.size(); i ++) {
      ArrayList<Integer> tempArray = 
          this.levelObjects.get(i).objectCollision(this.pos.x, this.pos.y, this.xacc, this.yacc, this.size);
      if(tempArray.get(4) == 1) {
        tempx = tempArray.get(0);
        tempy = tempArray.get(1);
        this.xacc = tempArray.get(2);
        this.yacc = tempArray.get(3);
      }      
    }
    //right side
    if(tempx >= width - this.size) {
      tempx = this.pos.x + (width - this.size - this.pos.x);
      xacc = 0;
    }
    //left side
    if(tempx <= size) {
      tempx = this.pos.x + (-this.pos.x + this.size);
      xacc = 0;
    }
    //bottom side
    if(tempy >= height - this.size) {
      tempy = this.pos.y + (height - this.size - this.pos.y);
      yacc = -1;
    }
    //top side
    if(tempy <= this.size) {
      tempy = this.pos.y  + (-this.pos.y + this.size);
      yacc = 0;
    }
    this.pos = new Posn(tempx, tempy);
  }
  
  //when keys are pressed accelerates character in corresponding direction
  //Key Event
  void accPlayer(String button) {
    if(button.equalsIgnoreCase("w")) {
      this.yacc = 0;
      this.yacc += -this.speeds * this.jump;
    }

    if(button.equalsIgnoreCase("a")) {
      /*
      if(this.xacc > 2) {
        this.xacc = 4;
      }
      */
      if(this.xacc == 0 - this.maxSpeed) {
        //do nothing;
      }
      else {
        this.xacc += -this.speeds;
      }
    }    
    if(button.equalsIgnoreCase("d")) {
      /*
      if(this.xacc < -2) {
        this.xacc = -4;
      }
      */
      if(this.xacc == this.maxSpeed) {
        //do nothing;
      }
      else {
        this.xacc += this.speeds;
      }
    }
    
    if(button.equalsIgnoreCase("s")) {
      this.yacc += this.speeds;
    }
    
  }
  
  //calculates gravity in the world
  public void gravity() {
    this.yacc += 1;
  }
  
  //calculates friction in the world
  //currently unused
  public void friction() {
    if(yacc >= 0 && yacc <= 1) {
      if(this.xacc > 0) {
        this.xacc += -1;
      }
      else if(this.xacc < 0) {
        this.xacc += 1;
      }
      else {
        //do nothing
      }
    }
  }
  
}