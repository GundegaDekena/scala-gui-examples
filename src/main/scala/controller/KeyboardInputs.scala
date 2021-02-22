package controller

import javafx.event.EventHandler
import javafx.scene.input.KeyEvent
import model.Snake

abstract class KeyboardInputs(user: Snake) extends EventHandler[KeyEvent] {

  val LEFT: String
  val RIGHT: String
  val UP: String
  val DOWN: String

  override def handle(event: KeyEvent): Unit = {
    val keyCode = event.getCode
    event.getEventType.getName match {
      case "KEY_RELEASED" => keyCode.getName match {
        case this.LEFT => user.leftReleased()
        case this.RIGHT => user.rightReleased()
        case this.UP => user.upReleased()
        case this.DOWN => user.downReleased()
        case _ =>
      }
      case "KEY_PRESSED" => keyCode.getName match {
        case this.LEFT => user.leftPressed()
        case this.RIGHT => user.rightPressed()
        case this.UP => user.upPressed()
        case this.DOWN => user.downPressed()
        case _ =>
      }
      case _ =>
    }
  }
}

class ArrowInputs(party: Snake) extends KeyboardInputs(party) {
  // can change to WASD or alternatively make new cases to allow both inputs
  override val LEFT: String = "A"
  override val RIGHT: String = "Right"
  override val UP: String = "Up"
  override val DOWN: String = "Down"
}
