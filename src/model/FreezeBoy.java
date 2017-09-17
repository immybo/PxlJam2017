package model;

import java.awt.Graphics;

public class FreezeBoy extends Enemy {

    private double shootTimer = 0;

    public FreezeBoy(AABB aabb, int depth, double mass, Level level) {
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
		g.drawImage(Textures.FREEZE_BOY, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
        super.render(g);
    }

    private void shoot(double dt) {
        AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().extents.mult(0.1), this.getVelocity(), null);
        this.shootTimer += dt;
        if(this.shootTimer >= 3) {
            Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 50, new Vector(-10, 0), false, StatusEffect.FROZEN);
            this.getLevel().addEntity(bullet);
            this.shootTimer = 0;
        }
    }
}
