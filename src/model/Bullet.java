package model;

public class Bullet extends AbstractEntity {
    private final int damage;
    public final boolean friendler;
    public final StatusEffect effect;

    public Bullet(AABB aabb, int depth, double mass, Level level, int damage, Vector velocity, boolean friendler, StatusEffect effect) {
        super(aabb, depth, mass, level);
        this.damage = damage;
        this.setVelocity(velocity);
        this.friendler = friendler;
        this.effect = effect;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public boolean isSolid(){
        return false;
    }
}
