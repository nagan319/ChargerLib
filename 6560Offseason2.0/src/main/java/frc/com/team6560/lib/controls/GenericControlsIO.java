package frc.com.team6560.lib.controls;

/**
 * Interface featuring all required methods in generic controller classes.
 */
public interface GenericControlsIO {
    
    /**
     * Translational moment in X direction.
     * @return processed joystick output.
     */
    public double driveX();

    /**
     * Translational movement in Y direction.
     * @return processed joystick output.
     */
    public double driveY();

    /**
     * Rotational movement in X direction.
     * @return processed joystick output.
     */
    public double driveRotationX();

    /**
     * Rotational movement in Y direction.
     * @return processed joystick output.
     */
    public double driveRotationY();

    /**
     * Reset gyroscope yaw.
     * @return Button output.
     */
    public boolean driveResetYaw();

    /**
     * Reset drivetrain odometry.
     * @return Button output.
     */
    public boolean driveResetGlobalPose();

}
