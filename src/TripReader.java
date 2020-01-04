import java.io.*;
import java.util.ArrayList;

public class TripReader {
    static ArrayList<Trip> trips = new ArrayList<Trip>();

   /* public static void main(String[] args) {
        loadTripsFromFile("PossibleTrips.txt");
        System.out.println(trips);
    }
    */


    public static void loadTripsFromFile(String fileName) {
        try {
            File f = new File(fileName);
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);

            while(fis.available() > 0)
            {
                String city = dis.readUTF();
                String state = dis.readUTF();
                int latDeg  = dis.readInt();
                int latMin = dis.readInt();
                int longDeg = dis.readInt();
                int longMin = dis.readInt();


                trips.add(new Trip(city, state, latDeg, latMin, longDeg, longMin));
            }



        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


    }


}
