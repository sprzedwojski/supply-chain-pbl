package adapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import structure.DistributionCenter;
import structure.Edge;
import structure.Node;

public class Adapter {

	List<FrontNode> NodeList = new ArrayList<FrontNode>();
	List<DistributionCenter> DCs = new ArrayList<DistributionCenter>();
	int maxDemandLenght = 0;

	/*
	 * 
	 * This method convert nodes from an xml node file, containing Front-end
	 * nodes and converts it to Back-end nodes. MaxDemand is calculatefd by
	 * simple min/max function, and then used in DistributionCenter constructor.
	 */
	public List<DistributionCenter> ConvertNodes(String nodesXmlFilename) throws Exception {
		// Importing FrontNodes list
		try {
			NodeList = JAXBXMLHandler.unmarshalNode(new File(nodesXmlFilename));
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		maxDemandLenght = maxDemandLength(NodeList);
		// Creating Backend-Node list.
		for (FrontNode node : NodeList) {
			// Node without demand
			if (node.getDemand() != null) {
				DCs.add(new DistributionCenter(node.getDemand(), node
						.getBaseStockLevel(), node.getInitialStockLevel(), node
						.getHoldingCost(), node.getPurchaseCost(), node
						.getNegativeStockLevelCost(), maxDemandLenght + 1));
			}// Node with demand
			else {
				DCs.add(new DistributionCenter(node.getBaseStockLevel(), node
						.getInitialStockLevel(), node.getHoldingCost(), node
						.getPurchaseCost(), node.getNegativeStockLevelCost(),
						maxDemandLenght + 1));
			}

		}
		return DCs;
	}

	// Saving to the file calculated and modified nodes.
	public void ConvertBackendNodes() throws Exception {
		List<FrontNode> NewNodeList = new ArrayList<FrontNode>();
		for (int i = 0; i < DCs.size(); i++) {
			FrontNode newFront = new FrontNode(NodeList.get(i).getName(), NodeList.get(
					i).getType(), DCs.get(i).getDemand(), DCs.get(i)
					.getBaseStockLevel(), DCs.get(i)
					.getInitialStockLevel(), DCs.get(i)
					.getHoldingCost(), DCs.get(i).getPurchaseCost(),
					DCs.get(i).getNegativeStockLevelCost(),
					maxDemandLenght + 1);
			newFront.setInventoryLevel(DCs.get(i).getInventoryLevel());
			newFront.setOnOrderInventory(DCs.get(i).getOnOrderInventory());
			newFront.setOrderHistory(DCs.get(i).getOrderHistory());
			NewNodeList.add(newFront);
		}

		try {
			//For the purposes of tests, we save list into different file. Ultimately it will be the same file.
			JAXBXMLHandler.marshalNode(NewNodeList, new File("Nodes2.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method works the same as ConvertNodes, but in this case we are
	 * working on connections.
	 */
	public List<Edge> ConvertConnections(String connectionsXmlFilename) {

		List<Edge> Edges = new ArrayList<Edge>();
		List<Connection> ConnectionList = new ArrayList<Connection>();
		try {
			ConnectionList = JAXBXMLHandler.unmarshalConnection(new File(
					connectionsXmlFilename));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		//For each connection
		for (Connection conn : ConnectionList) {
			//If startpoint is an External
			if (conn.getExternal() == true) {

				Node EndNode = null;
				for (int i = 0; i < NodeList.size(); i++) {
					if (conn.getEndpoint() == NodeList.get(i).getId()) {
						EndNode = DCs.get(i);
					}
				}

				Edges.add(new Edge(conn.getFraction(), conn.getDelay(), null,
						EndNode));
			}
			//In case of normal connection
			if (conn.getExternal() == false) {
				Node StartNode = null;
				Node EndNode = null;
				for (int i = 0; i < NodeList.size(); i++) {
					if (conn.getEndpoint() == NodeList.get(i).getId()) {
						EndNode = DCs.get(i);
					}
				}
				for (int i = 0; i < NodeList.size(); i++) {
					if (conn.getStartpoint() == NodeList.get(i).getId()) {
						StartNode = DCs.get(i);
					}
				}
				Edges.add(new Edge(conn.getFraction(), conn.getDelay(),
						StartNode, EndNode));
			}
		}
		return Edges;
	}

	private int maxDemandLength(List<FrontNode> NodeList) {
		int max = 0;
		for (FrontNode node : NodeList) {

			if (node.getDemand() != null && node.getDemand().length > max) {
				max = node.getDemand().length;

			}
		}
		return max;
	}

}
