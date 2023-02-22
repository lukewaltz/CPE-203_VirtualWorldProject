package src;

import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Sapling extends Plant implements Transform {
    private final int healthLimit;

    public Sapling(String id, Point position, List<PImage> images, int healthLimit) {
        super(id, position, images, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD,0);
        this.healthLimit = healthLimit;
    }

    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {
        eventScheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getEntityActionPeriod());
        eventScheduler.scheduleEvent(this, new AnimationAction(this, 0), this.getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
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
        if (this.health <= 0) {
            Stump stump = new Stump(WorldModel.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(WorldModel.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }
        //sapling to tree
        else if (this.health >= this.healthLimit) {
            Tree tree = new Tree(WorldModel.TREE_KEY + "_" + this.id, this.position,imageStore.getImageList(WorldModel.TREE_KEY),
                    getNumFromRange(WorldModel.TREE_ACTION_MAX, WorldModel.TREE_ACTION_MIN),
                    getNumFromRange(WorldModel.TREE_ANIMATION_MAX, WorldModel.TREE_ANIMATION_MIN),
                    getIntFromRange(WorldModel.TREE_HEALTH_MAX, WorldModel.TREE_HEALTH_MIN)
                    );

            world.removeEntity(scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    public static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }
}
