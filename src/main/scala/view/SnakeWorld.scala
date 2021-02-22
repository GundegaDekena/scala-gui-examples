package view
import controller.ArrowInputs
import javafx.scene.input.KeyEvent
import model.{Game, Snake}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.canvas.Canvas
import scalafx.scene.{Group, Scene}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle, Shape}

object SnakeWorld extends JFXApp {
  // set up the basic sizing data
  val windowWidth: Int = 600
  val windowHeight: Int = 600
  val tileSize: Int = 100
  val snakeSize: Int = 20

  // init a new game using the game class that holds game specific data and methods
  val game: Game = new Game()

   // set up some example unit images, position and speed using Snake objects
  val snake:Snake  = new Snake("Zorro", getImage(game.snakeAttack1))
  snake.posY = -80
  val snake2:Snake  = new Snake("Brigitta", getImage(game.snakeIdle))
  snake2.posX = -20
  snake2.velocity.x = 35
  snake2.velocity.y = 15

  // Canvas for drawing the images, this has better performance than redrawing the whole scene
  // but it is optional
  val gameCanvas = new Canvas(windowWidth, windowHeight)
  val gc = gameCanvas.graphicsContext2D

  // the primary stage - window where everything will show up
  this.stage = new PrimaryStage {
    width = windowWidth
    height = windowHeight
    resizable = false
    title = "Ssssssssnake game"
    addEventHandler(KeyEvent.ANY, new ArrowInputs(snake))
    scene = new Scene()
    {
      fill = Color.Green
      root = new Pane {
        // list of objects on the scene
        children=List(renderMapTiles, gameCanvas, renderCircle(500, 251))
      }
    }
  }

  // update/redraw the whole scene
  def renderBG(): Unit = {
    this.stage.scene = new Scene() {
      content = List(renderMapTiles, gameCanvas)
    }
  }


  // rendering one tile (square) of the map
  def renderTile(passable: Boolean, col: Int, row: Int): Shape = {
    var color: Color = Color.Green
    if (!passable) {
      color = Color.DarkSlateGrey
    }
    new Rectangle() {
      width = tileSize
      height = tileSize
      x = tileSize * col
      y = tileSize * row
      fill = color
    }
  }

  // rendering a group of tiles that compose a map
  def renderMapTiles: Group = {
    // a way to group objects
    val mapTiles: Group = new Group {}

    // use some datasource, in this case a list of lists as basis for the map
    for (row <- game.map.indices) {
      for (i <- game.map(row).indices) {
        var passable = true
        if (game.map(row)(i) == 0) {
          passable = false
        }
        val tile: Shape = renderTile(passable, i, row)
        // add new objects to the group
        mapTiles.children.add(tile)
      }
    }
    mapTiles
  }

  // just an example of a simple rendering of a single shape with some params
  def renderCircle(posX: Int, posY: Int): Shape = {
    val color: Color = Color.Gold
    new Circle() {
      radius = snakeSize
      centerX = posX
      centerY = posY
      fill = color
    }
  }

  // create a new image object from a url string
  // url can be an actual URL or local file, eg
  // https://example.com/myimage.png
  // file:data/images/myimage.jpg <- relative to root of the project, so data here is on same level as src
  def getImage(url: String): Image = {
    new Image(url) {
    }
  }

  // render image from image object
  def renderPicture(img: Image, posX: Int, posY: Int, angle: Int): ImageView = {
    new ImageView(img) {
      x = posX
      y = posY
      rotate = angle
      if (angle > 0) {
        scaleY = -1
      }
      fitWidth = 150
      preserveRatio = true
    }
  }

  ///// view update process

  // get when game was last updated
  var lastUpdateTime: Long = System.nanoTime()

  // update function that will update game status and visuals according to the time passed
  def update(time: Long): Unit = {
    val dt: Double = (time - lastUpdateTime) / 1000000000.0
    lastUpdateTime = time
    // update game objects. for dynamic objects this would be a loop through a list
    snake.update(dt)
    snake2.update(dt)

    // update canvas
    // clear previous drawings on canvas
    gc.clearRect(0, 0, windowWidth, windowHeight)
    // draw objects in the new positions
    gc.drawImage(snake.image, snake.posX, snake.posY)
    gc.drawImage(snake2.image, snake2.posX, snake2.posY)

    // alternatively you can just call a method that updates the objects and recreates scene, like updateBG
  }

  // start the animation timer that will call update function every time it can
  AnimationTimer(update).start()
}
