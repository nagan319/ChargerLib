# Generic Commands

In addition to generic subsystems, ChargerLib also offers generic commands that can be used in conjunction with said subsystems.

## GenericRollerCommand

The GenericRollerCommand allows for easily mapping controls to subsystems that subclass the GenericRollerSubsystem class. 

### Initialization

There are two ways to initialize a GenericRollerCommand. 

The first method is to input a subsystem, a method that activates the subsystem's forward motion when it returns true, and similar method for reverse motion:

```
GenericRollerCommand command = new GenericRollerCommand(BasicRollerSubsystem subsystem, Function<Void, Boolean> runMethod, Function<Void, Boolean> reverseMethod)
```

The GenericRollerCommand can also be initialized without a reverse method if reverse motion is not required:

```
GenericRollerCommand command = new GenericRollerCommand(BasicRollerSubsystem subsystem, Function<Void, Boolean> runMethod)
```

### Example Usage

In this example, we will assume that an Intake subsystem that inherits from the GenericRollerSubsystem class, as well as a ManualControls object defining the robot control scheme, have been created elsewhere in the code.

In order to use the GenericRollerCommand, it is generally good practice to create a command particular to the subsystem (in this case Intake) which extends the base class and takes the subsystem as well as the controls object as input parameters.

The superclass constructor can then be called using appropriate arguments, in this case the subsystem as well as the methods in the controller class that control subsystem functionality:

```
public class IntakeCommand extends GenericRollerCommand {
    
    public IntakeCommand(Intake intake, ManualControls controls) {
        super(
            intake, 
            (Void) -> controls.getRunIntake(), 
            (Void) -> controls.getReverseIntake()
        );
    }

}
```

If you're wondering why the lambda map ```(Void) -> controls.getRunIntake()``` is necessary as opposed to simply passing in ```controls::getRunIntake```, it's because the command's input parameter functoins must return a wrapped Boolean value which can also include null values (this is purely a Java-related limitation). Creating the lambda interface automatically wraps the return value, solving the problem.
