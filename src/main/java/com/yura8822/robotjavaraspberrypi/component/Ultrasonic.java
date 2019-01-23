package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;

public class Ultrasonic {
    private GpioController gpioController = GpioFactory.getInstance();
    private int pinIntWiringpiEcho;
    private int pinIntWiringpiTrig;
    private GpioPinDigitalInput gpioPinDigitalInputUltrasonicEcho;
    private GpioPinDigitalOutput gpioPinDigitalOutputUltrasonicTrig;
    private double maxMeasurementDistanceCentimeters;

    private double timeOut;

    private volatile Double distance;

    private void init(){
        gpioPinDigitalInputUltrasonicEcho =
                gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(pinIntWiringpiEcho),
                        PinPullResistance.PULL_UP);
        gpioPinDigitalOutputUltrasonicTrig =
                gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinIntWiringpiTrig), PinState.LOW);
        timeOut = maxMeasurementDistanceCentimeters / 0.017150;
    }

    public void calculateDistance(){
        gpioPinDigitalOutputUltrasonicTrig.high();
        try {
            Thread.sleep(0,10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gpioPinDigitalOutputUltrasonicTrig.low();

        long signalGenerationTime = System.nanoTime();

        while (gpioPinDigitalInputUltrasonicEcho.isLow())
            if (((System.nanoTime()-signalGenerationTime)/1000) > timeOut) return;


        long signalSentTime = System.nanoTime();

        while (gpioPinDigitalInputUltrasonicEcho.isHigh())
            if (((System.nanoTime()-signalSentTime)/1000) > timeOut) break;


        long signalReceivedTime = System.nanoTime();

        long signalDuration = (signalReceivedTime - signalSentTime)/1000;
        distance = signalDuration * 0.017150;

    }

    public Double getDistance() {
        return distance;
    }



    public double getMaxMeasurementDistanceCentimeters() {
        return maxMeasurementDistanceCentimeters;
    }

    public void setMaxMeasurementDistanceCentimeters(double maxMeasurementDistanceCentimeters) {
        this.maxMeasurementDistanceCentimeters = maxMeasurementDistanceCentimeters;
    }

    public int getPinIntWiringpiEcho() {
        return pinIntWiringpiEcho;
    }

    public void setPinIntWiringpiEcho(int pinIntWiringpiEcho) {
        this.pinIntWiringpiEcho = pinIntWiringpiEcho;
    }

    public int getPinIntWiringpiTrig() {
        return pinIntWiringpiTrig;
    }

    public void setPinIntWiringpiTrig(int pinIntWiringpiTrig) {
        this.pinIntWiringpiTrig = pinIntWiringpiTrig;
    }
}
