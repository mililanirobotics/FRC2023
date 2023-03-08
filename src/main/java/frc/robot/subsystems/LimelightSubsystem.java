package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
//constants
import frc.robot.Constants.LimelightConstants; 



public class LimelightSubsystem extends SubsystemBase {
    private NetworkTable table;

    public enum Pipeline {
        APRIL_TAGS(0), REFLECTIVE_TAPE(1), DRIVER_VIEW(2);
        private Pipeline(int PipelineID){
            this.PipelineID = PipelineID;
        }

        private int PipelineID;
    }
    

    public LimelightSubsystem() {
        this.table = NetworkTableInstance.getDefault().getTable("limelight");
        setPipeline(Pipeline.DRIVER_VIEW);
    }

    /**
     * Tells the user if the limelight has identified any valid targets
     * @return whether or not hte limelight has any valid targets
     */
    public boolean isTargetFound() {
        return table.getEntry("tv").getDouble(0) == 0f;
    }

    /**
     * Returns the horizontal offset from the crosshair to the target
     * @return the horizontal offset from crosshair to the target (-29.8 to +29.8 degrees)
     */
    public double getHorizontalOffset() {
        double horizontalOffset = table.getEntry("tx").getDouble(0.0);
        return horizontalOffset;
    }

    /**
     * Returns the vertical offset from the crosshair to the target
     * @return vertical offset from the target from -24.85 to +24.85
     */
    public double getVerticalOffset() {
        double verticalOffset = table.getEntry("ty").getDouble(0);
        return verticalOffset;  
    }

    /**
     * Sets the current pipeline on the limelight to the desired one
     * @param pipeline sets the limelight's current pipeline
     */
    public void setPipeline(Pipeline pipeline) {
        table.getEntry("pipeline").setValue(pipeline.PipelineID);
    }

    /**
     * Returns the current depth from the target identified by the limelight
     * @return distance from the target in inches
     */
    public double getDepth(double targetHeight) {
        double verticalAngle = (LimelightConstants.kMountAngle + getVerticalOffset()) * Math.PI / 180.0;
        double height = Math.abs(LimelightConstants.kMountHeight - targetHeight); 
        double depth = height * Math.tan(verticalAngle);

        return depth;
    }

    /**
     * Returns the current depth of the robot, minus the distance from the poles to the hybrid nodes
     * @return Depth from the hybrid nodes minus 6 inches (WIP)
     */
    // public double getTravelDepth() {
    //     return getDepth(targetHeight) - 1;
    // }

    /**
     * Gets the horizontal distance (x) needed to travel in order to align the cone poles
     * Reference the diagram in drive for further visual aid
     * https://drive.google.com/file/d/1ntnvpNzdPv9vcbq1GWd7axAXRVNkUBS_/view (diagram)
     * (Math)
     * @return The distance needed to travel in the x-axis
     */
    // public double getXDistance() {
    //     //sets the current pipeline to only see the high pole
    //     //gets the depth (x)
    //     setPipeline(LimelightConstants.kAlignmentPipelineHigh);
    //     double highDepth = getDepth(targetHeight);


    //     System.out.println("Waited 2 seconds");

    //     //sets the current pipeline to only see the med pole
    //     //gets the depth (y) and horizontal offset in degrees (θ)
    //     setPipeline(LimelightConstants.kAlignmnetPipelineMed);
    //     double medDepth = getDepth(targetHeight);
    //     double medOffset = getHorizontalOffset();

    //     //the angle used to calculate the horizontal distance (solving the big triangle angles)
    //     //see documentation for a further breakdown
    //     double solvingAngle = Math.asin(medDepth * (Math.sin(medOffset * Math.PI / 180) / GameConstants.kPoleSpace));

    //     //gets the x distance the robot needs to travel to align targets
    //     //uses the previously obtained angle and tangent to find the z-side of the big triangle
    //     double xDistance = highDepth * Math.tan(solvingAngle);
    //     return xDistance;
    // }

    /**
     * Checks to see if the robot is too close to the nodes
     * @return returns if scoring is currently possible from the current depth
     */
    public boolean inRange() {
        return true; //placeholder for now
    }

    public void printInfo() {
        SmartDashboard.putNumber("Horizontal Angle Offset", getHorizontalOffset());
        SmartDashboard.putNumber("Vertical Angle Offset", getVerticalOffset());
        // SmartDashboard.putNumber("Depth to Target", getDepth(targetHeight));
        SmartDashboard.putBoolean("Valid Target", isTargetFound());
        SmartDashboard.updateValues();
    }

}
