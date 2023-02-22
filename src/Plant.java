package src;

import processing.core.PImage;

import java.util.List;

public abstract class Plant extends Actionable {

    protected int health;

    public Plant(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }

    public int getEntityHealth(){return this.health;}

    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {

    }

    public abstract boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
