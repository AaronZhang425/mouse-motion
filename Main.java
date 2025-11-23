public class Main {

    public static void main(String[] args) {
        // Mouse mouse = new Mouse(2);
        // System.out.println(mouse.getMouseHandlerFile());
        
        
        Odometer odometer = new Odometer(1000, 5);
        while (true) {
            long[] time = odometer.getEventTime();
            System.out.println();

            for (long elem : time) {
                System.out.print(elem + ", ");
                

            }

            System.out.println();

        }


    }


}
