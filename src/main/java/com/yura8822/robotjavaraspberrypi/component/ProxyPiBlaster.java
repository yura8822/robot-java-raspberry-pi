package com.yura8822.robotjavaraspberrypi.component;

import java.io.IOException;

public class ProxyPiBlaster {

    private int pinIntBCM;
    private double valueDoublePWM;

    public  ProxyPiBlaster(){

    }

    public void generatePwmWithValue(double valueDoublePWM){
        this.valueDoublePWM = checkValueDoublePWM(valueDoublePWM);

        String pinStringBCM = String.valueOf(pinIntBCM);
        String valueStringPWM = String.valueOf(this.valueDoublePWM);

        String command = String.format("echo %s=%s > /dev/pi-blaster", pinStringBCM, valueStringPWM);
        String[] run = new String[] {"/bin/bash", "-c", command};

        try {
            Process proc = new ProcessBuilder(run).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopGeneratePWM(){
        String pinStringBCM = String.valueOf(pinIntBCM);

        String command = String.format("echo %s=0 > /dev/pi-blaster", pinStringBCM);
        String[] run = new String[] {"/bin/bash", "-c", command};

        try {
            Process proc = new ProcessBuilder(run).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double checkValueDoublePWM(double valueDoublePWM){
        if (valueDoublePWM < 0) return  0;
        else if (valueDoublePWM > 1) return  1;
        return valueDoublePWM;
    }

    public int getPinIntBCM() {
        return pinIntBCM;
    }

    public void setPinIntBCM(int pinIntBCM) {
        this.pinIntBCM = pinIntBCM;
    }

    public double getValueDoublePWM() {
        return valueDoublePWM;
    }

    public void setValueDoublePWM(double valueDoublePWM) {
        this.valueDoublePWM = valueDoublePWM;
    }
}
