package com.yura8822.robotjavaraspberrypi.robotcontrol;

public class StartingInitialization {
    public String commandOptionsRunPiBlaster;
    public int horizontalServoPinBCM;
    public int verticalServoPinBCM;
    public int leftMotorPinBCM;
    public int rightMotorPinBCM;
    public String commandRunV4L;

    private void initPinBCMPiBlaster(){
        StringBuilder command = new StringBuilder();
        command.append(commandOptionsRunPiBlaster + " --gpio ").append(horizontalServoPinBCM + ",")
                .append(verticalServoPinBCM + ",").append(leftMotorPinBCM + ",").append(rightMotorPinBCM);

        System.out.println("initPinBCMPiBlaster command: " + command);

        String[] run = new String[] {"/bin/bash", "-c", String.valueOf(command)};

        try {
            Process process = new ProcessBuilder(run).start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runV4L(){
        System.out.println("runV4L: " + commandRunV4L);

        String[] run = new String[] {"/bin/bash", "-c", commandRunV4L};

        try {
            Process process = new ProcessBuilder(run).start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init(){
        initPinBCMPiBlaster();
        runV4L();
    }

    public String getCommandOptionsRunPiBlaster() {
        return commandOptionsRunPiBlaster;
    }

    public void setCommandOptionsRunPiBlaster(String commandOptionsRunPiBlaster) {
        this.commandOptionsRunPiBlaster = commandOptionsRunPiBlaster;
    }

    public int getHorizontalServoPinBCM() {
        return horizontalServoPinBCM;
    }

    public void setHorizontalServoPinBCM(int horizontalServoPinBCM) {
        this.horizontalServoPinBCM = horizontalServoPinBCM;
    }

    public int getVerticalServoPinBCM() {
        return verticalServoPinBCM;
    }

    public void setVerticalServoPinBCM(int verticalServoPinBCM) {
        this.verticalServoPinBCM = verticalServoPinBCM;
    }

    public int getLeftMotorPinBCM() {
        return leftMotorPinBCM;
    }

    public void setLeftMotorPinBCM(int leftMotorPinBCM) {
        this.leftMotorPinBCM = leftMotorPinBCM;
    }

    public int getRightMotorPinBCM() {
        return rightMotorPinBCM;
    }

    public void setRightMotorPinBCM(int rightMotorPinBCM) {
        this.rightMotorPinBCM = rightMotorPinBCM;
    }

    public String getCommandRunV4L() {
        return commandRunV4L;
    }

    public void setCommandRunV4L(String commandRunV4L) {
        this.commandRunV4L = commandRunV4L;
    }
}
