package structure;

public class Edge {

	private int id;
	
    /**
     * Part of the order of the receiving Node that
     * is delivered by this edge (lambda).
     */
    private double fraction;

    /**
     * Number of days that the delivery through this edge will require.
     */
    private int delay;

    /**
     * The Node that is the source of the order.
     */
    private Node sender;

    /**
     * The Node that is the recipient of the order.
     */
    private Node receiver;


    public Edge(double fraction, int delay, Node sender, Node receiver) {
        super();
        this.fraction = fraction > 1.0 ? fraction/100d : fraction;
        this.delay = delay;
        this.sender = sender;
        this.receiver = receiver;
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        this.fraction = fraction;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Node getSender() {
        return sender;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }

    public Node getReceiver() {
        return receiver;
    }

    public void setReceiver(Node receiver) {
        this.receiver = receiver;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
