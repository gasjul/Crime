package me.seemslegit.crime.cop;

import me.seemslegit.crime.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CopCMD implements CommandExecutor{

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(!(cs instanceof Player)){
			cs.sendMessage("You have to be a player !");
		}
		Player p = (Player) cs;
		if(args[0].equalsIgnoreCase("switch")){
			p.sendMessage(Messages.prefix+"�aYou switched successfully to"+CopManager.switchCop(p));
			
		}if(args[0].equalsIgnoreCase("respawn")){
			
		}
		return true;
	}
	
	

}
