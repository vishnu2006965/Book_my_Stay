import java.util.*;

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

class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}


class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processQueue(BookingQueue queue) {
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {
                String roomId = generateRoomId(type);

                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                inventory.decrement(type);

                System.out.println("Booking Confirmed for " + r.getGuestName() +
                        " | Room Type: " + type +
                        " | Room ID: " + roomId);
            } else {
                System.out.println("Booking Failed for " + r.getGuestName() +
                        " | Room Type: " + type + " (Not Available)");
            }
        }
    }

    private String generateRoomId(String type) {
        String roomId;
        do {
            roomId = type.substring(0, 2).toUpperCase() + new Random().nextInt(1000);
        } while (allocatedRoomIds.contains(roomId));
        return roomId;
    }
}

public class Usecase6 {
    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Standard", 2);
        inventory.addRoom("Suite", 1);

        BookingQueue queue = new BookingQueue();
        queue.addRequest(new Reservation("Vishnu", "Standard"));
        queue.addRequest(new Reservation("Arun", "Standard"));
        queue.addRequest(new Reservation("Priya", "Suite"));
        queue.addRequest(new Reservation("Kiran", "Suite"));

        BookingService service = new BookingService(inventory);

        service.processQueue(queue);
    }
}