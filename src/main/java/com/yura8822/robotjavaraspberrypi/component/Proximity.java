package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Proximity {

    private GpioController gpioController = GpioFactory.getInstance();
    private int pinIntWiringpi;
    private volatile boolean obstacle;
    private GpioPinDigitalInput gpioPinDigitalInputProximity;

    public Proximity(){

    }

    private void init() {
        gpioPinDigitalInputProximity = gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(pinIntWiringpi),
                PinPullResistance.PULL_UP);
    }

    public void start(){
        gpioPinDigitalInputProximity.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpioPinDigitalStateChangeEvent) {
                if (gpioPinDigitalStateChangeEvent.getState() == PinState.HIGH) obstacle = false;
                else obstacle = true;
            }
        });
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

    public boolean isObstacle() {
        return obstacle;
    }
}