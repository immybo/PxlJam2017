package model;

import java.awt.Graphics;

/**
 * Created by Karametua on 16/09/2017.
 */
public class PoissonBoy extends Enemy {
    public PoissonBoy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
        this.applyForce(new Vector(-100, 0));
    }
    
    @Override
    public void render(Graphics g) {
		Vector min = getAABB().min();
		Vector ext = getAABB().extents;
		g.drawImage(Textures.POISSON_BOY, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
    }
}
