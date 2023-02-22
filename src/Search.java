package src;

import processing.core.PImage;

import java.util.List;

public abstract class Search extends Actionable implements Movable{

    private PathingStrategy strategy;

    public Search(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, PathingStrategy strategy) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.strategy = strategy;
    }
    @Override
    public Point nextPosition(WorldModel world, Point destPos) {
        List<Point> path = strategy.computePath(this.getPosition(), destPos, p -> (world.withinBounds(p) && !world.isOccupied(p)), this::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        if (path.size() == 0){
            return this.getPosition();
        } else {
            return path.get(0);
        }
    }

    private boolean adjacent(Point p1, Point p2) {
            return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
                    (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }
}
