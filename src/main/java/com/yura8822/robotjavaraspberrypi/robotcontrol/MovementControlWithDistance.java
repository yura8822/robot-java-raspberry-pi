package com.yura8822.robotjavaraspberrypi.robotcontrol;

import com.yura8822.robotjavaraspberrypi.component.PhotoInterrupter;

import java.util.Date;

public class MovementControlWithDistance {
    private MovementControl movementControl;
    private PhotoInterrupter leftPhotoInterrupter;
    private PhotoInterrupter rightPhotoInterrupter;
    private int wheelLockingTimeAllowed;
    private double diameterWheelsCentimeters;
    private int numberStepsEncoder;
    private double distanceBetweenWheelsCentimeters;

    private Date currentTime;
    private Date previousTime;

    private int previousNumberStepLeft;
    private int previousNumberStepRight;

    private double valueSpeedDoubleLeftMotor;
    private double valueSpeedDoubleRightMotor;

    public MovementControlWithDistance(){

    }

    public void forwardWithDistance(double valueSpeedDoubleMotor, int centimeters){

        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleMotor;

        int numberStepsLeftMotor = convertCentimetersSteps(centimeters);
        int numberStepsRightMotor = convertCentimetersSteps(centimeters);

        previousTime = new Date();
        previousNumberStepLeft = 0;
        previousNumberStepRight = 0;

        movementControl.forward(this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor);

        controlDistanceAndSpeed(numberStepsLeftMotor, numberStepsRightMotor,
                this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor,
                DirectionMove.FORWARD);
    }

    public void backWithDistance(double valueSpeedDoubleMotor, int centimeters){
        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleMotor;

        int numberStepsLeftMotor = convertCentimetersSteps(centimeters);
        int numberStepsRightMotor = convertCentimetersSteps(centimeters);

        previousTime = new Date();
        previousNumberStepLeft = 0;
        previousNumberStepRight = 0;

        movementControl.back(this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor);

        controlDistanceAndSpeed(numberStepsLeftMotor, numberStepsRightMotor,
                this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor,
                DirectionMove.BACK);
    }

    public void leftOnAngle(double valueSpeedDoubleMotor, int angleRotation){
        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleMotor;

        int numberStepsLeftMotor = convertAngleStep(angleRotation) ;
        int numberStepsRightMotor = convertAngleStep(angleRotation);

        previousTime = new Date();
        previousNumberStepLeft = 0;
        previousNumberStepRight = 0;

        movementControl.left(this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor);

        controlDistanceAndSpeed(numberStepsLeftMotor, numberStepsRightMotor,
                this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor,
                DirectionMove.LEFT);
    }

    public void rightOnAngle(double valueSpeedDoubleMotor, int angleRotation){
        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        this.valueSpeedDoubleLeftMotor = valueSpeedDoubleMotor;
        this.valueSpeedDoubleRightMotor = valueSpeedDoubleMotor;

        int numberStepsLeftMotor = convertAngleStep(angleRotation) ;
        int numberStepsRightMotor = convertAngleStep(angleRotation);

        previousTime = new Date();
        previousNumberStepLeft = 0;
        previousNumberStepRight = 0;

        movementControl.right(this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor);

        controlDistanceAndSpeed(numberStepsLeftMotor, numberStepsRightMotor,
                this.valueSpeedDoubleLeftMotor, this.valueSpeedDoubleRightMotor,
                DirectionMove.RIGHT);
    }

    private int convertCentimetersSteps(int centimeters){
        double centimetersOneStep = (Math.PI*diameterWheelsCentimeters)/numberStepsEncoder;
        int numberSteps = (int)(centimeters/centimetersOneStep);
        return numberSteps;
    }

    private int convertAngleStep(int angleRotation){
        double centimetersOneDegree = (Math.PI*distanceBetweenWheelsCentimeters)/360;
        int numberSteps = convertCentimetersSteps((int)(angleRotation*centimetersOneDegree));
        return numberSteps;
    }

    private void controlDistanceAndSpeed(int numberStepsLeftMotor, int numberStepsRightMotor,
                                         double valueSpeedDoubleLeftMotor, double valueSpeedDoubleRightMotor,
                                         DirectionMove directionMove){
        while (!checkWheelLocks() && !checkDistance(numberStepsLeftMotor, numberStepsRightMotor)){

            balanceMotorSpeed(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor, directionMove);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean checkWheelLocks(){
        currentTime = new Date();
        if (currentTime.getTime() - previousTime.getTime() > wheelLockingTimeAllowed){
            if (previousNumberStepLeft == leftPhotoInterrupter.getNumberSteps()
                    || previousNumberStepRight == rightPhotoInterrupter.getNumberSteps()) {
                System.out.println("Wheels lock"); //test
                movementControl.stop();
                return true;
            }
            else {
                previousTime = new Date();
                previousNumberStepLeft = leftPhotoInterrupter.getNumberSteps();
                previousNumberStepRight = rightPhotoInterrupter.getNumberSteps();
                System.out.println("Wheels unlock" + " left = " + leftPhotoInterrupter.getNumberSteps() + " right = " + rightPhotoInterrupter.getNumberSteps()); //test
            }
        }
        return false;
    }

    private boolean checkDistance(int numberStepsLeftMotor, int numberStepsRightMotor){
        if (numberStepsLeftMotor < leftPhotoInterrupter.getNumberSteps()
                || numberStepsRightMotor < rightPhotoInterrupter.getNumberSteps()){
            System.out.println("Distance passed"); //test
            movementControl.stop();
            return true;
        }else
            return false;
    }

    private void balanceMotorSpeed(double valueSpeedDoubleLeftMotor,double valueSpeedDoubleRightMotor, DirectionMove directionMove){

        if (directionMove == DirectionMove.FORWARD){

            if (leftPhotoInterrupter.getNumberSteps() > rightPhotoInterrupter.getNumberSteps())
                movementControl.forward(valueSpeedDoubleLeftMotor/1.2, valueSpeedDoubleRightMotor);
            else if (leftPhotoInterrupter.getNumberSteps() < rightPhotoInterrupter.getNumberSteps())
                movementControl.forward(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor/1.2);
            else if (leftPhotoInterrupter.getNumberSteps() == leftPhotoInterrupter.getNumberSteps())
                movementControl.forward(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        }else if (directionMove == DirectionMove.BACK){

            if (leftPhotoInterrupter.getNumberSteps() > rightPhotoInterrupter.getNumberSteps())
                movementControl.back(valueSpeedDoubleLeftMotor/1.2, valueSpeedDoubleRightMotor);
            else if (leftPhotoInterrupter.getNumberSteps() < rightPhotoInterrupter.getNumberSteps())
                movementControl.back(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor/1.2);
            else if (leftPhotoInterrupter.getNumberSteps() == leftPhotoInterrupter.getNumberSteps())
                movementControl.back(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        }else if (directionMove == DirectionMove.LEFT){

            if (leftPhotoInterrupter.getNumberSteps() > rightPhotoInterrupter.getNumberSteps())
                movementControl.left(valueSpeedDoubleLeftMotor/1.2, valueSpeedDoubleRightMotor);
            else if (leftPhotoInterrupter.getNumberSteps() < rightPhotoInterrupter.getNumberSteps())
                movementControl.left(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor/1.2);
            else if (leftPhotoInterrupter.getNumberSteps() == leftPhotoInterrupter.getNumberSteps())
                movementControl.left(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        }else if (directionMove == DirectionMove.RIGHT){

            if (leftPhotoInterrupter.getNumberSteps() > rightPhotoInterrupter.getNumberSteps())
                movementControl.right(valueSpeedDoubleLeftMotor/1.2, valueSpeedDoubleRightMotor);
            else if (leftPhotoInterrupter.getNumberSteps() < rightPhotoInterrupter.getNumberSteps())
                movementControl.right(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor/1.2);
            else if (leftPhotoInterrupter.getNumberSteps() == leftPhotoInterrupter.getNumberSteps())
                movementControl.right(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);
        }
    }

    public MovementControl getMovementControl() {
        return movementControl;
    }

    public void setMovementControl(MovementControl movementControl) {
        this.movementControl = movementControl;
    }

    public PhotoInterrupter getLeftPhotoInterrupter() {
        return leftPhotoInterrupter;
    }

    public void setLeftPhotoInterrupter(PhotoInterrupter leftPhotoInterrupter) {
        this.leftPhotoInterrupter = leftPhotoInterrupter;
    }

    public PhotoInterrupter getRightPhotoInterrupter() {
        return rightPhotoInterrupter;
    }

    public void setRightPhotoInterrupter(PhotoInterrupter rightPhotoInterrupter) {
        this.rightPhotoInterrupter = rightPhotoInterrupter;
    }

    public int getWheelLockingTimeAllowed() {
        return wheelLockingTimeAllowed;
    }

    public void setWheelLockingTimeAllowed(int wheelLockingTimeAllowed) {
        this.wheelLockingTimeAllowed = wheelLockingTimeAllowed;
    }

    public double getDiameterWheelsCentimeters() {
        return diameterWheelsCentimeters;
    }

    public void setDiameterWheelsCentimeters(double diameterWheelsCentimeters) {
        this.diameterWheelsCentimeters = diameterWheelsCentimeters;
    }

    public int getNumberStepsEncoder() {
        return numberStepsEncoder;
    }

    public void setNumberStepsEncoder(int numberStepsEncoder) {
        this.numberStepsEncoder = numberStepsEncoder;
    }

    public double getDistanceBetweenWheelsCentimeters() {
        return distanceBetweenWheelsCentimeters;
    }

    public void setDistanceBetweenWheelsCentimeters(double distanceBetweenWheelsCentimeters) {
        this.distanceBetweenWheelsCentimeters = distanceBetweenWheelsCentimeters;
    }

}
