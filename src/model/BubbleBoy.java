package model;

import java.awt.*;

public class BubbleBoy extends Enemy {

    private double shootTimer = 0;
    public BubbleBoy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
    }

    @Override
    public void tick(double dt) {
        shoot(dt);
        super.tick(dt);
    }

    @Override
    public void render(Graphics g) {
        Vector min = getAABB().min();
        Vector ext = getAABB().extents;
        g.drawImage(Textures.BUBBLE_BOY, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
        super.render(g);
    }

    private void shoot(double dt) {
        AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().extents.mult(0.1), this.getVelocity(), null);
        this.shootTimer += dt;
        if(this.shootTimer >= 10) {
            Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 0, new Vector(-5, -0.1), false, StatusEffect.BUBBLE);
            this.getLevel().addEntity(bullet);
            this.shootTimer = 0;
        }
    }
}
