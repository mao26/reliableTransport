import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class SocialNetwork {
	public Set<Integer> reachable = new TreeSet<Integer>();

	public static void main(String[] args) {

		String[] people = { "1", "0 2", "1" };
		SocialNetwork sn = new SocialNetwork();
		System.out.println(sn.butterflies(people));
	}

	private boolean countNodes(int index, ArrayList<Vertex> vertices,
			int trialButterfly) {
		if (vertices.get(index).edges.size() == 0) {
			return true; // recursion exit point
		}
		if (reachable.size() == vertices.size() - 2) {
			return true;
		}
		reachable.add(index);
		for (int i = 0; i < vertices.get(index).edges.size(); i++) {
			int endpt = vertices.get(index).edges.get(i).endpoint;
			if (endpt == trialButterfly || reachable.contains(endpt)) {
				continue;
			}
			// this is the startpoint (the i)
			reachable.add(endpt);
			countNodes(vertices.get(index).edges.get(i).endpoint, vertices,
					trialButterfly);
		}
		return false;
	}

	public class Edge {
		public int endpoint;

		public Edge(int endpt) {
			this.endpoint = endpt;
		}
	}

	public class Vertex { // same thing as a Node class
		public int startpoint;
		public ArrayList<Edge> edges = new ArrayList<Edge>();
	}

	public int butterflies(String[] people) {
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		// our big overall data structure, that has a list of Nodes/vertex which
		// each has a startpoint and a list of edges
		int count = 0;
		for (String person : people) {
			// creates a vertex and then goes into the times and connections
			// string arrays and takes each individual number
			Vertex v = new Vertex();
			v.startpoint = count;
			String[] endpoints = person.split(" ");
			count++;
			if (!endpoints[0].trim().isEmpty()) {
				// if there are edges
				for (int i = 0; i < endpoints.length; i++) {
					// then this one will add to the specific vertex a list of
					// all of the edges that connect to that start point
					Edge e = new Edge(Integer.parseInt(endpoints[i]));
					v.edges.add(e);
				}
			}
			vertices.add(v);
		}
		// now augment list to show bidirectional graph
		// for(int i =0; i < vertices.size(); i++){
		// for(int j = 0; j < vertices.get(i).edges.size(); j++){
		// int endpt = vertices.get(i).edges.get(j).endpoint;
		// Edge e = new Edge(i);
		// vertices.get(endpt).edges.add(e);
		// }
		// }
		int butterflyCount = 0;
		for (int trialButterfly = 0; trialButterfly < people.length; trialButterfly++) {
			if (isButterfly(trialButterfly, vertices)) {

				butterflyCount++;
			}
		}
		return butterflyCount;
	}

	private boolean isButterfly(int trialButterfly, ArrayList<Vertex> vertices) {
		for (Edge e : vertices.get(trialButterfly).edges) {
			reachable.clear();
			countNodes(e.endpoint, vertices, trialButterfly);
			if (reachable.size() < vertices.size() - 2 || reachable.size() == 1) {
				return true;
			}
		}

		return false;
	}
}