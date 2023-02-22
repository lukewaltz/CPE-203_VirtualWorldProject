package src;

import java.util.*;

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler {
    private final PriorityQueue<Event> eventQueue;
    private final Map<Entity, List<Event>> pendingEvents;
    public double currentTime;

    public EventScheduler() {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.currentTime = 0;
    }

    public void updateOnTime(double time) {
        double stopTime = this.currentTime + time;
        while (!this.eventQueue.isEmpty() && this.eventQueue.peek().time <= stopTime) {
            Event next = this.eventQueue.poll();
            removePendingEvent(next);
            this.currentTime = next.time;
            next.action.executeAction(this);
        }
        this.currentTime = stopTime;
    }

    private void removePendingEvent(Event event) {
        List<Event> pending = this.pendingEvents.get(event.entity);

        if (pending != null) {
            pending.remove(event);
        }
    }

    public void unscheduleAllEvents(Entity entity) {
        List<Event> pending = this.pendingEvents.remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                this.eventQueue.remove(event);
            }
        }
    }

    public void scheduleEvent(Entity entity, Action action, double afterPeriod) {
        double time = this.currentTime + afterPeriod;

        Event event = new Event(action, time, entity);

        this.eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList<>());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }

//    public void scheduleActions(Entity entity, WorldModel world, ImageStore imageStore) {
//        switch (entity.getEntityKind()) {
//            case DUDE_FULL:
//                this.scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getEntityActionPeriod());
//                this.scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
//                break;
//
//            case DUDE_NOT_FULL:
//                this.scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getEntityActionPeriod());
//                this.scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
//                break;
//
//            case OBSTACLE:
//                this.scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
//                break;
//
//            case FAIRY:
//                this.scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getEntityActionPeriod());
//                this.scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
//                break;
//
//            case SAPLING:
//                this.scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getEntityActionPeriod());
//                this.scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
//                break;
//
//            case TREE:
//                this.scheduleEvent(entity, entity.createActivityAction(world, imageStore), entity.getEntityActionPeriod());
//                this.scheduleEvent(entity, entity.createAnimationAction(0), entity.getAnimationPeriod());
//                break;
//
//            default:
//        }
//    }
}
