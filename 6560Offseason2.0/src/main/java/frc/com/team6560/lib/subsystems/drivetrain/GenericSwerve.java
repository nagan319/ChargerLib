package frc.com.team6560.lib.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.MotorType;
import com.pathplanner.lib.util.GeometryUtil;

public class GenericSwerve implements SwerveIO {
    
    private final MotorType driveMotorType;
    private final MotorType steerMotorType;

    private final double trackwidth;
    private final double wheelbase;
    private final SwerveDriveKinematics mKinematics;

    public GenericSwerve(MotorType driveMotorType, MotorType steerMotorType, double trackwidthMeters, double wheelbaseMeters) {

        this.driveMotorType = driveMotorType;
        this.steerMotorType = steerMotorType;

        this.trackwidth = trackwidthMeters;
        this.wheelbase = wheelbaseMeters;

        mKinematics = new SwerveDriveKinematics(
            new Translation2d(trackwidth / 2.0, wheelbase / 2.0),
            new Translation2d(trackwidth / 2.0, -wheelbase / 2.0),
            new Translation2d(-trackwidth / 2.0, wheelbase / 2.0),
            new Translation2d(-trackwidth / 2.0, -wheelbase / 2.0)     
        );

    }

}
