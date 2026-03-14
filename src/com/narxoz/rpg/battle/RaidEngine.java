package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Objects;
import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        Objects.requireNonNull(teamA, "teamA must not be null");
        Objects.requireNonNull(teamB, "teamB must not be null");
        Objects.requireNonNull(teamASkill, "teamASkill must not be null");
        Objects.requireNonNull(teamBSkill, "teamBSkill must not be null");
        RaidResult result = new RaidResult();
        result.addLine("=== Raid Start ===");
        result.addLine("Team A: " + teamA.getName() + " (HP=" + teamA.getHealth() + ")");
        result.addLine("Team B: " + teamB.getName() + " (HP=" + teamB.getHealth() + ")");
        result.addLine("Team A skill: " + summarize(teamASkill));
        result.addLine("Team B skill: " + summarize(teamBSkill));

        if (!teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner("Draw");
            result.setRounds(0);
            result.addLine("Both teams are already defeated.");
            result.addLine("=== Raid End ===");
            return result;
        }

        if (!teamA.isAlive()) {
            result.setWinner(teamB.getName());
            result.setRounds(0);
            result.addLine("Team A is already defeated.");
            result.addLine("=== Raid End ===");
            return result;
        }

        if (!teamB.isAlive()) {
            result.setWinner(teamA.getName());
            result.setRounds(0);
            result.addLine("Team B is already defeated.");
            result.addLine("=== Raid End ===");
            return result;
        }
        int rounds = 0;
        final int maxRoundsSafety = 10_000;

        while (teamA.isAlive() && teamB.isAlive() && rounds < maxRoundsSafety) {

            rounds++;

            result.addLine("");
            result.addLine("--- Round " + rounds + " ---");

            // Team A attack
            if (teamA.isAlive()) {
                castWithOptionalCrit("Team A", teamA, teamB, teamASkill, result);
            }

            if (!teamB.isAlive()) {
                break;
            }

            // Team B attack
            if (teamB.isAlive()) {
                castWithOptionalCrit("Team B", teamB, teamA, teamBSkill, result);
            }
        }

    }
}
