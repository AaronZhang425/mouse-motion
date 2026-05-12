package inputanalysis;

import devicemanagement.Mouse;

public class SystemComponent {
    private final Mouse MOUSE;
    private final MouseLocationData LOCATION_DATA;
   
    public SystemComponent(Mouse mouse, MouseLocationData locationData) {
        this.MOUSE = mouse;
        this.LOCATION_DATA = locationData;

    }

    /**
     * Get the location of the mouse system component
     * 
     * @return
     */
    public MouseLocationData getLocationData() {
        return LOCATION_DATA;

    }

    /**
     * Get the mouse representing the system component
     * 
     * @return
     */
    public Mouse getMouse() {
        return MOUSE;

    }

}
