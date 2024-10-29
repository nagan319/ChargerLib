# Generic Subsystems

ChargerLib allows for quickly creating roller and flywheel-based subsystems, including those involving sensors and PID controls, without having to directly access hardware APIs or define additional hardware-related objects in the subsystem class.

The library contains a BasicRollerSubsystem class, which provides the base for a variety of subsystems, as well as BasicRollerSubsystemIO interface, which defines its functionality.

Additionaly, the library contains a RollerSubsystemMotor class, which is essentially a wrapper for TalonFXMotor and SparkMaxMotor objects that allows for them to be easily integrated into roller-based subsystems.

## BasicRollerSubsystemIO

The BasicRollerSubsystemIO interface defines the required functionality of a basic roller subsystem. It contains the following methods:

```run()```

Run the subsystem in the forward direction, setting the velocity or duty cycle percentage of all motors to their forward target.

```reverse()```

Run the subsystem in the reverse direction, setting the velocity or duty cycle percentage of all motors to their reverse target.

```stop()```

Stop the subsystem, setting the voltage of all motors to zero.

```boolean getSensorValue()```

Get the digital sensor value if the subsystem has a sensor attached, return false otherwise.

## RollerSubsystemMotor

The RollerSubsystemMotor class is a wrapper class for TalonFXMotor and SparkMaxMotor objects which allows for easy integration into generic roller subsystems. 

### MotorMode Enum

The RollerSubsystemMotor class contains an enum called MotorMode, which defines the mode in which the wrapped motor operates in. The enum contains two possible values:

```MotorMode.DUTY_CYCLE``` - the forward and reverse speeds of the motor are set using duty cycle percentages. Useful for subsystems with open-loop PID or no PID, such as intakes and transfers.

```MotorMode.VELOCITY``` - the forward and reverse speeds of the motor are set using target velocities. Useful for subsystems with closed-loop PID, such as shooter flywheels.

### Initialization

The RollerSubsystemMotor class can be initialized with the following parameters:
- A wrapped motor, either a TalonFX or SparkMax
- A forward target speed, defined in terms of duty cycle percentage or target velocity depending on the motor mode.
- A reverse target speed, defined in terms of duty cycle percentage or target velocity depending on the motor mode.
- A motor mode, either MotorMode.DUTY_CYCLE or MotorMode.VELOCITY, which specifies the behavior of the motor

Example of initializing a TalonFX motor with closed-loop PID:

```
RollerSubsystemMotor motor1 = new RollerSubsystemMotor(
    new TalonFXMotor(15)
        .withPIDProfile(.1, .05, 0.0)
        .withReversedMotor(),
    1000,
    -500,
    MotorMode.VELOCITY
);
```

Example of initializing a SparkMAX motor with open-loop PID:

```
RollerSubsystemMotor motor2 = new RollerSubsystemMotor(
    new SparkMaxMotor(12)
        .withCurrentLimit(50)
        .withOpenLoopRampConfig(0.8),
    0.8,
    -0.4,
    MotorMode.DUTY_CYCLE
);
```

### Additional Methods

```MotorIO getMotor()```

Returns MotorIO object (TalonFXMotor or SparkMaxMotor) with which the wrapper class was initialized.

```double getForwardTarget()```

Returns forward target speed.

```double getReverseTarget()```

Returns reverse target speed.

```MotorMode getMode()```

Returns operating mode of motor (duty cycle or velocity).

```setForwardTarget()```

Sets motor to run at forward target speed.

```setReverseTarget()```

Sets motor to run at reverse target speed.

## BasicRollerSubsystem

The BasicRollerSubsystem provides a base for creating generic roller subsystems such as flywheels, intakes, and transfers. It allows for quickly building subsystems using wrapped motors and sensors, along with configuring sensor modes.

### Example Usage

#### Basic Roller Subsystem

A basic 1-motor subystem without sensors can be created as follows:

```
public class Roller extends BasicRollerSubsystem {

    public Roller() {
        super("Roller");
        withMotor(
            new RollerSubsystemMotor(
                new SparkMaxMotor(10)
                    .withCurrentLimit(50)
                    .withOpenLoopRampConfig(1.0),
                0.8,
                -0.4,
                MotorMode.DUTY_CYCLE    
            )
        )
        .build();
    }

}
```
The 'super' constructor must be called with the name of the subsystem (ex. "Roller") - this defines the shuffleboard display name of the subsystem.

After motors are added using the withMotor constructor, the 'build' method must be chained so that shuffleboard values are properly displayed.

#### Subsystem With Multiple Motors and Sensor

A subsystem containing multiple motors and a sensor can be created as follows:

```
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

}
```

### Sensor Modes

Subsystems can be configured with various sensor modes, which determine how sensor input affects the functionality of the subsystem. The sensor modes are as follows:

```DEACTIVATED``` - sensor output does not affect subsystem functionality.

```FORWARD_REQUIRES_SENSOR_ON``` - forward motion of the subsystem requires the sensor to read 'true'. Reverse motion does not require sensor output.

```SENSOR_ON_PREVENTS_FORWARD``` - forward motion of the subsystem requires the sensor to read 'false'. Reverse motion does not require sensor output.

```REVERSE_REQUIRES_SENSOR_ON``` - reverse motion of the subsystem requires the sensor to read 'true'. Forward motion does not require sensor output.

```SENSOR_ON_PREVENTS_REVERSE``` - reverse motion of the subsystem requires the sensor to read 'false'. Forward motion does not require sensor output.
