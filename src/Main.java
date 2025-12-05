public class Main {

    public static void main(String[] args) {       
        Odometer odometer = new Odometer(1000, 5);
        while (true) {
            EventData inputData = odometer.getEventData();
            System.out.println();

            byte[] buffer = odometer.eventFileReader();


            for (byte elem : buffer) {
                System.out.print(elem + " ");
            }
            
        }


    }


}
