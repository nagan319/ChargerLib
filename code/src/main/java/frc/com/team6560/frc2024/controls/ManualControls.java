package frc.com.team6560.frc2024.controls;

import edu.wpi.first.wpilibj.XboxController;
import frc.com.team6560.lib.controls.DriveControlsConfig;
import frc.com.team6560.lib.controls.GenericDoubleXboxControls;

public class ManualControls extends GenericDoubleXboxControls {
    
    public ManualControls(XboxController driverController, XboxController shooterController) {
        super(driverController, shooterController, new DriveControlsConfig.Builder().build());
    }

    public ManualControls(XboxController driverController, XboxController shooterController, DriveControlsConfig config) {
        super(driverController, shooterController, config);
    }

    public boolean getRunTransfer() {
        return shooterController.getRightBumper();
    }

    public boolean getRunShooter() {
        return shooterController.getLeftBumper();
    }

    public boolean getRunIntake() {
        return driverController.getRightBumper();
    }

    public boolean getReverseIntake() {
        return driverController.getLeftBumper();
    }

}
