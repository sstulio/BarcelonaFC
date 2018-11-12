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
				AttackerPlayer attacker = new AttackerPlayer(commander);
				attacker.start();
				break;
			default:
				HolderPlayer holder = new HolderPlayer(commander, ag);
				holder.start();
				break;
		}
		
	}

}
