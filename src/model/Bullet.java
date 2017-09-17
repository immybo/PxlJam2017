package model;

import java.awt.*;

public class Bullet extends AbstractEntity {
    private final int damage;
    public final boolean friendler;
    public final StatusEffect effect;
    private Color color;

    public Bullet(AABB aabb, Color color, int depth, double mass, Level level, int damage, Vector velocity, boolean friendler, StatusEffect effect) {
        super(aabb, depth, mass, level);
        super.getAABB().extents = new Vector(5,5);
        this.damage = damage;
        this.setVelocity(velocity);
        this.friendler = friendler;
        this.effect = effect;
        this.color = color;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public boolean isSolid(){
        return false;
    }

    @Override
    public void render(Graphics g) {
        Vector min = getAABB().min();
        Vector ext = getAABB().extents;
        g.setColor(color);
        g.fillOval((int)min.x, (int)min.y, (int)(2*ext.x), (int)(2*ext.y));
    }

    @Override
    public void tick(double dt) {
        if (this.getAABB().center.sub(this.getLevel().getPlayer().getAABB().center).mag() > 100000) {
            this.getLevel().removeEntity(this);
        }
        super.tick(dt);
    }
}
