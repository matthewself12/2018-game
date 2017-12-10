package org.firstinspires.ftc.teamcode.Meet2.Experimental.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Meet2.Experimental.Framework.BNO055PIDController;
import org.firstinspires.ftc.teamcode.Meet2.Experimental.Framework.Subsystem;
import org.firstinspires.ftc.teamcode.competitionCode.Log;
import org.firstinspires.ftc.teamcode.competitionCode.MotorGroup;

import static org.firstinspires.ftc.teamcode.competitionCode.Utils.maxDouble;

/**
 * Created by Varun on 11/17/2017.
 */

public class DriveSubsystem extends Subsystem {
    private Log driveLog;
    private MotorGroup left, right, strafe;
    private Servo ls,rs;
    private BNO055IMU imu;
    private BNO055PIDController rc;
    private boolean PIDEnabled = false, gyroEnabled = false;
    private double PIDoutput;

    public DriveSubsystem(OpMode opmode, MotorGroup left, MotorGroup right, MotorGroup strafe){
        super(opmode);

        this.left = left;
        this.right = right;
        this.strafe = strafe;
        driveLog = new Log(opmode);
    }

    public DriveSubsystem(OpMode opmode, MotorGroup left, MotorGroup right, MotorGroup strafe, BNO055IMU imu){
        super(opmode);

        this.left = left;
        this.right = right;
        this.strafe = strafe;

        this.imu = imu;
        this.rc = new BNO055PIDController(imu);

        gyroEnabled = true;
        driveLog = new Log(opmode);
    }

    public void enablePID(boolean enabled){
        rc.enable();
        PIDEnabled = enabled;
    }

    public void enablePID(boolean enabled, double kP, double movingScalar){
        rc.enable(kP, movingScalar);
        PIDEnabled = enabled;
    }

    public void resetPID(){
        rc.resetPID();
    }

    public void setPIDTarget(double target){
        rc.setTarget(target);
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {
        set(0,0,0);
    }

    @Override
    public void stop(){
        rc.close();
        imu.close();
        android.util.Log.d("HazmatRobot", "Drive Subsystem Stopped");
    }

    private void set(double leftPow, double rightPow, double strafePow){
        left.set(leftPow);
        right.set(rightPow);
        strafe.set(strafePow);
    }

    private void setCapped(double leftPower, double rightPower, double strafePower) {
        double max = maxDouble(leftPower, rightPower, strafePower);

        if (max < 1) {
            set(leftPower, rightPower, strafePower);
            return;
        }
        set(leftPower / max, rightPower / max, strafePower / max);
    }

    public void arcadeDrive(double x, double y){
        setCapped(x+y, y-x, 0);
    }

    public void strafeArcadeDrive(double x, double y, double z){
        if(Math.abs(z) > 0.1){
            rc.isTurning(true);
        } else{
            rc.isTurning(false);
        }

        if(Math.abs(x) > 0.1 || Math.abs(y) > 0.1){
            rc.isMoving(true);
        } else {
            rc.isMoving(false);
        }

        PIDoutput = rc.getOutput();
        setCapped(-y+z+PIDoutput, -y-z-PIDoutput, -x);

        driveLog.update();
        driveLog.add("PID Output", PIDoutput);
        driveLog.add("Current", rc.currentAngle);
        driveLog.add("Expected", rc.expAngle);
        driveLog.add("Turning", rc.isTurning);
        driveLog.add("Moving", rc.isMoving);
    }
}