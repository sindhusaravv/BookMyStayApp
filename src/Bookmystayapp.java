import java.util.HashMap;
import java.util.Map;

abstract class Room {
    String roomType;
    double size;
    double pricePerNight;
    Room(String roomType, double size, double pricePerNight) {
        this.roomType = roomType;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }
    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Size: " + size);
        System.out.println("Price Per Night: ₹" + pricePerNight);
    }
}
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 120, 1500);
    }
}
class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 180, 2500);
    }
}
class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 300, 5000);
    }
}
class RoomInventory {

    private HashMap<String, Integer> inventory;

    RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room[] rooms) {

        System.out.println("\nAvailable Rooms:\n");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.roomType);

            if (available > 0) {

                room.displayRoomDetails();
                System.out.println("Available: " + available);
                System.out.println("----------------------------");
            }
        }
    }
}

class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System Version 4.1");

        RoomInventory inventory = new RoomInventory();

        Room[] rooms = {
                new SingleRoom(),
                new DoubleRoom(),
                new SuiteRoom()
        };

        RoomSearchService searchService = new RoomSearchService();

        searchService.searchAvailableRooms(inventory, rooms);
    }
}