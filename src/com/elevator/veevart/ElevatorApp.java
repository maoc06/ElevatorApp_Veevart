package com.elevator.veevart;

import java.util.ArrayList;

import com.elevator.veevart.domain.Elevator;
import com.elevator.veevart.domain.Request;
import com.elevator.veevart.domain.Side;

/**
 * This is the main class.
 * @author Miguel Orrego
 * 
 * It initializes:
 * 
 * 		(Primitive Data Types)
 * 		initFloors - A collection of initial floors.
 * 		floorBuilding - The total number of floors the building has.
 * 		initFloor - The initial floor of the elevator.
 * 
 * 		(Object)
 * 		elevator - Elevator object to execute actions
 * 		side - manages the lists of requested floors on both sides of the elevator direction: up and down
 * 
 *
 */
public class ElevatorApp {

	public static void main(String[] args) {
		Integer[] initFloors = { 5, 29, 13, 10 };
		Integer floorsBuilding = 29;
		Integer initFloor = 4;

		Elevator elevator = new Elevator(floorsBuilding);
		Side side = new Side(initFloors, initFloor);

		// get initial sort lists of requests from both sides
		ArrayList<ArrayList<Integer>> sides = side.getSidesReq();

		// set the requests from both sides
		Request.setDownRequests(sides.get(0));
		Request.setUpRequests(sides.get(1));
		
		// set the min and max values are allow
		Request.setMinFloorAllow(Elevator.getFirstFloor());
		Request.setMaxFloorAllow(elevator.getFloorsBuilding());

		// execute the elevator tour with some initial request, and a start floor
		elevator.execute(initFloors, initFloor);
	}

}
