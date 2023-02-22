package src;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy{
    @Override
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point,
                                           Stream<Point>> potentialNeighbors) {

        //Store the path as a linked List of Points
        LinkedList<Point> path = new LinkedList<>();

        //store the openList as a Priority Queue of PathNodes
        PriorityQueue<PathNode> openList = new PriorityQueue<>();

        //store the closedList as a HashSet of PathNodes
        Set<PathNode> closedList = new HashSet<>();

        openList.offer(new PathNode(start, 0, Double.MAX_VALUE, null));
        boolean flag = true;

        while (!openList.isEmpty()) {

            //currentNode is chosed based on compareTo defined in PathNode
            PathNode currentNode = openList.peek();
            for (Point adjPoint: potentialNeighbors.apply(currentNode.point()).toList()){

                //if you cant pass through this neighbor, move onto the next one
                if (!canPassThrough.test(adjPoint))
                    continue;

                //if this neighbor is within reach of the end, add it to closeList and remove from openList
                if (withinReach.test(adjPoint,end)) {
                    closedList.add(openList.poll());
                    closedList.add(new PathNode(adjPoint, currentNode.gScore() + 1, 0, currentNode));
                    flag = false;break;
                }

                //if already in the closedList, move onto next Neighbor
                if (closedList.parallelStream().anyMatch(closedListPoint -> closedListPoint.point().equals(adjPoint)))
                    continue;
                Optional<PathNode> adjNode = openList.stream()
                        .filter(openListPoint-> openListPoint.point().equals(adjPoint))
                        .findAny();

                //if the neighbor already has been stored as a value, update its costs.
                if (adjNode.isPresent()){
                    if (adjNode.get().gScore() > currentNode.gScore()){
                        adjNode.get().setGScore(currentNode.gScore() + 1);
                        adjNode.get().setParentNode(currentNode);
                    }
                }

                //if not, store it with current scores
                else {
                    PathNode newAdjNode = new PathNode(
                            adjPoint,
                            currentNode.gScore() + 1,
                            adjPoint.distanceSquared(end),
                            currentNode
                    );
                    openList.offer(newAdjNode);
                }
            }
            if (!flag) break;
            openList.remove(currentNode);
            closedList.add(currentNode);
        }
        Optional<PathNode> targetNodeOptional = closedList.stream()
                .filter(openListPoint-> openListPoint.point().adjacent(end))
                .findFirst();
        //if target us empty, path is complete
        if (targetNodeOptional.isEmpty()) {
            return path;
        }
        PathNode currentNode = targetNodeOptional.get();
        while (currentNode.parentNode() != null) {
            path.addFirst(currentNode.point());
            currentNode = currentNode.parentNode();
        }
        return path;
    }

    //pathNode stores parentNode, gCost, hCost and location
    //pathNode has methods; getters, compareTo, equals, hashCode, & setters
    static final class PathNode implements Comparable<PathNode> {
        private final Point point;
        private int gScore;
        private final double hScore;
        private PathNode parentNode;

        PathNode(Point point, int gScore, double hScore, PathNode parentNode) {
            this.point = point;
            this.gScore = gScore;
            this.hScore = hScore;
            this.parentNode = parentNode;
        }

        public double getfScore() {
            return this.gScore + this.hScore;
        }

        @Override
        public int compareTo(PathNode o) {
            return Comparator.comparingDouble(PathNode::getfScore)
                    .thenComparingDouble(PathNode::gScore)
                    .thenComparingDouble(PathNode::hScore)
                    .compare(this, o);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PathNode that = (PathNode) o;
            return gScore == that.gScore && Double.compare(that.hScore, hScore) == 0 && point.equals(that.point) && Objects.equals(parentNode, that.parentNode);
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, gScore, hScore, parentNode);
        }

        public Point point() {
            return point;
        }

        public int gScore() {
            return gScore;
        }

        public double hScore() {
            return hScore;
        }

        public void setGScore(int gScore){
            this.gScore = gScore;
        }

        public void setParentNode(PathNode parentNode){
            this.parentNode = parentNode;
        }

        public PathNode parentNode() {
            return parentNode;
        }

    }

/*
    public List<Point> constructPath(Map<Node, Node> cameFrom, Node cur){
        List<Point> path = new ArrayList<>();
        while(cameFrom.containsKey(cur)){
            path.add(0, cur.getPoint());
            cur = cameFrom.get(cur);
            if (cur == null){break;}
        }
        Collections.reverse(path);
        path.remove(0);
        //System.out.println("Path Size: " + path.size());
        cameFrom.clear();
        return path;
    }

    @Override
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors) {


        //OPENSET -- the set of discovered nodes that may need to be re expanded
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Node startN = new Node(start, start.getFCost(start, end), start.getHCost(end), start.getGCost(start) );
        openSet.add(startN);


        //CAMEFROM -- NODE to NODE
        Map<Node, Node> cameFrom = new HashMap<>();


        //GSCORE -- NODE to INTEGER -- distance to start
        Map<Node, Integer> gScore = new HashMap<>();
        gScore.put(startN, 0);


        //FSCORE -- NODE to INTEGER -- distance start to cur to end
        Map<Node, Integer> fScore = new HashMap<>();
        int startFC = startN.getHCost(end);
        fScore.put(startN, startFC);

        //CLOSEDLIST -- just for filtering validNieghbors
        List<Point> closeList = new ArrayList<>();
        //closeList.add(start);


        while (!openSet.isEmpty()){
            //lowest FC from CompareTo method in Node
            Node curN = openSet.poll();

            //validNeighbors of the current Node
            List<Point> validNeighbors = potentialNeighbors.apply(curN.pt)
                    .filter(canPassThrough).filter(p -> !p.equals(start) && !p.equals(end)).filter(p -> !closeList.contains(p)).toList();
            //System.out.println(validNeighbors.size());

            //Check if any of the neighbors are beside the target
            List<Point> byTarget = validNeighbors.stream().filter(point -> withinReach.test(point, end)).toList();

            if (byTarget.size() > 0){
                //Point bt = byTarget.get(0);
                //Node btn = new Node(bt, bt.getFCost(start, end), bt.getHCost(end), bt.getGCost(start));
                //cameFrom.put(btn, curN);
                //openSet.add(btn);
                return constructPath(cameFrom, curN);
            }
//            if (curN.pt == end){
//                return constructPath(cameFrom, curN);
//            }


            for(Point p : validNeighbors){
                Node neighborNode = new Node(p, p.getFCost(start, end), p.getHCost(end), p.getGCost(start));
                int tentativeGS = gScore.get(curN) + neighborNode.getGCost(curN.pt);
                if (!gScore.containsKey(neighborNode)){gScore.put(neighborNode, 100000);}
                if (tentativeGS < gScore.get(neighborNode)) {
                    //this path to neighbor is better than any previously observed

                    //store data
                    cameFrom.put(neighborNode, curN);
                    gScore.put(neighborNode, tentativeGS);
                    fScore.put(neighborNode, tentativeGS + neighborNode.getHCost(end));

                    neighborNode.gCost = tentativeGS;
                    neighborNode.fCost = tentativeGS + neighborNode.getHCost(end);
                    closeList.remove(curN.pt);
                    closeList.add(p);

                    //add to openSet if not already added
                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                        //System.out.println("NextStep: (" + neighborNode.pt.x + ", " + neighborNode.pt.y + ")");
                    }
                }
            }
        }
        //no valid path to goal
        return new ArrayList<>();
    }*/
}
