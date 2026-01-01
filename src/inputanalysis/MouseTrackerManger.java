package inputanalysis;

public class MouseTrackerManger {
    private MouseMotionTracker mouseTracker;
    private Thread runner;

    public MouseTrackerManger(MouseMotionTracker mouseTracker) {
        this.mouseTracker = mouseTracker;

        runner = new Thread(mouseTracker);

    }

    public void start() {
        runner.start();
    }


}
