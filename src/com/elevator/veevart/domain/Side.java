package com.elevator.veevart.domain;

import java.util.ArrayList;
import java.util.Collections;

import com.elevator.veevart.utilities.Directions;

/**
 * This class represents the Side entity and its actions.
 * Lists of requests for the elevator to go up or down.
 * @author Miguel Orrego
 */
public class Side {

	// list of the two directions: up and down.
	private ArrayList<ArrayList<Integer>> sidesReq;
	private Integer initialFloor;

	/*
	 * Main constructor
	 * 
	 * @param floors - List with the requests of the flats.
	 * @param initiFloor - The floor from where you are going to start.
	 */
	public Side(Integer[] floors, Integer initialFloor) {
		this.initialFloor = initialFloor;
		this.sidesReq = initSortSides(floors);
	}

	public static void sortSides() {
		Collections.sort(Request.downRequests);
		Collections.sort(Request.upRequests);
	}

	/*
	 * Add a new request in the correct list according to the current floor and direction.
	 * 
	 * @param newFloorReq - The new request to go to a floor.
	 * @param currFloor - Current elevator floor.
	 * */
	public static void addFloorReqToSide(Integer newFloorReq, Integer currFloor) {
		if (Elevator.direction == Directions.GOING_UP && newFloorReq < currFloor) {
			Request.downRequests.add(newFloorReq);
		} else if (Elevator.direction == Directions.GOING_UP && newFloorReq > currFloor) {
			Request.upRequests.add(newFloorReq);
		} else if (Elevator.direction == Directions.GOING_DOWN && newFloorReq < currFloor) {
			Request.downRequests.add(newFloorReq);
		} else if (Elevator.direction == Directions.GOING_DOWN && newFloorReq > currFloor) {
			Request.upRequests.add(newFloorReq);
		}
	}

	/*
	 * Determines the list and sorts it in the initial state of a list of floors.
	 * 
	 * @param floors - List with the requests of the flats.
	 * */
	public ArrayList<ArrayList<Integer>> initSortSides(Integer[] floors) {
		ArrayList<ArrayList<Integer>> sides = new ArrayList<ArrayList<Integer>>(2);

		ArrayList<Integer> down = new ArrayList<Integer>();
		ArrayList<Integer> up = new ArrayList<Integer>();

		for (int i = 0; i < floors.length; i++) {
			if (floors[i] < this.initialFloor)
				down.add(floors[i]);
			else if (floors[i] > this.initialFloor)
				up.add(floors[i]);
		}

		Collections.sort(down);
		Collections.sort(up);

		sides.add(down);
		sides.add(up);

		return sides;
	}

	public ArrayList<ArrayList<Integer>> getSidesReq() {
		return sidesReq;
	}

}
