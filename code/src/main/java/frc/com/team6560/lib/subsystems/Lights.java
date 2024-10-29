package frc.com.team6560.lib.subsystems;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.com.team6560.lib.util.NetworkTable.NtValueDisplay.ntDispTab;

/**
 * Generic class for LED lights.
 */
public class Lights extends SubsystemBase {
    
    private static final double DEFAULT_BRIGHTNESS = 0.7;

    private final CANdle candle;

    /**
     * Initialize LED light subsystem. Displays current consumed by CANdle on Shuffleboard.
     * @param name Subsystem name to display on Shuffleboard
     * @param canID CAN ID for Phoenix CANdle
     */
    public Lights(String name, int canID) {
        this.candle = new CANdle(canID);
        candle.configBrightnessScalar(DEFAULT_BRIGHTNESS);
        ntDispTab(name).add("CANDle current", this::getCurrent);
    }

    /**
     * Get current being drawn by lights.
     * @return Current in amps.
     */
    public double getCurrent() {
        return candle.getCurrent();
    }

    /**
     * Set brightness to specified value. Value must be in range [0, 1]
     * @param brightness Brightness value to set.
     */
    public void setBrightness(double brightness) {
        if (brightness < 0.0 || brightness > 1.0) {
            throw new IllegalArgumentException("Brightness value must be in range [0, 1]");
        }
        candle.configBrightnessScalar(brightness);
    }

    /**
     * Set color of lights using a WPILib Color object.
     * @param color Target color to set.
     */
    public void setColor(Color color){
        candle.setLEDs((int)(color.red *255),(int)(color.green *255), (int)(color.blue *255));
    }

    /**
     * Set color of lights by specifying RGB values.
     * @param r Red (0-255)
     * @param g Green (0-255)
     * @param b Blue (0-255)
     */
    public void setColor(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("Color values must be in range [0, 255]");       
        }
        candle.setLEDs(r, g, b);      
    }

}
