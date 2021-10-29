package com.elevator.veevart.utilities;

/**
 * This class contains methods to print in console the actions of the elevator.
 * They are only auxiliary methods.
 * @author Miguel Orrego
 */
public class Prints {

	public static void stopElevator() {
		System.out.println("Elevator stops");
	}

	public static void showCurrFloor(Integer floor) {
		System.out.println("Elevator on floor " + floor);
	}

	public static void showElevatorDir(String direction) {
		System.out.println("Elevator going " + direction);
	}

	public static void showFloorEntered(Integer floorEntered) {
		System.out.println("Floor entered " + floorEntered);
	}

	public static void determineAndShowDirection(Directions direction) {
		if (direction == Directions.GOING_UP)
			showElevatorDir("up");
		else if (direction == Directions.GOING_DOWN)
			showElevatorDir("down");
	}
	
	public static void showMsgReqEntry() {
		System.out.println("Request a new floor? (y/n)");
	}
	
	public static void showErrorReqEntry() {
		System.err.println("The elevator is damaged. Call the fire department.");
	}

	public static void showMsgInvalidReqEntry() {
		System.out.println("Invalid entry. Please enter a valid entry (y/n).");
	}
	
	public static void showMsgFloorEntry() {
		System.out.println("Which floor would you like to go to?");
	}

	public static void showMsgInvalidFloorReqEntry(Integer minAllow, Integer maxAllow) {
		System.out.println("Invalid entry. Please enter a valid values, between " + minAllow + " and " + maxAllow + ".");
	}
}
