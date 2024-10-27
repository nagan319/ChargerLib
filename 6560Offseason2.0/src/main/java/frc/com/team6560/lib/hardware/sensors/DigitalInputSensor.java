package frc.com.team6560.lib.hardware.sensors;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Class representing an abstraction of a digital input sensor such as a limit switch.
 */
public class DigitalInputSensor {
    
    private DigitalInput sensor;
    private boolean reverseOutput;

    /**
     * Initialize digital input sensor.
     * @param DIO_Port Port that sensor is attached to.
     */
    public DigitalInputSensor(int DIO_Port) {
        sensor = new DigitalInput(DIO_Port);
        reverseOutput = false;
    }

    /**
     * Initialize sensor with reversed output (false reads as true).
     * @return DigitalInputSensor for chainability.
     */
    public DigitalInputSensor withReversedOutput() {
        reverseOutput = true;
        return this;
    }

    /**
     * Get sensor value
     * @return Sensor reading
     */
    public boolean get() {
        return reverseOutput ? !sensor.get() : sensor.get();
    }

}
