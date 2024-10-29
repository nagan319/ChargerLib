# Lights

### Initialization

ChargerLib provides a generic Lights subsystem which can be assigned a name and a CANdle CAN ID. 

Upon initialization, the class displays the amount of currents consumed by the CANdle object on its Shuffleboard tab.

The Lights class can be initialized as follows:

```
Lights lights = new Lights("LEDs", 15);
```

### Functionality

The class features a few different methods for accessing and configuring LED parameters:

```getCurrent()```

Gets the current, in amps, drawn by the CANdle. This value is automatically displayed on Shuffleboard.

```setBrightness(double brightness)```

Sets LED brightness to a value between 0 and 1. Brightness is configured to 0.7 by default.

```setColor(Color color)```

Sets LED color using a WPILib Color object. The Color class can be found in ```edu.wpi.first.wpilibj.util.Color```.

```setColor(int r, int g, int b)```

Sets LED color using integer RGB values ranging from 0 to 255 inclusive.
