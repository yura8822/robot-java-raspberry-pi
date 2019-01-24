package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;

public class Ultrasonic implements Runnable {
    private GpioController gpioController = GpioFactory.getInstance();
    private int pinIntWiringpiEcho;
    private int pinIntWiringpiTrig;
    private GpioPinDigitalInput gpioPinDigitalInputUltrasonicEcho;
    private GpioPinDigitalOutput gpioPinDigitalOutputUltrasonicTrig;
    private double maxMeasurementDistanceCentimeters;

    private double timeOut;
    private boolean startUltrasonic;

    private volatile double distance;

    private void init(){
        gpioPinDigitalInputUltrasonicEcho =
                gpioController.provisionDigitalInputPin(RaspiPin.getPinByAddress(pinIntWiringpiEcho),
                        PinPullResistance.PULL_UP);
        gpioPinDigitalOutputUltrasonicTrig =
                gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinIntWiringpiTrig), PinState.LOW);
        timeOut = maxMeasurementDistanceCentimeters / 0.017150;
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    private void calculateDistance(){
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

    public void start(){
        startUltrasonic = true;
    }

    public void stop(){
        startUltrasonic = false;
    }

    public double getDistance() {
        if (!startUltrasonic) throw new RuntimeException("You must run Ultrasonic using the start() method. "
                + Ultrasonic.class.getName());
        return distance;
    }

    @Override
    public void run() {
        while (true) {
            while (startUltrasonic){
                calculateDistance();
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
