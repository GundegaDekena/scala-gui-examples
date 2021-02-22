package model

/**
 *
 * @param x magnitude of the vector in the x direction (horizontal)
 * @param y magnitude of the vector in the y direction (vertical)
 */
class PhysicsVector(var x: Double = 0.0, var y: Double = 0.0) {

  override def toString: String = {
    "(" + x + ", " + y + ")"
  }

}
