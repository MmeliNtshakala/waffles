package za.co.wethinkcode.botworld.model;

import java.util.Objects;

/**
 * I encapsulate the notion of a single point - a coordinate - such as might be used to
 * represent a position inside the World (or even outside it; I know nothing about Worlds,
 * and I shouldn't have any such knowledge) or, indeed, any pair of values (such as a
 * <width,height> concept.)
 * <p/>
 * I provide some convenience methods for working with pairs-of-numbers, such as incrementing/
 * decrementing one or the other number, finding multipying/dividing both by another number.
 * <p/>
 * My instances are immutable.
 */
public class Coord {
    // declare instance variables here!
    private final int x;
    private final int y;

    /**
     * The one and only constructor for initialising new Coords.
     * @param xValue The x value of the Coord
     * @param yValue The y value of the Coord
     */
    public Coord( int xValue, int yValue ){
        this.x = xValue;
        this.y = yValue;
    }

    public int x(){
        return x;
    }

    public int y(){
        return y;
    }

    public Coord incrementX(){
        return new Coord(x + 1, y);
    }

    public Coord decrementX(){
        return new Coord( x - 1, y);
    }

    public Coord incrementY(){
        return new Coord(x, y + 1);
    }

    public Coord decrementY(){
        return new Coord(x, y - 1);
    }

    public Coord add( Coord aPoint ){
        return new Coord(x+ aPoint.x(), y + aPoint.y());
    }

    public Coord minus( Coord aPoint ){
        return new Coord(x - aPoint.x(), y - aPoint.y());
    }

    public Coord divideBy( int n ){
        if (n == 0){
            throw new ArithmeticException("Cannot devide by zero");
        }
        return new Coord(x / n, y / n);
    }

    public Coord multiplyBy( int n ){
        return new Coord(x * n, y * n);
    }

    // We want to be able to test whether one Coord instance is equal to another,
    // even if they are not actually the same instance. i.e. Do the two instances
    // have the same x and y values?
    //
    // The following method implements such a test, and will be used by the standard
    // libraries if we do things like keep Coords in any of the standard Collection classes.
    //
    // Note that the signature (and return-type) of any such `equals` method is
    // required to be *exactly* as we have declared it here!

    @Override
    public boolean equals( Object obj ){
        if( ! (obj instanceof Coord other) ) return false;
        return x() == other.x()
            && y() == other.y();
    }

    // And, almost always, if we write an `equals` method, we should *also* provide
    // a `hashCode` method, declared as below. We'll discuss the reasons for this later
    // in the course material, or, if you want to jump ahead, there's an explanation at
    // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object)

    @Override
    public int hashCode(){
        return Objects.hash( x(), y() );
    }

    // `toString` is a convenience that is useful in debugging: if we try to print out an object
    // using any of the (many) System.out.print methods, they will call the object's `toString`
    // so it makes sense to provide one that prints the object in a reasonably readable fashion.
    // It is *not* intended for producing user-facing output, though, and should not be used for
    // formatting objects in a user-interface.

    @Override
    public String toString(){
        return "(x=" + x() + ", y=" + y() + ')';
    }
}
