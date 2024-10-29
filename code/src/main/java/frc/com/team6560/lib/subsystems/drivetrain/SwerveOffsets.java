package frc.com.team6560.lib.subsystems.drivetrain;

/**
 * Class containing swerve offsets for drivetrains.
 */
public class SwerveOffsets {
    
    public final double FLOffset;
    public final double FROffset;
    public final double BLOffset;
    public final double BROffset;

    /**
     * Initialize a SwerveOffsets object using offset degree values.
     * @param FLdeg Input the absolute offset value shuffleboard displays for the FL module
     * @param FRdeg Input the absolute offset value shuffleboard displays for the FR module
     * @param BLdeg Input the absolute offset value shuffleboard displays for the BL module
     * @param BRdeg Input the absolute offset value shuffleboard displays for the BR module
     */
    public SwerveOffsets(double FLdeg, double FRdeg, double BLdeg, double BRdeg) {
        FLOffset = -Math.toRadians(FLdeg);
        FROffset = -Math.toRadians(FRdeg);
        BLOffset = -Math.toRadians(BLdeg);
        BROffset = -Math.toRadians(BRdeg);
    }

}
