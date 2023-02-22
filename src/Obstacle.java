package src;

import processing.core.PImage;

import java.util.List;

public class Obstacle extends Animateable {

    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod){
        super(id, position, images, animationPeriod);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new AnimationAction(this, 0), getAnimationPeriod());
    }
}
