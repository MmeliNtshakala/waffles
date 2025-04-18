package za.co.wethinkcode.botworld.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest
{
    @Test
    void topLeftIsAlwaysLessThanBottomRight(){
        Coord topLeft = new Coord(5, 5);
        Coord bottomRight = new Coord(3, 3);

        Rectangle rect = new Rectangle(topLeft, bottomRight);

        assertEquals(3, rect.left());
        assertEquals(3, rect.top());
        assertEquals(5, rect.right());
        assertEquals(5, rect.bottom());
    }

    @Test
    void createRectArea_singlePointAreaIsOK(){
        Coord p1 = new Coord( 10, 10 );
        Coord p2 = new Coord( 10, 10 );
        new Rectangle( p1, p2 );
    }

    @Test
    void whatIsInsideTheRectangle(){
        Rectangle r = new Rectangle( 0, 0, 10, 10 );
        assertTrue( r.contains( new Coord( 5, 5 )));
        assertTrue( r.contains( new Coord( 0, 0 )));
        assertTrue( r.contains( new Coord( 10, 10 )));
        assertFalse( r.contains( new Coord( -1, 5 )));
        assertFalse( r.contains( new Coord( 5, -1 )));
        assertFalse( r.contains( new Coord( 11, 5 )));
        assertFalse( r.contains( new Coord( 5, 11 )));
    }
}
