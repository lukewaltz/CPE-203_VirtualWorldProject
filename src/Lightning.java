package src;

import processing.core.PImage;

import java.util.List;

public class Lightning extends Actionable {

    public Lightning(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod){
        super(id, position, images, animationPeriod, actionPeriod);
        //image = imageStore.getImageList(STUMP_KEY)
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getEntityActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, 0), getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        world.removeEntity(scheduler, this);
    }
}
