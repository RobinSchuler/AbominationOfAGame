/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludumdare32;

import SimpleGameLibrarry.SimpleGameLibrarry;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 *
 *  
 */
public class Game extends SimpleGameLibrarry{
    World w;
    private boolean up = false, right = false, left = false;
    private float mouseX, mouseY;
    public static boolean nextLevel = true;
    public static String naechsterLevel = "story landung";
    private BufferedImage wasserfall;
    private BufferedImage mouse;
    public static int level = 0;
    public static boolean play = false;
    public static boolean map = false;
    public static boolean option = false;
    public static boolean team = false;
    
    public static void main(String args[]){
        Game g = new Game();
        
    }

    public Game() {
    	 try {
             mouse = ImageIO.read(new File("assets/Maus.png"));
         } catch (IOException ex) {
             Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
         }
    	 
    	 try {
             wasserfall = ImageIO.read(new File("assets/Unbenannt2.png"));
         } catch (IOException ex) {
             Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
         }
    	 
        start(800, 600, 120, 120, "A Platformer", false, false);
        
    }
    
    

    @Override
    public void onRender() {
        if(w != null){
            drawRect(0, 0, getResolutionFactor(), 1, Color.blue);
            w.render(this);
        }
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onRenderLight() {
    }

    @Override
    public void onRenderGui() {
    	
    	
    	if (play == false)
    	{
        drawImage(wasserfall, 0.0f, 0.0f, 1.35f, 1.0f);
        //drawRect(0.0f, 0.0f, 1.5f, 1.5f, Color.white);
    	//drawRectOutline(0.1f, 0.1f, 1.125f, 0.8f, Color.black);	
    	//drawRectOutline(0.1f, 0.3f, 1.125f, 0.2f, Color.black);
    	//drawRectOutline(0.1f, 0.7f, 1.125f, 0.2f, Color.black);
    	
    	if (map == true) {
    		//map bild
    		if (level == 1)
    		drawString("lvl1 in progress", 0.4f, 0.235f, "Arial", Font.BOLD, 60, Color.black);	    		
    		if (level == 2)
    				{
    		drawString("lvl1 complete", 0.4f, 0.235f, "Arial", Font.BOLD, 60, Color.black);
    		drawString("lvl2 in progress", 0.4f, 0.435f, "Arial", Font.BOLD, 60, Color.black);
    				}
    		if (level == 3)
    		{
    			drawString("lvl1 complete", 0.4f, 0.235f, "Arial", Font.BOLD, 60, Color.black);
    			drawString("lvl2 complete", 0.4f, 0.435f, "Arial", Font.BOLD, 60, Color.black);
    			drawString("lvl3 in progress", 0.4f, 0.635f, "Arial", Font.BOLD, 60, Color.black);
    		}
        	
    		if (level == 4)
    		{
    		
    		drawString("lvl1 complete", 0.4f, 0.235f, "Arial", Font.BOLD, 60, Color.black);
    		drawString("lvl2 complete", 0.4f, 0.435f, "Arial", Font.BOLD, 60, Color.black);
        	drawString("lvl3 complete", 0.4f, 0.635f, "Arial", Font.BOLD, 60, Color.black);
    		}
    		
    		drawString("back", 0.4f, 0.835f, "Arial", Font.BOLD, 60, Color.black);
    	}
    	else if( option == true) {
    		drawString("op1", 0.4f, 0.235f, "Arial", Font.BOLD, 60, Color.black);
        	drawString("op2", 0.4f, 0.435f, "Arial", Font.BOLD, 60, Color.black);
        	drawString("op3", 0.4f, 0.635f, "Arial", Font.BOLD, 60, Color.black);
        	drawString("back", 0.4f, 0.835f, "Arial", Font.BOLD, 60, Color.black);
    	}
    	else if( team == true) {
    		//team bild
    		drawString("back", 0.4f, 0.835f, "Arial", Font.BOLD, 60, Color.black);
    	}
    	
    	else {	
    	drawString("Start Playing", 0.4f, 0.235f, "Arial", Font.BOLD, 60, Color.black);
    	drawString("View Map", 0.4f, 0.435f, "Arial", Font.BOLD, 60, Color.black);
    	drawString("Options", 0.4f, 0.635f, "Arial", Font.BOLD, 60, Color.black);
    	drawString("The Team", 0.4f, 0.835f, "Arial", Font.BOLD, 60, Color.black);
     
    	}
    	drawImage (mouse, mouseX,mouseY, 0.06f, 0.06f);
    	}
    	
        if(w != null){
            drawString("FPS: " + getCurrFps(), 0.01f, 0.025f, Color.black);
            drawString("TPS: " + getCurrTps(), 0.01f, 0.045f, Color.black);
            //drawString("speed: " + w.character.getSpeedX() + " X: " + w.character.getPosX() + " Y: " + w.character.getPosY(), 0.01f, 0.055f, Color.black);
            w.renderGui(this);
        }
    }

    @Override
    public void onUpdate(float passedTime) {
    	if (play == true) {
        if(nextLevel){
            if(naechsterLevel.equals("")){
                shutdown();
                return;
            }
            if(naechsterLevel.substring(0, naechsterLevel.indexOf(" ")).equals("level"))
            {
                w = new World("assets/" + naechsterLevel.substring(naechsterLevel.indexOf(" ") + 1));
                level++;
                System.out.println("level" + level);
                
            }
            else if(naechsterLevel.substring(0, naechsterLevel.indexOf(" ")).equals("story"))
                w = new Cinematik("assets/" + naechsterLevel.substring(naechsterLevel.indexOf(" ") + 1));
            nextLevel = false;
        }
        if(left){
            w.makeCharacterMoveLeft();
        }
        if(right){
            w.makeCharacterMoveRight();
        }
        if(!left && !right || left && right){
            w.makeCharacterMoveStop();
        }
        if(w != null)
            w.update(passedTime);
    }
    }
    @Override
    public void onKey(int keyId, boolean pressed) {
        if(keyId == KeyEvent.VK_ESCAPE){
            shutdown();
        }
        if(keyId == KeyEvent.VK_M){
            play = false;
        }
        if(keyId == KeyEvent.VK_D){
            right = pressed;
        }
        if(keyId == KeyEvent.VK_A){
            left = pressed;
        }
        if(keyId == KeyEvent.VK_SPACE){
            if(pressed)
                w.makeCharacterJump();
        }
        
    }

    @Override
    public void onMouse(int Id, boolean pressed, int x, int y) {
    	
        if (map == true) {
        	if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.1f && mouseY <= 0.7f )
        	{
        		 if(naechsterLevel.substring(0, naechsterLevel.indexOf(" ")).equals("level"))
                 {
                     w = new World("assets/" + naechsterLevel.substring(naechsterLevel.indexOf(" ") + 1));
                     
                 }
        	}
        	
        	if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.7f && mouseY <= 0.899f )
    		  {
    	    	map = false; 
    	    	//w.render(this);
    		  }
       }
       else if(option == true) {
    	   if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.1f && mouseY <= 0.299f )
    	   {
    		   option = false;
    		   
    	   }
           
       	else if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.3f && mouseY <= 0.499f )
       	{
       	 option = false;
      
       	}
       	else if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.5f && mouseY <= 0.699f )
       	{  
       	 option = false;
       	} 
       	else if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.7f && mouseY <= 0.899f )
       	{  
           option = false;
           //w.render(this);
       	}
       }
       else if (team == true){
    	    if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.7f && mouseY <= 0.899f )
    	    {
    	    	team = false;
    	    	//w.render(this);	
    	    }
    		   }
       
       else{
    	
    	if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.1f && mouseY <= 0.299f )
    	{
        play = true;	
        //w.render(this);
    	}
    	else if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.3f && mouseY <= 0.499f )
    	{ 
        map = true;	
        //w.render(this);
    	}
    	else if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.5f && mouseY <= 0.699f )
    	{
        option = true;	
        //w.render(this);
    	}
    	else if (pressed && Id == MouseEvent.BUTTON1 && play ==false && mouseX >= 0.1f && mouseX <= 1.125f && mouseY >= 0.7f && mouseY <= 0.899f )
    	{
        team = true;	
        //w.render(this);
    	}
       }
    	
        if(pressed && Id == MouseEvent.BUTTON1)
            w.makeCharacterToggleBoat();
        if(pressed && Id == MouseEvent.BUTTON3)
            w.makeCharacterAttack();
    }

    @Override
    public void onMouseMove(int x, int y, int movex, int movey) {
    	mouseX = x/(float)getResolutionX()*(float)getResolutionX()/(float)getResolutionY();
        mouseY = y/(float)getResolutionY();
    
    }

    @Override
    public float light() {
        return 1;
    }
    
}