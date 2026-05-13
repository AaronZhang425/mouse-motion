package devicemanagement;

public class Mouse {
    /**
     * The EventDevice object that represents the mosue
     */
    private final EventDevice DEVICE;

    /**
     * The dots/counts per inch of the mouse 
     */
    private final int DPI;

    public Mouse(EventDevice device, int dpi) {
        this.DEVICE = device;
        this.DPI = dpi;
    }

    /**
     * Gets the device that represents the mouse
     * 
     * @return Mouse device
     */
    public EventDevice getDevice() {
        return DEVICE;
    
    }

    /**
     * Gets the DPI of the mosue
     * 
     * @return Mouse DPI
     */
    public int getDpi() {
        return DPI;
    
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
            DEVICE.equals(other.DEVICE) &&
            DPI == other.DPI
        );
    }

}