package designAnElevatorSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Lift {

	public static Set<Lift> liftsInUse = new HashSet<>();

	private KeyPad keypad;
	private int maxCapacity;
	int currentCapacity;
	private int currentFloor;
	private Client client;
	private Direction direction;
	private Doors doors;

	private static List<Integer> goTo = new ArrayList<>();
	private static List<Integer> nextRound = new ArrayList<>();

	enum Doors {
		OPEN, CLOSED, STUCK;

	}

	enum Direction {
		UP, DOWN, REST;

	}

	public Lift(KeyPad keypad, int maxCapacity) {
		this.keypad = keypad;
		this.maxCapacity = maxCapacity;
		currentCapacity = 0;
		currentFloor = 0;
		client = new Client();
		direction = Direction.UP;
		doors = Doors.CLOSED;
	}

	public Lift() {

	}

	public void callLift(Lift.Direction upDown, int clientIsOnThisFloor) {
		Iterator<Lift> liftIt = liftsInUse.iterator();
		while (liftIt.hasNext()) {
			Lift lift = liftIt.next();
			if (lift.direction == Direction.UP || lift.direction == Direction.REST) {
				if (upDown == Direction.UP) {
					if (clientIsOnThisFloor >= currentFloor) {
						if (!goTo.contains(clientIsOnThisFloor)) {
							goTo.add(clientIsOnThisFloor);
							System.out.println("Client waiting on floor " + clientIsOnThisFloor);
							return;
						}
					}
				} else {
					if (!liftIt.hasNext()) {
						if (!nextRound.contains(clientIsOnThisFloor)) {
							nextRound.add(clientIsOnThisFloor);
							System.out.println("Client waiting on floor " + clientIsOnThisFloor);
							return;
						}
					}
				}
			} else {
				if (upDown == Direction.DOWN) {
					if (clientIsOnThisFloor <= currentFloor) {
						goTo.add(clientIsOnThisFloor);
						System.out.println("Client waiting on floor " + clientIsOnThisFloor);
						return;
					}
				} else {
					if (!liftIt.hasNext()) {
						if (!nextRound.contains(clientIsOnThisFloor)) {
							nextRound.add(clientIsOnThisFloor);
							System.out.println("Client waiting on floor " + clientIsOnThisFloor);
							return;
						}
					}
				}
			}
		}
	}

	public void stopAndOpen() {
		if (currentFloor == goTo.get(0)) {
			goTo.remove(0);
			doors = Doors.OPEN;
			System.out.println("Doors open at floor " + getCurrentFloor());
		}
		else System.out.println("What next?");
	}

	public void takeWaitingClients(int clientRequestsThisFloor) {
		if (clientRequestsThisFloor > keypad.getTopFloor() || clientRequestsThisFloor < keypad.getBottomFloor())
			throw new IllegalArgumentException("Floor doesn't exist");

		doors = Doors.OPEN;
		boolean incoming = true;
		while (incoming) {
			if (direction == Direction.UP) {
				if (clientRequestsThisFloor > currentFloor) {
					if (!goTo.contains(clientRequestsThisFloor)) {
						goTo.add(clientRequestsThisFloor);
						System.out.println("Client picked up. Floor requested is " + clientRequestsThisFloor
								+ ". Will drop off this round");
					}
				} else {
					if (!nextRound.contains(clientRequestsThisFloor)) {
						nextRound.add(clientRequestsThisFloor);
						System.out.println("Client picked up. Floor requested is " + clientRequestsThisFloor
								+ ". Will drop off next round");
					}
				}
				incoming = false;
			} else if (direction == Direction.DOWN) {
				if (clientRequestsThisFloor < currentFloor) {
					if (!goTo.contains(clientRequestsThisFloor)) {
						goTo.add(clientRequestsThisFloor);
						System.out.println("Client picked up. Floor requested is " + clientRequestsThisFloor
								+ ". Will drop off this round");
					}
				} else {
					if (!nextRound.contains(clientRequestsThisFloor)) {
						nextRound.add(clientRequestsThisFloor);
						System.out.println("Client picked up. Floor requested is " + clientRequestsThisFloor
								+ ". Will drop off next round");
					}
				}
			}
			incoming = false;
		}

	}

	public void closeDoors(int currentCapacity) {
		while (currentCapacity > maxCapacity) {
			doors = Doors.STUCK;			
			System.out.println("Lift too full, unable to move");
			currentCapacity--;
		}
		doors = Doors.CLOSED;
		System.out.println("Doors closing on floor " + getCurrentFloor());
	}

	public void move() {
		if (direction == Direction.UP) {
			if (currentFloor < keypad.getTopFloor()) {
				if (!goTo.isEmpty()) {
					currentFloor = goTo.get(0);
					System.out.println("Moving up");
				} else {
					changeDirection();
				}
			} else {
				changeDirection();
			}

		} else {
			if (direction == Direction.DOWN) {
				if (currentFloor > keypad.getBottomFloor()) {
					if (!goTo.isEmpty()) {
						currentFloor = goTo.get(0);
						System.out.println("Moving down");
					} else {
						changeDirection();
					}
				} else {
					changeDirection();
				}
			}
		}
	}

	private void changeDirection() {
		if (currentFloor == keypad.getTopFloor() || (direction == Direction.UP && goTo.isEmpty())) {
			if (nextRound.isEmpty()) {
				setCurrentFloor(0);
				direction = Direction.REST;
			} else {
				System.out.println("Changing direction to down");
				direction = Direction.DOWN;
				goTo = nextRound;
				Collections.sort(goTo, Collections.reverseOrder());
				nextRound = new ArrayList<>();
			}
		} else if (currentFloor == keypad.getBottomFloor() || (direction == Direction.DOWN && goTo.isEmpty())) {
			if (nextRound.isEmpty()) {
				setCurrentFloor(0);
				direction = Direction.REST;
			} else {
				System.out.println("Changing direction to up");
				direction = Direction.UP;
				goTo = nextRound;
				Collections.sort(goTo);
				nextRound = new ArrayList<>();
			}
		}
	}

	public static Set<Lift> getLiftsInUse() {
		return liftsInUse;
	}

	public static void setLiftsInUse(Set<Lift> liftsInUse) {
		Lift.liftsInUse = liftsInUse;
	}

	public KeyPad getKeypad() {
		return keypad;
	}

	public void setKeypad(KeyPad keypad) {
		this.keypad = keypad;
	}

	public int getCapacity() {
		return maxCapacity;
	}

	public void setCapacity(int capacity) {
		this.maxCapacity = capacity;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Doors getDoors() {
		return doors;
	}

	public void setDoors(Doors doors) {
		this.doors = doors;
	}

	public static List<Integer> getNextRound() {
		return nextRound;
	}

	public static void setNextRound(ArrayList<Integer> nextRound) {
		Lift.nextRound = nextRound;
	}

	public static List<Integer> getGoTo() {
		return goTo;
	}

	public static void setGoTo(ArrayList<Integer> goTo) {
		Lift.goTo = goTo;
	}

}

class KeyPad {

	private Integer display;
	private int currentFloor;
	private int floorWaiting;
	private int topFloor;
	private int bottomFloor;
	private boolean openDoor;

	KeyPad() {
		display = new Integer(0);
		currentFloor = 0;
		floorWaiting = 0;
		topFloor = 6;
		bottomFloor = -1;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getFloorWaiting() {
		return floorWaiting;
	}

	public void setFloorWaiting(int floorRequested) {
		this.floorWaiting = floorRequested;
	}

	public int getTopFloor() {
		return topFloor;
	}

	public void setTopFloor(int topFloor) {
		this.topFloor = topFloor;
	}

	public int getBottomFloor() {
		return bottomFloor;
	}

	public void setBottomFloor(int bottomFloor) {
		this.bottomFloor = bottomFloor;
	}

	public boolean isOpenDoor() {
		return openDoor;
	}

	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}

}
