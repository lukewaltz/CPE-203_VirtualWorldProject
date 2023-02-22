package src;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Monster extends Search{

    private static final PathingStrategy Monster_PATHING = new AStarPathingStrategy();
    //private static final PathingStrategy Monster_PATHING = new SingleStepPathingStrategy();


    public Monster(String id, Point position, List<PImage> images, double animationPeriod, double actionPeriod) {
        super(id, position, images, animationPeriod, actionPeriod, Monster_PATHING);
    }

    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {
        eventScheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getEntityActionPeriod());
        eventScheduler.scheduleEvent(this, new AnimationAction(this, 0), this.getAnimationPeriod());
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        //GET MONSTER TARGET
        Optional<Entity> monsterTarget = world.findNearest(this.position, new ArrayList<>(List.of(Dude.class)));

        if (monsterTarget.isPresent()) {
            Point tgtPos = monsterTarget.get().position;

            if (this.moveTo(world, monsterTarget.get(), scheduler)) {

                //System.out.println("DUDE DEAD");
            }
        }

        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
    }


//    @Override
//    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//
//            world.removeEntity(scheduler, target);
//            return true;
//
//        } else {
//            //Optional<Entity> obstacleArr = world.findNearest(this.position, new ArrayList<>(List.of(Obstacle.class)));
//            //if (obstacleArr.isPresent()) {
//                //Point nearestObstacle = obstacleArr.get().position;
//
//                //if (this.position.adjacent(nearestObstacle)) {
//
//                    //REMOVE THIS MONSTER IF HE CROSSES WATER
//                    //world.removeEntity(scheduler, this);
//
//               // }
//            //}
//
//            Point nextPos = this.nextPosition(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        Optional<Entity> fairys = world.findNearest(this.position, new ArrayList<>(List.of(Fairy.class)));

        if (fairys.isPresent()) {
            Point tgtPos = fairys.get().position;
            if (this.position.adjacent(tgtPos)){
                //System.out.println("Fairy goated on god");
                world.removeEntity(scheduler, this);
            }
        }
        if (this.position.adjacent(target.position)) {
            //System.out.println("Monster adjacent to Dude");
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
