package src;

import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public class Entity {
    //instance variables
    final String id;
    Point position;
    final List<PImage> images;
    int imageIndex;
//    private final int resourceLimit;
//    int resourceCount;
//    private final double actionPeriod;
//    private final double animationPeriod;
//    int health;
//    private final int healthLimit;

    //getters
    public String getEntityID(){return this.id;}
//    public int getEntityHealth(){return this.health;}
//    public double getEntityActionPeriod(){return this.actionPeriod;}
    public int getEntityImageIndex(){return this.imageIndex;}
    public Point getPosition(){return this.position;}
    public void setEntityPosition(Point pos){this.position = pos;}

    //constructor
    public Entity(
            String id,
            Point position,
            List<PImage> images
//            int resourceLimit,
//            int resourceCount,
//            double actionPeriod,
//            double animationPeriod,
//            int health,
//            int healthLimit
    ) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
//        this.resourceLimit = resourceLimit;
//        this.resourceCount = resourceCount;
//        this.actionPeriod = actionPeriod;
//        this.animationPeriod = animationPeriod;
//        this.health = health;
//        this.healthLimit = healthLimit;
    }

    //methods

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }
    public  PImage getCurrentImage() {
        return this.images.get(this.imageIndex % this.images.size());
    }


//    public Point nextPositionDude(WorldModel world, Point destPos) {
//        int horiz = Integer.signum(destPos.x - this.position.x);
//        Point newPos = new Point(this.position.x + horiz, this.position.y);
//
//        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//            int vert = Integer.signum(destPos.y - this.position.y);
//            newPos = new Point(this.position.x, this.position.y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
//                newPos = this.position;
//            }
//        }
//
//        return newPos;
//    }

//    public Point nextPositionFairy(WorldModel world, Point destPos) {
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

//    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//            return true;
//        } else {
//            Point nextPos = this.nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//            this.resourceCount += 1;
//            target.health--;
//            return true;
//        } else {
//            Point nextPos = this.nextPositionDude(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
//        if (this.position.adjacent(target.position)) {
//            world.removeEntity(scheduler, target);
//            return true;
//        } else {
//            Point nextPos = this.nextPositionFairy(world, target.position);
//
//            if (!this.position.equals(nextPos)) {
//                world.moveEntity(scheduler, this, nextPos);
//            }
//            return false;
//        }
//    }

//    public boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        //sapling to stump
//        if (this.health <= 0) {
//            Entity stump = createStump(WorldModel.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(WorldModel.STUMP_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            world.addEntity(stump);
//
//            return true;
//        }
//        //sapling to tree
//        else if (this.health >= this.healthLimit) {
//            Entity tree = createTree(WorldModel.TREE_KEY + "_" + this.id, this.position, Functions.getNumFromRange(WorldModel.TREE_ACTION_MAX, WorldModel.TREE_ACTION_MIN), Functions.getNumFromRange(WorldModel.TREE_ANIMATION_MAX, WorldModel.TREE_ANIMATION_MIN), Functions.getIntFromRange(WorldModel.TREE_HEALTH_MAX, WorldModel.TREE_HEALTH_MIN), imageStore.getImageList(WorldModel.TREE_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            world.addEntity(tree);
//            scheduler.scheduleActions(tree, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean transformTree(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        //tree to stump
//        if (this.health <= 0) {
//            Entity stump = createStump(WorldModel.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(WorldModel.STUMP_KEY));
//
//            world.removeEntity(scheduler, this);
//
//            world.addEntity(stump);
//
//            return true;
//        }
//
//        return false;
//    }

//    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        //if tree -> tree to stump
//        if (this.kind == EntityKind.TREE) {
//            return this.transformTree(world, scheduler, imageStore);
//         //if sapling -> sapling to stump OR sapling to tree
//        } else if (this.kind == EntityKind.SAPLING) {
//            return this.transformSapling(world, scheduler, imageStore);
//        } else {
//            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
//        }
//    }

//    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        //dudeFull to dudeNotFull
//        Entity dude = createDudeNotFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);
//
//        world.removeEntity(scheduler, this);
//
//        world.addEntity(dude);
//        scheduler.scheduleActions(dude, world, imageStore);
//    }

//    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
//        //dudeNotFull to dudeFull
//        if (this.resourceCount >= this.resourceLimit) {
//            Entity dude = createDudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);
//
//            world.removeEntity(scheduler, this);
//            scheduler.unscheduleAllEvents(this);
//
//            world.addEntity(dude);
//            scheduler.scheduleActions(dude, world, imageStore);
//
//            return true;
//        }
//
//        return false;
//    }

//    public void executeDudeFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> fullTarget = world.findNearest(this.position, new ArrayList<>(List.of(EntityKind.HOUSE)));
//
//        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
//            this.transformFull(world, scheduler, imageStore);
//        } else {
//            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        }
//    }

//    public void executeDudeNotFullActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> target = world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));
//
//        if (target.isEmpty() || !this.moveToNotFull(world, target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        }
//    }

//    public void executeFairyActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        Optional<Entity> fairyTarget = world.findNearest(this.position, new ArrayList<>(List.of(EntityKind.STUMP)));
//
//        if (fairyTarget.isPresent()) {
//            Point tgtPos = fairyTarget.get().position;
//
//            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {
//
//                Entity sapling = createSapling(WorldModel.SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, imageStore.getImageList(WorldModel.SAPLING_KEY));
//
//                world.addEntity(sapling);
//                scheduler.scheduleActions(sapling, world, imageStore);
//            }
//        }
//
//        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//    }

//    public void executeTreeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//
//        if (!this.transformPlant(world, scheduler, imageStore)) {
//
//            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        }
//    }

//    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        this.health++;
//        if (!this.transformPlant(world, scheduler, imageStore)) {
//            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
//        }
//    }

//    public static House createHouse(String id, Point position, List<PImage> images) {
//        return new House(id, position, images);
//    }
//
//    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
//        return new Obstacle(id, position, images, animationPeriod);
//    }
//
//    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
//        return new Tree(id, position, images, actionPeriod, animationPeriod, health);
//    }

//    public static Stump createStump(String id, Point position, List<PImage> images) {
//        return new Stump(id, position, images);
//    }

//    // health starts at 0 and builds up until ready to convert to Tree
//    public static Sapling createSapling(String id, Point position, List<PImage> images) {
//        return new Sapling(id, position, images, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD, WorldModel.SAPLING_ACTION_ANIMATION_PERIOD,0, WorldModel.SAPLING_HEALTH_LIMIT);
//    }

//    public static Fairy createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
//        return new Fairy(id, position, images, animationPeriod, actionPeriod);
//    }

//    // need resource count, though it always starts at 0
//    public static DudeNotFull createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit, List<PImage> images) {
//        return new DudeNotFull(id, position, images, resourceCount, resourceLimit, animationPeriod, actionPeriod);
//    }
//
//    // don't technically need resource count ... full
//    public static DudeFull createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
//        return new DudeFull(id, position, images, resourceLimit, animationPeriod, actionPeriod);
//    }




    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }
}
