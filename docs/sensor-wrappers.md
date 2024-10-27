# Sensor Wrappers

ChargerLib provides wrapper classes for sensors that allow for cleaner code, removing the need to store state variables or reverse sensor output in subsystem code. 

## DigitalInputSensor

The DigitalInputSensor class provides a wrapper for sensors that are connected to the RoboRIO's DIO ports and provide a digital (boolean) output. It includes the following methods:

```DigitalInputSensor(int DIO_Port)```

Initializes a DigitalInputSensor given a DIO port.

```DigitalInputSensor withReversedOutput()```

Sets the DigitalInputSensor's output to be reversed, as in true will now return false and vice versa.

```boolean get()```

Retrieves output from sensor, taking into account potential reversed state.

The following example initializes a digital sensor, in this case a limit switch, to DIO port 7 and sets its output to be reversed: 

```
DigitalInputSensor limitSwitch = new DigitalInputSensor(7)
    .withReversedOutput();
```

The (reversed) output can then be retrieved by calling:

```
limitSwitch.get();
```
