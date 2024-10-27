package frc.com.team6560.frc2024.commands;

import frc.com.team6560.lib.commands.GenericRollerCommand;
import frc.com.team6560.frc2024.controls.ManualControls;
import frc.com.team6560.frc2024.subsystems.Intake;

public class IntakeCommand extends GenericRollerCommand {
    
    public IntakeCommand(Intake intake, ManualControls controls) {
        super(intake, (Void) -> controls.getRunIntake(), (Void) -> controls.getReverseIntake());
    }

}
