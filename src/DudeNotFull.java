package src;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DudeNotFull extends Dude implements Transform {
    int resourceCount;


    public DudeNotFull(String id, Point position, List<PImage> images, int resourceCount, int resourceLimit, double animationPeriod, double actionPeriod) {
        super(id, position, images, resourceLimit, animationPeriod, actionPeriod);
        this.resourceCount = resourceCount;
    }

    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.position.adjacent(target.position)) {
            //System.out.println("DudeNotFull adjacent to Tree");
            this.resourceCount += 1;
            ((Plant)target).health--;
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.position);

            if (!this.position.equals(nextPos)) {
                //System.out.println("DudeNotFull move once");
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
        Optional<Entity> target = world.findNearest(this.position, new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        Optional<Entity> monster = world.findNearest(this.position, new ArrayList<>(List.of(Monster.class)));
        if (monster.isPresent()){
            this.transformScared(world, scheduler, imageStore);
        }

        if (target.isEmpty() || !this.moveTo(world, target.get(), scheduler) || !this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    @Override
    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        //dudeNotFull to dudeFull
        if (this.resourceCount >= this.resourceLimit) {
            //System.out.println("NotFull -> Full");
            DudeFull dude = new DudeFull(this.id, this.position, this.images, this.resourceLimit, this.getAnimationPeriod(), this.actionPeriod);

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean transformScared(WorldModel world, EventScheduler scheduler, ImageStore imageStore){
        DudeScared dude = new DudeScared(this.id, this.position, imageStore.getImageList("scared"), this.resourceLimit, this.getAnimationPeriod(), this.actionPeriod * 0.5);

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
        return true;
    }
}
