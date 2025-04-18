package za.co.wethinkcode.botworld.model;

import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static za.co.wethinkcode.botworld.model.Heading.*;

public class ExplorerBotTest {

    private  World world;
    @BeforeEach void setup(){
        world = new World(3, 3);
    }

    private ExplorerBot createBotAt(int x, int y, float fuel) {
        return new ExplorerBot(world, x, y, fuel);
    }

    private ExplorerBot createBotAt(int x, int y){
        return createBotAt(x, y, 100f);
    }
    @Test
    void move_north_toANewPositionInsideTheWorld() {
        ExplorerBot bot =  createBotAt(1, 1);
        bot.turnTo(North);
        bot.move();
        assertEquals(new Coord(1, 0), world.locationOf(bot));
    }

    @Test
    void move_south_toANewPositionInsideTheWorld() {
        ExplorerBot bot = createBotAt(1, 1);
        bot.turnTo(South);
        bot.move();
        assertEquals(new Coord(1, 2), world.locationOf(bot));
    }

    @Test
    void move_west_toANewPositionInsideTheWorld() {
        ExplorerBot bot = createBotAt(1, 1);
        bot.turnTo(West);
        bot.move();
        assertEquals(new Coord(0, 1), world.locationOf(bot));
    }

    @Test
    void move_east_toANewPositionInsideTheWorld() {
        ExplorerBot bot = createBotAt(1, 0);
        bot.turnTo(East);
        bot.move();
        assertEquals(new Coord(2, 0), world.locationOf(bot));
    }

    @Test
    void move_north_toANewPositionOutsideTheWorld() {
        ExplorerBot bot = createBotAt(1, 0);
        bot.turnTo(North);
        bot.move();
        assertEquals(new Coord(1, 0), world.locationOf(bot));
    }

    @Test
    void move_south_toANewPositionOutsideTheWorld() {
        ExplorerBot bot = createBotAt(1, 2);
        bot.turnTo(South);
        bot.move();
        assertEquals(new Coord(1, 2), world.locationOf(bot));
    }

    @Test
    void move_west_toANewPositionOutsideTheWorld() {
        ExplorerBot bot = createBotAt(0, 1);
        bot.turnTo(West);
        bot.move();
        assertEquals(new Coord(0, 1), world.locationOf(bot));
    }

    @Test
    void move_east_toANewPositionOutsideTheWorld() {
        ExplorerBot bot = createBotAt(2, 1);
        bot.turnTo(East);
        bot.move();
        assertEquals(new Coord(2, 1), world.locationOf(bot));
    }

    @Test
    void consume_fuel() {
        ExplorerBot bot = createBotAt(1, 1);
        bot.setFuelConsumptionPerKlik(5f);
        float initialFuel = bot.getFuelLevel();
        bot.turnTo(North);
        bot.move();
        assertEquals(initialFuel - 5f, bot.getFuelLevel(), 0.001);
    }

    @Test void insufficient_fuel(){
        ExplorerBot bot = createBotAt(1, 0, 50.0f);
        bot.setFuelConsumptionPerKlik(50.0f);
        bot.move();
        bot.move();
        assertEquals(new Coord(1, 0), world.locationOf(bot));
    }

    @Test void fuel_cap() {
        ExplorerBot bot = createBotAt(1, 1, 95f);
        bot.addFuel(10f);
        assertEquals(100f, bot.getFuelLevel(), 0.001);
    }

    @Test void negative_fuel() {
        ExplorerBot bot = createBotAt(1, 1, 50f);
        bot.addFuel(-10f);
        assertEquals(50f, bot.getFuelLevel(), 0.001);
    }
    @Test void fuelLevel_is_clamped() {
        ExplorerBot bot = createBotAt(1, 1, 150f);
        bot.setFuelConsumptionPerKlik(7.5f);
        assertEquals(100f, bot.getFuelLevel(), 0.001);
    }
    @Test void duel_can_be_set_and_retrieved() {
        ExplorerBot bot = createBotAt(1, 1);
        bot.setFuelConsumptionPerKlik(7.5f);
        assertEquals(7.5f, bot.fuelConsumptionPerKlik(), 0.001);
    }
}