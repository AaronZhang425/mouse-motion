public class Main {
    public static void main(String[] args) {
        String os = getOS();
        System.out.println(os);
        System.out.println();
    }

    public static String getOS() {
        return System.getProperty("os.name");
    }

}
