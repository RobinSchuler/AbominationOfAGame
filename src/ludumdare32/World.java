/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludumdare32;

import SimpleGameLibrarry.Animation;
import SimpleGameLibrarry.SimpleGameLibrarry;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 *  
 */
public class World {
    private final int N_BACKGROUNDS, BACKGROUND_FRAMES; 
    private final float BACKGROUND_SCALING;
    private final LinkedBlockingQueue<Spawner> spawner = new LinkedBlockingQueue<>();
    public final LinkedBlockingQueue<Entity> entitys = new LinkedBlockingQueue<>();
    public final LinkedBlockingQueue<Point2D.Float> goals = new LinkedBlockingQueue<>();
    public final LinkedBlockingQueue<String> toLevel = new LinkedBlockingQueue<>();
    public final You character;
    protected final float sizeX, spawnX, spawnY, sizeY, leftBorder, rightBorder;
    private final byte[][] collisionBox;
    public boolean doneLoading = false;
    
    Animation[] backgrounds;
    BufferedImage front, kulisse;

    public World(String path) {
        Scanner s = null;
        try {
            File f = new File(path + "/world.txt");
            s = new Scanner(f);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        int ispawnX = s.nextInt();
        int ispawnY = s.nextInt();
        System.out.println(s.nextLine());
        int borderL = s.nextInt();
        int borderR = s.nextInt();
        System.out.println(s.nextLine());
        int ngoals = s.nextInt();
        System.out.println(s.nextLine());
        for (int i = 0; i < ngoals; i++) {
            goals.add(new Point2D.Float(s.nextInt(),s.nextInt()));
            String to = s.nextLine();
            toLevel.add(to.substring(1));
        }
        sizeX = s.nextInt() / 100f;
        sizeY = s.nextInt() / 100f;
        System.out.println(s.nextLine());
        N_BACKGROUNDS = s.nextInt();
        s.nextLine();
        BACKGROUND_FRAMES = s.nextInt();
        System.out.println(s.nextLine());
        BACKGROUND_SCALING = s.nextInt() / 100f;
        System.out.println(s.nextLine());
        int spoonX = s.nextInt();
        int spoonY = s.nextInt();
        System.out.println(s.nextLine());
        int bots = s.nextInt();

        backgrounds = new Animation[N_BACKGROUNDS];
        for (int i = 0; i < N_BACKGROUNDS; i++) {
            backgrounds[i] = new Animation(path + "/background" + i, BACKGROUND_FRAMES, 0, 5, true, null);
        }
        //front = new Animation(path + "/front", 1, 0, 1, true, null);
        try {
            front = ImageIO.read(new File(path + "/front.0000.png"));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            kulisse = ImageIO.read(new File(path + "/kulisse.0000.png"));
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        spawnX = ispawnX * sizeX / front.getWidth(); 
        spawnY = ispawnY * sizeY / front.getHeight(); 
        Iterator<Point2D.Float> itt= goals.iterator();
        for (int i = 0; i < ngoals; i++) {
            Point2D.Float f = itt.next();
            f.setLocation(f.getX()* sizeX / front.getWidth(), f.getY()* sizeY / front.getHeight());
        }
        
        System.out.println(s.nextLine());
        for (int i = 0; i < bots; i++) {
            int x = s.nextInt();
            int y = s.nextInt();
            entitys.add(new Bot(x* sizeX / front.getWidth(), y* sizeY / front.getHeight()));
            System.out.println(s.nextLine());
        }
        int shoots = s.nextInt();
        System.out.println(s.nextLine());
        for (int i = 0; i < shoots; i++) {
            int x = s.nextInt();
            int y = s.nextInt();
            entitys.add(new Shoot(x* sizeX / front.getWidth(), y* sizeY / front.getHeight()));
            System.out.println(s.nextLine());
        }
        
        int spawnerc = s.nextInt();
        System.out.println(s.nextLine());
        for (int i = 0; i < spawnerc; i++) {
            int secs = s.nextInt();
            int x = s.nextInt();
            int y = s.nextInt();
            int max = s.nextInt();
            int type = s.nextInt();
            spawner.add(new Spawner(secs, x* sizeX / front.getWidth(), y* sizeY / front.getHeight(), type, max));
            System.out.println(s.nextLine());
        }
        
        entitys.add(new Spoon(spoonX* sizeX / front.getWidth(), spoonY* sizeY / front.getHeight()));
        leftBorder = borderL* sizeX / front.getWidth();
        rightBorder = borderR* sizeX / front.getWidth();

        collisionBox = new byte[front.getWidth()][front.getHeight()];
        for (int i = 0; i < front.getWidth(); i++) {
            for (int j = 0; j < front.getHeight(); j++) {
                int a = new Color(front.getRGB(i, j), true).getAlpha();
                if(new Color(front.getRGB(i, j), false).getRGB() == (new Color(255, 0, 255).getRGB()) ){
                    collisionBox[i][j] = 3; // stachel
                    front.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
                }
                else{
                    if(a >= 254)
                        collisionBox[i][j] = 0;//boden
                    else if(a > 1)
                        collisionBox[i][j] = 1;
                    else
                        collisionBox[i][j] = 2;//luft
                //System.out.print(collisionBox[i][j] + " ");
                }
            }
            //System.out.println(" .");
        }
        character = new You(spawnX,spawnY);
        s.close();
        
        //preloading assets
        new Shoot(0, 0);
        new Projectile(0, 0, true);
        new Bot(0, 0);
        new Spoon(0, 0);
        
        doneLoading = true;
    }
    
    public void update(float passedTime){
        for (Iterator<Entity> iterator = entitys.iterator(); iterator.hasNext();) {
            Entity next = iterator.next();
            next.update(passedTime, this);
        }
        character.update(passedTime, this);
        for (Iterator<Spawner> iterator = spawner.iterator(); iterator.hasNext();) {
            Spawner next = iterator.next();
            next.update(passedTime, this);
        }
        collideGoal();
    }
    
    public void render(SimpleGameLibrarry l){
        if(doneLoading){
            for (int i = 0; i < N_BACKGROUNDS; i++) {
                l.drawAnimation(-character.getPosX() / (BACKGROUND_SCALING*(i+1))+ Entity.KAMERA_VERSCHIEBUNG_X, -character.getPosY() / (BACKGROUND_SCALING*(i+1)), sizeX / (BACKGROUND_SCALING*(i+1)) , sizeY / (BACKGROUND_SCALING*(i+1)) + Entity.KAMERA_VERSCHIEBUNG_Y, backgrounds[i]);
                //l.drawAnimation(0, 0, l.getResolutionFactor(), 1, backgrounds[i]);
            }
            l.drawImage(kulisse,-character.getPosX() + Entity.KAMERA_VERSCHIEBUNG_X, -character.getPosY() + Entity.KAMERA_VERSCHIEBUNG_Y, sizeX , sizeY);
            l.drawImage(front,-character.getPosX() + Entity.KAMERA_VERSCHIEBUNG_X, -character.getPosY() + Entity.KAMERA_VERSCHIEBUNG_Y, sizeX , sizeY);
            for (Iterator<Entity> iterator = entitys.iterator(); iterator.hasNext();) {
                Entity next = iterator.next();
                next.draw(l, character);
            }
            character.draw(l, character);
        }
        
    }
    
    public void renderGui(SimpleGameLibrarry l){
        
    }
    
    public boolean collideToWorld(float x, float y){
        if(x < leftBorder || y < 0 || x >= rightBorder || y >= sizeY){
            return true;
        }
        return collisionBox[(int)(x*front.getWidth()/sizeX)][(int)(y*front.getHeight()/sizeY)] == 0;
    }
    public boolean collideToWorldFloating(float x, float y){
        if(x < leftBorder || y < 0 || x >= rightBorder || y >= sizeY){
            return true;
        }
        return collisionBox[(int)(x*front.getWidth()/sizeX)][(int)(y*front.getHeight()/sizeY)] != 2;
    }
    
    public void collideEnemys(Entity e){
        if(e == character){
            for (Iterator<Entity> iterator = entitys.iterator(); iterator.hasNext();) {
                Entity next = iterator.next();
                if( e.getPosX() + e.getWidth() >= next.getPosX() &&
                        e.getPosX() <= next.getPosX() + next.getHeight() &&
                        e.getPosY() + e.getHeight() >= next.getPosY() &&
                        e.getPosY() <= next.getPosY() + next.getHeight()){
                    if(e.getPosX() + e.getWidth()/2 > next.getPosX() + next.getWidth()/2)
                        next.damage(e.dmgOnTouch(), this, -Entity.KNOCKBACK);
                    else
                        next.damage(e.dmgOnTouch(), this, Entity.KNOCKBACK);
                    e.onCollide();
                    next.onCollide();
                }

            }
            if(e.getPosX()+e.getWidth()/2 < leftBorder || e.getPosY()+e.getHeight() < 0 || e.getPosX()+e.getWidth()/2 >= rightBorder || e.getPosY()+e.getHeight() >= sizeY){
            }
            else if(collisionBox[(int)((e.getPosX()+e.getWidth()/2 )*front.getWidth()/sizeX)][(int)( (e.getPosY()+e.getHeight() )*front.getHeight()/sizeY)] == 3){
                sbDied(e);
            }
            if(e.getPosX()+e.getWidth()/2  < leftBorder || e.getPosY() < 0 || e.getPosX()+e.getWidth()/2  >= rightBorder || e.getPosY() >= sizeY){
            }
            else if(collisionBox[(int)((e.getPosX()+e.getWidth()/2 )*front.getWidth()/sizeX)][(int)( (e.getPosY() )*front.getHeight()/sizeY)] == 3){
                sbDied(e);
            }
        }
        else{
            Entity next = character;
            if( e.getPosX() + e.getWidth() >= next.getPosX() &&
                    e.getPosX() <= next.getPosX() + next.getHeight() &&
                    e.getPosY() + e.getHeight() >= next.getPosY() &&
                    e.getPosY() <= next.getPosY() + next.getHeight()){
                if(e.getPosX() + e.getWidth()/2 > next.getPosX() + next.getWidth()/2)
                    next.damage(e.dmgOnTouch(), this, -Entity.KNOCKBACK);
                else
                    next.damage(e.dmgOnTouch(), this, Entity.KNOCKBACK);
                e.onCollide();
                next.onCollide();
            }

        }
    }
    public void collideGoal(){
        Iterator<String> itt = toLevel.iterator();
        for (Iterator<Point2D.Float> iterator = goals.iterator(); iterator.hasNext();) {
            Point2D.Float next = iterator.next();
            String snext = itt.next();
            if( next.getX() > character.getPosX() &&
                    next.getX() < character.getPosX() + character.getHeight() &&
                    next.getY() > character.getPosY() &&
                    next.getY() < character.getPosY() + character.getHeight()){
                Game.naechsterLevel = snext;
                Game.nextLevel = true;
            }
        }
    }
    
    public void collideEnemysArea(float x, float y, float xe, float ye, int dmg, float knockback, Entity e){
        if(e == character){
            for (Iterator<Entity> iterator = entitys.iterator(); iterator.hasNext();) {
                Entity next = iterator.next();
                if( xe > next.getPosX() &&
                        x < next.getPosX() + next.getHeight() &&
                        ye > next.getPosY() &&
                        y < next.getPosY() + next.getHeight())
                    next.damage(dmg, this, knockback);
            }
        }
        else{
            Entity next = character;
            if( xe > next.getPosX() &&
                    x < next.getPosX() + next.getHeight() &&
                    ye > next.getPosY() &&
                    y < next.getPosY() + next.getHeight())
                next.damage(dmg, this, knockback);
        }
    }
    
    public float getDistanceToGround(float x, float y){
        int i = (int)(x*front.getWidth()/sizeX);
        int j = (int)(y*front.getHeight()/sizeY);
        while( !(i < 0 || j < 0 || i >= front.getWidth() || j >= front.getHeight()) && collisionBox[i][j] == 0){
            j++;
        }
        if(i < 0 || j < 0 || i >= front.getWidth() || j >= front.getHeight()){
            return 0;
        }
        return y - ( j*(float)sizeY/front.getHeight() );
    }
    
    public void sbDied(Entity e){
        if(e == character){
            entitys.clear();
            Game.nextLevel = true;
        }
        else
            entitys.remove(e);
    }
    
    public void newSth(Entity e){
        entitys.add(e);
    }
    
    public int countPeople(){
        return entitys.size();
    }
    
    public void makeCharacterMoveRight() {
        character.setMovementRight();
    }
    
    public void makeCharacterMoveLeft() {
        character.setMovementLeft();
    }

    public void makeCharacterMoveStop() {
        character.setMovementStop();
    }

    public void makeCharacterJump() {
        character.jump();
    }

    public void makeCharacterToggleBoat() {
        character.toggleBoat();
    }

    public void makeCharacterAttack() {
        character.attack(this);
    }
    
}
