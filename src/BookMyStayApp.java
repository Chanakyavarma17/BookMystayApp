import java.util.*;

/**
 * UseCase7AddOnServiceSelection
 *
 * Demonstrates how optional add-on services can be attached
 * to an existing reservation without modifying booking logic.
 *
 * @author Chani
 * @version 7.0
 */

/* Add-On Service Class */
class AddOnService {

    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " - ₹" + price);
    }
}

/* Add-On Service Manager */
class AddOnServiceManager {

    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Attach service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to Reservation " + reservationId + ": " + service.getServiceName());
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nSelected Services for Reservation " + reservationId + ":");

        for (AddOnService service : services) {
            service.displayService();
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) return 0;

        double total = 0;

        for (AddOnService service : services) {
            total += service.getPrice();
        }

        return total;
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        Book My Stay App");
        System.out.println("        Version 7.0");
        System.out.println("=================================");

        // Sample reservation ID (created earlier in booking system)
        String reservationId = "S1";

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Guest selects add-on services
        serviceManager.addService(reservationId, new AddOnService("Breakfast", 500));
        serviceManager.addService(reservationId, new AddOnService("Airport Pickup", 1200));
        serviceManager.addService(reservationId, new AddOnService("Spa Access", 800));

        // Display services
        serviceManager.displayServices(reservationId);

        // Calculate total add-on cost
        double totalCost = serviceManager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);
    }
}
