package frc.robot;

public class Auto {
    
    private static final String kDockAndEngage1 = "Score then Dock and Engage Left";
    private static final String kDockAndEngage2 = "Score then Dock and Engage Middle";
    private static final String kDockAndEngage3 = "Score then Dock and Engage Right";
    private static final String kScoring1 = "Scoring only Left";
    private static final String kScoring2 = "Scoring only Middle";
    private static final String kScoring3 = "Scoring only Right";

    Drive drive = new Drive();
    PivotArm pivotArm = new PivotArm();
    Claw claw = new Claw();

    public void autonomous(String autopath)
    {
        switch(autopath) {
            case kDockAndEngage1: //scoring preload then balance and engage in left position
            pivotArm.setArmControl(40); //set arm to score
            claw.clawToggle(); //opens and close claw to score
            drive.encoderDrive(0.5, 198, "Backward", 5000); //drive backwards 16.5 ft for mobility
            pivotArm.setArmControl(0); //puts arm down
            drive.turnDrive(0.13, -90, 1500); //turn robot left 90 degrees (relative to starting position)
            drive.encoderDrive(0.3, 60, "Forward", 3000); //drives foward 5ft
            drive.turnDrive(0.13, 90, 1500); //turn robot right 90 degrees
            drive.encoderDrive(0.3, 36, "Forward", 3000); //drives foward 3 ft
            //start of balancing method 
            
            break;
          case kDockAndEngage2: //scoring preload then balance and engage in mid position
            pivotArm.setArmControl(40); //set arm to score
            claw.clawToggle(); //opens and close claw to score
            drive.encoderDrive(0.5, 200, "Backward", 1500); //drive backwards 16.5 ft (+2 inches to account for going over charge station)
            pivotArm.setArmControl(0); //puts arm down
            // drive.turnDrive(0.13, 180, 2000);
            drive.encoderDrive(0.4, 36, "Forward", 3500); //drive foward heading to balance on charge station
            //start of balancing method

            break;
          case kDockAndEngage3: //scoring preload then balance and engage in right position
            pivotArm.setArmControl(40); //set arm to score
            claw.clawToggle(); //opens and close claw to score
            drive.encoderDrive(0.5, 200, "Backward", 5000); //drive backwards 16.5 ft for mobility
            pivotArm.setArmControl(0); //puts arm down
            drive.turnDrive(0.13, 90, 1500); //turns 90 degree right (relative to robot starting position)
            drive.encoderDrive(0.3, 60, "Forward", 3000); //drives foward 5ft
            drive.turnDrive(0.13, -90, 1500); //turn 90 degree left to face charge station
            drive.encoderDrive(0.3, 36, "Forward", 3000); //drive foward heading to balance on charge station
            //start of balancing method

            break;
          case kScoring1: //scoring only in left position
            pivotArm.setArmControl(40); //set arm to score
            claw.clawToggle(); //opens and close claw to score
            drive.encoderDrive(0.4, 198, "Backward", 5000); //drive backwards 16.5 ft 
            pivotArm.setArmControl(0); //puts arm down
            drive.turnDrive(0.15, 185, 1500); //turns robot 180 degrees right relative to starting position (+5 for game piece heading)
            drive.encoderDrive(0.3, 36, "Forward", 3000); //drives 3ft foward approaching game piece
            break;

          case kScoring2: //scoring only in mid position
            pivotArm.setArmControl(40); //set arm to score
            claw.clawToggle(); //opens and close claw to score
            drive.encoderDrive(0.4, 200, "Backward", 1500); //drive backwards 16.5 ft (+2 inches to account for going over charge station)
            pivotArm.setArmControl(0); //puts arm down
            drive.turnDrive(0.13, -90, 1500); //turns 90 degree left (relative to starting position)
            drive.encoderDrive(0.3, 30, "Forward", 3500); //drives foward
            drive.turnDrive(0.13, -90, 1500); //turns 90 degree left 
            drive.encoderDrive(0.3, 60, "Forward", 3000); //drives 5ft foward approaching game piece
            break;

          case kScoring3: //scoring only in right position
            pivotArm.setArmControl(40); //set arm to score
            claw.clawToggle(); //opens and close claw to score
            drive.encoderDrive(0.4, 200, "Backward", 5000); //drive backwards 16.5 ft 
            pivotArm.setArmControl(0); //puts arm down
            drive.turnDrive(0.13, 185, 1500); //turns robot 180 degrees right relative to stating positon (+5 for game piece heading)
            drive.encoderDrive(0.3, 36, "Forward", 2000); //drives 3ft foward approaching game piece
            break;
        }
    }
}
