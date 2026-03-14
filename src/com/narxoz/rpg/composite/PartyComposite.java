package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    public void add(CombatNode node) {
        Objects.requireNonNull(node, "node must not be null");
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int total = 0;
        for (CombatNode child : children) {
            total += child.getHealth();
        }
        return total;
    }

    @Override
    public int getAttackPower() {
        // TODO: Composite aggregation
        // Return total attack of alive children only.
        return 0;
    }

    @Override
    public void takeDamage(int amount) {
        // TODO: Composite distribution
        // Distribute incoming damage across alive children.
        // Suggested baseline:
        // 1) Collect alive children
        // 2) Split amount evenly (or using your own documented rule)
        // 3) Apply damage to each child
    }

    @Override
    public boolean isAlive() {
        // TODO: Composite liveness
        // Return true when at least one child is alive.
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        // TODO: Tree visualization
        // Print this node and recurse into children with increased indent.
        System.out.println(indent + "+ " + name + " [TODO: compute HP/ATK]");
    }

    private List<CombatNode> getAliveChildren() {
        // TODO: helper for takeDamage()
        return new ArrayList<>();
    }
}
