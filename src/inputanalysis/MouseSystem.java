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

    /**
     * Gets the dot product of the total displacement of the mice
     * 
     * @param mouse1 A mouse tracker
     * @param mouse2 A mouse tracker representing another mouse
     * @return Dot product of the total displacement of the mice
     */
    private double getTotalDisplacementDotProduct(
        MouseMotionTracker mouse1,
        MouseMotionTracker mouse2
    ) {
        return getDotProduct(
            mouse1.getDisplacement(),
            mouse2.getDisplacement()
        );

    }

    /**
     * Gets the dot product of 2 vectors that are 2D
     * 
     * @param vector1
     * @param vector2
     * @return
     */
    private double getDotProduct(double[] vector1, double[] vector2) {
        return vector1[0] * vector2[0] + vector1[1] + vector2[1];

    }

    

}
