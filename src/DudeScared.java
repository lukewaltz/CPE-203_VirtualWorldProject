package src;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DudeScared extends Dude implements Transform{


    public DudeScared(String id, Point position, List<PImage> images, int resourceLimit, double animationPeriod, double actionPeriod) {
        super(id, position, images, resourceLimit, animationPeriod, actionPeriod);
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.position.adjacent(target.position)) {
            //System.out.println("DudeFull adjacent to House");

            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.position);

            if (!this.position.equals(nextPos)) {
                //System.out.println("DudeFull move once");
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    @Override
    public void scheduleActions(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore) {
        eventScheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.getEntityActionPeriod());
        eventScheduler.scheduleEvent(this, new AnimationAction(this, 0), this.getAnimationPeriod());
    }


    //THIS IS WHERE YOU CHOSE TARGET TO MOVE TO
    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        //GET TARGET
        Optional<Entity> scaredTarget = world.findNearest(this.position, new ArrayList<>(List.of(Fairy.class)));

        Optional<Entity> monster = world.findNearest(this.position, new ArrayList<>(List.of(Monster.class)));
        if (monster.isEmpty()){
            this.transform(world, scheduler, imageStore);
        }

        //CONDITIONS FOR TRANSFORM
        if (scaredTarget.isPresent() && this.moveTo(world, scaredTarget.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
            //System.out.println("BODYGUARD");

        } else {

            //CALLS EXECUTE ACTIVITY AGAIN ON EVERY CLOCK TICK
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }


    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        //dudeScared to dudeFull

        DudeFull dude = new DudeFull(this.id, this.position, imageStore.getImageList("dude"), this.resourceLimit, this.getAnimationPeriod(), this.actionPeriod * 2);

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }



}
