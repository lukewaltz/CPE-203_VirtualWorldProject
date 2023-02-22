package src;

import processing.core.PImage;

import java.util.List;

public class Dude extends Search {
    final int resourceLimit;
    private static final PathingStrategy Dude_PATHING = new AStarPathingStrategy();
    //private static final PathingStrategy Dude_PATHING = new SingleStepPathingStrategy();

    public Dude(String id, Point position, List<PImage> images, int resourceLimit, double animationPeriod, double actionPeriod) {
        super(id, position, images, animationPeriod, actionPeriod, Dude_PATHING);
        this.resourceLimit = resourceLimit;
    }

    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {

    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        return false;
    }
/*
    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.position.x);
        Point newPos = new Point(this.position.x + horiz, this.position.y);
        if (horiz == 0 || world.isOccupied(newPos) && !((world.getOccupancyCell(newPos)) instanceof Stump)) {

            int vert = Integer.signum(destPos.y - this.position.y);
            newPos = new Point(this.position.x, this.position.y + vert);

            if (vert == 0 || world.isOccupied(newPos) && !((world.getOccupancyCell(newPos)) instanceof Stump)) {
                newPos = this.position;
            }
        }
        System.out.println(world.getOccupancyCell(newPos));
        return newPos;
    }
 */
}
