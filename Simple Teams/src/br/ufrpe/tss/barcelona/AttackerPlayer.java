package br.ufrpe.tss.barcelona;

import java.util.Random;

import simple_soccer_lib.PlayerCommander;
import simple_soccer_lib.perception.FieldPerception;
import simple_soccer_lib.perception.PlayerPerception;
import simple_soccer_lib.utils.Vector2D;


public class AttackerPlayer extends Thread {
	private PlayerCommander commander;
	
	private PlayerPerception selfPerc;
	private FieldPerception  fieldPerc;
	
	
	public AttackerPlayer(PlayerCommander player) {
		commander = player;
	}

	@Override
	public void run() {
		System.out.println(">> 1. Waiting initial perceptions...");
		selfPerc  = commander.perceiveSelfBlocking();
		fieldPerc = commander.perceiveFieldBlocking();
		
//		System.out.println(">> 2. Moving to initial position...");
		commander.doMoveBlocking(-25.0d, 0.0d);
		
		selfPerc  = commander.perceiveSelfBlocking();
		fieldPerc = commander.perceiveFieldBlocking();
		
//		System.out.println(">> 3. Now starting...");
		while (commander.isActive()) {
			
			if (isAlignedToBall()) {
				if (closeToBall()) {
					
					PlayerPerception otherPlayer = fieldPerc.getTeamPlayers(selfPerc.getSide()).stream().filter(o -> o.getUniformNumber() == 3).findFirst().get();
					commander.doKickBlocking(20, 0);
					
//					System.out.println("P" + otherPlayer.getUniformNumber() + " => Posicoes: OTHER = " + otherPlayer.getPosition() + ", angle = " + angle);
					
				} else {
					runToBall();
				}
			} else {
				turnToBall();
			}
			
			updatePerceptions(); //non-blocking
			
		}
		
		System.out.println(">> 4. Terminated!");
	}

	private boolean closeToBall() {
		Vector2D ballPos = fieldPerc.getBall().getPosition();
		Vector2D myPos = selfPerc.getPosition();
		
		return ballPos.distanceTo(myPos) < 2.0;
	}

	private boolean isAlignedToBall() {
		Vector2D ballPos = fieldPerc.getBall().getPosition();
		Vector2D myPos = selfPerc.getPosition();
		
		if (ballPos == null || myPos == null) {
			return false;			
		}
		
		double angle = selfPerc.getDirection().angleFrom(ballPos.sub(myPos));
		//System.out.println("Vetores: " + ballPos.sub(myPos) + "  " + selfPerc.getDirection());
		//System.out.println(" => Angulo agente-bola: " + angle);
		
		return angle < 15.0d && angle > -15.0d;
	}
	
	double angleToBall() {
		Vector2D ballPos = fieldPerc.getBall().getPosition();
		Vector2D myPos = selfPerc.getPosition();
		
		return selfPerc.getDirection().angleFrom(ballPos.sub(myPos));
	}
	
	double angleToPlayer(PlayerPerception player) {
		Vector2D playerPos = player.getPosition();
		Vector2D myPos = selfPerc.getPosition();
		
		return selfPerc.getDirection().angleFrom(playerPos.sub(myPos));
	}

	private void updatePerceptions() {
		PlayerPerception newSelf = commander.perceiveSelf();
		FieldPerception newField = commander.perceiveField();
		
		if (newSelf != null) {
			this.selfPerc = newSelf;
		}
		if (newField != null) {
			this.fieldPerc = newField;
		}
	}
	
	private void turnToPosition(Vector2D position) {
//		System.out.println("TURN");
		
		commander.doTurnToPointBlocking(position);	
	}

	private void turnToBall() {
//		System.out.println("TURN");
		Vector2D ballPos = fieldPerc.getBall().getPosition();
		Vector2D myPos = selfPerc.getPosition();
//		System.out.println(" => Angulo agente-bola: " + angleToBall() + " (desalinhado)");
//		System.out.println(" => Posicoes: ball = " + ballPos + ", player = " + myPos);
		
		Vector2D newDirection = ballPos.sub(myPos);
//		System.out.println(" => Nova direcao: " + newDirection);
		
		commander.doTurnToPoint(ballPos);
		//DirectionBlocking(newDirection);		
	}
	
	private void runToBall() {
//		System.out.println("RUN");
		commander.doDashBlocking(100.0d);
	}

}
