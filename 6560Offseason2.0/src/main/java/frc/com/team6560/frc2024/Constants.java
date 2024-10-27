package frc.com.team6560.frc2024;

import frc.com.team6560.lib.drivers.CANDeviceId;

public class Constants {

    public class CanIDs {
        public static final CANDeviceId INTAKE_FEED_MOTOR_ID = new CANDeviceId(20);
        public static final CANDeviceId INTAKE_TRANSFER_MOTOR_ID = new CANDeviceId(19);
    }

    public class Intake {
        public static final int DISTANCE_SENSOR_PORT = 7;

        public static final double INTAKE_FEED_RATE = 0.6;
        public static final double INTAKE_REVERSE_RATE = -0.3;

        public static final double TRANSFER_FEED_RATE = 1.0;
        public static final double TRANSFER_REVERSE_RATE = -0.5;

    }

}