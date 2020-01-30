package ludumdare32;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import SimpleGameLibrarry.Animation;
import SimpleGameLibrarry.AnimationDoneListener;
import SimpleGameLibrarry.SimpleGameLibrarry;
import SimpleGameLibrarry.SmoothAnimationSequence;
import java.awt.Color;

/**
 *
 *  
 */
public abstract class Entity implements AnimationDoneListener{
    public static final int ANIMATION_FRAMES = 1;
    public static final float ANIMATION_LENTH = .3f;
    public static final float KAMERA_VERSCHIEBUNG_X = .5f - You.WIDHT/2; 
    public static final float KAMERA_VERSCHIEBUNG_Y = .6f - You.HEIGHT; 
    public static final float KNOCKBACK = .02f;
    private static final float GRAVITY = 2f,
            REIBUNGS_FAKTOR = 1.5f,
            REIBUNGS_MINUENT = .25f,
            MAX_MOVE_UP = .1f,
            MAX_ATTACKCOOLDOWN = 1,
            FLOAT_SPEED = 3,
            REGEN = 3;
    private int facing = 0; //0=geradeaus 1=links 2 = rechts
    private float live = 100;
    private Animation[] animations;
    private SmoothAnimationSequence animation;
    private float posX, posY, width, height, speedX = 0, speedY = 0, accelerationX = 0, accelerationY = 0, attackcooldown = 0;
    protected int jmpReady = 0;

    public Entity(float posX, float posY, float width, float height) {
        animations = gibAnimationen();
        animation = new SmoothAnimationSequence(animations[whenwhichAnim()[0]], animations[whenwhichAnim()[0]], this);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
    
    public void update(float timePassed, World w){
        speedX += accelerationX * timePassed;
        speedY += accelerationY * timePassed;
        speedY += GRAVITY * timePassed;
        if(w.collideToWorld(posX + width/2 + speedX * timePassed, posY + height + speedY * timePassed)){//left foot
            float moveUp = w.getDistanceToGround(posX + width/2 + speedX * timePassed, posY + height + speedY * timePassed);
            if(moveUp < MAX_MOVE_UP * timePassed && moveUp > 0){
                posY -= moveUp;
            }
            if(floating())
                jmpReady = 1;
            else
                jmpReady = 2;
            speedY = 0;
            onCollide();
        }
        if(w.collideToWorld(posX + speedX * timePassed + width/2, posY + speedY * timePassed + height - MAX_MOVE_UP * timePassed * .5f)) {//legs
            speedX *= .95f;
            speedY = -.4f;
            onCollide();
        }
        if(floating() && w.collideToWorldFloating(posX + speedX * timePassed + width/2, posY + speedY * timePassed + height - MAX_MOVE_UP * timePassed * .5f)) {//legs
            speedY -= FLOAT_SPEED * timePassed;
            jmpReady = 1;
            onCollide();
        }
        if(w.collideToWorld(posX + speedX * timePassed + width/2, posY + speedY * timePassed)){//head
            speedX = 0;
            speedY = .1f;
            onCollide();
        }
        if(w.collideToWorld(posX + speedX * timePassed + width/2, posY + speedY * timePassed + height/3)|| w.collideToWorld(posX + speedX * timePassed + width/2, posY + speedY * timePassed + height*2/3)){//else
            speedX = 0f;
            speedY = 0f;
            onCollide();
        }
        
        
        posX += speedX * timePassed;
        posY += speedY * timePassed;
        
        updateReibung(timePassed);
        
        w.collideEnemys(this);
        attackcooldown -= timePassed;
        if(attackcooldown < 0){
            attackcooldown = 0;
        }
        
        live += REGEN*timePassed;
        if(live > 100){
            live = 100;
        }
    }
    
    private void updateReibung(float timePassed){
        speedX *= ( 1 - REIBUNGS_FAKTOR * timePassed);
        speedY *= ( 1 - REIBUNGS_FAKTOR * timePassed);
        if(speedX > REIBUNGS_MINUENT * timePassed){
            speedX -= REIBUNGS_MINUENT * timePassed;
        }
        else if(speedX < -REIBUNGS_MINUENT * timePassed){
            speedX += REIBUNGS_MINUENT * timePassed;
        }
        else{
            speedX = 0;
        }
        if(speedY > REIBUNGS_MINUENT * timePassed){
            speedX -= REIBUNGS_MINUENT * timePassed;
        }
        else if(speedY < -REIBUNGS_MINUENT * timePassed){
            speedY += REIBUNGS_MINUENT * timePassed;
        }
        else{
            speedY = 0;
        }
    }
    
    public void draw(SimpleGameLibrarry l, You y){
        l.drawSmoothAnimationSequence(posX - y.getPosX() + KAMERA_VERSCHIEBUNG_X, posY - y.getPosY() + KAMERA_VERSCHIEBUNG_Y, width, height, animation);
        if(live < 100)
            l.drawRect(posX - y.getPosX() + KAMERA_VERSCHIEBUNG_X, posY - y.getPosY() + KAMERA_VERSCHIEBUNG_Y, width*live/100, height*0.05f, Color.red);
        l.drawRect(posX - y.getPosX() + KAMERA_VERSCHIEBUNG_X, posY - y.getPosY() + KAMERA_VERSCHIEBUNG_Y + height*0.05f, width*(attackcooldown / MAX_ATTACKCOOLDOWN), height*0.05f, Color.blue);
    }
    
    
    public abstract String getAnimationPath();
    public abstract float jumpStrength();
    public abstract float acceleration();
    public abstract int dmgOnTouch();
    public abstract int dmgOnAttack();
    public abstract float range();
    public abstract float knockback();
    public abstract Animation[] gibAnimationen();
    public abstract boolean floating();
    /** standing, walkingR, walkingL, jumping, attackingR, attackingL, hurt **/
    public abstract int[] whenwhichAnim();

    @Override
    public void animationDone(Animation a){
        if(facingright())
            changeToanimation(whenwhichAnim()[1]);
        else
            changeToanimation(whenwhichAnim()[0]);
    }
    
    protected void onCollide(){
        
    }
    
    private void setAccelerationX(float x){
        accelerationX = x;
        if(facingright()){
            changeToanimation(whenwhichAnim()[2]);
        }
        else{
            changeToanimation(whenwhichAnim()[3]);
        }
    }
    
    public void setMovementRight(){
        facing = 2;
        setAccelerationX(acceleration());
    }
    
    public void setMovementLeft(){
        facing = 1;
        setAccelerationX(-acceleration());
    }
    
    public void setMovementStop(){
        if(!facingright()){
            facing = 0;
        }
        else
            facing = 3;
        if(facingright())
            forceToanimation(whenwhichAnim()[1]);
        else
            forceToanimation(whenwhichAnim()[0]);
        setAccelerationX(0);
    }
    
    private void setAccelerationY(float y){
        accelerationY = y;
    }
    
    public void addToSpeedY(float y){
        speedY += y;
    }
    
    public void addToSpeedX(float x){
        speedX += x;
    }
    
    public float getSpeedX(){
        return speedX;
    }
    
    public void jump(){
        if(jmpReady > 0){
            if(facingright())
                forceToanimation(whenwhichAnim()[4]);
            else
                forceToanimation(whenwhichAnim()[5]);
            jmpReady--;
            setAccelerationY(0);
            speedY -= jumpStrength();
            if(speedY > -jumpStrength()){
                speedY = - jumpStrength();
            }
            if(speedY < -jumpStrength()*7){
                speedY = - jumpStrength()*7;
            }
        }
    }
    
    public void setFacingRight(){
        facing=2;
        if(facingright())
            forceToanimation(whenwhichAnim()[1]);
        else
            forceToanimation(whenwhichAnim()[0]);
    }
    public void setFacingLEft(){
        facing=1;
        if(facingright())
            forceToanimation(whenwhichAnim()[1]);
        else
            forceToanimation(whenwhichAnim()[0]);
    }
    
    public boolean facingright(){
        return facing >= 2;
    }
    
    public void attack(World w){
        if(attackcooldown <= 0){
            attackcooldown += MAX_ATTACKCOOLDOWN;
            if(facingright()){
                forceToanimation(whenwhichAnim()[6]);
                w.collideEnemysArea(getPosX() + getWidth(), getPosY(), getPosX() + getWidth() + range(), getPosY() + getHeight(), dmgOnAttack(), knockback(), this);
            }
            else{
                forceToanimation(whenwhichAnim()[7]);
                w.collideEnemysArea(getPosX() - range(), getPosY(), getPosX(), getPosY() + getHeight(), dmgOnAttack(), -knockback(), this);
            }
        }
    }
    
    public void damage(int i, World w, float knockback){
        speedX += knockback;
        speedY -= knockback/2;
        live -= i;
        if(live < 0){
            w.sbDied(this);
        }
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
    
    public void forceToanimation(int i){
        animation.forceChange(animations[i]);
    }
    public void changeToanimation(int i){
        animation.setNext(animations[i]);
    }
    
    
}
