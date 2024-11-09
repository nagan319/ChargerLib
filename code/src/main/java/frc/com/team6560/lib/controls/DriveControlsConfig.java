package frc.com.team6560.lib.controls;

/**
 * Config class for encapsulating all required variables for setting up drive control speeds, turn speeds, and deadbands.
 */
public class DriveControlsConfig {
    public final double speedInitialPercent;
    public final double speedMinPercent;
    public final double speedMaxPercent;
    public final double speedStepPercent;

    public final double turnSpeedInitialPercent;
    public final double turnSpeedMinPercent;
    public final double turnSpeedMaxPercent;
    public final double turnSpeedStepPercent;

    public final double deadband;

    public final boolean reverseX;
    public final boolean reverseY;

    /**
     * Private constructor to enforce usage of builder.
     * @param builder Builder to initialize class.
     */
    private DriveControlsConfig(Builder builder) {
        this.speedInitialPercent = builder.speedInitialPercent;
        this.speedMinPercent = builder.speedMinPercent;
        this.speedMaxPercent = builder.speedMaxPercent;
        this.speedStepPercent = builder.speedStepPercent;

        this.turnSpeedInitialPercent = builder.turnSpeedInitialPercent;
        this.turnSpeedMinPercent = builder.turnSpeedMinPercent;
        this.turnSpeedMaxPercent = builder.turnSpeedMaxPercent;
        this.turnSpeedStepPercent = builder.turnSpeedStepPercent;

        this.deadband = builder.deadband;

        this.reverseX = builder.reverseX;
        this.reverseY = builder.reverseY;
    }

    /**
     * Builder for DriveControlsConfig.
     */
    public static class Builder {

        private double speedInitialPercent = 0.4;
        private double speedMinPercent = 0.0; 
        private double speedMaxPercent = 0.6; 
        private double speedStepPercent = 0.025;

        private double turnSpeedInitialPercent = 0.125;
        private double turnSpeedMinPercent = 0.0;
        private double turnSpeedMaxPercent = 0.3;
        private double turnSpeedStepPercent = 0.0025;

        private double deadband = 0.1;

        private boolean reverseX = false;
        private boolean reverseY = false;

        public Builder setSpeedInitialPercent(double percent) { this.speedInitialPercent = percent; return this; }
        public Builder setSpeedMinPercent(double percent) { this.speedMinPercent = percent; return this; }
        public Builder setSpeedMaxPercent(double percent) { this.speedMaxPercent = percent; return this; }
        public Builder setSpeedStepPercent(double percent) { this.speedStepPercent = percent; return this; }

        public Builder setTurnSpeedInitialPercent(double percent) { this.turnSpeedInitialPercent = percent; return this; }
        public Builder setTurnSpeedMinPercent(double percent) { this.turnSpeedMinPercent = percent; return this; }
        public Builder setTurnSpeedMaxPercent(double percent) { this.turnSpeedMaxPercent = percent; return this; }
        public Builder setTurnSpeedStepPercent(double percent) { this.turnSpeedStepPercent = percent; return this; }

        public Builder setDeadband(double deadband) { this.deadband = deadband; return this; }

        public Builder setReverseX(boolean reverseX) {this.reverseX = reverseX; return this; }
        public Builder setReverseY(boolean reverseY) {this.reverseY = reverseY; return this; }

        public DriveControlsConfig build() {
            return new DriveControlsConfig(this);
        }
    }
}
