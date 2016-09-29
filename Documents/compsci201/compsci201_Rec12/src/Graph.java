import java.util.PriorityQueue;


public class Graph {
    public int[][] myGraph;

    public Graph(String[] dependencies, String[] weights){
        myGraph = new int[dependencies.length][dependencies.length];
        for(int i = 0; i < dependencies.length; i++){
            String[] adj = dependencies[i].split(" ");
            String[] w = weights[i].split(" ");
            for(int j = 0; j < adj.length; j++){
                myGraph[i][Integer.parseInt(adj[j])] = Integer.parseInt(w[j]);
            }
        }
    }

    public void dijkstra(int start, int end){
        GraphVertex[] vertices = new GraphVertex[myGraph.length];
        PriorityQueue<GraphVertex> pq = new PriorityQueue<GraphVertex>(); //unvisited vertices

        for(int i = 0; i < myGraph.length; i++){
            GraphVertex v = new GraphVertex(i);
            if(i == start)
                v.setDistance(0);
            pq.add(v);
            vertices[i] = v;
        }

        while(!pq.isEmpty()){
            GraphVertex cur = pq.poll(); //visit GraphVertex
            int name = cur.getName();
            if(name == end){
                break;
            }
            for(int i = 0; i < myGraph.length; i++){
                int distance = myGraph[name][i];
                if(pq.contains(vertices[i])){
                    if (distance > 0 ){ // they are neighbors
                        int curDist = vertices[i].myDistance;
                        int possibleDist = vertices[name].myDistance + distance;
                        if(possibleDist < curDist){
                            vertices[i].setDistance(possibleDist);
                            pq.remove(vertices[i]);
                            pq.add(vertices[i]);
                        }   
                    }
                }
            }
        }
        System.out.printf("The shortest distance from %d to %d is %d", start, end, vertices[end].myDistance);
    }

    public class GraphVertex implements Comparable<GraphVertex>{
        private int myName;
        private int myDistance;

        public GraphVertex(int name){
            myName = name;
            myDistance = Integer.MAX_VALUE;
        }

        public void setDistance(int d){
            myDistance = d;
        }

        public int getName() {
            return myName;
        }

        @Override
        public int compareTo(GraphVertex arg0) {
            return this.myDistance - arg0.myDistance;
        }

    }

    public static void main(String[] args){
        String[] dependencies = {"1 2 3", "0 4 5", "0 6", "0 5", "1", "1 3", "2"};
        String[] weights = {"3 6 2", "3 4 6", "6 4", "2 5", "4", "6 5", "4"};
        Graph dijkstra = new Graph(dependencies, weights);
        dijkstra.dijkstra(2, 5);
    }
}
