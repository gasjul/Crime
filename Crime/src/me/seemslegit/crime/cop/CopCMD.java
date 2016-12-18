package me.seemslegit.crime.cop;

import java.util.HashMap;

import me.seemslegit.crime.Messages;
import me.seemslegit.crime.playerapi.User;
import me.seemslegit.crime.plugin.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CopCMD implements CommandExecutor {

	private HashMap<String, Thread> delay = new HashMap<String, Thread>();

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage("You have to be a player !");
			return true;
		}

		Player p = (Player) cs;
		User u = new User(p);
		if (p.hasPermission("crime.cop")) {
			if(args.length == 0){
				p.sendMessage(Messages.error);
			}
			else if (args[0].equalsIgnoreCase("switch")) {
				boolean changed = Main.instance.getCopManager().switchCop(u);
				
				if(!changed) {
					p.sendMessage(Messages.prefix + " �cYou can't switch right now. You got crime.");
					return true;
				}
				
				if(u.isCop()) {
					//WIRD COP
					p.sendMessage(Messages.prefix+"�6You switched successfully to �1cop.");
				}else{
					//WIRD NORMAL 
					p.sendMessage(Messages.prefix+"�6You switched successfully to �crobber.");
				}
			} else if (args[0].equalsIgnoreCase("respawn")) {
				p.sendMessage(Messages.prefix
						+ "�aInitiated �cemergency �adeath. �6Please wait 3 minutes to let the poison appeal.");
				String uuid = p.getUniqueId().toString();

				if (delay.containsKey(uuid)) {

					Thread d = delay.get(uuid);
					d.stop();

					delay.remove(uuid);

				} else {
					final Player t = p;
					Thread d = new Thread(new Runnable() {

						public void run() {
							try {
								Thread.sleep(1000 * 60 * 3);
								t.setHealth(0d);
							} catch (InterruptedException e) {
								t.sendMessage(Messages.error);
							}
							if (delay.containsValue(Thread.currentThread())) {
								delay.remove(Thread.currentThread());
							}
						}
					});
					delay.put(uuid, d);
					d.start();
				}

			} else {
				p.sendMessage(Messages.unknownCommand);
			}
		}else{
			p.sendMessage("�cNope.");
		}

		return true;

	}

}
