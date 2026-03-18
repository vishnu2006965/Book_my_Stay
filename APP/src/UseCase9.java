import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public void validateRoomType(String type) throws InvalidBookingException {
        if (!inventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
    }

    public void validateAvailability(String type) throws InvalidBookingException {
        if (inventory.get(type) <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }
    }

    public void bookRoom(String type) throws InvalidBookingException {
        validateRoomType(type);
        validateAvailability(type);
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("--- Inventory ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class UseCase9{
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        inventory.addRoom("Standard", 1);
        inventory.addRoom("Suite", 0);

        try {
            System.out.println("Attempting valid booking...");
            inventory.bookRoom("Standard");
            System.out.println("Booking successful");

            System.out.println("Attempting booking with no availability...");
            inventory.bookRoom("Suite");
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            System.out.println("Attempting booking with invalid room type...");
            inventory.bookRoom("Deluxe");
        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }

        inventory.displayInventory();
    }
}