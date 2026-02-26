package devicemanagement;

// public record Mouse(InputDevice device, int dpi) {}

public class Mouse {
    private final InputDevice device;
    private final int dpi;

    public Mouse(InputDevice device, int dpi) {
        this.device = device;
        this.dpi = dpi;
    }

    /**
     * Gets the device that represents the mouse
     * 
     * @return Mouse device
     */
    public InputDevice getDevice() {
        return device;
    
    }

    public int getDpi() {
        return dpi;
    
    }

    public boolean equals(Mouse other) {
        if (other == null) {
            return false;
        }

        return (
            device.equals(other.device) &&
            dpi == other.dpi
        );
    }




}