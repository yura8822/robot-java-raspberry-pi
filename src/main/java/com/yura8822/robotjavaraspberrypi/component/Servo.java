package com.yura8822.robotjavaraspberrypi.component;

public class Servo {

    private ProxyPiBlaster proxyPiBlaster;
    private double valueDoublePWM;
    private int pinIntBCM;
    private double minValuePWM;
    private double maxValuePWM;

    public Servo(){

    }

    public void moveServo(double valueDoublePWM){
        this.valueDoublePWM = checkValueDoublePWM(valueDoublePWM);
        proxyPiBlaster.generatePwmWithValue(this.valueDoublePWM);

    }

    public void stopServo(){
        proxyPiBlaster.stopGeneratePWM();
    }

    private double checkValueDoublePWM(double valueDoublePWM){
        if (valueDoublePWM < minValuePWM) return minValuePWM;
        else if (valueDoublePWM > maxValuePWM) return maxValuePWM;
        return valueDoublePWM;
    }

    private void init(){
        if (pinIntBCM == 0)
            throw new RuntimeException("You must set the property in component.properties for class:"
                    + Servo.class.getName());
        proxyPiBlaster.setPinIntBCM(pinIntBCM);
    }

    public ProxyPiBlaster getProxyPiBlaster() {
        return proxyPiBlaster;
    }

    public void setProxyPiBlaster(ProxyPiBlaster proxyPiBlaster) {
        this.proxyPiBlaster = proxyPiBlaster;
    }

    public double getValueDoublePWM() {
        return valueDoublePWM;
    }

    public void setValueDoublePWM(double valueDoublePWM) {
        this.valueDoublePWM = valueDoublePWM;
    }

    public int getPinIntBCM() {
        return pinIntBCM;
    }

    public void setPinIntBCM(int pinIntBCM) {
        this.pinIntBCM = pinIntBCM;
    }

    public double getMinValuePWM() {
        return minValuePWM;
    }

    public void setMinValuePWM(double minValuePWM) {
        this.minValuePWM = minValuePWM;
    }

    public double getMaxValuePWM() {
        return maxValuePWM;
    }

    public void setMaxValuePWM(double maxValuePWM) {
        this.maxValuePWM = maxValuePWM;
    }
}

