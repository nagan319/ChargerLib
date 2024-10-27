package frc.com.team6560.lib.drivers;

/**
 *  Class to represent CAN device ID and bus. 
 */
public class CANDeviceId {
    private final int deviceNumber;
    private final String bus;

    public CANDeviceId(int deviceNumber, String bus) {
        this.deviceNumber = deviceNumber;
        this.bus = bus;
    }

    // Bus is assigned a default blank ID if not specified in the constructor.
    public CANDeviceId(int deviceNumber) {
        this(deviceNumber, "");
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public String getBus() {
        return bus;
    }

    public boolean equals(CANDeviceId other) {
        return other.deviceNumber == deviceNumber && other.bus == bus;
    }
}
