package src;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DudeFull extends Dude implements Transform {


    public DudeFull(String id, Point position, List<PImage> images, int resourceLimit, double animationPeriod, double actionPeriod) {
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

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.position, new ArrayList<>(List.of(House.class)));

        Optional<Entity> monster = world.findNearest(this.position, new ArrayList<>(List.of(Monster.class)));
        if (monster.isPresent()){
            this.transformScared(world, scheduler, imageStore);
        }


        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)) {
            this.transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }


    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        //dudeFull to dudeNotFull
        //System.out.println("Full -> NotFull");

        DudeNotFull dude = new DudeNotFull(this.id, this.position, this.images, 0, this.resourceLimit, this.getAnimationPeriod(), this.actionPeriod);

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }

    public boolean transformScared(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        DudeScared dude = new DudeScared(this.id, this.position, imageStore.getImageList("scared"), this.resourceLimit, this.getAnimationPeriod(), this.actionPeriod * 0.5);

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }
}
