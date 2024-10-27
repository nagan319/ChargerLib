package frc.com.team6560.lib.commands;

import edu.wpi.first.wpilibj2.command.Command;

import frc.com.team6560.lib.subsystems.roller.BasicRollerSubsystem;

import java.util.function.Function;

/**
 * Generic command for a basic subsystem involving rollers. 
 */
public class GenericRollerCommand extends Command {
    
    private BasicRollerSubsystem subsystem;
    private Function<Void, Boolean> runMethod;
    private Function<Void, Boolean> reverseMethod;

    /**
     * Initialize command using target subsystem and boolean-returning method that determines whether to run the subsystem.
     * @param subsystem Target subsystem.
     * @param runMethod Method for running subsystem.
     */
    public GenericRollerCommand(BasicRollerSubsystem subsystem, Function<Void, Boolean> runMethod) {
        this.subsystem = subsystem;
        this.runMethod = runMethod;
        this.reverseMethod = (Void) -> false;
        addRequirements(subsystem);
    }

    /**
     * Initialize command using target subsystem, a boolean-returning method that determines whether to run the subsystem,
     * and a boolean-returning method that indicates whether to reverse the subsystem.
     * @param subsystem Target subsystem.
     * @param runMethod Method for running subsystem.
     * @param reverseMethod Method for running subsystem in reverse direction.
     */
    public GenericRollerCommand(BasicRollerSubsystem subsystem, Function<Void, Boolean> runMethod, Function<Void, Boolean> reverseMethod) {
        this.subsystem = subsystem;
        this.runMethod = runMethod;
        this.reverseMethod = reverseMethod;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.stop();
    }

    // Sensor checking logic is handled in subsystem.
    // Outtaking is prioritized over intaking just in case.

    @Override
    public void execute() {
      if (reverseMethod.apply(null)) {
        subsystem.reverse();
      } else if (runMethod.apply(null)) {
        subsystem.run();
      } else {
        subsystem.stop();
      }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.stop();
    }

    @Override
    public boolean isFinished() {
      return false;
    }
}
