import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " - " + roomType;
    }
}

class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    public SystemState load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state loaded successfully.");
            return (SystemState) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.");
            return null;
        }
    }
}

class BookingSystem {

    Map<String, Integer> inventory = new HashMap<>();
    List<Reservation> bookings = new ArrayList<>();

    public BookingSystem() {
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
        inventory.put("Standard", 3);
    }

    public void bookRoom(String id, String type) {
        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            bookings.add(new Reservation(id, type));
            System.out.println("Booking confirmed: " + id);
        } else {
            System.out.println("Booking failed: No rooms available.");
        }
    }

    public void showData() {
        System.out.println("\nInventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }

        System.out.println("\nBookings:");
        for (Reservation r : bookings) {
            System.out.println(r);
        }
    }
}

 class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();
        BookingSystem system = new BookingSystem();

        SystemState loadedState = persistence.load();

        if (loadedState != null) {
            system.inventory = loadedState.inventory;
            system.bookings = loadedState.bookings;
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Room Type: ");
        String type = sc.nextLine();

        system.bookRoom(id, type);

        system.showData();

        SystemState newState = new SystemState(system.inventory, system.bookings);
        persistence.save(newState);

        sc.close();
    }
}