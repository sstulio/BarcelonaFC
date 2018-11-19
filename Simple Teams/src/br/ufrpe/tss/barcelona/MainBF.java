package br.ufrpe.tss.barcelona;

import java.net.UnknownHostException;


public class MainBF {

	public static void main(String[] args) throws UnknownHostException {
		BarcelonaFC team1 = new BarcelonaFC("A");
		BarcelonaFC team2 = new BarcelonaFC("B");
		
		team1.launchTeamAndServer();
//		team2.launchTeam();
	}
	
}

