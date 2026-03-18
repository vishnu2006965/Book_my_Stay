import java.util.*;

abstract class Room {
    protected String type;
    protected double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getAmenities();
}

class StandardRoom extends Room {
    public StandardRoom() {
        super("Standard", 2000);
    }

    public String getAmenities() {
        return "Basic Bed, Free WiFi";
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom() {
        super("Deluxe", 3500);
    }

    public String getAmenities() {
        return "King Bed, AC, WiFi, TV";
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 5000);
    }

    public String getAmenities() {
        return "Luxury Bed, AC, WiFi, TV, Mini Bar";
    }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailability(String type) {
        return availability.getOrDefault(type, 0);
    }
}

class SearchService {
    private RoomInventory inventory;
    private List<Room> rooms;

    public SearchService(RoomInventory inventory, List<Room> rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void search() {
        System.out.println("--- Available Rooms ---");
        for (Room room : rooms) {
            int count = inventory.getAvailability(room.getType());
            if (count > 0) {
                System.out.println("Type: " + room.getType());
                System.out.println("Price: ₹" + room.getPrice());
                System.out.println("Amenities: " + room.getAmenities());
                System.out.println("Available: " + count);
                System.out.println("----------------------");
            }
        }
    }
}

public class UseCase4{
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Standard", 5);
        inventory.addRoom("Deluxe", 0);
        inventory.addRoom("Suite", 2);

        List<Room> rooms = new ArrayList<>();
        rooms.add(new StandardRoom());
        rooms.add(new DeluxeRoom());
        rooms.add(new SuiteRoom());

        SearchService searchService = new SearchService(inventory, rooms);

        System.out.println("Guest searching for rooms...");
        searchService.search();

        System.out.println("Search completed. Inventory unchanged.");
    }
}