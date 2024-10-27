package frc.com.team6560.lib.hardware.motors;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.com.team6560.lib.drivers.CANDeviceId;

/**
 * Class representing a SparkMax motor implementation of the MotorIO interface.
 * Provides methods to control the motor and read its state.
 */
public class SparkMaxMotor implements MotorIO {
    private CANSparkMax sparkMax; 
    private RelativeEncoder encoder; 
    private boolean isReversed;

    /**
     * Constructor to initialize a brushless SparkMax motor with a CAN ID.
     * @param CANId The device's CAN ID.
     */
    public SparkMaxMotor(CANDeviceId CANId) {
        this.sparkMax = new CANSparkMax(CANId.getDeviceNumber(), MotorType.kBrushless);
        this.sparkMax.restoreFactoryDefaults();
        this.encoder = sparkMax.getEncoder(); 
        this.isReversed = false;
    }

    /**
     * Constructor to initialize a SparkMax motor with a CAN ID and motor type.
     * @param CANId The device's CAN ID.
     * @param motorType The type of motor used.
     */
    public SparkMaxMotor(CANDeviceId CANId, MotorType motorType) {
        this.sparkMax = new CANSparkMax(CANId.getDeviceNumber(), motorType);
        this.encoder = sparkMax.getEncoder(); 
        this.isReversed = false;
    }

    /**
     * Initialize motor with open-loop ramp rate configuration.
     * @param rampRate The ramp rate in seconds to go from 0 to full output.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withOpenLoopRampConfig(double rampRate) {
        sparkMax.setOpenLoopRampRate(rampRate); 
        return this; 
    }

    /**
     * Initialize motor with custom PID values.
     * @param kP proportional constant.
     * @param kI integral constant.
     * @param kD derivative constant.
     * @param kF feedforward constant.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withPIDProfile(double kP, double kI, double kD, double kF) {
        sparkMax.getPIDController().setP(kP);
        sparkMax.getPIDController().setI(kI);
        sparkMax.getPIDController().setD(kD);
        sparkMax.getPIDController().setFF(kF);
        return this;
    }

    /**
     * Initialize motor with set current limit.
     * @param currentLimit limit in amps.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withCurrentLimit(int currentLimit) {
        sparkMax.setSmartCurrentLimit(currentLimit);
        return this;
    }

    /**
     * Initialize motor with set forward soft limit.
     * @param maxRotations Forward soft limit in rotations.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withForwardSoftLimit(float maxRotations) {
        this.setForwardSoftLimit(maxRotations);
        return this;
    }

    /**
     * Initialize motor with set reverse soft limit.
     * @param maxRotations Backward soft limit in rotations.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withReverseSoftLimit(float maxRotations) {
        this.setReverseSoftLimit(maxRotations);
        return this;
    }

    /**
     * Initialize motor in a reversed orientation.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withReversedMotor() {
        this.setReversed(true);
        return this;
    }


    /**
     * Initialize motor with brake mode as default.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withBrakeMode() {
        this.setBrakeMode();
        return this;
    }

    /**
     * Initialize motor with coast mode as default.
     * @return SparkMaxMotor for chainability.
     */
    public SparkMaxMotor withCoastMode() {
        this.setBrakeMode();
        return this;
    }

    // Accessor methods

    @Override
    public double getDutyCyclePercent() {
        return sparkMax.get();
    }

    @Override
    public double getVelocityRPM() {
        return encoder.getVelocity(); 
    }

    @Override
    public double getPositionRotations() {
        return encoder.getPosition(); 
    }

    @Override
    public double getAppliedVolts() {
        return sparkMax.getAppliedOutput() * sparkMax.getBusVoltage(); 
    }

    @Override
    public double getCurrentSupplyAmps() {
        return sparkMax.getOutputCurrent(); 
    }

    // Setter methods

    /**
     * Set the motor direction to reversed.
     * @param reversed True to reverse the motor, false to set it to normal.
     */
    public void setReversed(boolean reversed) {
        this.isReversed = reversed;
    }

    @Override
    public void setOpenLoopDutyCycle(double dutyCycle) {
        sparkMax.set(isReversed ? -dutyCycle : dutyCycle); 
    }

    @Override
    public void setCurrentPositionAsZero() {
        encoder.setPosition(0); 
    }

    @Override
    public void setCurrentPosition(double position) {
        encoder.setPosition(position); 
    }

    @Override
    public void setVelocity(double targetVelocity) {
        sparkMax.getPIDController().setReference(isReversed ? -targetVelocity : targetVelocity, CANSparkMax.ControlType.kVelocity);
    }

    @Override
    public void setBrakeMode() {
        sparkMax.setIdleMode(CANSparkBase.IdleMode.kBrake);
    }

    @Override
    public void setCoastMode() {
        sparkMax.setIdleMode(CANSparkBase.IdleMode.kCoast);
    }

    @Override
    public void setForwardSoftLimit(float maxRotations) {
        sparkMax.setSoftLimit(SoftLimitDirection.kForward, maxRotations);
    }

    @Override
    public void setReverseSoftLimit(float maxRotations) {
        sparkMax.setSoftLimit(SoftLimitDirection.kReverse, maxRotations);
    }

    @Override
    public void stop() {
        sparkMax.stopMotor();
    }
}
