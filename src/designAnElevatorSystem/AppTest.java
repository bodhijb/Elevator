package designAnElevatorSystem;

import designAnElevatorSystem.Lift.Direction;

public class AppTest {

	public static void main(String[] args) {

		AppTest at = new AppTest();

		System.out.println("create some clients");
		Client c1 = new Client("c1", 0);
		Client c2 = new Client("c2", 1);
		Client c3 = new Client("c3", 2);

		System.out.println("create a keypad and lift");

		Lift lift1 = new Lift(new KeyPad(), 15);
		Lift lift2 = new Lift(new KeyPad(), 15);
		Lift lift3 = new Lift(new KeyPad(), 15);
		Lift.getLiftsInUse().add(lift1);
		Lift.getLiftsInUse().add(lift2);
		Lift.getLiftsInUse().add(lift3);

		c1.getClientLift().callLift(Direction.UP, 2);
		c1.getClientLift().callLift(Direction.DOWN, -1);
		lift1.setCurrentFloor(Lift.getGoTo().get(0));
		lift1.stopAndOpen();
		int floorRequested = 4;
		lift1.takeWaitingClients(floorRequested);
		floorRequested = 2;
		lift1.takeWaitingClients(floorRequested);
		lift1.closeDoors(4);
		lift1.move();
		lift1.stopAndOpen();
		floorRequested = 6;
		lift1.takeWaitingClients(floorRequested);
		lift1.closeDoors(4);
		lift1.move();
		lift1.stopAndOpen();
		floorRequested = 1;
		lift1.takeWaitingClients(floorRequested);
		lift1.closeDoors(4);
		lift1.move();

	}

}
