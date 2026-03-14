package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

import java.util.List;

public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null || !target.isAlive()) {
            return;
        }

        int dmg = resolvedDamage();
        if (dmg <= 0) {
            return;
        }
        CombatNode leaf = findFirstAliveLeaf(target);
        if (leaf != null) {
            leaf.takeDamage(dmg);
        }
    }
}
