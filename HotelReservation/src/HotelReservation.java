import java.util.Scanner;
import java.util.Date;

public class HotelReservation {

	// Create an 2D array of 10 rooms and 5 days.
	private static int[] rooms = new int[5];

	public static void main(String args[]) {
		System.out.println("Welcome to the Hotel reservation application!");
		System.out.println();
		

		// Lets start by setting all rooms equal to 0 (i.e. Empty)
		for (int i = 0; i < 5; i++) {
				rooms[i] = 0;
			}

			
		// Setup our scanner and default the choice to window.
		Scanner s = new Scanner(System.in);
		int choice = 1;

		// Ask user for their choice.
		System.out.print("Please enter 1 for booking a room, 2 for cancelling a booking, or 0 to exit: ");
		choice = s.nextInt();


		// If the choice is not the one for exit, execute the booking/cancellation.
		while (choice != 0) {
			int roomnumber = 0;
		//	boolean cancellation;
			int start = 0;
			int end = 0;

			// try to book a room.
			if (choice == 1) {
				
				//ask the user for the start and end date of booking
				System.out.print("Please enter the start date for your booking: ");
				start = s.nextInt();
				System.out.print("Please enter the end date for your booking: ");
				end = s.nextInt();

				if (start >= 0 && start <= 5 && end >= 0 && end <= 5) {
					roomnumber = bookRoom(start, end);
					
					if (roomnumber != -1) {
						System.out.println("Room number " + roomnumber + " booked successfully.");
						printInvoice(roomnumber, start, end);
					}
					else {
						System.out.println("Sorry, all the rooms are full, we were not able to book a room at the moment.");
					}	
					
				}
				else {
					System.out.println("start and/or end dates are not withing acceptable range, please try again!!");
					System.out.println();
				}
	
			} 
			
			// try to cancel a booking.
			else if (choice == 2) {

			/*	System.out.print("Please enter the room number: ");
				int cancelroomnumber = s.nextInt();
				cancellation = cancelRoombooking(cancelroomnumber);
			
				// If not available, see if we have window seats available.
				if (cancellation == true) {
						System.out.println("The booking of Room Number " + cancelroomnumber + " was cancelled.");
				} 
				else {
					System.out.println("The cancellation was not successful please re-check the room number");
				}  */
			}
			
			else if (choice == 3) {
				for (int i = 0; i < 5; i++) {
						System.out.println(rooms[i]);
						}
			}
			
			//invalid option, print message
			else {
				System.out.println("Invalid choice made. Please try again!");
				choice = 0;
			}

			// Reprompt for a choice
			System.out.print("Please enter 1 for booking a room, 2 for cancelling a booking, or 0 to exit: ");
			choice = s.nextInt();
		}
		
		s.close();
	}


	// This function checks for the availability of rooms and returns the room number or -1 if full.
	private static int bookRoom(int start, int end) {
			for (int i = 0; i < 5; i++) {
					if (rooms[i] == 0) {
						rooms[i] = 1;
					    return i+1;
				   }
			}
		return -1;
	}


	// This function checks to see if a valid room exists and returns 1 if booking was cancelled, -1 if invalid.
/*	private static boolean cancelRoombooking(int cancelroomnumber) {
			if (rooms[cancelroomnumber-1] == 1) {
				rooms[cancelroomnumber-1] = 0;
				return true;
			}
		return false;
	} */


	// This simply prints out a nice little boarding pass message with their seat number and date of issue.
	private static void printInvoice(int roomnumber, int start, int end) {
		Date timenow = new Date();
		System.out.println();
		System.out.println("Booking Confirmation");
		System.out.println("**************************************");
		System.out.println("Date of booking      : " + timenow.toString());
		System.out.println("Room Number          : " + roomnumber);
		System.out.println("Start of reservation : " + start);
		System.out.println("End of reservation   : " + end);
		System.out.println("**************************************");
		System.out.println("Thank you and have a nice day !!!");		
		System.out.println();
	}
}
