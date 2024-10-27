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


