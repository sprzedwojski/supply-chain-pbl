package adapter;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "connection")
public class Connection {

	public int startpoint;
	public int endpoint;
	
	public int direction;
	
	public double fraction;
	public int delay;
	
	public boolean external;
	public Connection()
	{
		
	}
	public Connection (int start, int end, int dir, double frac, int del){
		startpoint = start;
		endpoint = end;
		direction = dir;
		fraction = frac;
		delay = del;
	}
	
	public int getStartpoint(){
		return startpoint;
	}
	public int getEndpoint(){
		return endpoint;
	}
	public int getDirection(){
		return direction;
	}
	public double getFraction(){
		return fraction;
	}
	public int getDelay(){
		return delay;
	}
	
	public boolean getExternal()
	{
		return external;
	}
	public void setStartpoint(int _endpoint){
		endpoint = _endpoint;
	}
	
	public void setDirection(int _direction){
		direction = _direction;
	}
	
	public void setFraction(int _fraction){
		fraction = _fraction;
	}
	public void setDelay(int _delay){
		delay = _delay;
	}  
	public void setExternal(boolean _external)
	{
		external = _external;
	}
}