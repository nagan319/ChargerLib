package frc.com.team6560.lib.time;

import org.littletonrobotics.junction.Logger;

// Class to keep track of time for logging purposes.
public class RobotTime {
    public static double getTimestampSeconds() {
        long micros = Logger.getTimestamp();
        return (double) micros * 1.0E-6;
    }
}
