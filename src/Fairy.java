package src;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fairy extends Search{

    private static final PathingStrategy Fairy_PATHING = new AStarPathingStrategy();
    //private static final PathingStrategy Fairy_PATHING = new SingleStepPathingStrategy();

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod) {
        super(id, position, images, animationPeriod, actionPeriod, Fairy_PATHING);
    }

    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {
        eventScheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getEntityActionPeriod());
        eventScheduler.scheduleEvent(this, new AnimationAction(this, 0), this.getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(this.position, new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (this.moveTo(world, fairyTarget.get(), scheduler)) {

                Sapling sapling = new Sapling(WorldModel.SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, imageStore.getImageList(WorldModel.SAPLING_KEY), WorldModel.SAPLING_HEALTH_LIMIT);

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
    }

    //PROBLEM IS THAT TARGET DOESNT CHANGE WHEN HE CHOPS DOWN THE TREE.

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.position.adjacent(target.position)) {
            //System.out.println("Fairy adjacent to Stump");
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.position);

            if (!this.position.equals(nextPos)) {
                //System.out.println("Fairy move once");
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }


//    @Override
//    public Point nextPosition(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

}
