package src;

public class Node implements Comparable {

    public Point pt;
    public int fCost;
    public int hCost;
    public int gCost;

    public Node(Point pt, int fCost, int hCost, int gCost) {
        this.pt = pt;
        this.fCost = fCost;
        this.hCost = hCost;
        this.gCost = gCost;
    }

    public int getFCost(Point start, Point end){
        //get G cost -- dist to start
        int xDisStart = Math.abs(this.pt.x - start.x);
        int yDisStart = Math.abs(this.pt.y - start.y);
        int gCost = xDisStart + yDisStart;

        //get H cost -- dist to end
        int xDisEnd = Math.abs(this.pt.x - end.x);
        int yDisEnd = Math.abs(this.pt.y - end.y);
        int hCost = xDisEnd + yDisEnd;

        this.fCost = gCost + hCost;
        //get F cost -- sum of costs
        return gCost + hCost;
    }

    public int getHCost(Point end){
        //get H cost -- dist to end
        int xDisEnd = Math.abs(this.pt.x - end.x);
        int yDisEnd = Math.abs(this.pt.y - end.y);
        this.hCost = xDisEnd + yDisEnd;
        return xDisEnd + yDisEnd;
    }

    public int getGCost(Point start){
        //get G cost -- dist to end
        int xDisStart = Math.abs(this.pt.x - start.x);
        int yDisStart = Math.abs(this.pt.y - start.y);
        this.gCost = xDisStart + yDisStart;
        return xDisStart + yDisStart;
    }

    public Point getPoint(){
        return this.pt;
    }

//    //@Override
//    public int compareTo(Node n) {
//        //Node n = (Node)o;
//
//        if(this.fCost > n.fCost){return 1;}
//        if(this.fCost < n.fCost){return -1;}
//        if(this.hCost > n.hCost){return 1;}
//        if(this.hCost < n.hCost){return -1;}
//        if(this.gCost > n.gCost){return 1;}
//        if(this.gCost < n.gCost){return -1;}
//
//
//        return 0;
//    }


    @Override
    public int compareTo(Object o) {
        return this.compareTo((Node)o);
    }

    public int compareTo(Node n){
        if (this.fCost != n.fCost){
            return this.fCost - n.fCost;

        } else if (this.gCost != n.gCost){
            return this.gCost - n.gCost;

        } else if(this.pt.x != n.pt.x){
            return this.pt.x - n.pt.y;

        } else if(this.pt.y != n.pt.y){
            return this.pt.y - n.pt.y;

        }
        else if (this.hCost != n.hCost){
            return this.hCost - n.hCost;

        } else {
            return 0;
        }
    }
}

