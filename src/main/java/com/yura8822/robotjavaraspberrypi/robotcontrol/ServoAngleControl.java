package com.yura8822.robotjavaraspberrypi.robotcontrol;

import com.yura8822.robotjavaraspberrypi.component.Servo;

public class ServoAngleControl {
    private Servo servo;
    private int maxRotationAngleServo;
    private double angle;
    private double valuePWMTurnOneDegree;
    private int minAngle;
    private int maxAngle;

    private void init(){
        minAngle = maxRotationAngleServo/2*(-1);
        maxAngle = maxRotationAngleServo/2;
        valuePWMTurnOneDegree = (servo.getMaxValuePWM() - servo.getMinValuePWM())/maxRotationAngleServo;
    }

    public void turnServoAngle(double angle){
        this.angle = checkAngle(angle);
        if (this.angle < 0){
            double result = (minAngle + this.angle)*valuePWMTurnOneDegree - servo.getMinValuePWM();
            servo.moveServo(Math.abs(result));
        } else{
            double result = (maxAngle - this.angle)*valuePWMTurnOneDegree + servo.getMinValuePWM();
            servo.moveServo(Math.abs(result));
        }

    }
    public void stopServo(){
        servo.stopServo();
    }

    private double checkAngle(double angle){
        if (angle < minAngle) return minAngle;
        else if (angle > maxAngle) return maxAngle;
        else return angle;
    }

    public Servo getServo() {
        return servo;
    }

    public void setServo(Servo servo) {
        this.servo = servo;
    }

    public int getMaxRotationAngleServo() {
        return maxRotationAngleServo;
    }

    public void setMaxRotationAngleServo(int maxRotationAngleServo) {
        this.maxRotationAngleServo = maxRotationAngleServo;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
