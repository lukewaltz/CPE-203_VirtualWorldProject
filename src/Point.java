package src;

/**
 * A simple class representing a location in 2D space.
 */
public class Point implements Comparable{
    public final int x;
    public final int y;


    public Point(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public int distanceSquared(Point p2) {
        int deltaX = this.x - p2.x;
        int deltaY = this.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) ||
                (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point) other).x == this.x &&
                ((Point) other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }

    public int getFCost(Point start, Point end){
        //get G cost -- dist to start
        int xDisStart = Math.abs(this.x - start.x);
        int yDisStart = Math.abs(this.y - start.y);
        int gCost = xDisStart + yDisStart;

        //get H cost -- dist to end
        int xDisEnd = Math.abs(this.x - end.x);
        int yDisEnd = Math.abs(this.y - end.y);
        int hCost = xDisEnd + yDisEnd;

        //this.fCost = gCost + hCost;
        //get F cost -- sum of costs
        return gCost + hCost;
    }

    public int getHCost(Point end){
        //get H cost -- dist to end
        int xDisEnd = Math.abs(this.x - end.x);
        int yDisEnd = Math.abs(this.y - end.y);
        //this.hCost = xDisEnd + yDisEnd;
        return xDisEnd + yDisEnd;
    }

    public int getGCost(Point start){
        //get G cost -- dist to end
        int xDisStart = Math.abs(this.x - start.x);
        int yDisStart = Math.abs(this.y - start.y);
        //this.gCost = xDisStart + yDisStart;
        return xDisStart + yDisStart;
    }

    @Override
    public int compareTo(Object o) {
        Point p = (Point) o;
        if(this.x == p.x && this.y == p.y){return 0;}
        if(this.x + this.y > p.x + p.y){return 1;}
        if(this.x + this.y < p.x + p.y){return -1;}
        else{return -1;}
    }
}
