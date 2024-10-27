# Motor Wrappers

A goal of ChargerLib is to provide teams with wrappers for the SparkMax and TalonFX motor controller APIs that utilize identical IO functions, allowing for newer team members to utilize motor controllers without needing to understand vendor-specific motor configuration and API usage.

This is achieved by using a generic interface, MotorIO, for both motor types, which allows for initializing subsystems without the need to distinguish between TalonFX and SparkMax motor controllers.

## MotorIO

The MotorIO interface defines all methods that would typically be used to receive and set motor states. It defines the following methods:

```double getDutyCyclePercent() ``` 

Allows for seeing what percentage of its duty cycle a motor is currently running at. This is generally useful for motors that form a part of a subsystem which does not require closed-loop PID, such as simple intakes and transfers.

```double getVelocityRPM() ```

Allows for seeing what velocity, in RPM, a motor is currently running at. Useful for flywheel-type subsystems involving closed-loop PID which tend to have a target velocity.

```double getPositionRotations() ```

Allows for seeing what position, in mechanism rotations, a motor's encoder is currently set to. 

```double getAppliedVolts()```

Returns the voltage that is currently applied to the motor. May be useful for diagnosing electrical or hardware issues.

```double getCurrentSupplyAmps() ```

Returns the current supply, in amps, being drawn by the motor. May be useful for diagnosing electrical or hardware issues.

```setReversed(boolean reversed)```

Sets the motor to run in reverse if the input parameter is true, forward otherwise.

```setOpenLoopDutyCycle(double dutyCycle)```

Sets the motor to run at a certain percentage of its duty cycle. This is generally useful for motors that form a part of a subsystem which does not require closed-loop PID, such as simple intakes and transfers.

```setVelocity(double targetVelocity)```

Sets the motor's velocity to a specified target value. This is generally useful for motors that form a part of a subsystem which does not require closed-loop PID, such as simple intakes and transfers.

```setCurrentPosition(double position)```

Sets the current encoder position of a motor to a certain value. Can be useful when used in conjunction with limit switches to reset subsystem states mid-game.

```setCurrentPositionAsZero()```

Sets the current encoder position of a motor to zero. Can be useful when used in conjunction with limit switches to reset subsystem states mid-game.

```setBrakeMode()```

Sets the motor to brake mode, which allows it to stop faster and respond with a frictional force to any external movement.

```setCoastMode()```

Sets the motor to coast mode, essentially allowing it to move freely if no current is supplied.

```setForwardSoftLimit(float maxRotations)```

Sets the motor's forward soft limit to a certain number of rotations.

```setReverseSoftLimit(float maxRotations)```

Sets the motor's reverse soft limit to a certain number of rotations.

```stop()```

Stops the motor, setting voltage to zero.

## SparkMaxMotor

The SparkMaxMotor class provides a wrapper class for motors running with the  CANSparkMax encoder that follows the MotorIO interface. 

In addition to overriden interface methods, the class also includes constructors for initializing the motor with parameters such as a set PID or open-loop profile, a reversed motor, or a set current limit. 

The following example initializes a brushless SparkMaxMotor set to CAN ID 10, with an open loop ramp time of 1 second: 

```
SparkMaxMotor motor = new SparkMaxMotor(10, MotorType.kBrushless)
    .withOpenLoopConfig(1.0);
```

The following initializes a SparkMaxMotor, brushless by default, set to CAN ID 10, with a PID profile of P = .05, I = .01, D = 0.0, and FF = 0.0, a current limit of 30 amps, a forward soft limit of 15.2 rotations, a reverse soft limit of -10.8 rotations, brake mode, and a reversed motor:

```
SparkMaxMotor motor = new SparkMaxMotor(15)
    .withPIDProfile(.05, .01, 0.0, 0.0)
    .withCurrentLimit(30)
    .withForwardSoftLimit(15.2)
    .withReverseSoftLimit(-10.8)
    .withBrakeMode()
    .withReversedMotor();
```

Note that unlike the TalonFX, which accepts values of type double, the SparkMAX encoder only allows for setting current limits to integer values.

## TalonFXMotor

The TalonFXMotor class provides a wrapper class for motors running with the TalonFX encoder that follows the MotorIO interface. 

In addition to overriden interface methods, the class also includes constructors for initializing the motor with parameters such as a set PID or open-loop profile, a reversed motor, or a set current limit. 

The following example initializes a TalonFXMotor set to CAN ID 10, with an open loop ramp time of 1 second: 

```
TalonFXMotor motor = new TalonFXMotor(10)
    .withOpenLoopRampConfig(1.0);
```

The following initializes a TalonFXMotor set to CAN ID 10, with a PID profile of P = .05, I = .01, D = 0.0, and FF = 0.0, a current limit of 30 amps, a forward soft limit of 15.2 rotations, a reverse soft limit of -10.8 rotations, brake mode, and a reversed motor:

```
TalonFXMotor motor = new TalonFXMotor(15)
    .withPIDProfile(.05, .01, 0.0, 0.0)
    .withCurrentLimit(30)
    .withForwardSoftLimit(15.2)
    .withReverseSoftLimit(-10.8)
    .withBrakeMode()
    .withReversedMotor();
```

In addition to functionality shared with the SparkMAX, TalonFX motors also allow for getting the amount of stator amps (the current actually going through the motor) in addition to the supply amps, which is the sum of current supplied to the motor and encoder. This may be useful for diagnosing electrical issues.

Stator amps can be accessed using the following method:

```double getCurrentStatorAmps()```
