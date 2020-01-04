import static java.lang.Math.PI;

public class totalMiles {

    double RADIAN_FACTOR = 180 / PI;
    int EARTH_RADIUS = 6371;


    public double calculateDistance(int lat1, int lat2, int long1, int long2)
    {
        double x = (Math.sin(lat1/RADIAN_FACTOR) * Math.sin(lat2/RADIAN_FACTOR))
                + (Math.cos(lat1/RADIAN_FACTOR)
                * Math.cos(lat2/RADIAN_FACTOR)
                * Math.cos((long2/RADIAN_FACTOR) - (long1/RADIAN_FACTOR)));
        double distance = EARTH_RADIUS * Math.atan((Math.sqrt(1 - Math.pow(x, 2))/x));
        return distance;
    }




}
