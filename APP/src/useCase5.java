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
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public void displayQueue() {
        System.out.println("--- Booking Request Queue ---");
        for (Reservation r : queue) {
            System.out.println("Guest: " + r.getGuestName() + " | Room: " + r.getRoomType());
        }
    }
}

public class useCase5 {
    public static void main(String[] args) {
        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Vishnu", "Standard"));
        bookingQueue.addRequest(new Reservation("Arun", "Suite"));
        bookingQueue.addRequest(new Reservation("Priya", "Double"));
        bookingQueue.addRequest(new Reservation("Kiran", "Standard"));

        System.out.println("Booking requests received...");
        bookingQueue.displayQueue();

        System.out.println("Requests are queued in FIFO order. No allocation done yet.");
    }
}