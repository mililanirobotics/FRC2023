package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public final class Apriltags {
    public static final double kTurnToleranceDeg = 5;
       public static final double kTurnRateToleranceDegPerS = 2; // degrees per second
       public static final double kTargetHeight = 31.625; //Apriltags / reflectives height
       public static final double kLensHeight = 23.5; // Probably not needed
       public static final double kMountAngle = 0; // Camera lens angle
    // public static final double kLensHeight = .737; 
       private NetworkTable table;
       
           public Apriltags()
           {
               table = NetworkTableInstance.getDefault().getTable("limelight");
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

               return table.getEntry("tv").getDouble(0) == 0f; 
               // changed this to "not" as it was returning opposite
           }
       
           /**
            * @return the horizontal offset from crosshair to target (-29.8 to +29.8 degrees)
            */
           public double getHorizontalDegToTarget() {
            double horizontalDegOffset = table.getEntry("tx").getDouble(0);

            return horizontalDegOffset;
           }
       
           /**
            * @return vertical offset from crosshair to target (-24.85 to +24.85 degrees)
            */
           public double getVerticalDegToTarget() {
            double verticalDegOffset = table.getEntry("ty").getDouble(0);
            
            return verticalDegOffset;
           }
       
           /**
            * @param pipeline sets the limelight’s current pipeline
            */
           public void setPipeline(int pipeline) {
               if (pipeline < 0) {
                   pipeline = 0;
                   throw new IllegalArgumentException("Pipeline can not be less than zero");
               } else if(pipeline > 9) {
                   pipeline = 1;
                   throw new IllegalArgumentException("Pipeline can not be greater than one");
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
            * @return distance from target in inches
           */
           public double estimateVerticalDistance(){
               double veritcalTargetAngle = getVerticalDegToTarget();
               double height = kTargetHeight - kLensHeight;
               double angleInRadians = (kMountAngle + veritcalTargetAngle) * (Math.PI / 180);
                
               double horizontalDistance = height / Math.tan(angleInRadians);
               //Determines the distance with camera height minus target height devided by tangent of camera angle plus the vertical offset angle
               return horizontalDistance;
           }
       
           public double estimateHorizontalDistance(double horizontalDistance){
               double horizontalTargetAngle = getHorizontalDegToTarget();
               
               double verticalDistance = horizontalDistance * Math.tan(horizontalTargetAngle);
               //Determines the distance with vertical distance multiply by the tangent of horizontal target angle
               return verticalDistance;
           }
       
           public boolean isDistancePossible(){
               return((estimateVerticalDistance() < 2 && estimateVerticalDistance() > 2.5) ? true : false);
           }
           //Determines if the distance to target is posible

           public void log() {
               SmartDashboard.putNumber("X offset degree", getHorizontalDegToTarget());
               SmartDashboard.putNumber("Y offset degree", getVerticalDegToTarget());
               SmartDashboard.putNumber("X distance to target", estimateVerticalDistance());
               SmartDashboard.putBoolean("distance possible?", isDistancePossible());
               SmartDashboard.putBoolean("target found?", isTargetFound());  
            } 
       
       
}