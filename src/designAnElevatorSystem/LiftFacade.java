package designAnElevatorSystem;



public class LiftFacade {
	
	private Lift lift;
	
	public LiftFacade(Lift lift) {
		this.lift = lift;
	}
	
	public void createLifts() {
		KeyPad keyPad = new KeyPad();
		Lift lift1 = new Lift(keyPad, 15);
		Lift lift2 = new Lift(keyPad, 15);
		Lift lift3 = new Lift(keyPad, 15);
		Lift.getLiftsInUse().add(lift1);
		Lift.getLiftsInUse().add(lift2);
		Lift.getLiftsInUse().add(lift3);
	}
	
	public void liftRound(Lift.Direction upDown, int clientIsOnThisFloor, int clientRequestsThisFloor) {	
		
		lift.callLift(upDown, clientIsOnThisFloor);	
		System.out.println(Lift.getGoTo());
		System.out.println(Lift.getNextRound());
		lift.closeDoors(5);
		lift.move();
		lift.stopAndOpen();
		lift.takeWaitingClients(clientRequestsThisFloor);
		System.out.println(Lift.getGoTo());
		System.out.println(Lift.getNextRound());
		lift.closeDoors(6);
		lift.move();
		lift.stopAndOpen();
		clientRequestsThisFloor = lift.getCurrentFloor()-3;
		lift.takeWaitingClients(clientRequestsThisFloor);
		System.out.println(Lift.getGoTo());
		System.out.println(Lift.getNextRound());
		lift.closeDoors(6);
		lift.move();
		lift.stopAndOpen();
		
	}
		
		
		
	
	
	
	

}
