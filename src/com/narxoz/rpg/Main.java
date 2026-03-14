package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.AreaSkill;
import com.narxoz.rpg.bridge.FireEffect;
import com.narxoz.rpg.bridge.IceEffect;
import com.narxoz.rpg.bridge.SingleTargetSkill;
import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;
import com.narxoz.rpg.composite.EnemyUnit;
import com.narxoz.rpg.composite.HeroUnit;
import com.narxoz.rpg.composite.PartyComposite;
import com.narxoz.rpg.composite.RaidGroup;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        CombatNode heroesPreview = buildHeroesTeam();
        CombatNode enemiesPreview = buildEnemiesTeam();
        System.out.println("--- Team Structures ---");
        heroesPreview.printTree("");
        enemiesPreview.printTree("");

        Skill slashFire = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill stormFire = new AreaSkill("Storm", 15, new FireEffect());

        System.out.println("\n--- Bridge Preview ---");
        System.out.println("Same skill, different effects:");
        System.out.println("- " + slashFire.getSkillName() + " using " + slashFire.getEffectName());
        System.out.println("- " + slashIce.getSkillName() + " using " + slashIce.getEffectName());
        System.out.println("Same effect, different skills:");
        System.out.println("- " + slashFire.getSkillName() + " using " + slashFire.getEffectName());
        System.out.println("- " + stormFire.getSkillName() + " using " + stormFire.getEffectName());

        long seed = 42L;
        RaidResult run1 = runRaidOnce(seed);
        RaidResult run2 = runRaidOnce(seed);

        boolean deterministic = run1.getWinner().equals(run2.getWinner())
                && run1.getRounds() == run2.getRounds()
                && run1.getLog().equals(run2.getLog());

        System.out.println("\n--- Determinism Check ---");
        System.out.println("Seed: " + seed + " => deterministic? " + deterministic);

        System.out.println("\n--- Raid Result (Run #1) ---");
        System.out.println("Winner: " + run1.getWinner());
        System.out.println("Rounds: " + run1.getRounds());
        for (String line : run1.getLog()) {
            System.out.println(line);
        }

        long differentSeed = 7L;
        RaidResult run3 = runRaidOnce(differentSeed);
        System.out.println("\nDifferent seed (" + differentSeed + ") changes log? " + !run1.getLog().equals(run3.getLog()));

        System.out.println("\n=== Demo Complete ===");
    }

    private static RaidResult runRaidOnce(long seed) {
        CombatNode heroes = buildHeroesTeam();
        CombatNode enemies = buildEnemiesTeam();

        Skill teamASkill = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill teamBSkill = new AreaSkill("Storm", 15, new FireEffect());

        RaidEngine engine = new RaidEngine().setRandomSeed(seed);
        return engine.runRaid(heroes, enemies, teamASkill, teamBSkill);
    }

    private static CombatNode buildHeroesTeam() {
        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina", 90, 40);

        PartyComposite heroes = new PartyComposite("Heroes");
        heroes.add(warrior);
        heroes.add(mage);
        return heroes;
    }

    private static CombatNode buildEnemiesTeam() {
        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);

        PartyComposite frontline = new PartyComposite("Frontline");
        frontline.add(goblin);
        frontline.add(orc);

        // Nested composite: RaidGroup contains a PartyComposite
        RaidGroup enemies = new RaidGroup("Enemy Raid");
        enemies.add(frontline);
        return enemies;
    }
}
