package com.yura8822.robotjavaraspberrypi.robotcontrol;

import com.yura8822.robotjavaraspberrypi.component.PhotoInterrupter;
import com.yura8822.robotjavaraspberrypi.component.Proximity;
import com.yura8822.robotjavaraspberrypi.component.Ultrasonic;

import java.util.Date;

public class MovementControlWithDistance {
    private MovementControl movementControl;
    private PhotoInterrupter leftPhotoInterrupter;
    private PhotoInterrupter rightPhotoInterrupter;
    private Proximity leftProximity;
    private Proximity rightProximity;
    private Ultrasonic ultrasonic;
    private int minimumObstacleDistanceCentimeters;
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
        ultrasonic.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        ultrasonic.stop();
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
        ultrasonic.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        ultrasonic.stop();
    }

    public void rightOnAngle(double valueSpeedDoubleMotor, int angleRotation){
        ultrasonic.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        ultrasonic.stop();
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
        while (!checkWheelLocks() && !checkDistance(numberStepsLeftMotor, numberStepsRightMotor)
                && !checkObstacle(directionMove) && !checkDistanceUltrasonic(directionMove)){

            balanceMotorSpeed(valueSpeedDoubleLeftMotor, valueSpeedDoubleRightMotor, directionMove);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean checkDistanceUltrasonic(DirectionMove directionMove){
        if (directionMove != DirectionMove.BACK) {
            if (ultrasonic.getDistance() > minimumObstacleDistanceCentimeters)
                return false;
            else {
                System.out.println("Ultrasonic=" + ultrasonic.getDistance()); //test
                movementControl.stop();
                return true;
            }
        }
        return false;
    }

    private boolean checkObstacle(DirectionMove directionMove){
        if (directionMove == DirectionMove.FORWARD) {
            if (leftProximity.isObstacle() == false && rightProximity.isObstacle() == false) return false;
            else {
                movementControl.stop();
                System.out.println("Forward obstacle"); //test
                return true;
            }
        }else if (directionMove == DirectionMove.LEFT){
            if ((leftProximity.isObstacle() == false && rightProximity.isObstacle() == false)
                    || (leftProximity.isObstacle() == false && rightProximity.isObstacle() == true)) return false;
            else {
                movementControl.stop();
                System.out.println("Left obstacle"); //test
                return true;
            }
        }else if (directionMove == DirectionMove.RIGHT){
            if ((leftProximity.isObstacle() == false && rightProximity.isObstacle() == false)
                    || (leftProximity.isObstacle() == true && rightProximity.isObstacle() == false)) return false;
            else {
                movementControl.stop();
                System.out.println("Right obstacle"); //test
                return true;
            }
        } else if (directionMove == DirectionMove.BACK) return false;

        return true;
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

    public Proximity getLeftProximity() {
        return leftProximity;
    }

    public void setLeftProximity(Proximity leftProximity) {
        this.leftProximity = leftProximity;
    }

    public Proximity getRightProximity() {
        return rightProximity;
    }

    public void setRightProximity(Proximity rightProximity) {
        this.rightProximity = rightProximity;
    }

    public Ultrasonic getUltrasonic() {
        return ultrasonic;
    }

    public void setUltrasonic(Ultrasonic ultrasonic) {
        this.ultrasonic = ultrasonic;
    }

    public int getMinimumObstacleDistanceCentimeters() {
        return minimumObstacleDistanceCentimeters;
    }

    public void setMinimumObstacleDistanceCentimeters(int minimumObstacleDistanceCentimeters) {
        this.minimumObstacleDistanceCentimeters = minimumObstacleDistanceCentimeters;
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
