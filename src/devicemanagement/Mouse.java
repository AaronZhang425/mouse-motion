package devicemanagement;

// public record Mouse(InputDevice device, int dpi) {}

public class Mouse {
    private InputDevice device;
    private int dpi;

    public Mouse(InputDevice device, int dpi) {
        this.device = device;
        this.dpi = dpi;
    }

    public InputDevice getDevice() {
        return device;
    
    }

    @Deprecated
    public InputDevice device() {
        return getDevice();
    
    }

    public int getDpi() {
        return dpi;
    
    }

    @Deprecated
    public int dpi() {
        return getDpi();
    
    }

    public boolean equals(Mouse other) {
        if (other == null || !(other instanceof Mouse)) {
            return false;
        }

        return (
            device.equals(other.device) &&
            dpi == other.dpi
        );
    }




}
