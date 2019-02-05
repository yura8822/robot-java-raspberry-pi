package com.yura8822.robotjavaraspberrypi.robotaction;

import com.yura8822.robotjavaraspberrypi.component.Ultrasonic;
import com.yura8822.robotjavaraspberrypi.robotcontrol.MovementControlWithDistance;
import com.yura8822.robotjavaraspberrypi.robotcontrol.ServoAngleControl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DirectionSearch {
    private MovementControlWithDistance movementControlWithDistance;
    private Ultrasonic ultrasonic;
    private ServoAngleControl horizontalServoAngleControl;
    private ServoAngleControl verticalServoAngleControl;

    public void findDirection180Degrees(){
        HashMap<Integer, Integer> distanceMap = getDistanceMap();

        int maxDistance = (Collections.max(distanceMap.values()));

        int angle = 0;
        for (Map.Entry<Integer, Integer> entry: distanceMap.entrySet()){
            if (entry.getValue() == maxDistance)
                angle = entry.getKey();
        }
        System.out.println("Angle = " + angle + "Distance = " + maxDistance); //test

        if (angle != 0){
            if (angle > 0) movementControlWithDistance.rightOnAngle(0.5, angle);
            else movementControlWithDistance.leftOnAngle(0.5, Math.abs(angle));
        }
    }

    private HashMap<Integer, Integer> getDistanceMap(){
        HashMap<Integer, Integer> distanceMap = new HashMap<Integer, Integer>();

        try {
            ultrasonic.start();
            horizontalServoAngleControl.turnServoAngle(0);
            Thread.sleep(300);
            distanceMap.put(0, (int) Math.round(ultrasonic.getDistance()));
            ultrasonic.stop();

            int count = -75;
            while (count <= 75){
                horizontalServoAngleControl.turnServoAngle(count);
                ultrasonic.start();
                Thread.sleep(300);
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
}
