package src;

import processing.core.PImage;

import java.util.List;

public class Tree extends Plant implements Transform {

    public Tree(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod, int health) {
        super(id, position, images, animationPeriod, actionPeriod, health);
    }


    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {
        eventScheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getEntityActionPeriod());
        eventScheduler.scheduleEvent(this, new AnimationAction(this, 0), this.getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!this.transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    @Override
    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        return this.transform(world, scheduler, imageStore);
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        //tree to stump
        if (this.health <= 0) {
            //System.out.println("Tree -> Stump");
            Stump stump = new Stump(WorldModel.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(WorldModel.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }
}
