package model;

import java.awt.*;

public abstract class Enemy extends Character {

    public Enemy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
    }

    @Override
    public int getMovementSpeed() {
        return 400;
    }

    @Override
    public void render(Graphics g){
        double healthBarFull = this.getHealth()/this.getMaxHealth();
        g.setColor(Color.RED);
        g.fillRect((int)this.getAABB().min().x, (int)this.getAABB().min().y - 50, (int)this.getAABB().get_size().x, 20);
        g.setColor(Color.GREEN.darker());
        g.fillRect((int)this.getAABB().min().x, (int)this.getAABB().min().y - 50, (int)(this.getAABB().get_size().x*healthBarFull), 20);
        g.setColor(Color.BLACK);
        g.drawRect((int)this.getAABB().min().x, (int)this.getAABB().min().y - 50, (int)this.getAABB().get_size().x, 20);
    }
}
