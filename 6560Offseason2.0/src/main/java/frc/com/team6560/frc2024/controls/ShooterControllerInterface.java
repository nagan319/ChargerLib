package frc.com.team6560.frc2024.controls;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/* Interface describing all methods that are required to be implemented in the shooter controller. */
public interface ShooterControllerInterface {

    Trigger getRevShooter();
    
    Trigger getShoot();

    Trigger getTurretSafetyPos();

    Trigger getTurretPassingPos();

}
