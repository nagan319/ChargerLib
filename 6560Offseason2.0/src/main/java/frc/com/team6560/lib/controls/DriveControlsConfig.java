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

    /**
     * Get default config for driver controls
     * @return DriveControlsConfig object with default parameters.
     */
    public DriveControlsConfig getDefaultConfig() {
        return new DriveControlsConfig(
            0.4, 
            0.0, 
            0.6, 
            .025, 
            .125, 
            0.0, 
            0.3, 
            .0025, 
            0.1
        );
    }
}
