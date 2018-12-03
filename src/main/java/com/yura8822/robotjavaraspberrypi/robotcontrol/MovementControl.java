package com.yura8822.robotjavaraspberrypi.robotcontrol;

import com.yura8822.robotjavaraspberrypi.component.Motor;

public class MovementControl {

    private Motor leftMotor;
    private Motor rightMotor;
    private double valueSpeedDoubleLeftMotor;
    private double valueSpeedDoubleRightMotor;

    public MovementControl(){

    }

    public void forward(double valueSpeedDoubleLeftMotor, double valueSpeedDoubleRightMotor){
        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleLeftMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleRightMotor;
        leftMotor.moveDirectionMovementA(this.valueSpeedDoubleLeftMotor);
        rightMotor.moveDirectionMovementB(this.valueSpeedDoubleRightMotor);
    }

    public void back(double valueSpeedDoubleLeftMotor, double valueSpeedDoubleRightMotor){
        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleLeftMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleRightMotor;
        leftMotor.moveDirectionMovementB(this.valueSpeedDoubleLeftMotor);
        rightMotor.moveDirectionMovementA(this.valueSpeedDoubleRightMotor);
    }

    public void left(double valueSpeedDoubleLeftMotor, double valueSpeedDoubleRightMotor){
        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleLeftMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleRightMotor;
        leftMotor.moveDirectionMovementB(this.valueSpeedDoubleLeftMotor);
        rightMotor.moveDirectionMovementB(this.valueSpeedDoubleRightMotor);

    }

    public void right(double valueSpeedDoubleLeftMotor, double valueSpeedDoubleRightMotor){
        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleLeftMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleRightMotor;
        leftMotor.moveDirectionMovementA(this.valueSpeedDoubleLeftMotor);
        rightMotor.moveDirectionMovementA(this.valueSpeedDoubleRightMotor);

    }

    public void stop(){
        leftMotor.stopMotion();
        rightMotor.stopMotion();
    }

    public Motor getLeftMotor() {
        return leftMotor;
    }

    public void setLeftMotor(Motor leftMotor) {
        this.leftMotor = leftMotor;
    }

    public Motor getRightMotor() {
        return rightMotor;
    }

    public void setRightMotor(Motor rightMotor) {
        this.rightMotor = rightMotor;
    }

    public double getValueSpeedDoubleLeftMotor() {
        return valueSpeedDoubleLeftMotor;
    }

    public void setValueSpeedDoubleLeftMotor(double valueSpeedDoubleLeftMotor) {
        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleLeftMotor;
    }

    public double getValueSpeedDoubleRightMotor() {
        return valueSpeedDoubleRightMotor;
    }

    public void setValueSpeedDoubleRightMotor(double valueSpeedDoubleRightMotor) {
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleRightMotor;
    }
}
