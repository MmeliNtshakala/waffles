package za.co.wethinkcode.botworld.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import static com.google.common.base.Preconditions.checkNotNull;

public class World
{
    public static final int WORLD_MAX_X = 10;
    public static final int WORLD_MAX_Y = 10;

    protected final Coord extent;

    private final BiMap<ExplorerBot, Coord> robots = HashBiMap.create();

    private World(){
        extent = new Coord( 0, 0 );
    }

    public World( int width, int height ){
        if (width <= 0) throw new IllegalArgumentException("World width must be greater than 0");
        if (height <= 0) throw new IllegalArgumentException("World height must be greater than 0");
        this.extent = new Coord(width, height);

    }

    public int botCount(){
        return robots.size();
    }

//    public ExplorerBot add( ExplorerBot aBot ){
//        if( robots.containsKey( aBot ))
//            throw new IllegalArgumentException( aBot + " already added to this world" );
//        robots.put( checkNotNull( aBot ), midPoint() );
//        return aBot;
//    }

    /**
     * Convenience method for tests
     */
    ExplorerBot add( ExplorerBot aBot, int x, int y ){
        return add( aBot, new Coord( x, y ));
    }

    public ExplorerBot add( ExplorerBot aBot, Coord position ){
        if( robots.containsKey( aBot ))
            throw new IllegalArgumentException( aBot + " already added to this world" );
        return safelyAdd( aBot, position );
    }

    /**
     * Answer the position of a robot. If the given bot does not exist in this world,
     * return {@literal null}.
     *
     * @param aBot a non-null bot. Send me {@literal null}} and I will throw a NullPointerException.
     * @return The position of the bot if it exists, or {@literal null} if it does not.
     */
    public Coord locationOf( ExplorerBot aBot ){
        if( aBot == null ) throw new NullPointerException();
        return robots.get( aBot );
    }

    /**
     * Move a bot to a new position if allowed. Note that this method imposes NO restrictions
     * on the new position of the bot except that the new position must be inside the receiver's
     * extent. If the move is not allowed (or something is {@literal null} when it shouldn't be)
     * I will throw an exception.
     *
     * @param aBot A non-null bot to be moved.
     * @param newPosition A non-null Coord inside the receiver world.
     * @return The new position of the bot if the move was successful.
     * @throws IllegalStateException if the destination position is already occupied be something else.
     */
    public Coord moveBot( ExplorerBot aBot, Coord newPosition )
        throws IllegalStateException
    {
        if( aBot == null ) throw new NullPointerException();
        safelyAdd( aBot, newPosition );
        return newPosition;
    }

    public boolean contains( Coord aPoint ){
        return aPoint.x() >= 0
            && aPoint.y() >= 0
            && aPoint.x() < extent().x()
            && aPoint.y() < extent().y();
    }

    Coord midPoint(){
        return extent().divideBy( 2 );
    }

    Coord extent(){
        return extent;
    }

    int height(){
        return extent().y();
    }

    int width(){
        return extent().x();
    }

    int westEdge() {
        return 0;
    }

    int eastEdge() {
        return width() - 1;
    }

    int northEdge() {
        return 0;
    }

    int southEdge() {
        return height() - 1;
    }

    private ExplorerBot safelyAdd( ExplorerBot aBot, Coord position ){
        if( ! contains( position )) throw new IllegalArgumentException();
        if( robots.containsValue( position ))
            throw new IllegalStateException( "Position " + position + " already occupied by " + objectAt( position ));
        robots.put( checkNotNull( aBot ), position );
        return aBot;
    }

    private ExplorerBot objectAt( Coord position ){
        return position == null
            ? null
            : robots.inverse().get( position );
    }
}
