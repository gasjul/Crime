package me.seemslegit.crime.managment;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.seemslegit.crime.listener.P_Crime_Listener;
import me.seemslegit.crime.playerapi.UserBase;
import me.seemslegit.crime.plugin.Main;

public class CrimeManager {

	public static final long MAX_CRIME = 1*60*60*24;
	public static final long CRIME_PER_KILL = 400;
	public static final long CRIME_PER_ROB = 480;
	public static final long CRIME_PER_HIT = 100;
	
	private void init() {
		Bukkit.getPluginManager().registerEvents(new P_Crime_Listener(), Main.instance);
	}
	
	public CrimeManager() {
		init();
	}

	/**
	 * 
	 * @param u {@link UserBase}
	 */
	public void clearCrime(UserBase u) {
		u.getStats().set("crime", null);
	}
	
	/**
	 * 
	 * @param u {@link UserBase}
	 * @param crime {@link Long}
	 */
	public void setCrime(UserBase u, long crime) {
		if(crime < 0) crime = 0;
		if(crime > MAX_CRIME) crime = MAX_CRIME;

		if(crime > 0 && u.isCop()) Main.instance.getCopManager().switchCop(u);
		u.getStats().set("crime", System.currentTimeMillis() + (crime * 1000));
	}
	
	/**
	 * 
	 * @param u {@link UserBase}
	 * @return {@link Long}
	 */
	public long getCrime(UserBase u) {
		long l =  u.getStats().getLong("crime", 0);
		if(l != 0) {
			l -= System.currentTimeMillis();
			l /= 1000;
			if(l < 0) l = 0;
			if(l > MAX_CRIME) l = MAX_CRIME;
		}
		return l;
	}
	
	/**
	 * 
	 * @param u {@link UserBase}
	 * @return {@link Boolean}
	 */
	public boolean hasCrime(UserBase u) {
		return getCrime(u) > 0;
	}
	/**
	 * 
	 * @param u {@link Boolean}
	 * @return {@link Void}
	 */
	public void updateCrimeBoard(UserBase u){
		Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective score = (Objective) board.registerNewObjective("aaa", "bbb");
		score.setDisplayName("�c�oCrime Board");
		score.setDisplaySlot(DisplaySlot.SIDEBAR);
		Score a = score.getScore("�aYou are");
		Score b = score.getScore("");
		if(u.hasCrime()){
			b = score.getScore("�c�lbeing searched");
		}else if(!u.hasCrime()){
			b = score.getScore("�6�linnocent");	
		}else if(u.isCop()){
			b = score.getScore("�1�la cop");
		}
		Score c = score.getScore(" ");
		Score d = score.getScore("�7----------------");
		Score e = score.getScore("�3Money: "+u.getCoins());
		Score f = score.getScore("�c�lCrime: �c"+u.getCrime());


		a.setScore(6);
		b.setScore(5);
		c.setScore(4);
		d.setScore(3);
		e.setScore(2);
		f.setScore(1);

		u.getPlayer().setScoreboard(board);
	}
	
}
