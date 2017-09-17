package model;

import java.awt.Graphics;

/**
 * Created by Karametua on 16/09/2017.
 */
public class SquishBoy extends Enemy{
    public SquishBoy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
    }

    private void jump(double dt){
        if(Math.random() < .03 && this.getVelocity().y >= 0){
            this.applyForce(new Vector(0, -1500));
        }
    }
    
    @Override
    public void render(Graphics g) {
		Vector min = getAABB().min();
		Vector ext = getAABB().extents;
		g.drawImage(Textures.SQUISH_BOY, (int) min.x, (int) min.y, (int) (2 * ext.x), (int) (2 * ext.y), null);
		super.render(g);
    }

    @Override
    public void tick(double dt) {
        jump(dt);
        super.tick(dt);
    }
}
