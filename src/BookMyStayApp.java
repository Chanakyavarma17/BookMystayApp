abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type : " + getRoomType());
        System.out.println("Beds      : " + beds);
        System.out.println("Size      : " + size + " sq.ft");
        System.out.println("Price     : ₹" + price);
    }
}

/* Single Room Class */
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 2000);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

/* Double Room Class */
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 3500);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

/* Suite Room Class */
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 6000);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      Book My Stay App");
        System.out.println("      Version 2.0");
        System.out.println("=================================");

        // Creating room objects (Polymorphism)
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        System.out.println("\n--- Room Details ---");

        single.displayRoomDetails();
        System.out.println("Available : " + singleAvailability);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available : " + doubleAvailability);
        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available : " + suiteAvailability);
    }
}
