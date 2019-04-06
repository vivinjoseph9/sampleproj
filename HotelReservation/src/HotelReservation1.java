// Assumptions/Conditions-
//
// Hotel has 1000 rooms - Room 1, Room 2, ... Room 1000  
// Date values for which bookings are accepted - 1 to 365
//   1 - Day 1 of the year  - Jan 1st
//   2 - Day 2 of the year  - Jan 2nd
//   ....
//   365 - Day 365 of the year - Dec 31st
//  
// the following must hold for booking to be accepted
// booking start date < booking end date  OR
// booking start date = booking end date  i.e. start = 10 , end = 10 -> room will be booked for 1 day i.e day 10 i.e. January 10th 
// 
// Rooms are checked sequentially from 1,2...n . i.e if room 1 can accept the booking then booking will be made following the statement 
// (If a booking request arrives and we can accept it, we accept it directly. We do not wait for later request) in the pdf. 
// only if room 1 cannot accomodate the mentioned dates, room 2 will be checked and so on..

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HotelReservation1 {
	
	// Create a hashmap which stores room numbers as key and booking dates as its values 
	// i.e. map<roomNumber, List<bookingDates>>
	static Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
	
	public static void main(String args[]) {
	
		System.out.println("Welcome to the Hotel reservation application!");
		System.out.println();
		
		// Setup our scanner and default the choice to window.
		Scanner s = new Scanner(System.in);
		int choice = 1;

		// Ask user for their choice.
		System.out.print("Please enter 1 for booking a room, 2 to show all bookings, or 0 to exit: ");
		choice = s.nextInt();
		
		while (choice != 0) {
			int roomnumber = 0;
			int start = 0;
			int end = 0;

			// Try to book a room.
			if (choice == 1) {
				
				// Ask the user for the start and end date of booking
				System.out.print("Please enter the start date for your booking: ");
				start = s.nextInt();
				System.out.print("Please enter the end date for your booking: ");
				end = s.nextInt();
				
				// Check the following conditions:
				// 		1. if the booking start and end dates are between 1 and 365
				// 		2. if start booking date < end booking date
				//
				// if true, check for availability of room for the mentioned date
				// else display error message
				
				if (start <= end && start >=1 && start <= 365 && end >= 1 && end <= 365) {
					roomnumber = bookRoom(start, end);
				} else {
					roomnumber = -1;
					System.out.println("invalid dates, please try again! ");
				}
				
				if (roomnumber != -1) {
					System.out.println("Room number " + roomnumber + " booked successfully.");
					printInvoice(roomnumber, start, end);
				} 
				else {
					System.out.println("Room could not be booked ");
				}
			}
			
			else if(choice ==2) {
				if (map.isEmpty()) {
					System.out.println("No bookings made yet");
				} 
				else {
					for(Map.Entry<Integer, List<Integer>> entry : map.entrySet() ) {
					int key = entry.getKey();
					List<Integer> values = entry.getValue();
					System.out.println("Room: " + key + " - Dates: " + values);
					}
				}
			}
			
			else {
				System.out.println("Invalid choice made. Please try again!");
				choice = 0;
			}
			
			// Reprompt the options for the user
			System.out.print("Please enter 1 for booking a room, 2 to show all bookings, or 0 to exit: ");
			choice = s.nextInt();	
		}
		s.close();
	}
	
	// This function checks the availability of the rooms - returns the room number if available or -1 if full.
	private static int bookRoom(int start, int end) {
		List<Integer> valset = new ArrayList<Integer>();
		
		// transferring the reservation dates to a list
		for(int i = start; i <= end; i++) {
			valset.add(i);
		}
		
		// Looping through all rooms starting from 1 to 1000, checking if booking can be made or not 
		// to add/remove rooms, change the upper limit of the loop accordingly
		for(int i = 1; i <= 1000; i++) {
			
			// Room is empty i.e. has no booking at all, add booking for the respective room
			if (!map.containsKey(i)) {
				map.put(i, valset);
				return i;
			}
			else {
				//Flag for checking if input date is present in the booked dates for the room
				boolean flag = false;
				for(int j = start; j <= end; j++) {
					if (map.get(i).contains(j)) {
						flag = true;
					}
				}
				
				// Room has 1 or more bookings, however room is free on the desired dates, update the booking for the respective room 
				if (flag == false) {
					valset.addAll(map.get(i));
					map.put(i, valset);
					return i;
				}
			}
		}
		
		return -1;
	}
	
	// prints out a an invoice with the details of the booking.
	private static void printInvoice(int roomnumber, int start, int end) {
		Date timenow = new Date();
		System.out.println();
		System.out.println("Booking Confirmation");
		System.out.println("***************************************************");
		System.out.println("Date of booking          : " + timenow.toString());
		System.out.println("Room Number              : " + roomnumber);
		System.out.println("Start day of reservation : " + start);
		System.out.println("End day of reservation   : " + end);
		System.out.println("***************************************************");
		System.out.println("Thank you and have a nice day !!!");		
		System.out.println();
	}
}
