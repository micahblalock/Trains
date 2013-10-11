package trains.app;

public class Route {

	Node to;
	Node from;
	double distance;
	int color = 0;
	
	public Route(Node from, Node to, double weight){
		this.to = to;
		this.from = from;
		this.distance = weight;
	}
	
    public String toString() {
        return String.format("%s-%s  %f ", from.getName(), to.getName(), distance);
    }

	public Node getTo() {
		return to;
	}

	public void setTo(Node to) {
		this.to = to;
	}

	public Node getFrom() {
		return from;
	}

	public void setFrom(Node from) {
		this.from = from;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void init(){
		this.color = 0;
	}
 

}
