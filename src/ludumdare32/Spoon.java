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
public class Spoon extends Entity{
    public static final float WIDHT = .1f, HEIGHT = .15f;

    public Spoon(float posX, float posY) {
        super(posX, posY, WIDHT, HEIGHT);
    }

    @Override
    public void update(float timePassed, World w) {
        super.update(timePassed, w);
        if(w.character.getPosX() > getPosX())
            setMovementRight();
        else
            setMovementLeft();
        if(w.character.getPosY() + getHeight() < getPosY())
            jump();
        if(Math.random() < 0.1f)
            attack(w);
    }

    @Override
    public void damage(int i, World w, float knockback) {
        addToSpeedX(knockback);
        addToSpeedY(knockback/2);
    }
    

    @Override
    public String getAnimationPath() {
        return "assets/pics/entitys/spoon";
    }

    @Override
    public float jumpStrength() {
        return .75f;
    }

    @Override
    public float acceleration() {
        return .8f;
    }

    @Override
    public int dmgOnTouch() {
        return 0;
    }

    @Override
    public int dmgOnAttack() {
        return 5;
    }

    @Override
    public float range() {
        return .05f;
    }

    @Override
    public float knockback() {
        return 0;
    }

    @Override
    public Animation[] gibAnimationen() {
        Animation[] animations = new Animation[]{
            new Animation(getAnimationPath() + "/spoonerstand", 1, 0, ANIMATION_LENTH, true, this), //0
            new Animation(getAnimationPath() + "/spoonerwalk", 4, 0, ANIMATION_LENTH, true, this), //1
            new Animation(getAnimationPath() + "/spoonerwalkl", 4, 0, ANIMATION_LENTH, true, this),//2
            new Animation(getAnimationPath() + "/spoonerjump", 1, 0, ANIMATION_LENTH, false, this),//3
            new Animation(getAnimationPath() + "/spoonerjumpl", 1, 0, ANIMATION_LENTH, false, this),//4
            new Animation(getAnimationPath() + "/spoonerhit", 3, 0, ANIMATION_LENTH, false, this),//5
            new Animation(getAnimationPath() + "/spoonerhitl", 3, 0, ANIMATION_LENTH, false, this),//6
        };
        return animations;
    }


    /** standing,standingR, walkingR, walkingL, jumping, jmpl, attackingR, attackingL, hurt **/
    @Override
    public int[] whenwhichAnim() {
        return new int[] {0,0,0,1,2,3,4,5,6,0};
    }
    @Override
    public boolean floating() {
        return false;
    }
    
    
}
