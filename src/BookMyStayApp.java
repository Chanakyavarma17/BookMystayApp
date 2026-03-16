
import java.util.HashMap;

/**
 * UseCase9ErrorHandlingValidation
 *
 * Demonstrates input validation and custom exception handling
 * to prevent invalid booking scenarios.
 *
 * @author Chani
 * @version 9.0
 */

/* Custom Exception */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

/* Inventory Service */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, -1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        int available = inventory.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        inventory.put(roomType, available - 1);

        System.out.println("Room booked successfully: " + roomType);
    }
}

/* Booking Validator */
class InvalidBookingValidator {

    public static void validateRoomType(String roomType) throws InvalidBookingException {

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty.");
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 9.0");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        String[] testInputs = {
                "Single Room",
                "Suite Room",
                "",
                "Luxury Room"
        };

        for (String roomType : testInputs) {

            try {

                System.out.println("\nProcessing booking request for: " + roomType);

                InvalidBookingValidator.validateRoomType(roomType);

                inventory.bookRoom(roomType);

            } catch (InvalidBookingException e) {

                System.out.println("Booking Failed: " + e.getMessage());
            }
        }

        System.out.println("\nSystem continues running safely...");
    }
}
