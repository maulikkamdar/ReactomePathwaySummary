package org.reactome.summary.client;
/**
 * Google Summer of Code 2012 Project
 * Reactome Pathway Summary Visualization
 *
 */
/**
 * Class to represent points on the summary canvas as a vector.
 * @author maulik
 * Modified from Google Canvas Demo Vector Class.
 * http://code.google.com/p/gwtcanvasdemo/source/browse/trunk/src/com/google/gwt/gwtcanvasdemo/client/Vector.java
 *
 * Creates a vector from the specified X & Y coordinates
 */

public class Vector {
  public double x, y;
  
  public Vector() {
    this(0, 0);
  }
  
  /**
   * 
   * @param x The X coordinate
   * @param y The Y coordinate
   */
  
  public Vector(double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public Vector(Vector v) {
    this(v.x, v.y);
  }
  
  public void add(double x, double y) {
    this.x += x;
    this.y += y;
  }
  
  public void add(Vector v) {
    add(v.x, v.y);
  }
  
  public void sub(Vector v) {
    sub(v.x, v.y);
  }
  
  public void sub(double x, double y) {
    this.x -= x;
    this.y -= y;
  }
  
  public void mult(double x, double y) {
    this.x *= x;
    this.y *= y;
  }
  
  public void mult(Vector v) {
    mult(v.x, v.y);
  }
  
  public void mult(double c) {
    mult(c, c);
  }
  
  public double mag() {
    if (x == 0 && y == 0) {
      return 0;
    } else {
      return Math.sqrt(x * x + y * y);
    }
  }
  
  public double magSquared() {
    return x * x + y * y;
  }
  
  public void set(Vector v) {
    x = v.x;
    y = v.y;
  }
  
  public static Vector sub(Vector a, Vector b) {
    return new Vector(a.x - b.x, a.y - b.y);
  }
  
  public static Vector mult(Vector v, double c) {
    return new Vector(v.x * c, v.y * c);
  }
}
