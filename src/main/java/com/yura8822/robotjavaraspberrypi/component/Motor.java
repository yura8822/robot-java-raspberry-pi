package com.yura8822.robotjavaraspberrypi.component;

import com.pi4j.io.gpio.*;

public class Motor {
    private ProxyPiBlaster proxyPiBlaster;
    private int pinIntBCM;
    private double valueSpeedDoublePWM;

    private GpioController gpioController = GpioFactory.getInstance();
    private GpioPinDigitalOutput gpioPinDigitalOutputMotorDirectionA;
    private GpioPinDigitalOutput gpioPinDigitalOutputMotorDirectionB;
    private int pinIntWiringpiA;
    private int pinIntWiringpiB;

    public Motor(){

    }

    public void moveDirectionMovementA(double valueSpeedDoublePWM){
        this.valueSpeedDoublePWM = valueSpeedDoublePWM;
        proxyPiBlaster.generatePwmWithValue(this.valueSpeedDoublePWM);
        gpioPinDigitalOutputMotorDirectionA.high();
        gpioPinDigitalOutputMotorDirectionB.low();

    }

    public void moveDirectionMovementB(double valueSpeedDoublePWM){
        this.valueSpeedDoublePWM = valueSpeedDoublePWM;
        proxyPiBlaster.generatePwmWithValue(this.valueSpeedDoublePWM);
        gpioPinDigitalOutputMotorDirectionA.low();
        gpioPinDigitalOutputMotorDirectionB.high();

    }

    public void stopMotion(){
        proxyPiBlaster.stopGeneratePWM();
        gpioPinDigitalOutputMotorDirectionA.low();
        gpioPinDigitalOutputMotorDirectionB.low();
    }


    private void init(){
        if (pinIntBCM == 0)
            throw new RuntimeException("You must set the property in component.properties for class:"
                    + Servo.class.getName());
        proxyPiBlaster.setPinIntBCM(pinIntBCM);

        gpioPinDigitalOutputMotorDirectionA =
                gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinIntWiringpiA), PinState.LOW);

        gpioPinDigitalOutputMotorDirectionB =
                gpioController.provisionDigitalOutputPin(RaspiPin.getPinByAddress(pinIntWiringpiB), PinState.LOW);

    }

    public ProxyPiBlaster getProxyPiBlaster() {
        return proxyPiBlaster;
    }

    public void setProxyPiBlaster(ProxyPiBlaster proxyPiBlaster) {
        this.proxyPiBlaster = proxyPiBlaster;
    }

    public int getPinIntBCM() {
        return pinIntBCM;
    }

    public void setPinIntBCM(int pinIntBCM) {
        this.pinIntBCM = pinIntBCM;
    }

    public double getValueSpeedDoublePWM() {
        return valueSpeedDoublePWM;
    }

    public void setValueSpeedDoublePWM(double valueSpeedDoublePWM) {
        this.valueSpeedDoublePWM = valueSpeedDoublePWM;
    }

    public int getPinIntWiringpiA() {
        return pinIntWiringpiA;
    }

    public void setPinIntWiringpiA(int pinIntWiringpiA) {
        this.pinIntWiringpiA = pinIntWiringpiA;
    }

    public int getPinIntWiringpiB() {
        return pinIntWiringpiB;
    }

    public void setPinIntWiringpiB(int pinIntWiringpiB) {
        this.pinIntWiringpiB = pinIntWiringpiB;
    }
}
