class TicketBooking {
    private boolean[] seats;

    public TicketBooking(int numSeats) {
        seats = new boolean[numSeats];
    }

    public synchronized boolean bookSeat(int seatNumber, String customerType) {
        if (seats[seatNumber]) {
            System.out.println(customerType + " - Seat " + seatNumber + " is already booked.");
            return false; 
        } else {
            seats[seatNumber] = true;
            System.out.println(customerType + " - Seat " + seatNumber + " successfully booked.");
            return true;
        }
    }

    public synchronized void printAvailableSeats() {
        System.out.print("Available Seats: ");
        for (int i = 0; i < seats.length; i++) {
            if (!seats[i]) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }
}

class RegularBookingThread extends Thread {
    private TicketBooking ticketBooking;
    private int seatNumber;

    public RegularBookingThread(TicketBooking ticketBooking, int seatNumber) {
        this.ticketBooking = ticketBooking;
        this.seatNumber = seatNumber;
    }

    @Override
    public void run() {
        ticketBooking.bookSeat(seatNumber, "Regular Customer");
    }
}

class VIPBookingThread extends Thread {
    private TicketBooking ticketBooking;
    private int seatNumber;

    public VIPBookingThread(TicketBooking ticketBooking, int seatNumber) {
        this.ticketBooking = ticketBooking;
        this.seatNumber = seatNumber;
    }

    @Override
    public void run() {
        ticketBooking.bookSeat(seatNumber, "VIP Customer");
    }
}

public class Main {
    public static void main(String[] args) {
        TicketBooking ticketBooking = new TicketBooking(5);

        Thread t1 = new RegularBookingThread(ticketBooking, 1);
        Thread t2 = new VIPBookingThread(ticketBooking, 2);
        Thread t3 = new RegularBookingThread(ticketBooking, 3);
        Thread t4 = new VIPBookingThread(ticketBooking, 1); 
        Thread t5 = new RegularBookingThread(ticketBooking, 2);
        Thread t6 = new RegularBookingThread(ticketBooking, 4);

        t2.setPriority(Thread.MAX_PRIORITY);
        t4.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();


        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ticketBooking.printAvailableSeats();
    }
}
