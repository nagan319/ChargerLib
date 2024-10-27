package frc.com.team6560.frc2024.commands;

import frc.com.team6560.lib.commands.GenericRollerCommand;
import frc.com.team6560.frc2024.controls.ManualControls;
import frc.com.team6560.frc2024.subsystems.Transfer;

public class TransferCommand extends GenericRollerCommand {
    
    public TransferCommand(Transfer transfer, ManualControls controls) {
        super(transfer, (Void) -> controls.getRunTransfer());
    }

}
