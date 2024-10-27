package frc.com.team6560.lib.hardware.motors;

/**
 * Generic interface for interacting with with motors. 
 */
public interface MotorIO {

    /**
     * Gets the current duty cycle rate in terms of percentage of total output.
     * @return Percentage of duty cycle motor is currently running at.
     */
    double getDutyCyclePercent();

    /**
     * Gets the current velocity in rotations per minute from the motor.
     * @return The velocity in rotations per minute.
     */
    double getVelocityRPM();

    /**
     * Gets the current position in terms of mechanism rotations.
     * @return The position in rotations.
     */
    double getPositionRotations();

    /**
     * Gets the applied voltage to the motor.
     * @return The applied voltage in volts.
     */
    double getAppliedVolts();

    /**
     * Gets the current supply amps drawn by the motor.
     * @return The current in supply amps.
     */
    double getCurrentSupplyAmps();

    /**
     * Sets the motor to run forward or reversed depending on the input value.
     * @param reversed Run in reversed it true.
     */
    void setReversed(boolean reversed);

    /**
     * Sets the motor to an open-loop duty cycle.
     * @param dutyCycle The duty cycle to set, ranging from -1.0 (full reverse) to 1.0 (full forward).
     */
    void setOpenLoopDutyCycle(double dutyCycle);

    /**
     * Sets motor velocity to target using PID.
     * @param targetVelocity
     */
    void setVelocity(double targetVelocity);

    /**
     * Sets the current position of the motor to a specified value.
     * @param positionUnits The desired current position in mechanism rotations.
     */
    void setCurrentPosition(double position);

    /**
     * Resets the current position of the motor to zero.
     */
    void setCurrentPositionAsZero();

    /**
     * Sets motor to brake mode.
     */
    void setBrakeMode();

    /**
     * Sets motor to coast mode.
     */
    void setCoastMode();

    /**
     * Set motor forward soft limit.
     * @param maxRotations Soft limit as number of mechanism rotations.
     */
    void setForwardSoftLimit(float maxRotations);

    /**
     * Set motor reverse soft limit.
     * @param maxRotations Soft limit as number of mechanism rotations.
     */
    void setReverseSoftLimit(float maxRotations);

    /**
     * Stop motor.
     */
    void stop();

}
