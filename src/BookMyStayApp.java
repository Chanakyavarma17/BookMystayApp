import java.util.*;

/**
 * UseCase6RoomAllocationService
 *
 * Demonstrates reservation confirmation and room allocation
 * while preventing double-booking.
 *
 * @author Chani
 * @version 6.0
 */

/* Reservation class */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* Room Inventory Service */
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrementRoom(String roomType) {
        int current = inventory.get(roomType);
        inventory.put(roomType, current - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

/* Booking Service */
class BookingService {

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {

        this.inventory = inventory;
        allocatedRoomIds = new HashSet<>();
        roomAllocations = new HashMap<>();
    }

    public void processBookings(Queue<Reservation> queue) {

        while (!queue.isEmpty()) {

            Reservation reservation = queue.poll();
            String roomType = reservation.getRoomType();

            System.out.println("\nProcessing booking for " + reservation.getGuestName());

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                allocatedRoomIds.add(roomId);

                roomAllocations
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                inventory.decrementRoom(roomType);

                System.out.println("Reservation Confirmed!");
                System.out.println("Room Type : " + roomType);
                System.out.println("Assigned Room ID : " + roomId);

            } else {

                System.out.println("Reservation Failed - No rooms available for " + roomType);
            }
        }
    }

    private String generateRoomId(String roomType) {

        String prefix = roomType.substring(0, 1).toUpperCase();
        String roomId;

        do {
            roomId = prefix + (allocatedRoomIds.size() + 1);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }

    public void displayAllocations() {

        System.out.println("\nAllocated Rooms:");

        for (Map.Entry<String, Set<String>> entry : roomAllocations.entrySet()) {

            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 6.0");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        Queue<Reservation> bookingQueue = new LinkedList<>();

        bookingQueue.add(new Reservation("Arjun", "Single Room"));
        bookingQueue.add(new Reservation("Priya", "Suite Room"));
        bookingQueue.add(new Reservation("Rahul", "Single Room"));
        bookingQueue.add(new Reservation("Sneha", "Suite Room"));

        BookingService bookingService = new BookingService(inventory);

        bookingService.processBookings(bookingQueue);

        bookingService.displayAllocations();

        inventory.displayInventory();
    }
}
