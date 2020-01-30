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
public class You extends Entity{
    private boolean boat = true;
    public static final float WIDHT = .1f, HEIGHT = .15f;

    public You(float posX, float posY) {
        super(posX, posY, WIDHT, HEIGHT);
    }

    @Override
    public String getAnimationPath() {
        return "assets/pics/entitys/character";
    }

    @Override
    public void update(float timePassed, World w) {
        super.update(timePassed, w);
    }

    @Override
    public float jumpStrength() {
        if(boat)
            return .4f;
        return 1.5f;
    }

    @Override
    public float acceleration() {
        if(boat)
            return .8f;
        return 1.3f;
    }

    @Override
    public int dmgOnTouch() {
        return 0;
    }

    @Override
    public int dmgOnAttack() {
        if(boat)
            return 25;
        return 0;
    }

    @Override
    public float range() {
        if(boat)
            return .1f;
        return 0;
    }

    @Override
    public float knockback() {
        if(boat)
            return 1.5f;
        return 0;
    }

    @Override
    public Animation[] gibAnimationen() {
        Animation[] animations = new Animation[]{
             new Animation(getAnimationPath() + "/fatmanstandr", 1, 0, ANIMATION_LENTH, true, this), //0
            new Animation(getAnimationPath() + "/fatmanstand", 1, 0, ANIMATION_LENTH, true, this), //0
            new Animation(getAnimationPath() + "/fatmanwalk", 4, 0, ANIMATION_LENTH, true, this), //1
            new Animation(getAnimationPath() + "/fatmanwalkl", 4, 0, ANIMATION_LENTH, true, this),//2
            new Animation(getAnimationPath() + "/fatmanjump", 1, 0, ANIMATION_LENTH, false, this),//3
            new Animation(getAnimationPath() + "/fatmanjumpl", 1, 0, ANIMATION_LENTH, false, this),//4
            new Animation(getAnimationPath() + "/fatmanjump", 1, 0, ANIMATION_LENTH, false, this),//5
            new Animation(getAnimationPath() + "/fatmanjumpl", 1, 0, ANIMATION_LENTH, false, this),//6
            new Animation(getAnimationPath() + "/fatmantubestand", 1, 0, ANIMATION_LENTH, true, this),//7
            new Animation(getAnimationPath() + "/fatmantubestandr", 1, 0, ANIMATION_LENTH, true, this),//7
            new Animation(getAnimationPath() + "/fatmantubewalk", 4, 0, ANIMATION_LENTH, true, this),//8
            new Animation(getAnimationPath() + "/fatmantubewalklinks", 4, 0, ANIMATION_LENTH, true, this),//9
            new Animation(getAnimationPath() + "/fatmantubejump", 1, 0, ANIMATION_LENTH, false, this),//10
            new Animation(getAnimationPath() + "/fatmantubejumplinks", 1, 0, ANIMATION_LENTH, false, this),//11
            new Animation(getAnimationPath() + "/fatmantubeattack", 1, 0, ANIMATION_LENTH, false, this),//12
            new Animation(getAnimationPath() + "/fatmantubeattacklinks", 1, 0, ANIMATION_LENTH, false, this),//13
        };
        return animations;
    }

    /** standing, walkingR, walkingL, jumping, jumpingL, attackingR, attackingL, hurt **/
    @Override
    public int[] whenwhichAnim() {
        if(boat)
            return new int[] {8,9,10,11,12,13,14,15,8};
        else
            return new int[] {0,1,2,3,4,5,6,7,0};
    }
    
    public void toggleBoat(){
        boat = !boat;
        jmpReady = 0;
    }

    @Override
    public boolean floating() {
        return boat;
    }
    
    
    
    
    
}
