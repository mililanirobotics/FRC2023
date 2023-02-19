package frc.robot;

public class Auto {
    
    private static final String kDockAndEngage1 = "Dock and Engage Left";
    private static final String kDockAndEngage2 = "Dock and Engage Middle";
    private static final String kDockAndEngage3 = "Dock and Engage Right";
    private static final String kScoring1 = "Scoring on the Left";
    private static final String kScoring2 = "Scoring in the Middle";
    private static final String kScoring3 = "Scoring on the Right";

    Drive drive = new Drive();
    PivotArm pivotArm = new PivotArm();
    Claw claw = new Claw();

    public void autonomous(String autopath)
    {
        switch(autopath) {
            case kDockAndEngage1:
            drive.encoderDrive(0.3, 12, "Forward", 1000);
            pivotArm.AutoEncoderRotation(35);
            claw.clawToggle();
            drive.encoderDrive(0.4, 136.5, "Backward", 5000);
            pivotArm.AutoEncoderRotation(0);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 36, "Forward", 3000);
            drive.turnDrive(0.13, 90, 1500);
            drive.encoderDrive(0.3, 40, "Forward", 3000);
            // Dock and Engage code
            break;
          case kDockAndEngage2:
            drive.encoderDrive(0.3, 12, "Forward", 1000);
            pivotArm.AutoEncoderRotation(30);
            claw.clawToggle();
            drive.encoderDrive(0.3, 20, "Backward", 1500);
            pivotArm.AutoEncoderRotation(0);
            drive.turnDrive(0.13, 180, 2000);
            drive.encoderDrive(0.4, 35, "Forward", 3500);
            // Dock and Engage code
            break;
          case kDockAndEngage3:
            drive.encoderDrive(0.3, 12, "Forward", 1000);
            pivotArm.AutoEncoderRotation(35);
            claw.clawToggle();
            drive.encoderDrive(0.4, 136.5, "Backward", 5000);
            pivotArm.AutoEncoderRotation(0);
            drive.turnDrive(0.13, 90, 1500);
            drive.encoderDrive(0.3, 36, "Forward", 3000);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 40, "Forward", 3000);
            // Dock and Engage code
            break;
          case kScoring1:
            drive.encoderDrive(0.3, 12, "Forward", 1000);
            pivotArm.AutoEncoderRotation(35);
            claw.clawToggle();
            drive.encoderDrive(0.4, 136.5, "Backward", 5000);
            pivotArm.AutoEncoderRotation(0);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 24, "Forward", 3000);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 36, "Forward", 3000);
            break;
          case kScoring2:
            drive.encoderDrive(0.3, 12, "Forward", 1000);
            pivotArm.AutoEncoderRotation(30);
            claw.clawToggle();
            drive.encoderDrive(0.3, 20, "Backward", 1500);
            pivotArm.AutoEncoderRotation(0);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 30, "Forward", 3500);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 36, "Forward", 3000);
            drive.turnDrive(0.13, -90, 1500);
            drive.encoderDrive(0.3, 36, "Forward", 3000);
            drive.turnDrive(0.13, 90, 1500);
            drive.encoderDrive(0.3, 40, "Forward", 4000);
            break;
          case kScoring3:
            drive.encoderDrive(0.3, 12, "Forward", 1000);
            pivotArm.AutoEncoderRotation(35);
            claw.clawToggle();
            drive.encoderDrive(0.4, 136.5, "Backward", 5000);
            pivotArm.AutoEncoderRotation(0);
            drive.turnDrive(0.13, 90, 1500);
            drive.encoderDrive(0.3, 20, "Forward", 2000);
            drive.turnDrive(0.13, 90, 1500);
            drive.encoderDrive(0.3, 40, "Forward", 3000);
        }
    }
}
