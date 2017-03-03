package org.usfirst.frc.team5338.robot.commands;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5338.robot.Robot;

public class MoveB extends Command {
	
	double rps = 1; //rotations per second at max speed
	double fpr = 1; //foot per rotation
	double dist = 0;
	double timefordist = 0;
	public MoveB(double feet){
		dist = feet;
		requires(Robot.drivetrain);
		timefordist = ((dist/fpr)/rps);
		setTimeout(timefordist);
	}
	
	protected void intitalize(){
		Robot.drivetrain.tank(0, 0);
	}
	
	protected void execute(double feet){
		Robot.drivetrain.tank(-0.5, -0.5);
		Timer.delay(timefordist);
		end();
	}
	
	protected boolean isFinished(){
		return isTimedOut();
	}
	
	protected void end(){
		Robot.drivetrain.tank(0, 0);
	}
	
	protected void interrupted(){
		end();
	}
}
