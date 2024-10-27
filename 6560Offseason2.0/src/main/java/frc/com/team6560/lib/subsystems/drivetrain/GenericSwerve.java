package frc.com.team6560.lib.subsystems.drivetrain;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;
import com.swervedrivespecialties.swervelib.MkModuleConfiguration;
import com.swervedrivespecialties.swervelib.MkSwerveModuleBuilder;
import com.swervedrivespecialties.swervelib.SwerveModule;
import com.swervedrivespecialties.swervelib.MotorType;

import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.hardware.TalonFX;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;

import java.util.function.Function;

/**
 * Generic swerve drive class that can be initialized with varying wheelbases, CAN IDs, and motor types.
 */
public class GenericSwerve extends SubsystemBase {

    public enum SwerveModuleIndex {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT;
    }

    public static final SwerveModuleState[] DEFAULT_MODULE_STATES = new SwerveModuleState[] {
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(-45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(-45.0)),
        new SwerveModuleState(0.0, Rotation2d.fromDegrees(45.0))
    };

    private final double maxVoltage;

    private final MotorType driveMotorType;
    private final MotorType steerMotorType;

    private final SwerveDriveKinematics mKinematics;

    private final double maxVelocity;
    private final double maxAngularVelocity;

    private SwerveModule[] modules;

    private ShuffleboardTab shuffleboardTab = Shuffleboard.getTab("Drivetrain");

    /**
     * Initialize a generic swerve drivetrian using a config and maximum voltage.
     * @param config SwerveConfig which includes CAN IDs, wheelbase size, and motor types and offsets.
     * @param maxVoltage Maximum global voltage.
     */
    public GenericSwerve(SwerveConfig config, double maxVoltage) {

        this.maxVoltage = maxVoltage;

        this.driveMotorType = config.driveMotorType;
        this.steerMotorType = config.steerMotorType;

        this.mKinematics = new SwerveDriveKinematics(
            new Translation2d(config.trackwidth / 2.0, config.wheelbase / 2.0),
            new Translation2d(config.trackwidth / 2.0, -config.wheelbase / 2.0),
            new Translation2d(-config.trackwidth / 2.0, config.wheelbase / 2.0),
            new Translation2d(-config.trackwidth / 2.0, -config.wheelbase / 2.0)     
        );

        this.maxVelocity = config.maxVelocity;
        this.maxAngularVelocity = config.maxAngularVelocity;

        this.modules = new SwerveModule[] {
            createSwerveModule(
                "FL Module", 
                config.FLDriveCanID,
                config.FLSteerCanID,
                config.FLEncoderCanID,
                0
            ),
            createSwerveModule(
                "FR Module", 
                config.FRDriveCanID,
                config.FRSteerCanID,
                config.FREncoderCanID,
                0
            ),
            createSwerveModule(
                "BL Module", 
                config.BLDriveCanID,
                config.BLSteerCanID,
                config.BLEncoderCanID,
                0
            ),      
            createSwerveModule(
                "BR Module", 
                config.BRDriveCanID,
                config.BRSteerCanID,
                config.BREncoderCanID,
                0
            )
        };

    }

    /**
     * Create swerve module using given motor and encoder IDs and offset.
     * @param subTabName Name of shuffleboard sub-tab for module.
     * @param driveMotorId Module drive motor ID.
     * @param steerMotorId Module steer motor ID.
     * @param steerEncoderId Module encoder ID.
     * @param steerOffset Steer offset for module.
     * @return SDS swerve module with given parameters.
     */
    private SwerveModule createSwerveModule(String subTabName, int driveMotorId, int steerMotorId, int steerEncoderId, double steerOffset) {
        return new MkSwerveModuleBuilder(MkModuleConfiguration.getDefaultSteerNEO())
            .withLayout(shuffleboardTab.getLayout(subTabName, BuiltInLayouts.kList)
                .withSize(2, 4)
                .withPosition(6, 0)
            )
            .withGearRatio(SdsModuleConfigurations.MK4I_L2)
            .withDriveMotor(driveMotorType, driveMotorId)
            .withSteerMotor(steerMotorType, steerMotorId)
            .withSteerEncoderPort(steerEncoderId)
            .withSteerOffset(steerOffset)
            .build();
    }

    // Accessors

    /**
     * Get linear and angular velocities of chassis.
     * @return ChassisSpeeds object containing chassis velocities.
     */
    public ChassisSpeeds getChassisSpeeds() {
        return mKinematics.toChassisSpeeds(getModuleStates());
    }

    /**
     * Get position of each swerve module.
     * @return List of swerve module positions.
     */
    public SwerveModulePosition[] getModulePositions() {
        return getModuleData(SwerveModule::getPosition, SwerveModulePosition.class);
    }
    
    /**
     * Get state of each swerve module.
     * @return List of swerve module states.
     */
    public SwerveModuleState[] getModuleStates() {
        return getModuleData(SwerveModule::getState, SwerveModuleState.class);
    }

    /**
     * Generic method to retrieve a specific property of each swerve module.
     * @param <T> Type of property to be extracted from swerve module
     * @param extractor a function that extracts a property of type T from a given SwerveModule.
     * @param type Type T used for creating array of appropriate type.
     * @return Extracted properties of each swerve module, with each element corresponding to a module.
     */
    private <T> T[] getModuleData(Function<SwerveModule, T> extractor, Class<T> type) {
        @SuppressWarnings("unchecked")
        T[] data = (T[]) java.lang.reflect.Array.newInstance(type, modules.length);
        for (int i = 0; i < modules.length; i++) {
            data[i] = extractor.apply(modules[i]);
        }
        return data;
    }

    // Modifiers

    /**
     * Activate motors using given chassis speeds.
     * @param chassisSpeeds Object containing linear and angular velocities of drivetrain as a whole.
     */
    public void drive(ChassisSpeeds chassisSpeeds) {
        SwerveModuleState[] speeds = DEFAULT_MODULE_STATES;
        if (chassisSpeeds.vxMetersPerSecond != 0.0 || 
            chassisSpeeds.vyMetersPerSecond != 0.0 || 
            chassisSpeeds.omegaRadiansPerSecond != 0.0) {
            speeds = mKinematics.toSwerveModuleStates(chassisSpeeds);
            SwerveDriveKinematics.desaturateWheelSpeeds(speeds, maxVelocity);
        }
        setChassisState(speeds);
    }

    /**
     * Set speed and rotation of each swerve module.
     * @param states States of swerve modules.
     */
    public void setChassisState(SwerveModuleState[] states) {
        double[] driveVoltages = new double[modules.length];
        double[] angles = new double[modules.length];
        
        for (int i = 0; i < states.length; i++) {
            driveVoltages[i] = states[i].speedMetersPerSecond / maxVelocity * maxVoltage;
            angles[i] = states[i].angle.getRadians();
        }
        
        applyChassisStates(driveVoltages, angles);
    }

    /**
     * Set chassis states given a list of rotations. Voltages are set to 0.
     * @param rotations Module angles in degrees.
     */
    public void setChassisState(double[] rotations) {
        double[] driveVoltages = new double[modules.length];
        double[] angles = new double[modules.length];
        
        for (int i = 0; i < modules.length; i++) {
            driveVoltages[i] = 0.0;
            angles[i] = Rotation2d.fromDegrees(rotations[i]).getRadians();
        }
        
        applyChassisStates(driveVoltages, angles);
    }

    /**
     * Stop all swerve modules.
     */
    public void stopModules() {
        double[] driveVoltages = new double[modules.length];
        double[] angles = new double[modules.length];
        
        for (int i = 0; i < modules.length; i++) {
            driveVoltages[i] = 0.0;
            angles[i] = modules[i].getSteerAngle();
        }

        applyChassisStates(driveVoltages, angles);
    }

    /**
     * Set chassis states given a list of voltages and angles.
     * @param driveVoltages Module voltages.
     * @param angles Module angles.
     */
    private void applyChassisStates(double driveVoltages[], double angles[]) {
        for (int i = 0; i < modules.length; i++) {
            modules[i].set(driveVoltages[i], angles[i]);
        }
    }

    /**
     * Set all drivetrain motors to break or coast mode.
     * @param brake True if braking, false if coasting.
     */
    public void setMotorBrakeMode(boolean brake) {

        IdleMode SPARK_MAX_BREAK_MODE = IdleMode.kBrake;
        IdleMode SPARK_MAX_COAST_MODE = IdleMode.kCoast;

        NeutralModeValue TALONFX_BREAK_MODE = NeutralModeValue.Brake;
        NeutralModeValue TALONFX_COAST_MODE = NeutralModeValue.Coast;

        if (steerMotorType == MotorType.NEO) {
            for (SwerveModule module : modules) {
                ((CANSparkMax) module.getSteerMotor()).setIdleMode(SPARK_MAX_COAST_MODE);
            }
        } else {
            for (SwerveModule module : modules) {
                ((TalonFX) module.getSteerMotor()).setNeutralMode(TALONFX_COAST_MODE);
            }
        }

        if (driveMotorType == MotorType.NEO) {
            for (SwerveModule module : modules) {
                ((CANSparkMax) module.getDriveMotor()).setIdleMode(brake ? SPARK_MAX_BREAK_MODE : SPARK_MAX_COAST_MODE);
            }
        } else {
            for (SwerveModule module : modules) {
                ((TalonFX) module.getDriveMotor()).setNeutralMode(brake ? TALONFX_BREAK_MODE : TALONFX_COAST_MODE);
            }
        }
    }

}
