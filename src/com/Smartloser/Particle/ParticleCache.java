package com.Smartloser.Particle;



import org.bukkit.World;

import org.bukkit.Bukkit;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import java.util.HashMap;

import org.bukkit.Location;

import java.util.List;

import java.util.Map;



public class ParticleCache

{

    public static Map<ParticleEffect, Double> amount;

    public static Map<ParticleEffect, Double> radius;

    public static double updown;

    private List<Location> heartPlaces;

    private Location prevLocation;

    private int heartPos;

    private String player;

    private ParticleEffect effect;

    

    static {

        ParticleCache.amount = new HashMap<ParticleEffect, Double>();

        ParticleCache.radius = new HashMap<ParticleEffect, Double>();

    }

    

    public static void InitValues() {

        final ParticleEffect[] values = ParticleEffect.values();

        for (int i = 0; i < values.length; ++i) {

            final ParticleEffect e = values[i];

            ParticleCache.amount.put(e, 10.0);

            ParticleCache.radius.put(e, 1.0);

        }

    }

    

    public ParticleCache(final Player player, final ParticleEffect effect) {

        super();

        this.player = player.getName();

        this.prevLocation = null;

        this.heartPos = 0;

        this.heartPlaces = new ArrayList<Location>();

        this.effect = effect;

    }

    

    public void tick() {

        final Player p = Bukkit.getPlayer(this.player);

        if (p == null) {

            return;

        }

        if (!p.getLocation().equals((Object)this.prevLocation)) {

            this.recalculateLocs(p);

            this.prevLocation = p.getLocation();

        }

        this.effect.display(this.heartPlaces.get(this.heartPos), 0.0f, 0.0f, 0.0f, 2.0f, 1);

        ++this.heartPos;

        this.heartPos %= this.heartPlaces.size();

    }

    

    private void recalculateLocs(final Player p) {

        final Location center = p.getLocation();

        center.add(0.0, ParticleCache.updown, 0.0);

        final World world = center.getWorld();

        final double amount = ParticleCache.amount.get(this.effect);

        final double radius = ParticleCache.radius.get(this.effect);

        final double increment = 6.283185307179586 / amount;

        this.heartPlaces.clear();

        for (int i = 0; i < amount; ++i) {

            final double angle = i * increment;

            final double x = center.getX() + radius * Math.cos(angle);

            final double z = center.getZ() + radius * Math.sin(angle);

            this.heartPlaces.add(new Location(world, x, center.getY(), z));

        }

    }

}