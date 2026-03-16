import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3InventorySetup
 *
 * Demonstrates centralized room inventory management
 * using HashMap as the single source of truth.
 *
 * @author Chani
 * @version 3.0
 */

/* RoomInventory class manages room availability */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes room inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (increase or decrease)
    public void updateAvailability(String roomType, int countChange) {
        int current = inventory.getOrDefault(roomType, 0);
        inventory.put(roomType, current + countChange);
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}


public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay App");
        System.out.println("      Version 3.0");
        System.out.println("=================================");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Simulate booking (reduce availability)
        System.out.println("\nBooking 1 Single Room...");
        inventory.updateAvailability("Single Room", -1);

        // Show updated inventory
        inventory.displayInventory();
    }
}
