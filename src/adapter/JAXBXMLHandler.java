package adapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



 
public class JAXBXMLHandler {
 
    // Export List of nodes into xml file.
    public static void marshalNode(List<FrontNode> nodes, File selectedFile)
            throws IOException, JAXBException {
        JAXBContext context;
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(selectedFile));
        context = JAXBContext.newInstance(Nodes.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(new Nodes(nodes), writer);
        writer.close();
    }
 // Export List of connections into xml file.
    public static void marshalConnection(List<Connection> connections, File selectedFile)
            throws IOException, JAXBException {
        JAXBContext context;
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(selectedFile));
        context = JAXBContext.newInstance(Connections.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(new Connections(connections), writer);
        writer.close();
    }
    
    
    // Import xml node file and create a list of it.
    public static List<FrontNode> unmarshalNode(File importFile) throws JAXBException {
        Nodes nodes = new Nodes();
 
        JAXBContext context = JAXBContext.newInstance(Nodes.class);
        Unmarshaller um = context.createUnmarshaller();
        nodes = (Nodes) um.unmarshal(importFile);
 
        return nodes.getNodes();
    }
    // Import xml connection file and create a list of it.
    public static List<Connection> unmarshalConnection(File importFile) throws JAXBException {
        Connections connections = new Connections();
 
        JAXBContext context = JAXBContext.newInstance(Connections.class);
        Unmarshaller um = context.createUnmarshaller();
        connections = (Connections) um.unmarshal(importFile);
 
        return connections.getConnections();
    }
    
  
    
 
}