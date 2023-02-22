package src;

import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class VirtualWorld extends PApplet {
    private static String[] ARGS;

    private static final int VIEW_WIDTH = 1280;
    private static final int VIEW_HEIGHT = 960;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private String loadFile = "world.sav";
    private long startTimeMillis = 0;
    private double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        parseCommandLine(ARGS);
        loadImages(IMAGE_LIST_FILE_NAME);
        loadWorld(loadFile, this.imageStore);

        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler();
        this.startTimeMillis = System.currentTimeMillis();
        this.scheduleActions(world, scheduler, imageStore);
    }

    public void draw() {
        double appTime = (System.currentTimeMillis() - startTimeMillis) * 0.001;
        double frameTime = (appTime - scheduler.currentTime)/timeScale;
        this.update(frameTime);
        view.drawViewport();
    }

    public void update(double frameTime){
        scheduler.updateOnTime(frameTime);
    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
//    public void mousePressed() {
//        Point pressed = mouseToPoint();
//        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);
//
//        Optional<Entity> entityOptional = world.getOccupant(pressed);
//        if (entityOptional.isPresent()) {
//            Entity entity = entityOptional.get();
//            //System.out.println(entity.getEntityID() + ": " + "Bad Question" + " : " + entity.getEntityHealth());
//        }
//
//    }
    //USE THIS TO SPAWN MONSTER
    public void mousePressed() {
        Point pressed = mouseToPoint();
        //System.out.println("CLICK! " + pressed.x + ", " + pressed.y);

        if(world.isOccupied(pressed)){
            System.out.println("CANT SPAWN HERE");
            return;
        }

        Monster monster = new Monster("monster", pressed, imageStore.getImageList("monster"), 0.2, 0.4);
        world.addEntity(monster);
        monster.scheduleActions(scheduler, world, imageStore);

        for(int i = pressed.y - 1; i >= 0; i--){
            Point curPt = new Point(pressed.x, i);
            //Optional<Entity> ent = world.getOccupant(curPt);

            if (!world.isOccupied(curPt)) {
                Lightning lightning = new Lightning("lightning", curPt, imageStore.getImageList("lightning"), 0.02, 1);
                world.addEntity(lightning);
                lightning.scheduleActions(scheduler, world, imageStore);
            }

        }

        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
        }

    }

    public void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        for (Entity entity : world.entities) {
            if (entity instanceof Animateable){((Animateable)entity).scheduleActions(scheduler, world, imageStore);}
        }
    }

    private Point mouseToPoint() {
        return view.viewport.viewportToWorld(mouseX / TILE_WIDTH, mouseY / TILE_HEIGHT);
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP -> dy -= 1;
                case DOWN -> dy += 1;
                case LEFT -> dx -= 1;
                case RIGHT -> dx += 1;
            }
            view.viewport.shiftView(dx, dy, view);
        }
        //spawn DUDE at MOUSEPOINT
        if (key == ' '){
            DudeFull dude = new DudeFull("dudeNotFull", mouseToPoint(), imageStore.getImageList("dude"),  4,  0.180, 0.787);
            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);
        }

        //spawn HOUSE at MOUSEPOINT
        if (key == 'h'){
            House house = new House("house", mouseToPoint(), imageStore.getImageList("house"));
            world.addEntity(house);
        }

        //DELETE ENTITY at MOUSEPOINT
        if (key == 'e'){
            world.removeEntityAt(mouseToPoint());
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList(DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    public void loadImages(String filename) {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        try {
            Scanner in = new Scanner(new File(filename));
            Background.loadImages(in, imageStore,this);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadWorld(String file, ImageStore imageStore) {
        this.world = new WorldModel();
        try {
            Scanner in = new Scanner(new File(file));
            world.load(in, imageStore, createDefaultBackground(imageStore));
        } catch (FileNotFoundException e) {
            Scanner in = new Scanner(file);
            world.load(in, imageStore, createDefaultBackground(imageStore));
        }
    }

    public void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG -> timeScale = Math.min(FAST_SCALE, timeScale);
                case FASTER_FLAG -> timeScale = Math.min(FASTER_SCALE, timeScale);
                case FASTEST_FLAG -> timeScale = Math.min(FASTEST_SCALE, timeScale);
                default -> loadFile = arg;
            }
        }
    }

    public static void main(String[] args) {
        VirtualWorld.ARGS = args;
        PApplet.main(VirtualWorld.class);
    }

    public static List<String> headlessMain(String[] args, double lifetime){
        VirtualWorld.ARGS = args;

        VirtualWorld virtualWorld = new VirtualWorld();
        virtualWorld.setup();
        virtualWorld.update(lifetime);

        return virtualWorld.world.log();
    }
}
