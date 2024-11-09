package frc.com.team6560.lib.hardware.motors;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

/**
 * Class representing a TalonFX motor implementation of the MotorIO interface.
 * Provides methods to control the motor and read its state.
 */
public class TalonFXMotor implements MotorIO {
    private TalonFX talonFX; 

    /**
     * Constructor to initialize the TalonFX motor with a TalonFX object.
     * @param talonFX The TalonFX motor controller.
     */
    public TalonFXMotor(int CANId) {
        this.talonFX = new TalonFX(CANId);
        talonFX.getConfigurator().apply(new TalonFXConfiguration());
    }

    /**
     * Initialize motor with open loop ramp config.
     * @param rampPeriod Ramp time in seconds.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withOpenLoopRampConfig(double rampPeriod) {
        var config = new OpenLoopRampsConfigs();
        config.DutyCycleOpenLoopRampPeriod = rampPeriod;
        talonFX.getConfigurator().apply(config);
        return this;
    }

    /**
     * Initialize motor with closed-loop PID profile
     * @param kP proportional constant
     * @param KI integral constant
     * @param KD derivative constant
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withPIDProfile(double kP, double kI, double kD) {
        var config = new Slot0Configs();
        config.kP = kP;
        config.kI = kI;
        config.kD = kD;
        talonFX.getConfigurator().apply(config);
        return this;
    }

    /**
     * Initialize motor with set current limit.
     * @param currentLimit current limit in amps.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withCurrentLimit(double currentLimit) {
        var config = new CurrentLimitsConfigs();
        config.SupplyCurrentLimit = currentLimit;
        config.StatorCurrentLimitEnable = true;
        talonFX.getConfigurator().apply(config);
        return this;
    }

    /**
     * Initialize motor with set forward soft limit.
     * @param maxRotations Forward soft limit in rotations.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withForwardSoftLimit(float maxRotations) {
        this.setForwardSoftLimit(maxRotations);
        return this;
    }

    /**
     * Initialize motor with set reverse soft limit.
     * @param maxRotations Backward soft limit in rotations.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withReverseSoftLimit(float maxRotations) {
        this.setReverseSoftLimit(maxRotations);
        return this;
    }

    /**
     * Initialize motor set in reverse.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withReversedMotor() {
        this.setReversed(true);
        return this;
    }

    /**
     * Initialize motor with brake mode as default.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withBrakeMode() {
        this.setBrakeMode();
        return this;
    }

    /**
     * Initialize motor with coast mode as default.
     * @return TalonFXMotor for chainability.
     */
    public TalonFXMotor withCoastMode() {
        this.setBrakeMode();
        return this;
    }

    // Accessor methods

    @Override
    public double getDutyCyclePercent() {
        return talonFX.get();
    }

    @Override
    public double getVelocityRPM() {
        return talonFX.getVelocity().getValueAsDouble() * 60.0;
    }

    @Override
    public double getPositionRotations() {
        return talonFX.getPosition().getValueAsDouble(); 
    }

    @Override
    public double getAppliedVolts() {
        return talonFX.getMotorVoltage().getValueAsDouble(); 
    }

    @Override
    public double getCurrentSupplyAmps() {
        return talonFX.getSupplyCurrent().getValueAsDouble(); 
    }

    /**
     * Gets amount of current used by stator (motor itself).
     * @return current as a double.
     */
    public double getCurrentStatorAmps() {
        return talonFX.getStatorCurrent().getValueAsDouble(); 
    }

    // Setter methods

    @Override
    public void setReversed(boolean reversed) {
        talonFX.setInverted(reversed);
    }

    @Override
    public void setOpenLoopDutyCycle(double dutyCycle) {
        talonFX.set(dutyCycle); // Duty cycle ranges from -1.0 to 1.0
    }

    @Override
    public void setCurrentPositionAsZero() {
        talonFX.setPosition(0);
    }

    @Override
    public void setCurrentPosition(double position) {
        talonFX.setPosition(position); 
    }

    @Override
    public void setVelocity(double targetVelocity) {
        talonFX.setControl(new VelocityVoltage(targetVelocity / 60));
    }

    @Override
    public void setBrakeMode() {
        talonFX.setNeutralMode(NeutralModeValue.Brake); 
    }

    @Override
    public void setCoastMode() {
        talonFX.setNeutralMode(NeutralModeValue.Coast);
    }

    @Override
    public void setForwardSoftLimit(float maxRotations) {
        var config = new SoftwareLimitSwitchConfigs();
        config.ForwardSoftLimitEnable = true;
        config.ForwardSoftLimitThreshold = maxRotations;
        talonFX.getConfigurator().apply(config);
    }

    @Override
    public void setReverseSoftLimit(float maxRotations) {
        var config = new SoftwareLimitSwitchConfigs();
        config.ReverseSoftLimitEnable = true;
        config.ReverseSoftLimitThreshold = maxRotations;
        talonFX.getConfigurator().apply(config);
    }

    @Override
    public void stop() {
        talonFX.stopMotor();
    }
}
