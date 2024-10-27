# Generic Swerve

ChargerLib provides a generic swerve template for drivetrains using either NEO or Falcon motors using the SDS swerve library. The GenericSwerve class can be initialized with drivetrain CAN IDs, dimensions, and maximum velocities specified in the SwerveConfig class and allows for completely reusing swerve code throughout seasons. 

## SwerveConfig

The SwerveConfig class encapsulates all parameters necessary for initializing a GenericSwerve drivetrain, apart from global voltage. 

The SwerveConfig class stores the following fields:
- CAN IDs for drive motors, steer motors, and encoders
- Drive and steer motor types (NEO or FALCON)
- Drivetrain track width - width of drivetrain measured from centers of right and left wheels
- Drivetrain wheelbase - length of drivetrain measured from centers of front and back wheels
- Maximum velocity of drivetrain in m/s
- Maximum angular (rotational) velocity of drivetrain in rad/s

The class can be initialized as follows using default parameters (as of the 2024 offseason):
```
SwerveConfig config = new SwerveConfig.Builder().build();
```

If custom parameters are desired, the config class can be initialized as follows, with all parameters with non-default values specified:

```
SwerveConfig config = new SwerveConfig.Builder()
    .setFLSteerCanID(1) 
    .setFLDriveCanID(2)  
    .setFLEncoderCanID(3) 
    .setFRSteerCanID(4) 
    .setFRDriveCanID(5)  
    .setFREncoderCanID(6) 
    .setBLSteerCanID(7)  
    .setBLDriveCanID(8)  
    .setBLEncoderCanID(9)
    .setBRSteerCanID(10)
    .setBRDriveCanID(11) 
    .setBREncoderCanID(12)
    .setSteerMotorType(MotorType.NEO)
    .setDriveMotorType(MotorType.FALCON) 
    .setTrackwidth(0.6)
    .setWheelbase(0.6)
    .build(); 
```

## GenericSwerve

The GenericSwerve class itself provides a fully functional drivetrain subsystem based on the SDS library. 

### Return and Input Types

In order to fully understand the functionality of the class, it is necessary to understand the functionality of the following WPILib classes:

```ChassisSpeeds``` - Contains fields describing the linear and angular velocities (vx, vy, omega) of the drivetrain as a whole. 

```SwerveModulePosition``` - Represents the position of a swerve module. Contains fields for the angle of the module relative to the robot and the distance traveled by the module, in meters.

```SwerveModuleState``` - Represents the current state of a swerve module. Contains fields for the angle of the module and the current velocity of the module in meters per second.

### Class Functionality

The GenericSwerve class contains the following public methods:

```GenericSwerve(SwerveConfig config, double maxVoltage)```

Creates an instance of the GenericSwerve class using a SwerveConfig class containing all necessary configuration parameters and a global maximum voltage value. Creating a GenericSwerve subsystem automatically creates a Shuffleboard tab titled 'Drivetrain' which displays module offsets and velocities.

```ChassisSpeeds getChassisSpeeds()```

Retrieves speeds of chassis as a whole, including x and y velocities and angular velocity.

```SwerveModulePosition[] getModulePositions()```

Retrieves the positions of each module in terms of angle relative to the robot and distance traveled in meters.

```SwerveModuleState[] getModuleStates()```

Retrieves the states of each module in terms of angle relative to the robot and velocity in meters/second.

```drive(ChassisSpeeds chassisSpeeds)```

Runs modules to drive at the linear and angular velocities specified in the ChassisSpeeds parameter.

```setChassisState(SwerveModuleState[] states)```

Sets the SwerveModuleState of each module to the state specified in the input parameter.

```setChassisState(double[] rotations)```

Sets the SwerveModuleState of each module to the rotation specified in the input parameter and zero velocity.

```stopModules()```

Stops all swerve modules, setting velocity to zero.

```setMotorBrakeMode(boolean brake)```

Sets all motors (drive and steer) to either brake mode if the input parameter is true and coast mode otherwise.
