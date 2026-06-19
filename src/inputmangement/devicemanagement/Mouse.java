package inputmangement.devicemanagement;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(
            DEVICE,
            DPI
        );

    }

    /**
     * Determine if two objects are equal. True if the other object passed is 
     * a Mouse object with the same DPI and EventDevice.
     * 
     * @param other The other mouse to compare to
     * @return True if other object has equivalent dpi and EventDevice object
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;

        }

        if (!(other instanceof Mouse)) {
            return false;

        }

        Mouse otherMouse = (Mouse) other;

        return (
            DEVICE.equals(otherMouse.DEVICE)
            && DPI == otherMouse.DPI
        );


    }

}