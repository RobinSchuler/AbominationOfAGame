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
public class Bot extends Entity{
    public static final float WIDHT = .1f, HEIGHT = .15f;
    private float ai_timer = 0f;

    public Bot(float posX, float posY) {
        super(posX, posY, WIDHT, HEIGHT);
    }

    @Override
    public void update(float timePassed, World w) {
        super.update(timePassed, w);
        ai_timer -= timePassed;
        if(ai_timer < 0){
            ai_timer += 5*Math.random();
            switch ((int)(Math.random()*6)){
                case 0:
                    setMovementRight();
                    break;
                case 1:
                    setMovementLeft();
                    break;
                case 2:
                    jump();
                    break;
                case 3:
                    attack(w);
                    break;
                default:
                    setMovementStop();
                    break;
            }
        }
    }

    
    
    
    

    @Override
    public String getAnimationPath() {
        return "assets/pics/entitys/rat";
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
        return 1;
    }

    @Override
    public int dmgOnAttack() {
        return 10;
    }

    @Override
    public float range() {
        return 1;
    }

    @Override
    public float knockback() {
        return 0;
    }

    @Override
    public Animation[] gibAnimationen() {
        Animation[] animations = new Animation[]{
            new Animation(getAnimationPath() + "/slapratstand", 1, 0, ANIMATION_LENTH, true, this), //0
            new Animation(getAnimationPath() + "/slapratstandr", 1, 0, ANIMATION_LENTH, true, this), //0
            new Animation(getAnimationPath() + "/slapratwalkr", 2, 0, ANIMATION_LENTH, true, this), //1
            new Animation(getAnimationPath() + "/slapratwalk", 2, 0, ANIMATION_LENTH, true, this),//2
            new Animation(getAnimationPath() + "/slaprathit", 2, 0, ANIMATION_LENTH, false, this),//3
            new Animation(getAnimationPath() + "/slaprathitl", 2, 0, ANIMATION_LENTH, false, this),//4
        };
        return animations;
    }


    /** standing,standingR, walkingR, walkingL, jumping, jmpl, attackingR, attackingL, hurt **/
    @Override
    public int[] whenwhichAnim() {
        return new int[] {0,1,2,3,2,3,4,5,0};
    }
    
    @Override
    public boolean floating() {
        return false;
    }
    
}
