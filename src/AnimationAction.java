package src;

public class AnimationAction implements Action{

    private Animateable entity;
    private int repeatCount;

    public AnimationAction(Animateable entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;

    }

    @Override
    public void executeAction(EventScheduler eventScheduler) {
        entity.nextImage();
        if(this.repeatCount != 1){
            eventScheduler.scheduleEvent(this.entity,
                    new AnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    this.entity.getAnimationPeriod());
        }
    }
}
