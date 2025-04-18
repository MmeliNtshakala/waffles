 package za.co.wethinkcode.botworld.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExplorerBot {
    public static final float MAX_FUEL = 100f;
    public static final float MIN_FUEL = 0.0f;
    private World world;
    private Heading heading = Heading.North;
    private Coord position;
    private float fuelLevel;
    private float consumptionPerKlik = 1.0f;

    public ExplorerBot(World world, float x, float y, float fuelLevel){
        this(world, new Coord((int)x, (int)y), fuelLevel, 1.0f);
    }

    public ExplorerBot( World world){
        this(world, MAX_FUEL, 1.0f);
    }
    public ExplorerBot(World world, float fuelLevel, float consumptionPerKlik){
        this(world, new Coord(0, 0), fuelLevel, consumptionPerKlik);
    }
    public ExplorerBot(World world, Coord position, float fuelLevel, float consumptionPerKlik){
        this.world = checkNotNull(world);
        this.position = checkNotNull(position);
        this.fuelLevel = Math.max(MIN_FUEL, Math.min(fuelLevel, MAX_FUEL));
        setFuelConsumptionPerKlik(consumptionPerKlik);
        this.world.add(this, this.position);
    }
    public ExplorerBot(World world, int x, int y, float consumptionPerKlik){
        this(world, new Coord(x, y), MAX_FUEL, consumptionPerKlik);
    }
    /**
     * Turn the receiver bot to a new heading.
     * @param Heading What direction the receiver should face
     */
    /**
     * Answer the receiver bot's current heading.
     * @return a Heading
     */

    public void turnTo(Heading heading1){
        heading = heading1;
    }
    public Heading heading(){
        return heading;
    }

    /**
     * Answer the receiver's current position in the world.
     * @return The receiver's current position.
     */
    public Coord position(){
        return world.locationOf( this );
    }

    /**
     * Move the receiver bot 1 klik in the direction it currently faces but not
     * beyond the edges of the world. If the move would result in the bot ending
     * up in an illegal position, simply ignore the request and don't move.
     */
    public void move(){
        if (fuelLevel < consumptionPerKlik){
            return;
        }
        Coord newPosition = switch( heading() ){
            case North -> position().decrementY();
            case South -> position().incrementY();
            case West -> position().decrementX();
            case East -> position().incrementX();
        };
        if(world.contains(newPosition) ){
            world.moveBot(this, newPosition);
            fuelLevel -= consumptionPerKlik;
            if (fuelLevel < MIN_FUEL){
                fuelLevel = MIN_FUEL;
            }
        }
    }
    public float getFuelLevel(){
        return fuelLevel;
    }

    public void setFuelLevel(float level) {
        this.fuelLevel = Math.max(MIN_FUEL, Math.min(level , MAX_FUEL));
    }

    public void addFuel(float amount){
        if (amount <= 0) return;
        fuelLevel = Math.min(fuelLevel + amount, MAX_FUEL);

    }
    public float fuelConsumptionPerKlik(){
        return  consumptionPerKlik;
    }
    public void setFuelConsumptionPerKlik(float consumption){
        this.consumptionPerKlik = Math.max(0f, consumption);

    }
    @Override
    public String toString(){
        return "[" + ExplorerBot.class.getSimpleName()
            + '@'
            + position() + "]";
    }
}

