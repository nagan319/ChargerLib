package frc.com.team6560.lib.hardware.motors;

/**
 * Wrapper class for easily integrating motors into roller-type subsystems regardless of motor type.
 * Motor speeds can be configured with target velocity for PID-intensive systems or with duty cycle percentages otherwise.
 */
public class RollerSubsystemMotor {
    private final MotorIO motor;
    private double forwardTarget;
    private double reverseTarget;
    private MotorMode mode; 

    public enum MotorMode {
        VELOCITY,
        DUTY_CYCLE
    }

    public RollerSubsystemMotor(TalonFXMotor motor, double forwardTarget, double reverseTarget, MotorMode mode) {
        this.motor = motor;
        this.forwardTarget = forwardTarget;
        this.reverseTarget = reverseTarget;
        this.mode = mode;
    }

    public RollerSubsystemMotor(SparkMaxMotor motor, double forwardTarget, double reverseTarget, MotorMode mode) {
        this.motor = motor;
        this.forwardTarget = forwardTarget;
        this.reverseTarget = reverseTarget;
        this.mode = mode;
    }

    // Accessor methods 

    public MotorIO getMotor() {
        return motor;
    }

    public double getForwardTarget() {
        return forwardTarget;
    }

    public double getReverseTarget() {
        return reverseTarget;
    }

    public MotorMode getMode() {
        return mode;
    }

    // Setter methods
    
    public void setForwardTarget() {
        setTarget(this.forwardTarget);
    }

    public void setReverseTarget() {
        setTarget(this.reverseTarget);
    }

    private void setTarget(double target) {
        if (mode == MotorMode.VELOCITY) {
            motor.setVelocity(target);
        } else if (mode == MotorMode.DUTY_CYCLE) {
            motor.setOpenLoopDutyCycle(target);
        }
    }

}