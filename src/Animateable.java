package src;

import processing.core.PImage;

import java.util.List;

public abstract class Animateable extends Entity{
    private final double animationPeriod;


    public Animateable(String id, Point position, List<PImage> images, double animationPeriod){
        super(id, position, images);
        this.animationPeriod = animationPeriod;
    }

    public double getAnimationPeriod(){return this.animationPeriod;}

    public List<PImage> getEntityImages(){return this.images;}
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);


}
