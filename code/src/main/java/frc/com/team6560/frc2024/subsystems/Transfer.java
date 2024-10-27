package frc.com.team6560.frc2024.subsystems;

import frc.com.team6560.lib.drivers.CANDeviceId;
import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor;
import frc.com.team6560.lib.hardware.motors.SparkMaxMotor;
import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor.MotorMode;
import frc.com.team6560.lib.subsystems.roller.BasicRollerSubsystem;

public class Transfer extends BasicRollerSubsystem {
    
    private static final double FEED_RATE = 1.0;
    private static final double REVERSE_RATE = -1.0;
    private static final int CURRENT_LIMIT = 25;
    private static final double OPEN_LOOP_RAMP_TIME = 0.1;

    public Transfer() {
        super("Transfer");
        withMotor(
            new RollerSubsystemMotor(
                new SparkMaxMotor(new CANDeviceId(20))
                .withCurrentLimit(CURRENT_LIMIT)
                .withOpenLoopRampConfig(OPEN_LOOP_RAMP_TIME)
                .withBrakeMode(),
                FEED_RATE,
                REVERSE_RATE,
                MotorMode.DUTY_CYCLE
            )
        )
        .build();
    }
}
