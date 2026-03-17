import java.util.*;

// Represents a booking request
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

public class UseCase6RoomAllocationService {

    private static Queue<BookingRequest> requestQueue = new LinkedList<>();

    private static Map<String, Integer> inventory = new HashMap<>();

    private static Map<String, Set<String>> allocatedRooms = new HashMap<>();

    private static Set<String> assignedRoomIds = new HashSet<>();

    private static int roomCounter = 1;

    public static void main(String[] args) {

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);

        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());

        requestQueue.add(new BookingRequest("Alice", "Single"));
        requestQueue.add(new BookingRequest("Bob", "Double"));
        requestQueue.add(new BookingRequest("Charlie", "Single"));
        requestQueue.add(new BookingRequest("David", "Suite"));
        requestQueue.add(new BookingRequest("Eva", "Suite")); // This will fail due to inventory

        processBookings();
    }

    public static void processBookings() {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll(); // FIFO
            String roomType = request.roomType;

            System.out.println("\nProcessing booking for: " + request.guestName);

            if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
                System.out.println("No rooms available for type: " + roomType);
                continue;
            }

            String roomId;
            do {
                roomId = roomType.substring(0,1).toUpperCase() + roomCounter++;
            } while (assignedRoomIds.contains(roomId));

            assignedRoomIds.add(roomId);
            allocatedRooms.get(roomType).add(roomId);

            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Reservation confirmed!");
            System.out.println("Guest: " + request.guestName);
            System.out.println("Room Type: " + roomType);
            System.out.println("Room ID: " + roomId);
        }

        printSummary();
    }

    public static void printSummary() {
        System.out.println("\n=== Allocation Summary ===");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + " Rooms Allocated: " + allocatedRooms.get(type));
        }
        System.out.println("\nRemaining Inventory: " + inventory);
    }
}