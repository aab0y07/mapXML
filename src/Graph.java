

public class Graph {
	
	private Node 	firstNode;  // entry point
	private boolean directed;   
	
	public Graph(boolean directed) {
		firstNode = null;
		this.directed = directed;
	}

	public boolean isDirected() {
		return directed;
	}
	
	public Object getFirstNode() {
		return firstNode;
	}

	public Object getNodeKey(Object node) {
		return ((Node)node).nodeKey;
	}

	public Object getNodeInfo(Object node) {
		return ((Node) node).nodeInfo;
	}

	public Object getFirstEdge(Object node) {
		return ((Node) node).firstEdge;
	}

	public Object getFromNode(Object edge) {
		return ((Edge) edge).fromNode;
	}

	public Object getToNode(Object edge) {
		return ((Edge) edge).toNode;
	}

	public Object getEdgeInfo(Object edge) {
		return ((Edge) edge).edgeInfo;
	}

	public Object getNextNode(Object node) {
		return ((Node) node).nextNode;
	}
	
	public Object getNextEdge(Object edge) {
		return ((Edge) edge).nextEdge;
	}
	
	public Object getNodeByNodeKey(Object nodeKey) {
		for (Node frog = firstNode; frog != null; frog = frog.nextNode)
			if (frog.nodeKey.equals(nodeKey))
				return frog;
		return null;
	}
	
	public void insertNode(Object nodeKey, Object nodeInfo) {
		// find possible key duplicates
		for (Node frog = firstNode; frog != null; frog = frog.nextNode)
			if (frog.nodeKey.equals(nodeKey))
				throw new GraphException("GraphException: insertNode --- attempt to insert duplicate key");
		// insert new node
		firstNode = new Node(nodeKey, nodeInfo, null, firstNode);
		
		
	}
	
	
	public void deleteNode(Object nodeKey) {
		// delete the node and all edges leading from and to the node
		// if the node doesn't exist at all throw GraphException

		// *** not yet implemented ***
	}
	
	public void insertEdge(Object fromNodeKey, Object toNodeKey, Object edgeInfo) {
		// do the referred nodes exist?
		Node fromNode = (Node) getNodeByNodeKey(fromNodeKey);
		Node toNode = (Node) getNodeByNodeKey(toNodeKey);
		if (fromNode == null || toNode == null)
			throw new GraphException("GraphException: insertEdge --- attempt to insert edge with illegal node key");
		// insert new edge (possibly in both directions)
		fromNode.firstEdge = new Edge(fromNode, toNode, edgeInfo, fromNode.firstEdge);
		if (!directed)
			toNode.firstEdge = new Edge(toNode, fromNode, edgeInfo, toNode.firstEdge);
	}
	
	public void drawLines(){}
	
	public void deleteEdge(Object fromNodeKey, Object toNodeKey) {
		// delete the edge --- there might be multiple edges: delete all of them
		// if the edge doesn't exist at all throw GraphException
		// if the graph not directed, remove also the opposite edge

		// *** not yet implemented ***
	}
	
	
	
	public String toString() {
		
		String res = "";
		
		for (Node frog = firstNode; frog != null; frog = frog.nextNode) {
			res += frog.nodeKey + " " + frog.nodeInfo + '\n';
			for (Edge eFrog = frog.firstEdge; eFrog != null; eFrog = eFrog.nextEdge) {
				res += "\t" + eFrog.fromNode.nodeKey + " - " + eFrog.toNode.nodeKey + " <" + eFrog.edgeInfo + ">\n"; 
			}
		}
		
		
		return res.substring(0, res.length());
		
		
	}
	
	private class Node {
		private Object 	nodeKey ;
		private Object 	nodeInfo;
		private Edge	firstEdge;
		private Node	nextNode;
		
		public Node(Object nodeKey, Object nodeInfo, Edge firstEdge, Node nextNode) {
			this.nodeKey = nodeKey;
			this.nodeInfo = nodeInfo;
			this.firstEdge = firstEdge;
			this.nextNode = nextNode;
		}
	}
	
	private class Edge {
		private Node	fromNode;
		private Node	toNode;
		private Object	edgeInfo;
		private Edge 	nextEdge;
		
		public Edge(Node fromNodeKey, Node toNodeKey, Object edgeInfo, Edge nextEdge) {
			this.fromNode = fromNodeKey;
			this.toNode = toNodeKey;
			this.edgeInfo = edgeInfo;
			this.nextEdge = nextEdge;
		}
	}
	
	
}
