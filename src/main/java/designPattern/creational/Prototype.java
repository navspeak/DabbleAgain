package designPattern.creational;

public abstract class Prototype implements  Cloneable {
    // cloneable


  public abstract Prototype protoType() throws CloneNotSupportedException;
}
