package designAnElevatorSystem;

public class TestLiftFacade {

	public static void main(String[] args) {
		
		Lift lift = new Lift(new KeyPad(), 15);
		LiftFacade facade = new LiftFacade(lift);
		facade.createLifts();
		Client client1 = new Client("Tester1", 2);
		int clientRequestsThisFloor = 4;
		facade.liftRound(lift.getDirection(), client1.getClientFloor(), clientRequestsThisFloor);
		clientRequestsThisFloor = lift.getCurrentFloor()-3;
		facade.liftRound(lift.getDirection(), client1.getClientFloor(), clientRequestsThisFloor);
		

	}

}
