package mousemotion.inputanalysis;

import inputmangement.devicemanagement.Mouse;

public class SystemComponent {
    private final Mouse MOUSE;
    private final MouseLocationData LOCATION_DATA;
   
    public SystemComponent(Mouse mouse, MouseLocationData locationData) {
        MOUSE = mouse;
        LOCATION_DATA = locationData;

    }

    /**
     * Get the location of the mouse system component
     * 
     * @return Location of mouse in {x, y}
     */
    public MouseLocationData getLocationData() {
        return LOCATION_DATA;

    }

    /**
     * Get the mouse representing the system component
     * 
     * @return The mouse this system component represents
     */
    public Mouse getMouse() {
        return MOUSE;

    }

}
