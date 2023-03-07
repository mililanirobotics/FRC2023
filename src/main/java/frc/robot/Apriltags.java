package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

public final class Apriltags {
    public static final double kTurnToleranceDeg = 5;
    public static final double kTurnRateToleranceDegPerS = 5; // degrees per second
    public static final double kTargetHeight = 9; //Apriltags / reflectives height
    public static final double kLensHeight = 23.5; // Camera lens height 
    public static final double kMountAngle = 0; // Camera lens angle
    public static final double maxArmReach = 10; //Maximum reach in inches the arm can stretch outside frame
    public static final double minArmReach = 5; //Minimum reach in inches the arm can strecth outside frame
    public static final double bumperThickness = 3;
       private NetworkTable table;
    //    Drive drive = Robot.drive;
       
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
                   pipeline = 1;
                   throw new IllegalArgumentException("Pipeline can not be less than zero");
               } else if(pipeline > 9) {
                   pipeline = 2;
                   throw new IllegalArgumentException("Pipeline can not be greater than one");
               }
               table.getEntry("pipeline").setValue(pipeline);
               table.getEntry("pipeline").setNumber(pipeline);
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
           public double estimateHorizontalDistance(){
               double veritcalTargetAngle = getVerticalDegToTarget();
               double height = kTargetHeight - kLensHeight;
               double angleInRadians = (kMountAngle + veritcalTargetAngle) * (Math.PI / 180);

               double horizontalDistance = height / Math.tan(angleInRadians);
               //Determines the distance with camera height minus target height devided by tangent of camera angle plus the vertical offset angle
               return horizontalDistance;
           }
       
           public double estimateVerticalDistance(double horizontalDistance){
               double horizontalTargetAngle = getHorizontalDegToTarget();
               
               double verticalDistance = horizontalDistance * Math.tan(horizontalTargetAngle);
               //Determines the distance with vertical distance multiply by the tangent of horizontal target angle
               return verticalDistance;
           }
       
           public boolean isDistancePossible(){
                // drive.angleAlign();
                boolean distPossible = false;
               if (getPipeline() == 0) {
                    distPossible = ((estimateHorizontalDistance() < maxArmReach && estimateHorizontalDistance() > minArmReach) ? true : false);
           }
               if (getPipeline() == 1) {
                    distPossible = (((estimateHorizontalDistance() + 17.375)  < maxArmReach && (estimateHorizontalDistance() + 17.375) > minArmReach) ? true : false);
               }
               return distPossible;
           }

           public boolean isScoringPossible(){
            return((estimateHorizontalDistance() < 28.6) ? true : false);
           }

           /**
            * To determine on Shuffleboard if auto alignment is ready for the driver
            * To be finished along with auto alignment functionality
<<<<<<< HEAD
            */
=======
        //     */
>>>>>>> 906b20d7813570293641f33d7a8a2c68360af20b
        //    public boolean autoAlignPossible(){
        //     return(((isScoringPossible() = true)) && (isTargetFound() = true) ? true : false);
        //    }
        
           //Determines if the distance to target is posible

        //    public double alignLongitude() {
        //     setPipeline(1);
        //     double distance = 0;
        //     if (getHorizontalDegToTarget() < -1 || getHorizontalDegToTarget() > 1) {
        //         if (getPipeline() == 1) {
        //             drive.angleAlign();
        //         }
        //     } 
        //     if (getHorizontalDegToTarget() < 1 && getHorizontalDegToTarget() > -1) {
        //         double length1 = estimateVerticalDistance();
        //         double offset1 = getHorizontalDegToTarget();

        //         // drive.leftFront.stopMotor();
        //         // drive.rightFront.stopMotor();
        //         // drive.leftBack.stopMotor();
        //         // drive.rightBack.stopMotor();
        //         setPipeline(2);
        //         double length2 = estimateVerticalDistance();
        //         double offset2 = getHorizontalDegToTarget();

        //         double theta = (offset2 - offset1) * (Math.PI/180);
        //         double angle1 = Math.asin(length2 * (Math.sin(theta)/17));
    
        //         double angle2 = 180 - 90 - angle1;
        //         distance = length1 * Math.tan(angle2);
        //     }
        //     return distance;
        // }

           public void log() {
               SmartDashboard.putNumber("X offset degree", getHorizontalDegToTarget());
               SmartDashboard.putNumber("Y offset degree", getVerticalDegToTarget());
               SmartDashboard.putNumber("X distance to target", estimateHorizontalDistance());
            //    SmartDashboard.putBoolean("distance possible?", isDistancePossible());
               SmartDashboard.putBoolean("target found?", isTargetFound());  
            //    SmartDashboard.putNumber("longitude to target", alignLongitude());
               SmartDashboard.putNumber("pipeline", getPipeline());
               SmartDashboard.putBoolean("Is scoring possible?", isScoringPossible());
            //    SmartDashboard.putBoolean("Is auto alignment possible?", autoAlignPossible());

            } 
       
       
}