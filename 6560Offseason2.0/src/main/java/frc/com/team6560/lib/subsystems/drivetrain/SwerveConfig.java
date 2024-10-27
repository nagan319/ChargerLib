package frc.com.team6560.lib.subsystems.drivetrain;

import com.swervedrivespecialties.swervelib.MotorType;
import com.swervedrivespecialties.swervelib.SdsModuleConfigurations;

/**
 * Config class for encapsulating all swerve drivetrain parameters.
 */
public class SwerveConfig {
    public final int FLSteerCanID;
    public final int FLDriveCanID;
    public final int FLEncoderCanID;

    public final int FRSteerCanID;
    public final int FRDriveCanID;
    public final int FREncoderCanID;

    public final int BLSteerCanID;
    public final int BLDriveCanID;
    public final int BLEncoderCanID;

    public final int BRSteerCanID;
    public final int BRDriveCanID;
    public final int BREncoderCanID;

    public final MotorType steerMotorType;
    public final MotorType driveMotorType;

    public final double trackwidth;
    public final double wheelbase;

    public final double maxVelocity;
    public final double maxAngularVelocity;

    /**
     * Private constructor to enforce usage of builder.
     * @param builder Builder to initialize class.
     */
    private SwerveConfig(Builder builder) {
        this.FLSteerCanID = builder.FLSteerCanID;
        this.FLDriveCanID = builder.FLDriveCanID;
        this.FLEncoderCanID = builder.FLEncoderCanID;
        this.FRSteerCanID = builder.FRSteerCanID;
        this.FRDriveCanID = builder.FRDriveCanID;
        this.FREncoderCanID = builder.FREncoderCanID;
        this.BLSteerCanID = builder.BLSteerCanID;
        this.BLDriveCanID = builder.BLDriveCanID;
        this.BLEncoderCanID = builder.BLEncoderCanID;
        this.BRSteerCanID = builder.BRSteerCanID;
        this.BRDriveCanID = builder.BRDriveCanID;
        this.BREncoderCanID = builder.BREncoderCanID;
        this.steerMotorType = builder.steerMotorType;
        this.driveMotorType = builder.driveMotorType;
        this.trackwidth = builder.trackwidth;
        this.wheelbase = builder.wheelbase;
        this.maxVelocity = calculateMaxVelocity();
        this.maxAngularVelocity = calculateMaxAngularVelocity();
    }

    private double calculateMaxVelocity() {
        return 6380.0 / 60.0 *
            SdsModuleConfigurations.MK4I_L2.getDriveReduction() *
            SdsModuleConfigurations.MK4I_L2.getWheelDiameter() * Math.PI;
    }

    private double calculateMaxAngularVelocity() {
        return maxVelocity / Math.hypot(trackwidth / 2.0, wheelbase / 2.0);
    }

    /**
     * Allows for setting specific parameters.
     */
    public static class Builder {

        private int FLSteerCanID = 9;
        private int FLDriveCanID = 5;
        private int FLEncoderCanID = 1;
        private int FRSteerCanID = 12;
        private int FRDriveCanID = 8;
        private int FREncoderCanID = 4;
        private int BLSteerCanID = 10;
        private int BLDriveCanID = 6;
        private int BLEncoderCanID = 2;
        private int BRSteerCanID = 11;
        private int BRDriveCanID = 7;
        private int BREncoderCanID = 3;
        private MotorType steerMotorType = MotorType.NEO;
        private MotorType driveMotorType = MotorType.FALCON;
        private double trackwidth = 0.57785;
        private double wheelbase = 0.57785;

        public Builder setFLSteerCanID(int id) { this.FLSteerCanID = id; return this; }
        public Builder setFLDriveCanID(int id) { this.FLDriveCanID = id; return this; }
        public Builder setFLEncoderCanID(int id) { this.FLEncoderCanID = id; return this; }
        public Builder setFRSteerCanID(int id) { this.FRSteerCanID = id; return this; }
        public Builder setFRDriveCanID(int id) { this.FRDriveCanID = id; return this; }
        public Builder setFREncoderCanID(int id) { this.FREncoderCanID = id; return this; }
        public Builder setBLSteerCanID(int id) { this.BLSteerCanID = id; return this; }
        public Builder setBLDriveCanID(int id) { this.BLDriveCanID = id; return this; }
        public Builder setBLEncoderCanID(int id) { this.BLEncoderCanID = id; return this; }
        public Builder setBRSteerCanID(int id) { this.BRSteerCanID = id; return this; }
        public Builder setBRDriveCanID(int id) { this.BRDriveCanID = id; return this; }
        public Builder setBREncoderCanID(int id) { this.BREncoderCanID = id; return this; }
        public Builder setSteerMotorType(MotorType type) { this.steerMotorType = type; return this; }
        public Builder setDriveMotorType(MotorType type) { this.driveMotorType = type; return this; }
        public Builder setTrackwidth(double trackwidth) { this.trackwidth = trackwidth; return this; }
        public Builder setWheelbase(double wheelbase) { this.wheelbase = wheelbase; return this; }

        public SwerveConfig build() {
            return new SwerveConfig(this);
        }
    }
}
