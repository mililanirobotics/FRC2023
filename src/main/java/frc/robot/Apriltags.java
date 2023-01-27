package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public final class Apriltags {
    public static final double kTurnToleranceDeg = 5;
       public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
       public static final double kTargetHeight = 2.4384;
       public static final double kRobotHeight = 69; // Probably not needed
       public static final double kMountAngle = 35;
       public static final double kLensHeight = .737; 
       private NetworkTable table;
       
           public Apriltags()
           {
               table = NetworkTableInstance.getDefault().getTable("Limelight");
           }

           public Apriltags(String name) {
               table = NetworkTableInstance.getDefault().getTable(name);
           }
       
           public Apriltags(NetworkTable table) {
               this.table = table;
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
               double veritcalTargetAngle = getVerticalDegToTarget();
       
               double verticalDistance = (kTargetHeight - kLensHeight) / Math.tan(kMountAngle + veritcalTargetAngle);
               //Determines the distance with camera height minus target height devided by tangent of camera angle plus the vertical offset angle
               return verticalDistance;
           }
       
           public double estimateHorizontalDistance(double verticalDistance){
               double horizontalTargetAngle = getHorizontalDegToTarget();
               
               double horizontalDistance = verticalDistance * Math.tan(horizontalTargetAngle);
               //Determines the distance with vertical distance multiply by the tangent of horizontal target angle
               return horizontalDistance;
           }
       
           public boolean isDistancePossible(){
               return((estimateVerticalDistance() < 1.16 && estimateVerticalDistance() > 1.32) ? true : false);
           }
           //Determines if the distance to target is posible

           public void log() {
               SmartDashboard.putNumber("X", getHorizontalDegToTarget());
               SmartDashboard.putNumber("Y", getVerticalDegToTarget());
               SmartDashboard.putNumber("Distance", estimateVerticalDistance());
               SmartDashboard.putBoolean("Distance", isDistancePossible());
           } 
       
       
}