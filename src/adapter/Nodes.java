package adapter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "nodes")
public class Nodes {
 
    @XmlElement(name = "node", type = FrontNode.class)
    private List<FrontNode> nodes = new ArrayList<FrontNode>();
     
    public Nodes() {}
 
    public Nodes(List<FrontNode> nodes) {
        this.nodes = nodes;
    }
 
    public List<FrontNode> getNodes() {
        return nodes;
    }
 
    public void setNodes(List<FrontNode> books) {
        this.nodes = nodes;
    }  
    
    public FrontNode getNode(int i)
    {
    	return nodes.get(i);
    }
}