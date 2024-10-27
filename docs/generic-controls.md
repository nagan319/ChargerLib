# Generic Controls

ChargerLib provides classes and interfaces for generic controls that are reused year to year. 

The GenericDoubleXboxControls provides a base control scheme for driving the robot and resetting gyroscope and odometry readings, as well as allowing the driver to adjust drive and turn speeds using the POV buttons on the controller.

GenericDoubleXboxControls implements the GenericControlsIO interface, which defines base functionality for any controls class. It is initialized using the DriveControlsConfig class, which contains ranges and step values for translational and angular velocities as well as an adjustable deadband value for processing raw controller input.

GenericDoubleXboxControls can and should be subclassed, allowing for additional controls for various robot subsystems.

## GenericControlsIO

The GenericControlsIO interface defines all methods that would typically be used to interact with a generic control system for robot operation. It includes methods for translational and rotational drivetrain movement, as well as for resetting gyroscope and odometry readings. This interface provides the following methods:

```double driveX()```

Returns the processed joystick output for translational movement in the X direction. This is typically used to control the left-right motion of the robot.

```double driveY()```

Returns the processed joystick output for translational movement in the Y direction. This is typically used to control the forward and backward movement of the robot.

```double driveRotationX()```

Returns the processed joystick output for rotational movement in the X direction. 

```double driveRotationY()```

Returns the processed joystick output for rotational movement in the Y direction. 

```boolean driveResetYaw()```

Returns a boolean indicating whether the button to reset the gyroscope yaw has been activated. Allows for resetting gyro orientation mid-match.

```boolean driveResetGlobalPose()```

Returns a boolean indicating whether the button to reset the drivetrain odometry has been activated. Allows for recalibrating odometry mid-match.

## DriveControlsConfig

The DriveControlsConfig class encapsulates all parameters necessary for initializing a generic controller object, including translational and turn speeds as well as a deadband value for stick inputs. 

### Fields

The DriveControlsConfig class stores the following fields:

- Speed Initial Percentage: The initial percentage of speed used for driving. Must be in range [0, 1] and between the maximum and minimum speed percentages.
- Speed Minimum Percentage: The minimum allowable percentage of speed for driving. Must be in range [0, 1] and lower than the maximum speed percentage.
- Speed Maximum Percentage: The maximum allowable percentage of speed for driving. Must be in range [0, 1] and higher than the minimum speed percentage.
- Speed Step Percentage: The increment percentage for adjusting speed.

- Turn Speed Initial Percentage: The initial percentage of speed used for turning. Must be in range [0, 1] and between the maximum and minimum turn speed percentages.
- Turn Speed Minimum Percentage: The minimum allowable percentage of speed for turning. Must be in range [0, 1] and lower than the maximum turn speed percentage.
- Turn Speed Maximum Percentage: The maximum allowable percentage of speed for turning. Must be in range [0, 1] and higher than the minimum turn speed percentage.
- Turn Speed Step Percentage: The increment percentage for adjusting turn speed.

- Deadband: The deadband value used to filter out noise in joystick inputs. Must be in range [0, 1), would typically be somewhere between 0 and 0.2 for responsive controls.

### Initialization

The class can be initialized as follows using default parameters (as of the 2024 offseason):

```
DriveControlsConfig config = new DriveControlsConfig.Builder().build();
```

If custom parameters are desired, the config class can be initialized as follows, with all parameters with non-default values specified:

```
DriveControlsConfig config = new DriveControlsConfig.Builder()
    .setSpeedInitialPercent(0.5)
    .setSpeedMinPercent(0.1)
    .setSpeedMaxPercent(0.7)
    .setSpeedStepPercent(0.05)
    .setTurnSpeedInitialPercent(0.15)
    .setTurnSpeedMinPercent(0.0)
    .setTurnSpeedMaxPercent(0.4)
    .setTurnSpeedStepPercent(0.01)
    .setDeadband(0.08)
    .build();
```

## GenericDoubleXboxControls

The GenericDoubleXboxControls class is an implementation of the GenericControlsIO which uses two Xbox controllers, one for driving and another for shooting. 

The class can be initialized with two XboxController objects and a DriveControlsConfig as follows:

```
GenericDoubleXboxControls controls = new GenericDoubleXboxControls(
    new XboxController(0), 
    new XboxController(1), 
    new DriveControlsConfig.Builder().build()
);
```

### Control Bindings

- The left stick of the driving controller is bound to translational movement along the x and y axes
- The right stick of the driving controller is bound to rotational movement
- The start button of the driving controller resets gyroscope yaw (rotation)
- The back button of the driving controller resets the robot odometry pose

### Joystick Input Processing

Raw joystick input is processed in the following format:

Let \( v \) be the input value and \( d \) be the deadband.

1. **Apply Deadband**:
   After applying a deadband, the modified value is defined as:

   $$
   v' = 
   \begin{cases} 
   0 & \text{if } |v| \leq d \\
   \frac{v - d}{1 - d} & \text{if } v > 0 \\
   \frac{v + d}{1 - d} & \text{if } v < 0 
   \end{cases}
   $$

2. **Square the Value**:
   The value is squared and the original sign is preserved:

   $$
   v'' = \text{sign}(v')^2
   $$

3. **Optionally Reverse Output**:
   Depending on the orientation of the robot, the sign of the value may have to be reversed:

   $$
   \text{output} = -v''
   $$
