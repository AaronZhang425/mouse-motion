package inputanalysis;

import java.util.HashMap;

public class MouseSystem {
    private HashMap<MouseMotionTracker, double[]> mouseArrangement;

    public MouseSystem(HashMap<MouseMotionTracker, double[]> mouseArrangement) {
        this.mouseArrangement = (
            new HashMap<MouseMotionTracker, double[]>(mouseArrangement)
        );

    }

    public HashMap<MouseMotionTracker, double[]> getMouseArrangement() {
        return new HashMap<>(mouseArrangement);
        
    }

    

}
