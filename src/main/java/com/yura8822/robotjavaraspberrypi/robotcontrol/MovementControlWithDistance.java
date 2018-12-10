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

    public MovementControlWithDistance(){

    }

    public void forwardWithDistance(double valueSpeedDoubleLeftMotor,double valueSpeedDoubleRightMotor, int centimeters){

        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        int numberStepsLeftMotor = convertCentimetersSteps(centimeters);
        int numberStepsRightMotor = convertCentimetersSteps(centimeters);

        previousTime = new Date();
        previousNumberStepLeft = leftPhotoInterrupter.getNumberSteps();
        previousNumberStepRight = rightPhotoInterrupter.getNumberSteps();

        movementControl.forward(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        checkDistance(numberStepsLeftMotor, numberStepsRightMotor);

        leftPhotoInterrupter.stop();
        rightPhotoInterrupter.stop();
    }

    public void backWithDistance(double valueSpeedDoubleLeftMotor,double valueSpeedDoubleRightMotor, int centimeters){
        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        int numberStepsLeftMotor = convertCentimetersSteps(centimeters);
        int numberStepsRightMotor = convertCentimetersSteps(centimeters);

        previousTime = new Date();
        previousNumberStepLeft = leftPhotoInterrupter.getNumberSteps();
        previousNumberStepRight = rightPhotoInterrupter.getNumberSteps();

        movementControl.back(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        checkDistance(numberStepsLeftMotor, numberStepsRightMotor);

        leftPhotoInterrupter.stop();
        rightPhotoInterrupter.stop();
    }

    public void leftOnAngle(double valueSpeedDoubleLeftMotor,double valueSpeedDoubleRightMotor, int angleRotation){
        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        int numberStepsLeftMotor = convertAngleStep(angleRotation) ;
        int numberStepsRightMotor = convertAngleStep(angleRotation);

        previousTime = new Date();
        previousNumberStepLeft = leftPhotoInterrupter.getNumberSteps();
        previousNumberStepRight = rightPhotoInterrupter.getNumberSteps();

        movementControl.left(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        checkDistance(numberStepsLeftMotor, numberStepsRightMotor);

        leftPhotoInterrupter.stop();
        rightPhotoInterrupter.stop();

    }

    public void rightOnAngle(double valueSpeedDoubleLeftMotor,double valueSpeedDoubleRightMotor, int angleRotation){
        leftPhotoInterrupter.start();
        rightPhotoInterrupter.start();

        int numberStepsLeftMotor = convertAngleStep(angleRotation) ;
        int numberStepsRightMotor = convertAngleStep(angleRotation);

        previousTime = new Date();
        previousNumberStepLeft = leftPhotoInterrupter.getNumberSteps();
        previousNumberStepRight = rightPhotoInterrupter.getNumberSteps();

        movementControl.right(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor);

        checkDistance(numberStepsLeftMotor, numberStepsRightMotor);

        leftPhotoInterrupter.stop();
        rightPhotoInterrupter.stop();

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

    private void checkDistance(int numberStepsLeftMotor, int numberStepsRightMotor){
        while (!checkWheelLocks()){
            if (numberStepsLeftMotor < leftPhotoInterrupter.getNumberSteps()
                    || numberStepsRightMotor < rightPhotoInterrupter.getNumberSteps()){
                System.out.println("Distance passed"); //test
                movementControl.stop();
                break;
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
