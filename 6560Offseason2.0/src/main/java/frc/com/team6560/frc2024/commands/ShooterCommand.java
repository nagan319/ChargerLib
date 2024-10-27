package frc.com.team6560.frc2024.commands;

import frc.com.team6560.frc2024.controls.ManualControls;
import frc.com.team6560.lib.commands.GenericRollerCommand;
import frc.com.team6560.frc2024.subsystems.Shooter;

public class ShooterCommand extends GenericRollerCommand {
    
    public ShooterCommand(Shooter shooter, ManualControls controls) {
        super(shooter, (Void) -> controls.getRunShooter());
    }

}
