package src;

public class ActivityAction implements Action {

    private Actionable entity;
    private WorldModel world;
    private ImageStore imageStore;

    public ActivityAction(Actionable entity, WorldModel world, ImageStore imageStore){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    @Override
    public void executeAction(EventScheduler eventScheduler) {
        entity.executeActivity(this.world, this.imageStore, eventScheduler);
    }

}
