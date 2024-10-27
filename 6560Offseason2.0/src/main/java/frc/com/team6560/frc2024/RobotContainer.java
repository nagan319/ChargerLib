package frc.com.team6560.frc2024;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.com.team6560.frc2024.controls.ManualControls;

import frc.com.team6560.frc2024.subsystems.Intake;
import frc.com.team6560.frc2024.subsystems.Transfer;
import frc.com.team6560.frc2024.subsystems.Shooter;

import frc.com.team6560.frc2024.commands.IntakeCommand;
import frc.com.team6560.frc2024.commands.TransferCommand;
import frc.com.team6560.frc2024.commands.ShooterCommand;

public class RobotContainer {

    private final Intake intake;    
    private final Transfer transfer;
    private final Shooter shooter;

    private final IntakeCommand intakeCommand;
    private final TransferCommand transferCommand;
    private final ShooterCommand shooterCommand;

    private final ManualControls manualControls = new ManualControls(new XboxController(0), new XboxController(0));

    public RobotContainer() {
        intake = new Intake();
        intakeCommand = new IntakeCommand(intake, manualControls);
        intake.setDefaultCommand(intakeCommand);

        transfer = new Transfer();
        transferCommand = new TransferCommand(transfer, manualControls);
        transfer.setDefaultCommand(transferCommand);

        shooter = new Shooter();
        shooterCommand = new ShooterCommand(shooter, manualControls);
        shooter.setDefaultCommand(shooterCommand);
    }

    public Command getAutonomousCommand() {
        return null;
    }
}