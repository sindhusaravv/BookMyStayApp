abstract class Room {
String roomType;
    double size;
    double pricePerNight;
    int availability;

    Room(String roomType, double size, double pricePerNight, int availability) {
        this.roomType = roomType;
        this.size = size;
        this.pricePerNight = pricePerNight;
        this.availability = availability;
    }
    void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Size: " + size);
        System.out.println("Price per Night: " + pricePerNight);
        System.out.println("Availability: " + availability);
    }
}
class SingleRoom extends Room {

    SingleRoom() {
        super("Single Room", 250.0, 1500.0, 5);
    }
}
class DoubleRoom extends Room {

    DoubleRoom() {
        super("Double Room", 400.0, 2500.0, 3);
    }
}

class SuiteRoom extends Room {

    SuiteRoom() {
        super("Suite Room", 750.0, 5000.0, 2);
    }
}
class UseCase2RoomInitialization {
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay");
        System.out.println("Hotel Booking System Version 2.1");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();
        single.displayRoomDetails();
        System.out.println();
        doubleRoom.displayRoomDetails();
        System.out.println();
        suite.displayRoomDetails();
        System.out.println();
        System.out.println("Thank you for using Book My Stay!");
    }
}