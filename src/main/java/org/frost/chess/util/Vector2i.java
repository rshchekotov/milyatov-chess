package org.frost.chess.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector2i {
  int x;
  int y;

  /**
   * Determines whether this vector is diagonal in respect
   * to the origin
   *
   * @return true if this vector is diagonal, false otherwise
   */
  public boolean isDiagonal() {
    return Math.abs(x) == Math.abs(y);
  }

  /**
   * Determines whether this vector is horizontal or vertical.
   *
   * @return true if this vector is horizontal or vertical, false otherwise.
   */
  public boolean isNonOrthogonal() {
    return x != 0 && y != 0;
  }

  /**
   * Component-wise Subtraction.
   *
   * @param subtrahend the subtrahend vector.
   * @return the difference of the two vectors.
   */
  public Vector2i sub(Vector2i subtrahend) {
    return new Vector2i(x - subtrahend.x, y - subtrahend.y);
  }

  /**
   * Component-wise Addition.
   *
   * @param operand the operand vector.
   * @return the sum of the two vectors.
   */
  public Vector2i add(Vector2i operand) {
    return new Vector2i(x + operand.x, y + operand.y);
  }

  /**
   * Unit vector in the direction of this vector.
   *
   * @return the unit vector.
   */
  public Vector2i unit() {
    return new Vector2i((int) Math.signum(x), (int) Math.signum(y));
  }

  /**
   * Calculates the distance between the given position and
   * the origin using the Manhattan distance.
   *
   * @return the distance between the given position and the origin
   */
  public int rectilinearLength() {
    return Math.abs(x) + Math.abs(y);
  }

  public Vector2i copy() {
    return new Vector2i(x, y);
  }
}
