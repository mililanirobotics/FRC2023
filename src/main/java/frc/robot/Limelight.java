package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public final class Limelight {
    final double kTurnToleranceDeg = 5;
    final double kTurnRateToleranceDegPerS = 5; // degrees per second
    final double kTargetHeight = 9; //Apriltags / reflectives height
    final double kLensHeight = 23.5; // Camera lens height 
    final double kMountAngle = 0; // Camera lens angle
    final double maxArmReach = 10; //Maximum reach in inches the arm can stretch outside frame
    final double minArmReach = 5; //Minimum reach in inches the arm can strecth outside frame
    final double bumperThickness = 3;

    private NetworkTable table;

    // Drive drive = new Drive();
    // Align align = new Align();
       
    //        public Limelight()
    //        {
    //            table = NetworkTableInstance.getDefault().getTable("limelight");
    //        }
   
    //        /**
    //         * @return whether or not the limelight has any valid targets
    //         */
    //        public boolean isTargetFound() {

    //            return table.getEntry("tv").getDouble(0) == 0f; 
    //            // changed this to "not" as it was returning opposite
    //        }
       
    //        /**
    //         * @return the horizontal offset from crosshair to target (-29.8 to +29.8 degrees)
    //         */
    //        public double getHorizontalDegToTarget() {
    //         double horizontalDegOffset = table.getEntry("tx").getDouble(0);

    //         return horizontalDegOffset;
    //        }
       
    //        /**
    //         * @return vertical offset from crosshair to target (-24.85 to +24.85 degrees)
    //         */
    //        public double getVerticalDegToTarget() {
    //         double verticalDegOffset = table.getEntry("ty").getDouble(0);
            
    //         return verticalDegOffset;
    //        }

    //        public void switchPipeline(String m_pipelineSelected) {
    //             switch(m_pipelineSelected) {
    //                 case "Pipeline Zero":
    //                 setPipeline(0);
    //                 break;

    //                 case "Pipeline One":
    //                 setPipeline(1);
    //                 break;
    //             }
    //        }
       
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
           public double estimateDepthToTarget(){
            //    double verticalTargetAngle = getVerticalDegToTarget();
               double height = Math.abs(kTargetHeight - kLensHeight);
            //    double angleInRadians = Math.toRadians(kMountAngle + verticalTargetAngle);

            //    double depthToTarget = height / Math.tan(angleInRadians);
               //Determines the distance with camera height minus target height devided by tangent of camera angle plus the vertical offset angle
            //    return depthToTarget;
            return 0;
           }
       
        //    public double estimateHorizontalDistance(double depthToTarget){
            //    double horizontalTargetAngle = getHorizontalDegToTarget();
               
            //    double horizontalDistance = depthToTarget * Math.tan(horizontalTargetAngle);
               //Determines the distance with vertical distance multiply by the tangent of horizontal target angle
            //    return horizontalDistance;
            
       
           /**
            * @return wether or not the robot can score a gamepiece from it's current position
            */
           public boolean isDistancePossible(){
                // align.angleAlign();
                boolean distPossible = false;
               if (getPipeline() == 0) {
                    //AprilTags Pipeline
                    distPossible = ((estimateDepthToTarget() + 17.375)  < maxArmReach && (estimateDepthToTarget() + 17.375) > minArmReach);
                }
               if (getPipeline() == 1) {
                    //ReflectiveTape Pipeline
                    distPossible = (estimateDepthToTarget() < maxArmReach && estimateDepthToTarget() > minArmReach);          
                }
               return distPossible;
           }

           public boolean isScoringPossible(){
            return(estimateDepthToTarget() < 28.6);
           }
        
        //    public void printLimelightData() {
        //        SmartDashboard.putNumber("X offset degree", getHorizontalDegToTarget());
        //        SmartDashboard.putNumber("Y offset degree", getVerticalDegToTarget());
        //        SmartDashboard.putNumber("Depth to target", estimateDepthToTarget());
        //        SmartDashboard.putNumber("Horizontal dist to target", estimateHorizontalDistance(estimateDepthToTarget()));
        //        SmartDashboard.putBoolean("target found?", isTargetFound());  
        //        SmartDashboard.putNumber("pipeline", getPipeline());
        //        SmartDashboard.putBoolean("Is scoring possible", isScoringPossible());
        //     } 
       
       
}