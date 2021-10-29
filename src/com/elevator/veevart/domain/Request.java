package com.elevator.veevart.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.elevator.veevart.utilities.Prints;

/**
 * This class represents the Request entity and its actions.
 * @author Miguel Orrego
 */
public class Request {
	public static ArrayList<Integer> downRequests = new ArrayList<Integer>();
	public static ArrayList<Integer> upRequests = new ArrayList<Integer>();
	public static HashMap<Integer, Integer> floorsIncome = initFloorsReq();

	private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static Integer minFloorAllow = 1;
	private static Integer maxFloorAllow = 2;

	public Request() {
	}

	public static void setDownRequests(ArrayList<Integer> downRequests) {
		Request.downRequests = downRequests;
	}

	public static void setUpRequests(ArrayList<Integer> upRequests) {
		Request.upRequests = upRequests;
	}

	public static void setMinFloorAllow(Integer minFloor) {
		minFloorAllow = minFloor;
	}

	public static void setMaxFloorAllow(Integer maxFloor) {
		maxFloorAllow = maxFloor;
	}

	/*
	 * Initial floor request, through key-value. KEY: Floor where the elevator was
	 * requested; VALUE: Incoming floor that is requested.
	 */
	private static HashMap<Integer, Integer> initFloorsReq() {
		HashMap<Integer, Integer> floorsReq = new HashMap<Integer, Integer>();

		floorsReq.put(5, 2);
		floorsReq.put(29, 10);
		floorsReq.put(13, 1);
		floorsReq.put(10, 1);

		return floorsReq;
	}

	public static void determineListReq(Integer currFloor, Integer newFloorReq) {
		// if the request already exists, continue.
		if (downRequests.contains(newFloorReq) || upRequests.contains(newFloorReq)) {
			return;
		}

		Prints.showFloorEntered(newFloorReq);

		// determine if the new request in the mailing list should go.
		Side.addFloorReqToSide(newFloorReq, currFloor);
		Side.sortSides();
	}

	/*
	 * Add new floor requests to go to.
	 * 
	 * @param currFloor - current floor the elevator is on.
	 */
	public static void addFloorReqFromIncome(Integer currFloor) {
		// get the current floor request, and remove it.
		Integer newFloorReq = floorsIncome.get(currFloor);
		floorsIncome.remove(currFloor);

		determineListReq(currFloor, newFloorReq);
	}

	/*
	 * Validates whether the user's input for a floor request is within the expected
	 * values.
	 * 
	 * @param inputReq - user input value
	 */
	private static boolean checkRequestInput(String inputReq) {
		if (inputReq.equals("yes") || inputReq.equals("y") || inputReq.equals("n") || inputReq.contains("no")) {
			return true;
		}
		Prints.showMsgInvalidReqEntry();
		return false;
	}

	/*
	 * Validates whether the user's input for requesting a floor is within the
	 * allowed range.
	 * 
	 * @param newFloor - user input value
	 */
	private static boolean checkNewFloorReqInput(Integer newFloor) {
		if (newFloor > maxFloorAllow || newFloor < minFloorAllow) {
			Prints.showMsgInvalidFloorReqEntry(minFloorAllow, maxFloorAllow);
			return false;
		}
		return true;
	}

	/*
	 * Displays the data entry messages, validates the data and performs the
	 * corresponding actions (add floor or not).
	 * 
	 * @param currFloor - Current elevator floor.
	 */
	public static boolean inputEnterNewRequest(Integer currFloor) {
		boolean isRequestNew = false;
		String input;
		Integer newFloor;

		try {
			do {
				Prints.showMsgReqEntry();
				input = reader.readLine();
				input = input.toLowerCase();
			} while (!checkRequestInput(input));

			// if you want to order a new floor
			if (input.equals("yes") || input.equals("y")) {
				do {
					newFloor = inputEnterNewFloor();
				} while (!checkNewFloorReqInput(newFloor));

				determineListReq(currFloor, newFloor);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Prints.showErrorReqEntry();
		}

		return isRequestNew;
	}

	/*
	 * Requests to enter a floor to the user.
	 */
	private static Integer inputEnterNewFloor() {
		Integer newFloor = -1;
		String input;

		try {
			Prints.showMsgFloorEntry();
			input = reader.readLine();

			newFloor = tryParseFloorInput(input);
		} catch (IOException e) {
			e.printStackTrace();
			Prints.showErrorReqEntry();
		}

		return newFloor;
	}

	private static Integer tryParseFloorInput(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			return -1;
		}
	}

}
