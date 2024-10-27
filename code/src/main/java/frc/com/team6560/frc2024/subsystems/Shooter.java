package frc.com.team6560.frc2024.subsystems;

import frc.com.team6560.lib.drivers.CANDeviceId;
import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor;
import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor.MotorMode;
import frc.com.team6560.lib.subsystems.roller.BasicRollerSubsystem;
import frc.com.team6560.lib.hardware.motors.TalonFXMotor;

public class Shooter extends BasicRollerSubsystem {

    private final double MOTOR_CURRENT_LIMIT = 50.0;

    private final double kP = .05;
    private final double kI = .01;
    private final double kD = 0.0;

    private final double MOTOR_TARGET_VELOCITY = 4000.0;
    private final double MOTOR_REVERSE_TARGET_VELOCITY = -1000.0;

    public Shooter() {
        super("Shooter");
        withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(new CANDeviceId(15))
                .withCurrentLimit(MOTOR_CURRENT_LIMIT)
                .withPIDProfile(kP, kI, kD),
                MOTOR_TARGET_VELOCITY,
                MOTOR_REVERSE_TARGET_VELOCITY,
                MotorMode.VELOCITY
            )
        )
        .withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(new CANDeviceId(16))
                .withCurrentLimit(MOTOR_CURRENT_LIMIT)
                .withPIDProfile(kP, kI, kD)
                .withReversedMotor(),
                MOTOR_TARGET_VELOCITY,
                MOTOR_REVERSE_TARGET_VELOCITY,
                MotorMode.VELOCITY
            )  
        )
        .withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(new CANDeviceId(17))
                .withCurrentLimit(MOTOR_CURRENT_LIMIT)
                .withPIDProfile(kP, kI, kD),
                MOTOR_TARGET_VELOCITY,
                MOTOR_REVERSE_TARGET_VELOCITY,
                MotorMode.VELOCITY
            )  
        )
        .build();
    }
}
