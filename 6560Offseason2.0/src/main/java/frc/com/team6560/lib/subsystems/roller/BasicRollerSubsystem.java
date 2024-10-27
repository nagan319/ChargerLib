package frc.com.team6560.lib.subsystems.roller;

import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor;
import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor.MotorMode;
import frc.com.team6560.lib.hardware.sensors.DigitalInputSensor;

import java.util.List;
import java.util.ArrayList;

import static frc.com.team6560.lib.util.NetworkTable.NtValueDisplay.ntDispTab;

/** 
 * Basic roller subsystem that can be subclassed as needed.
 * Covers any subsystem that includes a combination of Talon or Spark-based motors and (optionally) the functionality of which is determined entirely by a single digital sensor.
 *  Works for intakes, flywheels, transfers etc.
 */
public class BasicRollerSubsystem implements BasicRollerSubsystemIO {

    private String name;

    private List<RollerSubsystemMotor> motors;
    private DigitalInputSensor digitalInputSensor;
    private SensorMode sensorMode;

    public enum SensorMode {
        DEACTIVATED,
        FORWARD_REQUIRES_SENSOR_ON,
        SENSOR_ON_PREVENTS_FORWARD,
        REVERSE_REQUIRES_SENSOR_ON,
        SENSOR_ON_PREVENTS_REVERSE
    }

    public BasicRollerSubsystem(String name) {
        this.name = name;
        this.motors = new ArrayList<>();
        this.digitalInputSensor = null;
        this.sensorMode = SensorMode.DEACTIVATED;
    }

    /**
     * Initialize with a wrapped motor.
     * @param motor The motor to use.
     * @return BasicRollerSubsystem for chainability.
     */
    public BasicRollerSubsystem withMotor(RollerSubsystemMotor motor) {
        this.motors.add(motor);
        return this; 
    }

    /**
     * Initialize with a digital input sensor.
     * @param sensor The digital input sensor to use.
     * @return BasicRollerSubsystem for chainability.
     */
    public BasicRollerSubsystem withDigitalInputSensor(DigitalInputSensor sensor) {
        this.digitalInputSensor = sensor;
        return this; 
    }

    /**
     * Initialize with a sensor mode.
     * @param mode The sensor mode to use.
     * @return BasicRollerSubsystem for chainability.
     */
    public BasicRollerSubsystem withSensorMode(SensorMode mode) throws IllegalStateException {
        if (this.digitalInputSensor != null) {
            this.sensorMode = mode;
            return this;
        } else {
            throw new IllegalStateException("Digital input sensor is not initialized.");
        }
    }

    /**
     * Build basic roller subsystem with given motors and sensors. Call after full initialization.
     * Adds relevant values to ntDispTab.
     */
    public void build() {
        for (int i = 0; i < this.motors.size(); i++) {
            
            RollerSubsystemMotor motor = this.motors.get(i);
            MotorMode motorMode = motor.getMode(); 

            if (motorMode == MotorMode.DUTY_CYCLE) {
                ntDispTab(name).add("Motor "+i+" Velocity: ", motor.getMotor()::getVelocityRPM);
            } else {
                ntDispTab(name).add("Motor "+i+" Duty Cycle %: ", motor.getMotor()::getDutyCyclePercent);
            }
        }
        if (digitalInputSensor != null) {
            ntDispTab(name).add("Sensor Reading: ", digitalInputSensor::get);
        }
    }

    @Override
    public void run() {
        if (runOK()) {
            for (RollerSubsystemMotor motorWrapper : this.motors) {
                motorWrapper.setForwardTarget();
            }
        }
    }

    private boolean runOK() {
        switch (this.sensorMode) {
            case DEACTIVATED:
                return true;
            case FORWARD_REQUIRES_SENSOR_ON:
                return this.digitalInputSensor.get();
            case SENSOR_ON_PREVENTS_FORWARD:
                return !this.digitalInputSensor.get();
            default:
                return true;
        }
    }

    @Override
    public void reverse() {
        if (reverseOK()) {
            for (RollerSubsystemMotor motorWrapper : this.motors) {
                motorWrapper.setReverseTarget(); 
            }
        }
    }

    private boolean reverseOK() {
        switch (this.sensorMode) {
            case DEACTIVATED:
                return true;
            case REVERSE_REQUIRES_SENSOR_ON:
                return this.digitalInputSensor.get();
            case SENSOR_ON_PREVENTS_REVERSE:
                return !this.digitalInputSensor.get();
            default:
                return true;
        }
    }

    @Override
    public void stop() {
        for (RollerSubsystemMotor motorWrapper : this.motors) {
            motorWrapper.getMotor().stop();
        }
    }

    @Override
    public boolean getSensorValue() {
        return (this.digitalInputSensor == null) ? false : this.digitalInputSensor.get();
    }
}
