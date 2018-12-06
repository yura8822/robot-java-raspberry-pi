package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class PhotoInterrupter {
    private GpioController gpioController = GpioFactory.getInstance();
    private int pinIntWiringpi;
    private GpioPinDigitalInput gpioPinDigitalInputPhotoInterrupter;
    private int numberSteps;

    private void init() {
        gpioPinDigitalInputPhotoInterrupter = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(pinIntWiringpi),
                PinPullResistance.PULL_UP);
    }

    public void start(){
        numberSteps = 0;
        gpioPinDigitalInputPhotoInterrupter.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                if (gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH) numberSteps++;
            }
        });
    }

    public void stop(){
        gpioPinDigitalInputPhotoInterrupter.removeAllListeners();
    }

    public int getPinIntWiringpi() {
        return pinIntWiringpi;
    }

    public void setPinIntWiringpi(int pinIntWiringpi) {
        this.pinIntWiringpi = pinIntWiringpi;
    }

    public int getNumberSteps() {
        return numberSteps;
    }
}
