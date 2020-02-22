import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args)
    {
        int distance = 0;
        int passengers = 0;
        int time = 0;
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt())
        {
            distance = sc.nextInt();
            passengers = sc.nextInt();
            time = sc.nextInt();
        }
        Request request = new Request(distance, passengers, time);
        
        List<Booking> bookings = new ArrayList<>();

        while(sc.hasNext())
        {
            if(sc.next().charAt(0) == 'N')
            {
                Car bookedCar = new NormalCab(sc.next(), sc.nextInt());
                bookings.add(new Booking(bookedCar, new JustRide(), request));
                bookings.add(new Booking(bookedCar, new TakeACab(), request));
            }
            else {
                Car bookedCar = new PrivateCar(sc.next(), sc.nextInt());
                bookings.add(new Booking(bookedCar, new JustRide(), request));
                bookings.add(new Booking(bookedCar, new ShareARide(), request));
            }
        }
        Collections.sort(bookings);
        for(Booking booking : bookings)
        {
            System.out.println(booking.toString());
        }
    }
}
