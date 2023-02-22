package src;

import processing.core.PImage;

import java.util.List;

public abstract class Actionable extends Animateable {

    double actionPeriod;

    public Actionable(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod){
        super(id, position, images, animationPeriod);
        this.actionPeriod = actionPeriod;
    }

    public abstract void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore);

    public double getEntityActionPeriod(){return this.actionPeriod;}

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

}
