package com.Smartloser.Particle;



import java.util.HashMap;

import java.util.Map;



import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;



public class ParticleTicker extends BukkitRunnable

{

	 // Copyright Smartloser(Matan), made for Matt Lucas @ ConstantPvP.com. Thank you brandon for also helping me on this

	public static Map<String, ParticleCache> effects = new HashMap<String, ParticleCache>();



	public void run()

	{

		for (ParticleCache cache : effects.values()) {

			cache.tick();

		}

	}



	public static void addPlayer(Player player, ParticleEffect e)

	{

		effects.put(player.getName(), new ParticleCache(player, e));

	}



	public static void removePlayer(Player player)

	{

		if (effects.containsKey(player.getName()))

		{

			effects.remove(player.getName());

		}

	}

}
