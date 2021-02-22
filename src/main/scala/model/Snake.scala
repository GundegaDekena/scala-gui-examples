package model

import scalafx.scene.image.Image

import scala.runtime.DoubleRef

class Snake(val name: String, val image: Image) {
  var posX: Double = 0
  var posY: Double = 0
  var speedX: Double = 0
  var speedY: Double = 0
  var speed: Double = 30

  // can also use vectors for describing locations and speed, eg
  var location: PhysicsVector = new PhysicsVector(posX, posY)
  var velocity: PhysicsVector = new PhysicsVector(speedX, speedY)

  def update(dt: Double): Unit = {
    moveSnake(dt)
    // you could do other things here, not only move,
    // like check some additional data and update it's health or battle status
  }

  def moveSnake(dt: Double): Unit = {
    posX += + dt * velocity.x
    posY += + dt * velocity.y
  }

  def leftPressed(): Unit = {
    // println("Left...")
    velocity.x = -speed
  }

  def rightPressed(): Unit = {
    // println("Right...")
    velocity.x = speed
  }

  def upPressed(): Unit = {
    // println("Up...")
    velocity.y = -speed
  }

  def downPressed(): Unit = {
    // println("Down...")
    velocity.y = speed
  }

  def leftReleased(): Unit = {
    // println("Left rel...")
    velocity.x = 0
  }

  def rightReleased(): Unit = {
    // println("Right rel...")
    velocity.x = 0
  }

  def upReleased(): Unit = {
    // println("Up rel...")
    velocity.y = 0
  }

  def downReleased(): Unit = {
    // println("Down rel...")
    velocity.y = 0
  }
}
