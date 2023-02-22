package src;

public interface Movable {

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);

    public abstract Point nextPosition(WorldModel world, Point destPos);
}
