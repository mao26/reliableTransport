import java.util.ArrayList;

public class LongWay {

	public static void main(String[] args) {
		String[] connections = { "1 2 3 4 5", "2 3 4 5", "3 4 5", "4 5", "5",
				"" };
		String[] times = { "2 2 2 2 2", "2 2 2 2", "2 2 2", "2 2", "2", "" };
		LongWay lw = new LongWay();
		System.out.println(lw.longest(connections, times));
	}

	ArrayList<Integer> weights = new ArrayList<Integer>();

	public class Edge {
		public int endpoint;
		public int weight;
	}

	public class Vertex { // same thing as a Node class
		public int startpoint;
		public ArrayList<Edge> edges = new ArrayList<Edge>();
	}

	private int countNodes(int index, ArrayList<Vertex> vertices, int weight) {
		if (vertices.get(index).edges.size() == 0) {
			weights.add(weight);
			return weight; // recursion exit point
		}
		// index tells us which edge we want to go through
		int max = 0;
		for (int i = 0; i < vertices.get(index).edges.size(); i++) {
			int wt = vertices.get(index).edges.get(i).weight;
			countNodes(vertices.get(index).edges.get(i).endpoint, vertices, wt
					+ weight);
			// this returns the specific wieght that belongs to the specific
			// path testing
			// vertices.get(index).edges.get(i) is looking at a specific inputed
			// index then looking at the edges and going through one path
			// (determined by i)
		}
		return weight;
	}

	public int longest(String[] connections, String[] times) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		// our big overall data structure, that has a list of Nodes/vertex which
		// each has a startpoint and a list of edges
		int count = 0;
		for (String connection : connections) {
			// creates a vertex and then goes into the times and connections
			// string arrays and takes each individual number
			Vertex v = new Vertex();
			v.startpoint = count;
			String[] endpoints = connection.split(" ");
			String[] weights = times[count].split(" ");
			count++;
			if (!weights[0].trim().isEmpty()) { 
				// if there are edges
				for (int i = 0; i < endpoints.length; i++) {
					// then this one will add to the specific vertex a list of
					// all of the edges that connect to that start point
					Edge e = new Edge();
					e.weight = Integer.parseInt(weights[i]);
					e.endpoint = Integer.parseInt(endpoints[i]);
					v.edges.add(e);
				}
			}
			vertices.add(v);

		}
		for (int i = 0; i < times.length; i++) {
			countNodes(i, vertices, 0);
		}
		int maxVal = 0;
		for (int j = 0; j < weights.size(); j++) {
			if (weights.get(j) > maxVal) {
				maxVal = weights.get(j);
			}
		}
		return maxVal;
	}
}
