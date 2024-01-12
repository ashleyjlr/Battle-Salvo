package cs3500.pa04.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Represents a coordinate on a 2D grid.
 *
 * @param xvalue The X value of the coordinate.
 * @param yvalue The Y value of the coordinate.
 */
public record Coordinate(
    @JsonProperty("x") int xvalue,
    @JsonProperty("y") int yvalue) {


  /**
   * Instantiates a new coordinate.
   *
   * @param xvalue the x value
   * @param yvalue the y value
   */
  public Coordinate {

  }

  /**
   * Determines if a given obj and this coordinate are equal based on their values.
   *
   * @param obj the object this coordinate is being compared to
   * @return whether the given object and this coordinate are equal
   */
  @Override
  public boolean equals(Object obj) {
    boolean result = false;
    if (obj != null) {
      if (this.getClass() == obj.getClass()) {
        Coordinate other = (Coordinate) obj;
        result = (this.xvalue == other.xvalue) && (this.yvalue == other.yvalue);
      }
    }
    return result;
  }

  /**
   * Gets the x value of this coordinate.
   *
   * @return the x value
   */
  @Override
  public int xvalue() {
    return this.xvalue;
  }

  /**
   * Gets the y value of this coordinate.
   *
   * @return the y value
   */
  @Override
  public int yvalue() {
    return this.yvalue;
  }

  /**
   * Determines the minimum value between the x value and y value.
   *
   * @return the int representing the minimum value of this coordinate
   */
  public int minimum() {
    return Math.min(this.yvalue, this.xvalue);
  }


  /**
   * Generates the hash code for this coordinate based on the x and y values.
   *
   * @return the int representing the hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.xvalue, this.yvalue);
  }
}
