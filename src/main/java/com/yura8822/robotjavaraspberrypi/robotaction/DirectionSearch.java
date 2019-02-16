package com.yura8822.robotjavaraspberrypi.robotaction;

import com.yura8822.robotjavaraspberrypi.component.Ultrasonic;
import com.yura8822.robotjavaraspberrypi.robotcontrol.MovementControlWithDistance;
import com.yura8822.robotjavaraspberrypi.robotcontrol.ServoAngleControl;

import java.util.HashMap;


public class DirectionSearch {
    private MovementControlWithDistance movementControlWithDistance;
    private Ultrasonic ultrasonic;
    private ServoAngleControl horizontalServoAngleControl;
    private ServoAngleControl verticalServoAngleControl;

    private int maxSumDistancesForRotateCentimeters_A;
    private int maxSumDistancesForRotateCentimeters_B;
    private int maxSumDistancesForRotateCentimeters_C;
    private int maxSumDistancesForRotateCentimeters_D;
    private int turnMultiplier;

    public void findDirection(){
        HashMap<Integer, Integer> distanceMap = getDistanceMap();

        int sumLeftAngles = distanceMap.get(-75) + distanceMap.get(-50) + distanceMap.get(-25);
        int sumRightAngles = distanceMap.get(75) + distanceMap.get(50) + distanceMap.get(25);
        int distanceAngleZero = distanceMap.get(0);

        if (distanceAngleZero < maxSumDistancesForRotateCentimeters_A){
            turn(sumLeftAngles, sumRightAngles, 5);
            System.out.println("Sum distance = " + (sumLeftAngles + sumRightAngles)); //test
        }
        else if (distanceAngleZero < maxSumDistancesForRotateCentimeters_B){
            turn(sumLeftAngles, sumRightAngles, 4);
            System.out.println("Sum distance = " + (sumLeftAngles + sumRightAngles)); //test
        }
        else if (distanceAngleZero < maxSumDistancesForRotateCentimeters_C){
            turn(sumLeftAngles, sumRightAngles, 3);
            System.out.println("Sum distance = " + (sumLeftAngles + sumRightAngles)); //test
        }
        else if (distanceAngleZero < maxSumDistancesForRotateCentimeters_D){
            turn(sumLeftAngles, sumRightAngles, 2);
            System.out.println("Sum distance = " + (sumLeftAngles + sumRightAngles)); //test
        }
        else if (distanceAngleZero <= ultrasonic.getMaxMeasurementDistanceCentimeters()){
            turn(sumLeftAngles, sumRightAngles, 1);
            System.out.println("Sum distance = " + (sumLeftAngles + sumRightAngles)); //test
        }
    }

    private HashMap<Integer, Integer> getDistanceMap(){
        HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();

        try {
            ultrasonic.start();
            horizontalServoAngleControl.turnServoAngle(0);
            Thread.sleep(250);
            distanceMap.put(0, (int) Math.round(ultrasonic.getDistance()));
            ultrasonic.stop();

            int count = -75;
            while (count <= 75){
                horizontalServoAngleControl.turnServoAngle(count);
                ultrasonic.start();
                Thread.sleep(250);
                distanceMap.put(count, (int) Math.round(ultrasonic.getDistance()));
                ultrasonic.stop();
                if (count != -25) {
                    count += 25;
                }else
                    count += 50;
            }

            horizontalServoAngleControl.turnServoAngle(0);
            Thread.sleep(500);
            horizontalServoAngleControl.stopServo();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return distanceMap;
    }

    private void turn(int sumLeftAngles, int sumRightAngles, int factor){
        int turn = turnMultiplier*factor;

        if (sumLeftAngles > sumRightAngles) {
            movementControlWithDistance.leftOnAngle(0.4, turn);
            System.out.print("LEFT turn = " + turn + " Factor = " + factor + "   "); //test
        }
        else if (sumRightAngles >= sumLeftAngles){
            movementControlWithDistance.rightOnAngle(0.4, turn);
            System.out.print("RIGHT turn = " + turn + " Factor = " + factor + "   "); //test
        }
    }

    public MovementControlWithDistance getMovementControlWithDistance() {
        return movementControlWithDistance;
    }

    public void setMovementControlWithDistance(MovementControlWithDistance movementControlWithDistance) {
        this.movementControlWithDistance = movementControlWithDistance;
    }

    public Ultrasonic getUltrasonic() {
        return ultrasonic;
    }

    public void setUltrasonic(Ultrasonic ultrasonic) {
        this.ultrasonic = ultrasonic;
    }

    public ServoAngleControl getHorizontalServoAngleControl() {
        return horizontalServoAngleControl;
    }

    public void setHorizontalServoAngleControl(ServoAngleControl horizontalServoAngleControl) {
        this.horizontalServoAngleControl = horizontalServoAngleControl;
    }

    public ServoAngleControl getVerticalServoAngleControl() {
        return verticalServoAngleControl;
    }

    public void setVerticalServoAngleControl(ServoAngleControl verticalServoAngleControl) {
        this.verticalServoAngleControl = verticalServoAngleControl;
    }

    public int getMaxSumDistancesForRotateCentimeters_A() {
        return maxSumDistancesForRotateCentimeters_A;
    }

    public void setMaxSumDistancesForRotateCentimeters_A(int maxSumDistancesForRotateCentimeters_A) {
        this.maxSumDistancesForRotateCentimeters_A = maxSumDistancesForRotateCentimeters_A;
    }

    public int getMaxSumDistancesForRotateCentimeters_B() {
        return maxSumDistancesForRotateCentimeters_B;
    }

    public void setMaxSumDistancesForRotateCentimeters_B(int maxSumDistancesForRotateCentimeters_B) {
        this.maxSumDistancesForRotateCentimeters_B = maxSumDistancesForRotateCentimeters_B;
    }

    public int getMaxSumDistancesForRotateCentimeters_C() {
        return maxSumDistancesForRotateCentimeters_C;
    }

    public void setMaxSumDistancesForRotateCentimeters_C(int maxSumDistancesForRotateCentimeters_C) {
        this.maxSumDistancesForRotateCentimeters_C = maxSumDistancesForRotateCentimeters_C;
    }

    public int getMaxSumDistancesForRotateCentimeters_D() {
        return maxSumDistancesForRotateCentimeters_D;
    }

    public void setMaxSumDistancesForRotateCentimeters_D(int maxSumDistancesForRotateCentimeters_D) {
        this.maxSumDistancesForRotateCentimeters_D = maxSumDistancesForRotateCentimeters_D;
    }

    public int getTurnMultiplier() {
        return turnMultiplier;
    }

    public void setTurnMultiplier(int turnMultiplier) {
        this.turnMultiplier = turnMultiplier;
    }
}
