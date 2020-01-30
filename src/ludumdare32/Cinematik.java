/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludumdare32;

import SimpleGameLibrarry.SimpleGameLibrarry;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *  
 */
public class Cinematik extends World{
    float time;
    private String text = "";
    LinkedBlockingQueue<String> order = new LinkedBlockingQueue<>();

    public Cinematik(String path) {
        super(path);
        Scanner s = null;
        try {
            File f = new File(path + "/story.txt");
            s = new Scanner(f);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        while (s.hasNextLine()) {            
            order.add(s.nextLine());
        }
        s.close();
    }
    
    @Override
    public void update(float passedTime){
        super.update(passedTime);
        time += passedTime;
        while (!order.isEmpty() && time*10 > Integer.valueOf(order.peek().substring(0,order.peek().indexOf(" ")))) {            
            String s = order.poll();
            s = s.substring(s.indexOf(" ") + 1);
            
            if(s.substring(0, s.indexOf(" ")).equals("bewege")){
                bewege(s.substring(s.indexOf(" ")+1));
            }
            if(s.substring(0, s.indexOf(" ")).equals("springe")){
                springe(s.substring(s.indexOf(" ")+1));
            }
            if(s.substring(0, s.indexOf(" ")).equals("entferne")){
                entferne(s.substring(s.indexOf(" ")+1));
            }
            if(s.substring(0, s.indexOf(" ")).equals("spawne")){
                spawne(s.substring(s.indexOf(" ")+1));
            }
            if(s.substring(0, s.indexOf(" ")).equals("text")){
                text(s.substring(s.indexOf(" ")+1));
            }
            if(s.substring(0, s.indexOf(" ")).equals("animation")){
                animation(s.substring(s.indexOf(" ")+1));
            }
            if(s.substring(0, s.indexOf(" ")).equals("gehezu")){
                level(s.substring(s.indexOf(" ")+1));
            }
        }
        if(order.isEmpty())
            Game.nextLevel = true;
    }
    
    public void bewege(String s){
        int id = Integer.valueOf(s.substring(0, s.indexOf(" ")));
        s = s.substring(s.indexOf(" ")+1);
        String dir = s;
        if(id == -1){
            if(dir.equals("rechts")){
                character.setMovementRight();
            }
            if(dir.equals("links")){
                character.setMovementLeft();
            }
            if(dir.equals("stop")){
                character.setMovementStop();
            }
            return;
        }
        Iterator<Entity> itt = entitys.iterator();
        Entity n = itt.next();
        for (int i = 0; i < id; i++) {
            n = itt.next();
        }
        if(dir.equals("rechts")){
            n.setMovementRight();
        }
        if(dir.equals("links")){
            n.setMovementLeft();
        }
        if(dir.equals("stop")){
            n.setMovementStop();
        }
        
    }
    
    public void springe(String s){
        int id = Integer.valueOf(s);
        if(id == -1){
            character.jump();
            return;
        }
        Iterator<Entity> itt = entitys.iterator();
        Entity n = itt.next();
        for (int i = 0; i < id; i++) {
            n = itt.next();
        }
        n.jump();
        
    }
    
    public void entferne(String s){
        int id = Integer.valueOf(s);
        
        Iterator<Entity> itt = entitys.iterator();
        itt.next();
        for (int i = 0; i < id; i++) {
            itt.next();
        }
        itt.remove();
        
    }
    
    public void spawne(String s){
        int id = Integer.valueOf(s.substring(0, s.indexOf(" ")));
        s = s.substring(0, s.indexOf(" "));
        int x = Integer.valueOf(s.substring(0, s.indexOf(" ")));
        s = s.substring(0, s.indexOf(" "));
        int y = Integer.valueOf(s.substring(0, s.indexOf(" ")));
        if(id == 0)
            entitys.add(new Bot(x* sizeX / front.getWidth(), y* sizeY / front.getHeight()));
        if(id == 1)
            entitys.add(new Shoot(x* sizeX / front.getWidth(), y* sizeY / front.getHeight()));
        
    }
    
    public void animation(String s){
        int id = Integer.valueOf(s.substring(0, s.indexOf(" ")));
        s = s.substring(s.indexOf(" ")+1);
        int x = Integer.valueOf(s);
        Iterator<Entity> itt = entitys.iterator();
        Entity n = itt.next();
        if(id == -1){
            character.forceToanimation(x);
            return;
        }
        for (int i = 0; i < id; i++) {
            n = itt.next();
        }
        n.forceToanimation(x);
    }
    
    public void text(String s){
        text = s;
    }
    
    public void level(String s){
        Game.naechsterLevel = s;
        Game.nextLevel = true;
    }
    
    @Override
    public void renderGui(SimpleGameLibrarry l){
        l.drawRect(0, .8f, l.getResolutionFactor(), .2f, Color.lightGray);
        l.drawString(text, .1f, .9f, Color.black);
    }
    
    @Override
    public void collideGoal(){
    }
    
    @Override
    public void sbDied(Entity e){
    }
    public void makeCharacterMoveRight() {
    }
    
    public void makeCharacterMoveLeft() {
    }

    public void makeCharacterMoveStop() {
    }

    public void makeCharacterJump() {
        time += 500;
    }

    public void makeCharacterToggleBoat() {
    }

    public void makeCharacterAttack() {
    }
    
    
}
