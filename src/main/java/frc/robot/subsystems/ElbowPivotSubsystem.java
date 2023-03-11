package frc.robot.subsystems;

//subsystems and commands
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//general imports
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder;
//constants
import frc.robot.Constants.PivotConstants;
import frc.robot.Constants.RobotConstants; 

public class ElbowPivotSubsystem extends SubsystemBase {
    //declaring the motors, motor controller groups, and encoders that control the elbow pivot
    private CANSparkMax leftElbowPivot;
    private CANSparkMax rightElbowPivot;

    private MotorControllerGroup elbowPivot;

    private RelativeEncoder leftElbowEncoder;
    private RelativeEncoder rightElbowEncoder;

    public ElbowPivotSubsystem() {
        //initializing pivot motors
        leftElbowPivot = new CANSparkMax(PivotConstants.kleftPivot, MotorType.kBrushless);
        rightElbowPivot = new CANSparkMax(PivotConstants.kRightPivot, MotorType.kBrushless);

        //reversing the pivot motors if necessary 
        leftElbowPivot.setInverted(PivotConstants.kLeftPivotReverse);
        rightElbowPivot.setInverted(PivotConstants.kRightPivotReverse);

        //initializes a motor controller group that controls the entire pivot
        elbowPivot = new MotorControllerGroup(leftElbowPivot, rightElbowPivot);

        //initializing elbow encoders
        leftElbowEncoder = leftElbowPivot.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);
        rightElbowEncoder = rightElbowPivot.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, RobotConstants.kCountsPerRev);

        //setting the conversion factor of the motors to fit the counts per revolution (42)
        leftElbowEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);
        rightElbowEncoder.setPositionConversionFactor(RobotConstants.kCountsPerRev);

        //resetting the encoders upon object initialization 
        resetEncoders();
    }

    /**
     * Resets all of the pivot encoder values
    */
    public void resetEncoders() {
        leftElbowEncoder.setPosition(0);
        rightElbowEncoder.setPosition(0);
    }

    /**
     * Returns the current counts of the left elbow encoder
     * @return The current counts of the left elbow encoder
     */
    public double getLeftElbowEncoder() {
        return leftElbowEncoder.getPosition();
    }

    /**
     * Returns the current counts of the right elbow encoder
     * @return The current counts of the right elbow encoder
     */
    public double getRightElbowEncoder() {
        return rightElbowEncoder.getPosition();
    }
   
    /**
     * Returns the pivot enocder's target in counts
     * @param angleRotation The angle the pivot is attempting to turn 
     * @return Returns the converted angle in terms of counts
     */
    public double convertAngle(double angleRotation) {
        return RobotConstants.kArmGearRatio * (angleRotation / 360);
    }

    /**
     * Sets the speed of the pivot motors
     * @param speed The desired speed
     */
    public void setPivotSpeed(double speed) {
        elbowPivot.set(speed);
    }

    /**
     * Sets all of the pivot motors to 0 power
     */
    public void shutdown() {
        elbowPivot.set(0);
    }
}
