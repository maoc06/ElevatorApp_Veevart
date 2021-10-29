package com.elevator.veevart.domain;

import java.util.ArrayList;

import com.elevator.veevart.utilities.Directions;
import com.elevator.veevart.utilities.Prints;

/**
 * This class represents the Elevator entity and its actions.
 * @author Miguel Orrego
 */
public final class Elevator {
	// Set the initial direction of the elevator to Upwards
	public static Directions direction = Directions.GOING_UP;
	// Set the first floor of the building in one(1)
	public static Integer firstFloor = 1;

	// The total number of floors the building has.
	private Integer floorsBuilding;

	public static Integer getFirstFloor() {
		return firstFloor;
	}

	public static void setFirstFloor(Integer firstFloor) {
		Elevator.firstFloor = firstFloor;
	}

	public Integer getFloorsBuilding() {
		return floorsBuilding;
	}

	public void setFloorsBuilding(Integer floorsBuilding) {
		this.floorsBuilding = floorsBuilding;
	}

	/*
	 * Main constructor
	 * 
	 * @param floorsBuilding - The total number of floors the building has
	 */
	public Elevator(Integer floorsBuilding) {
		this.floorsBuilding = floorsBuilding;
	}

	/*
	 * Change the direction of the elevator and then display it on the console.
	 * 
	 * @param newDirection - The enumeration of the new direction.
	 */
	private void changeAndShowDirection(Directions newDirection) {
		direction = newDirection;
		Prints.determineAndShowDirection(newDirection);
	}

	/*
	 * Determines in which direction the elevator has to be shifted
	 */
	private void determineChangeDirection() {
		if (direction == Directions.GOING_UP)
			direction = Directions.GOING_DOWN;
		else if (direction == Directions.GOING_DOWN)
			direction = Directions.GOING_UP;
	}

	/*
	 * Check and execute the actions carried out by the elevator on its way.
	 * 
	 * @param floor - current elevator floor
	 * 
	 * @param sideReq - list of requests ordered in the direction the elevator is
	 * moving.
	 * 
	 * @param going - the floor where you are going.
	 * 
	 * @param stopGoing - the floor limits how far the elevator can go in the
	 * direction it takes.
	 * 
	 * @return The updated and ordered list of requests from the direction the
	 * elevator is moving.
	 */
	private ArrayList<Integer> actions(Integer floor, ArrayList<Integer> sideReq, Integer going, Integer stopGoing) {
		Prints.showCurrFloor(floor);

		// does the request list on one side contain the current floor?
		boolean floorReq = sideReq.contains(floor);

		// if we are in a requested apartment or we have already reached the limit of
		// the address, then stop the elevator.
		if (floorReq || going == stopGoing)
			Prints.stopElevator();

		// if we are in a requested apartment remove the request from the list.
		if (floorReq) {
			int indexFloor = sideReq.indexOf(floor);
			sideReq.remove(indexFloor);
		}

		// if we are in a requested apartment and there is a new floor to enter.
		if (floorReq && Request.floorsIncome.get(floor) != null) {
			Request.addFloorReqFromIncome(floor);
		}

		Request.inputEnterNewRequest(floor);

		// if we keep moving in the same direction that we already had.
		if (going < stopGoing && sideReq.size() > 0)
			Prints.determineAndShowDirection(direction);
		// if the elevator reaches the end of the direction it was going, then change
		// direction.
		else if (going == stopGoing)
			determineChangeDirection();

		return sideReq;
	}

	/*
	 * Traverses all floor requests recursively.
	 * 
	 * @param floors - List of floor requests
	 * 
	 * @param currFloor - current floor
	 */
	public void execute(Integer[] floors, Integer currFloor) {
		// if we move up and still do not reach the limit floor.
		if (direction.equals(Directions.GOING_UP) && currFloor <= this.floorsBuilding) {
			// perform the actions on the current floor and update the request list upwards.
			Request.upRequests = actions(currFloor, Request.upRequests, currFloor, this.floorsBuilding);

			// there are still upward requests?
			if (Request.upRequests.size() > 0) {
				// so we keep going up.
				execute(floors, currFloor + 1);
			} else if (Request.downRequests.size() > 0) {
				// if there are no longer requests up, but still exist down.
				changeAndShowDirection(Directions.GOING_DOWN);
				execute(floors, currFloor - 1);
			}
			return;
		}

		// if we move down and still do not reach the limit floor.
		if (direction.equals(Directions.GOING_DOWN) && currFloor >= firstFloor) {
			// perform the actions on the current floor and update the request list down.
			Request.downRequests = actions(currFloor, Request.downRequests, firstFloor, currFloor);

			// there are still down requests?
			if (Request.downRequests.size() > 0) {
				// so we keep going down.
				execute(floors, currFloor - 1);
			} else if (Request.upRequests.size() > 0) {
				// if there are no longer requests down, but still exist up.
				changeAndShowDirection(Directions.GOING_UP);
				execute(floors, currFloor + 1);
			}
		}
		return;
	}
}
