package frc.com.team6560.frc2024.controls;

import java.util.Optional;

public class ControlMode {
    private static Optional<ControlMode> instance = Optional.empty();
   
    public enum Mode {
        NOT_SPECIFIED,
        COMPETITION,
        TESTING
    }

    private Mode mode = Mode.NOT_SPECIFIED;

    public static ControlMode getInstance() {
        if (instance.isEmpty()) {
        instance = Optional.of(new ControlMode());
        }
        return instance.get();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
