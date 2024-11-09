package frc.com.team6560.lib.controls;

import edu.wpi.first.wpilibj.XboxController;
import frc.com.team6560.lib.util.NumberStepper;
import frc.com.team6560.lib.util.PovNumberStepper;

/**
 * Generic controls class if using 2 Xbox controllers, contains ready methods for driving and swerve calibration.
 * Can be subclassed for more concrete controls implementation.
 */
public class GenericDoubleXboxControls implements GenericControlsIO {
    protected XboxController driverController;
    protected XboxController shooterController;
    private DriveControlsConfig config;

    private final PovNumberStepper speed;
    private final PovNumberStepper turnSpeed;

    public GenericDoubleXboxControls(XboxController driverController, XboxController shooterController, DriveControlsConfig config) {

        this.driverController = driverController;
        this.shooterController = shooterController;
        this.config = config;

        this.speed = new PovNumberStepper(
            new NumberStepper(
                config.speedInitialPercent, 
                config.speedMinPercent,
                config.speedMaxPercent, 
                config.speedStepPercent
            ),
            driverController,
            PovNumberStepper.PovDirection.VERTICAL
        );

        this.turnSpeed = new PovNumberStepper(
            new NumberStepper(
                config.turnSpeedInitialPercent, 
                config.turnSpeedMinPercent, 
                config.turnSpeedMaxPercent,
                config.speedStepPercent
            ),
            driverController,
            PovNumberStepper.PovDirection.HORIZONTAL
        );
    }

    /**
     * Filters out values with absolute value less than deadband, normalizes otherwise.
     * Output is in range [-1, 1] if deadband is in range [0, 1)
     * @param value input value
     * @param deadband deadband range
     * @return modified value
     */
    private static double deadband(double value, double deadband) throws IllegalArgumentException {
        if (deadband < 0 || deadband >= 1) {
            throw new IllegalArgumentException("Deadband must be in range [0, 1)");
        }
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } 
            return (value + deadband) / (1.0 - deadband);
        }
        return 0.0;
    }
    
    /**
     * Modifies controller axis inputs by applying deadband and squaring value.
     * Modified value is in range [-1, 1]
     * @param value input value
     * @param deadband deadband value
     * @return modified value
     */
    private static double modifyAxis(double value, double deadband) {
        value = deadband(value, deadband);
        value = Math.copySign(value * value, value);
        return value;
    }

    @Override
    public double driveX() {
        var sign = this.config.reverseY ? -1 : 1;
        return sign * modifyAxis(driverController.getLeftY() * speed.get(), config.deadband);
    }

    @Override
    public double driveY() {
        var sign = this.config.reverseX ? -1 : 1;
        return sign * modifyAxis(driverController.getLeftX() * speed.get(), config.deadband);
    }

    @Override
    public double driveRotationX() {
        return modifyAxis(driverController.getRightX() * turnSpeed.get(), config.deadband);
    }

    @Override
    public double driveRotationY() {
        return modifyAxis(driverController.getRightY() * turnSpeed.get(), config.deadband);
    }

    @Override
    public boolean driveResetYaw() {
        return driverController.getStartButton();
    }

    @Override
    public boolean driveResetGlobalPose() {
        return driverController.getBackButton();
    }
}
