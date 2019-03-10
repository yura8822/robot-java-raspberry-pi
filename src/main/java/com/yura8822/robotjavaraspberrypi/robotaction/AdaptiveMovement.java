package com.yura8822.robotjavaraspberrypi.robotaction;

import com.yura8822.robotjavaraspberrypi.component.Proximity;
import com.yura8822.robotjavaraspberrypi.component.Ultrasonic;
import com.yura8822.robotjavaraspberrypi.robotcontrol.MovementControlWithDistance;
import com.yura8822.robotjavaraspberrypi.robotcontrol.ServoAngleControl;

public class AdaptiveMovement {
    private MovementControlWithDistance movementControlWithDistance;
    private ServoAngleControl verticalServoAngleControl;
    private Ultrasonic ultrasonic;
    private Proximity leftProximity;
    private Proximity rightProximity;

    public void startMoving(){

        try {
            ultrasonic.start();
            Thread.sleep(200);
            int distance = (int)Math.round(ultrasonic.getDistance());
            ultrasonic.stop();

            int partialDistance = distance/100*20;

            verticalServoAngleControl.turnServoAngle(-15);
            movementControlWithDistance.forwardWithDistance(0.25, partialDistance);
            verticalServoAngleControl.stopServo();
            System.out.println("Speed = 0.25");

            verticalServoAngleControl.turnServoAngle(0);
            movementControlWithDistance.forwardWithDistance(0.45, partialDistance);
            verticalServoAngleControl.stopServo();
            System.out.println("Speed = 0.35");

            verticalServoAngleControl.turnServoAngle(-15);
            movementControlWithDistance.forwardWithDistance(0.25,partialDistance);
            verticalServoAngleControl.stopServo();
            System.out.println("Speed = 0.25");

            verticalServoAngleControl.turnServoAngle(0);

            ultrasonic.start();
            Thread.sleep(200);
            distance = (int)Math.round(ultrasonic.getDistance());
            ultrasonic.stop();

            verticalServoAngleControl.stopServo();

            if (leftProximity.isObstacle() && !rightProximity.isObstacle()){
                movementControlWithDistance.backWithDistance(0.25, 6);
                movementControlWithDistance.rightOnAngle(0.30, 35);
                movementControlWithDistance.backWithDistance(0.25, 6);
                movementControlWithDistance.leftOnAngle(0.30, 35);
                System.out.println("LEFT OBSTACLE");
            }else if (rightProximity.isObstacle() && !leftProximity.isObstacle()){
                movementControlWithDistance.backWithDistance(0.25, 6);
                movementControlWithDistance.leftOnAngle(0.30, 35);
                movementControlWithDistance.backWithDistance(0.25, 6);
                movementControlWithDistance.rightOnAngle(0.30, 35);
                System.out.println("RIGHT OBSTACLE");
            }else if (leftProximity.isObstacle() && rightProximity.isObstacle()){
                movementControlWithDistance.backWithDistance(0.30, 10);
                System.out.println("LEFT_RIGHT OBSTACLE");
            }else if (distance <= 15){
                movementControlWithDistance.backWithDistance(0.30, 10);
                System.out.println("ULTRASONIC <= 15se");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public MovementControlWithDistance getMovementControlWithDistance() {
        return movementControlWithDistance;
    }

    public void setMovementControlWithDistance(MovementControlWithDistance movementControlWithDistance) {
        this.movementControlWithDistance = movementControlWithDistance;
    }

    public ServoAngleControl getVerticalServoAngleControl() {
        return verticalServoAngleControl;
    }

    public void setVerticalServoAngleControl(ServoAngleControl verticalServoAngleControl) {
        this.verticalServoAngleControl = verticalServoAngleControl;
    }

    public Ultrasonic getUltrasonic() {
        return ultrasonic;
    }

    public void setUltrasonic(Ultrasonic ultrasonic) {
        this.ultrasonic = ultrasonic;
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
}
