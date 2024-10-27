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

    public DriveControlsConfig(double speedInitialPercent, double speedMinPercent, double speedMaxPercent, double speedStepPercent, double turnSpeedInitialPercent, double turnSpeedMinPercent, double turnSpeedMaxPercent, double turnSpeedStepPercent, double deadband) {
        this.speedInitialPercent = speedInitialPercent;
        this.speedMinPercent = speedMinPercent;
        this.speedMaxPercent = speedMaxPercent;
        this.speedStepPercent = speedStepPercent;

        this.turnSpeedInitialPercent = turnSpeedInitialPercent;
        this.turnSpeedMinPercent = turnSpeedMinPercent;
        this.turnSpeedMaxPercent = turnSpeedMaxPercent;
        this.turnSpeedStepPercent = turnSpeedStepPercent;

        this.deadband = deadband;
    }

    public DriveControlsConfig() {
        this.speedInitialPercent = 0.4;
        this.speedMinPercent = 0.0; 
        this.speedMaxPercent = 0.6; 
        this.speedStepPercent = .025;

        this.turnSpeedInitialPercent = .125;
        this.turnSpeedMinPercent = 0.0;
        this.turnSpeedMaxPercent = 0.3;
        this.turnSpeedStepPercent = .0025;
    
        this.deadband = 0.1;
    }
}
