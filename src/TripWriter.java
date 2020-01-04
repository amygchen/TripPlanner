import java.io.*;
import java.util.ArrayList;

public class TripWriter {
    static ArrayList<Trip> trips = new ArrayList<>();
    static ArrayList<String> textOnly = new ArrayList<>();

    public static void addTrips(ArrayList<Trip> thing) {
        //writeTripToFile("PossibleTrips.txt");
        presentable(thing);

    }


    public static void initTripArrayList() {

        trips.add(new Trip("Chicago", "IL", 41, 53, 87, 38));
        trips.add(new Trip("Dallas", "TX", 32, 51, 96, 51));
        trips.add(new Trip("Detroit", "MI", 42, 25, 83, 1));
        trips.add(new Trip("Houston", "TX", 29,59,95,22));
        trips.add(new Trip("Los Angeles", "CA",33, 56,118, 24));
        trips.add(new Trip ("New York City", "NY", 40,47,73,58));
        trips.add(new Trip("Philadelphia", "PA",39,53,75,15));
        trips.add(new Trip("Phoenix","AZ", 39, 53, 112, 1));
        trips.add(new Trip("San Antonio","TX", 29,32, 98,28));
        trips.add(new Trip("San Diego","CA", 32, 44, 117, 10));
    }



    public static void writeArrayListToFile(File file, ArrayList<String>trips){
        DataOutputStream dos;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            dos = new DataOutputStream(fos);
             for (int i = 0; i < trips.size(); i++){
                 dos.writeUTF(trips.get(i));
             }

        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }

    }


    public static void writeTripToFile(String fileName){
        DataOutputStream dos;
        try {

            File f = new File(fileName);
            FileOutputStream fos = new FileOutputStream(f);
            dos = new DataOutputStream(fos);

            for(int i= 0 ; i < trips.size(); i++)
            {
                dos.writeUTF(trips.get(i).getName());
                dos.writeUTF(trips.get(i).getState());
                dos.writeInt(trips.get(i).getLatDeg());
                dos.writeInt(trips.get(i).getLatMin());
                dos.writeInt(trips.get(i).getLongDeg());
                dos.writeInt(trips.get(i).getLongMin());

            }

            dos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static ArrayList<String> presentable(ArrayList<Trip> thing){
        String name;
        String city;
        for(int i =0; i<thing.size();i++){
            name = thing.get(i).getName();
            city = thing.get(i).getState();
            textOnly.add(name + ", " + city);

        }

        return textOnly;

    }

    public static void additionBaby(String city, String state, int latDeg, int latMin, int longDeg, int longMin, ArrayList<Trip> thing){
        thing.add(new Trip(city, state, latDeg, latMin, longDeg, longMin));
    }


}
