package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.LimeLightConstants;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Limelight {

    Constants constants = new Constants();

    private NetworkTable table;

    private boolean isConnected = false;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public Limelight(String name) {
        table = NetworkTableInstance.getDefault().getTable(name);
    }

    public Limelight(NetworkTable table) {
        this.table = table;
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * @return whether or not the limelight has any valid targets
     */
    public boolean isTargetFound() {
        return !(table.getEntry("tv").getDouble(0) == 0f); 
        // changed this to "not" as it was returning opposite
    }

    /**
     * @return the horizontal offset from crosshair to target (-29.8 to +29.8 degrees)
     */
    public double getHorizontalDegToTarget() {
        return table.getEntry("tx").getDouble(0);
    }

    /**
     * @return vertical offset from crosshair to target (-24.85 to +24.85 degrees)
     */
    public double getVerticalDegToTarget() {
        return table.getEntry("ty").getDouble(0);
    }

    /**
     * @return target area (0% to 100% of image)
     */
    public double getTargetArea() {
        return table.getEntry("ta").getDouble(0);
    }

    /**
     * @return skew or rotation (-90 to 0 degrees)
     */
    public double getSkewRotation() {
        return table.getEntry("ts").getDouble(0);
    }

    /**
     * @return current pipeline’s latency contribution (ms). Add at least 11ms for image capture latency.
     */
    public double getPipelineLatency() {
        return table.getEntry("tl").getDouble(0);
    }

    /**
     * @param pipeline sets the limelight’s current pipeline
     */
    public void setPipeline(int pipeline) {
        if (pipeline < 0) {
            pipeline = 0;
            throw new IllegalArgumentException("Pipeline can not be less than zero");
        } else if(pipeline > 9) {
            pipeline = 9;
            throw new IllegalArgumentException("Pipeline can not be greater than nine");
        }
        table.getEntry("pipeline").setValue(pipeline);
    }

    /**
     * @return current pipeline of limelight
     */
    public double getPipeline() {
        return table.getEntry("pipeline").getDouble(0.0);
    }

    /**
     * @return distance from target in meters
    */
    public double estimateVerticalDistance(){
        // double angleToTargetDegree = LimeLightConstants.kMountAngle + getVerticalDegToTarget();
        // double angleToTargetRadians = angleToTargetDegree * (Math.PI / 180);

        // double distanceToTarget = (LimeLightConstants.kTargetHeight - LimeLightConstants.kLensHeight) / Math.tan(angleToTargetRadians);
        // return distanceToTarget;
        double veritcalTargetAngle = getVerticalDegToTarget();

        double verticalDistance = (LimeLightConstants.kTargetHeight - LimeLightConstants.kRobotHeight) / Math.tan(LimeLightConstants.kMountAngle + veritcalTargetAngle);
        //Determines the distance with camera height minus target height devided by tangent of camera angle plus the vertical offset angle
        return verticalDistance;
    }

    public double estimateHorizontalDistance(double verticalDistance){
        double horizontalTargetAngle = getHorizontalDegToTarget();
        
        double horizontalDistance = verticalDistance * Math.tan(horizontalTargetAngle);
        //Determines the distance with vertical distance multiply by the tangent of horizontal target angle
        return horizontalDistance;
    }

    public boolean isTargetExistant(){
        return ((getHorizontalDegToTarget() != 0) ? true : false);
    }
    //Does the Limelight sees the target

    public boolean isDistancePossible(){
        return((estimateVerticalDistance() < 1.16 && estimateVerticalDistance() > 1.32) ? true : false);
    }
    //Determines if the distance to target is posible

    public boolean isAnglePossible(){
        return (Math.abs(getHorizontalDegToTarget()) > -.5 || Math.abs(getHorizontalDegToTarget()) < .5) == false;
    } 
    //Determines if the angle of the camera is within the range of the target

    public void log() {
        SmartDashboard.putNumber("X", getHorizontalDegToTarget());
        SmartDashboard.putNumber("Y", getVerticalDegToTarget());
        SmartDashboard.putNumber("Area", getTargetArea());
        SmartDashboard.putNumber("Distance", estimateVerticalDistance());
        SmartDashboard.putBoolean("Angle", isAnglePossible());
        SmartDashboard.putBoolean("Distance", isDistancePossible());
        SmartDashboard.putBoolean("Target Found", isTargetExistant());
    } 

}