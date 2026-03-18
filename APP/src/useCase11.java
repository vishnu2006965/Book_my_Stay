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

    public synchronized void addRequest(Reservation r) {
        queue.offer(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addRoom(String type, int count) {
        inventory.put(type, count);
    }

    public synchronized boolean allocateRoom(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        System.out.println("--- Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingProcessor implements Runnable {
    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            Reservation r;
            synchronized (queue) {
                r = queue.getRequest();
            }
            if (r == null) break;

            boolean success = inventory.allocateRoom(r.getRoomType());

            if (success) {
                System.out.println(Thread.currentThread().getName() +
                        " booked " + r.getRoomType() +
                        " for " + r.getGuestName());
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed booking for " + r.getGuestName());
            }
        }
    }
}

public class useCase11{
    public static void main(String[] args) throws InterruptedException {
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Standard", 2);

        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Vishnu", "Standard"));
        queue.addRequest(new Reservation("Arun", "Standard"));
        queue.addRequest(new Reservation("Priya", "Standard"));
        queue.addRequest(new Reservation("Kiran", "Standard"));

        Thread t1 = new Thread(new BookingProcessor(queue, inventory), "Thread-1");
        Thread t2 = new Thread(new BookingProcessor(queue, inventory), "Thread-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        inventory.display();
    }
}