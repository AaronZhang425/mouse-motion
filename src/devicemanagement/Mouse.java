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

    /**
     * Gets the DPI of the mosue
     * 
     * @return Mouse DPI
     */
    public int getDpi() {
        return dpi;
    
    }

    /**
     * Compares if two mouse objects have the same DPI and are represented
     * by the same devices.
     * 
     * @param other The other mouse to compare to
     * @return The equality between the two mouse objects
     */
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