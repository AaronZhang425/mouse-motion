import devicemanagement.InputReader;

public class Main {

    public static void main(String[] args) {       
        InputReader odometer = new InputReader(1000, 2);

        // int num = Key.TEMP.getValue();

        while (true) {
            EventData inputData = odometer.getEventData();
            System.out.println();

            byte[] buffer = odometer.eventFileReader();


            for (byte elem : buffer) {
                System.out.print(elem + " ");
            }
            
            // System.out.println(num);

        }


    }


}
