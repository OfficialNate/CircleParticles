package com.Smartloser.Main;


import java.io.File;

import java.util.logging.Logger;



import org.bukkit.ChatColor;

import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import org.bukkit.plugin.PluginDescriptionFile;

import org.bukkit.plugin.java.JavaPlugin;

import com.Smartloser.Particle.ParticleCache;
import com.Smartloser.Particle.ParticleTicker;

import com.Smartloser.Particle.ParticleEffect;

// Copyright Smartloser(Matan), made for Matt Lucas @ ConstantPvP.com.

public class ParticleMain extends JavaPlugin

{

	public static ParticleMain plugin;



	public final Logger logger = Logger.getLogger("Minecraft");



	public ParticleTicker ticker;



	@Override

	public void onDisable()

	{

		this.logger.info(this.getDescription().getName()

				+ " Has been disabled!");



	}



	@Override
	public void onEnable()
	{
		ticker = new ParticleTicker();

		if (!(new File(this.getDataFolder(), "config.yml").exists()))

		{

			saveDefaultConfig();

		} else {

			getConfig().options().copyDefaults(true);

			saveConfig();

		}

		for (ParticleEffect e : ParticleEffect.values()) {

			ParticleCache.amount.put(e, getConfig().getDouble("Effects." + e.toString() + ".Amount", 10));

			ParticleCache.radius.put(e, getConfig().getDouble("Effects." + e.toString() + ".Radius", 1));

		}

		//DEBUG

		for (ParticleEffect e : ParticleEffect.values()) {

			System.out.print(ParticleCache.amount.get(e) + ":" + ParticleCache.radius.get(e) + " " + e.toString());

		}//DEBUG

		

		PluginDescriptionFile pdfFile = this.getDescription();

		ticker.runTaskTimer(this, 0L, 4L);

		this.logger.info(pdfFile.getName() + " In version: " + pdfFile.getVersion() + " Has been enabled!");
	}



	public boolean onCommand(CommandSender sender, Command cmd,

			String commandLabel, String[] args)

	{

		if (commandLabel.equalsIgnoreCase("effect"))
		{
			if (!(sender instanceof Player))
			{
				sender

						.sendMessage("You have to be a player to use this command!");

				return true;

			}

			if (args.length > 0)
			{

				String s = args[0];
				if (s.equalsIgnoreCase("off"))
				{
					ParticleTicker.removePlayer((Player)sender);

					sender.sendMessage(ChatColor.GOLD + "Your effects are now off!.");

					return true;
				}

				

				if (s.equalsIgnoreCase("up"))
				{
					ParticleCache.updown = 1.9;
					sender.sendMessage(ChatColor.GOLD + "Your particle effects are now on!");
					return true;
				}

				

				else if (s.equalsIgnoreCase("down"))
				{
					ParticleCache.updown = 0.15;
					sender.sendMessage(ChatColor.GOLD + "Your particle effects are now off!");
					return true;
				}
			}
			ParticleEffect effect = ParticleEffect.fromName(args.length > 0 ? args[0] : "");

			if (effect == null)
			{
             sender.sendMessage(ChatColor.GOLD + "You must provide a valid particleffect! Such as: " + getEffects());
				return true;
			}

            if (sender.hasPermission("effect." + effect.getName()) || sender.isOp())
            {
			ParticleTicker.addPlayer((Player) sender, effect);
			sender.sendMessage(ChatColor.RED + "Your effect is now " + effect.getName());
            }
            
            else
            {
            	sender.sendMessage(ChatColor.RED + "You don't have the permission to use this effect!");
            }
		}

		return false;
	}



	private String getEffects()
	{
		StringBuilder sb = new StringBuilder(ParticleEffect.values().length);
		for (ParticleEffect e : ParticleEffect.values())
		{
			sb.append(e.getName());
			sb.append(", ");
		}
		return sb.toString();
	}
}

