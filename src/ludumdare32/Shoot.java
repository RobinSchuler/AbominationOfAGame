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
public class Shoot extends Entity{
    public static final float WIDHT = .1f, HEIGHT = .15f;
    private float ai_timer = 0f;

    public Shoot(float posX, float posY) {
        super(posX, posY, WIDHT, HEIGHT);
    }

    @Override
    public void update(float timePassed, World w) {
        super.update(timePassed, w);
        ai_timer -= timePassed;
        if(ai_timer < 0){
            ai_timer += 1*Math.random();
            switch ((int)(Math.random()*3)){
                case 0:
                    setFacingRight();
                    break;
                case 1:
                    setFacingLEft();
                    break;
                case 2:
                    if(facingright()){
                        w.newSth(new Projectile(getPosX() + getWidth() + .01f, getPosY(), true));
                    }
                    else
                        w.newSth(new Projectile(getPosX() - .01f, getPosY(), false));
                    break;
                default:
                    setMovementStop();
                    break;
            }
        }
    }

    
    
    
    

    @Override
    public String getAnimationPath() {
        return "assets/pics/entitys/shoot";
    }

    @Override
    public float jumpStrength() {
        return 1.5f;
    }

    @Override
    public float acceleration() {
        return 1;
    }

    @Override
    public int dmgOnTouch() {
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
            new Animation(getAnimationPath() + "/throwratstand", 1, 0, ANIMATION_LENTH, true, this), //0
            new Animation(getAnimationPath() + "/throwratstandr", 1, 0, ANIMATION_LENTH, true, this), //1
            new Animation(getAnimationPath() + "/throwrathitr", 2, 0, ANIMATION_LENTH, false, this),//2
            new Animation(getAnimationPath() + "/throwrathit", 2, 0, ANIMATION_LENTH, false, this),//3
        };
        return animations;
    }

    /** standing,standingR, walkingR, walkingL, jumping, jmpl, attackingR, attackingL, hurt **/
    @Override
    public int[] whenwhichAnim() {
        return new int[] {0,1,1,0,1,1,2,3,0};
    }
    
    @Override
    public boolean floating() {
        return false;
    }
    
}
