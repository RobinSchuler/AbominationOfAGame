/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludumdare32;

import SimpleGameLibrarry.Animation;

/**
 *
 *  
 */
public class Projectile extends Entity{
    public static final float WIDHT = .05f, HEIGHT = .05f;
    private boolean dir;
    private float time = 5;

    public Projectile(float posX, float posY, boolean rightDir) {
        super(posX, posY, WIDHT, HEIGHT);
        dir = rightDir;
        addToSpeedY(-.5f);
        
        if(dir)
            addToSpeedX(acceleration());
        else
            addToSpeedX(-acceleration());
    }

    @Override
    public void update(float timePassed, World w) {
        super.update(timePassed, w);
        time -= timePassed;
        if(time < -1){
            w.sbDied(this);
        }
    }
    
    
    

    @Override
    public String getAnimationPath() {
        return "assets/pics/entitys/shoot";
    }

    @Override
    public float jumpStrength() {
        return 0f;
    }

    @Override
    public float acceleration() {
        return 3;
    }

    @Override
    public int dmgOnTouch() {
        if(time > 0)
            return 33;
        return 0;
    }

    @Override
    public int dmgOnAttack() {
        return 0;
    }

    @Override
    public float range() {
        return 0;
    }

    @Override
    public float knockback() {
        return 0;
    }

    @Override
    public Animation[] gibAnimationen() {
        Animation[] animations = new Animation[]{
            new Animation(getAnimationPath() + "/projectile", 8, 0, ANIMATION_LENTH, true, this),
            new Animation(getAnimationPath() + "/projectilebroken", ANIMATION_FRAMES, 0, ANIMATION_LENTH, true, this),
        };
        return animations;
    }

    @Override
    protected void onCollide() {
        if(time > 0){
            time = 0;
        }
    }
    
    

    /** standing, walkingR, walkingL, jumping, attackingR, attackingL, hurt **/
    @Override
    public int[] whenwhichAnim() {
        if(time > 0)
            return new int[] {0,0,0,0,0,0,0,0};
        return new int[] {1,1,1,1,1,1,1,1};
    }
    @Override
    public boolean floating() {
        return false;
    }
    
}
