package org.firstinspires.ftc.teamcode.Supers.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Supers.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.Supers.Commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Supers.Commands.LiftCommand;
import org.firstinspires.ftc.teamcode.Supers.Framework.Controller;
import org.firstinspires.ftc.teamcode.Supers.Framework.ExplosiveTele;
import org.firstinspires.ftc.teamcode.Supers.HazmatRobot;

/**
 * Created by robotics9277 on 12/10/2017.
 */
@TeleOp(name = "Full Tele")
public class FullTele extends ExplosiveTele {

    //TODO: Fix Strafe Wheel Slippage, Right: 0.17, Left: 0.1

    HazmatRobot robot;
    DriveCommand drive;
    LiftCommand lift;
    IntakeCommand intake;

    @Override
    public void initHardware() {
        robot = new HazmatRobot(this);

        drive = new DriveCommand(this,robot.drive);
        lift = new LiftCommand(this, robot.lift);
        intake = new IntakeCommand(this, robot.intake);
    }

    @Override
    public void initAction() {
        dController.setJoystickDeadzone(Controller.DeadzoneType.CIRCULAR, 0.3);
        dController.setTriggerDeadzone(0.1);

        mController.setJoystickDeadzone(Controller.DeadzoneType.CIRCULAR, 0.3);
        mController.setTriggerDeadzone(0.1);

        drive.enable();
        lift.enable();
        intake.enable();
    }

    @Override
    public void firstLoop() {

    }

    @Override
    public void bodyLoop() {
        robot.vertical.setPosition(0.1);

        if(mController.b()){
            robot.lb.setPower(0.5);
            robot.rb.setPower(-0.5);
        }

        //telemetry.addData("Latency",robot.imu.getLatency());
    }

    @Override
    public void exit() {

    }
}