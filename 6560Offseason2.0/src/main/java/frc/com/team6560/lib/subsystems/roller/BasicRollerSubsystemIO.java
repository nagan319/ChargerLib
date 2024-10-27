package frc.com.team6560.lib.subsystems.roller;

/**
 * Interface for BasicRollerSubsystem class containing reuqired methods.
 */
public interface BasicRollerSubsystemIO {

    /**
     * Run subsystem in forward direction using given motors, target velocities or duty cycle rates, input sensor, and sensor mode.
     */
    void run();

    /**
     * Run subsystem in reverse direction using given motors, target velocities or duty cycle rates, input sensor, and sensor mode.
     */
    void reverse();

    /**
     * Stop subsystem.
     */
    void stop();

    /**
     * Get sensor value if applicable, return false if sensor is not present.
     * @return Sensor value or false if no sensor.
     */
    boolean getSensorValue();

}
