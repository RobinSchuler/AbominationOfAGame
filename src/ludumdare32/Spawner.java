/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ludumdare32;

/**
 *
 *  
 */
public class Spawner {
    private float timer, maxTime, posX, posY;
    private int type, maxPers;

    public Spawner(float maxTime, float posX, float posY, int type, int maxPers) {
        this.maxTime = maxTime;
        this.timer = maxTime;
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        this.maxPers = maxPers;
    }
    
    public void update(float passedTime, World w){
        timer -= passedTime;
        if(timer < 0){
            timer += maxTime;
            if(w.countPeople() < maxPers)
                switch (type){
                    case 1:
                        w.newSth(new Shoot(posX, posY));
                        break;
                    default:
                        w.newSth(new Bot(posX, posY));
                        break;
                }
        }
    }
    
    
    
}
