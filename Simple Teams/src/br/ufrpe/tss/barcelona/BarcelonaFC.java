package br.ufrpe.tss.barcelona;

import simple_soccer_lib.AbstractTeam;
import simple_soccer_lib.PlayerCommander;


public class BarcelonaFC extends AbstractTeam {

	public BarcelonaFC(String suffix) {
		super("Barcelona" + suffix, 7, false);
	}

	@Override
	protected void launchPlayer(int ag, PlayerCommander commander) {
		
		switch(ag) {
			case 0:
				GolKeeperPlayer golkeeper = new GolKeeperPlayer(commander, ag);
				golkeeper.start();
				break;
			case 1:
				AttackerPlayer attacker = new AttackerPlayer(commander);
				attacker.start();
				break;
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				MidFieldPlayer holder = new MidFieldPlayer(commander, ag);
				holder.start();
				break;
		}
		
	}

}
