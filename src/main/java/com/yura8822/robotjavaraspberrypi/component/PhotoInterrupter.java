package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;

public class PhotoInterrupter {
    private GpioController gpioController = GpioFactory.getInstance();
    private int pinIntWiringpi;
    private GpioPinDigitalInput gpioPinDigitalInputProximity;

    private void init() {
        gpioPinDigitalInputProximity = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(pinIntWiringpi),
                PinPullResistance.PULL_UP);
    }

    public void stop(){
        gpioPinDigitalInputProximity.removeAllListeners();
    }

    public int getPinIntWiringpi() {
        return pinIntWiringpi;
    }

    public void setPinIntWiringpi(int pinIntWiringpi) {
        this.pinIntWiringpi = pinIntWiringpi;
    }

    public GpioPinDigitalInput getGpioPinDigitalInputProximity() {
        return gpioPinDigitalInputProximity;
    }

    public void setGpioPinDigitalInputProximity(GpioPinDigitalInput gpioPinDigitalInputProximity) {
        this.gpioPinDigitalInputProximity = gpioPinDigitalInputProximity;
    }
}
