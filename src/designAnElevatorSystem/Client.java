package designAnElevatorSystem;

public class Client {

	private String name;
	private int clientFloor;
	private Lift clientLift;

	public Client(String name, int clientFloor) {
		this.name = name;
		this.clientFloor = clientFloor;
		clientLift = new Lift();
	}

	public Client() {

	}
	
	public void selectFloor(int request) {
		clientLift.getGoTo().add(request);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getClientFloor() {
		return clientFloor;
	}

	public void setClientFloor(int ClientFloor) {
		this.clientFloor = clientFloor;
	}

	public Lift getClientLift() {
		return clientLift;
	}

	public void setClientLift(Lift clientLift) {
		this.clientLift = clientLift;
	}

}
