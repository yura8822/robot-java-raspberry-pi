package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;

public class Proximity {

    private GpioController gpioController = GpioFactory.getInstance();
    private int pinIntWiringpi;
    private GpioPinDigitalInput gpioPinDigitalInputProximity;

    public Proximity(){

    }

    private void init() {
        gpioPinDigitalInputProximity = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(pinIntWiringpi),
                PinPullResistance.PULL_UP);
    }

    public int getPinIntWiringpi() {
        return pinIntWiringpi;
    }

    public void setPinIntWiringpi(int pinIntWiringpi) {
        this.pinIntWiringpi = pinIntWiringpi;
    }

    public boolean isObstacle() {
        if (gpioPinDigitalInputProximity.isHigh()) return false;
        else return true;
    }
}
