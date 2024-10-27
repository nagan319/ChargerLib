package frc.com.team6560.frc2024.controls;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/* Interface describing all methods that are required to be implemented in the driver controller. */
public interface DriverControlInterface {

    double getThrottle();

    double getStrafe();

    double getRotation();

    Trigger resetGyro();

    Trigger intake();

    Trigger outtake();
    
}
