import tester.Tester;

public class RunGame {
  public Game testGame = new Game(1280, 720);
  
  public void testWorld(Tester t) {
    this.testGame.bigBang(this.testGame.width, this.testGame.height, this.testGame.tick);
  }
}