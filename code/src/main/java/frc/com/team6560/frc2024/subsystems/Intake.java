package frc.com.team6560.frc2024.subsystems;

import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor;
import frc.com.team6560.lib.hardware.motors.TalonFXMotor;
import frc.com.team6560.lib.hardware.motors.SparkMaxMotor;
import frc.com.team6560.lib.hardware.motors.RollerSubsystemMotor.MotorMode;
import frc.com.team6560.lib.hardware.sensors.DigitalInputSensor;
import frc.com.team6560.lib.subsystems.roller.BasicRollerSubsystem;
import frc.com.team6560.frc2024.Constants;

public class Intake extends BasicRollerSubsystem {

    private static final int DISTANCE_SENSOR_PORT = 7;

    private static final double INTAKE_FEED_RATE = 0.6;
    private static final double INTAKE_REVERSE_RATE = -0.3;

    private static final double TRANSFER_FEED_RATE = 1.0;
    private static final double TRANSFER_REVERSE_RATE = -0.5;

    private static final double INTAKE_CURRENT_LIMIT = 25.0;
    private static final int TRANSFER_CURRENT_LIMIT = 25;

    private static final double OPEN_LOOP_RAMP_TIME = 0.5;

    public Intake() {
        super("Intake");
        withMotor(
            new RollerSubsystemMotor(
                new TalonFXMotor(Constants.CanIDs.INTAKE_FEED_MOTOR_ID)
                    .withCurrentLimit(INTAKE_CURRENT_LIMIT)
                    .withOpenLoopRampConfig(OPEN_LOOP_RAMP_TIME)
                    .withBrakeMode()
                    .withReversedMotor(),
                INTAKE_FEED_RATE,
                INTAKE_REVERSE_RATE,
                MotorMode.DUTY_CYCLE
            )
        )
        .withMotor(
            new RollerSubsystemMotor(
                new SparkMaxMotor(Constants.CanIDs.INTAKE_TRANSFER_MOTOR_ID)
                    .withCurrentLimit(TRANSFER_CURRENT_LIMIT)
                    .withOpenLoopRampConfig(OPEN_LOOP_RAMP_TIME)
                    .withBrakeMode()
                    .withReversedMotor(),
                TRANSFER_FEED_RATE,
                TRANSFER_REVERSE_RATE,
                MotorMode.DUTY_CYCLE
            )
        )
        .withDigitalInputSensor(
            new DigitalInputSensor(DISTANCE_SENSOR_PORT)
            .withReversedOutput()
        )
        .withSensorMode(
            SensorMode.SENSOR_ON_PREVENTS_FORWARD
        )
        .build();
    }

    public boolean gamePieceIn() {
        return super.getSensorValue();
    }
}
