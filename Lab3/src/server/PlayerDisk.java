/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;

/**
 *
 * @author mrowlie
 */
public class PlayerDisk extends Disk{
    
    
    public int score;
    
    public int pid;
    
    public static int playerAmount = 0;
    
    public PlayerDisk(int id) {
        super(Main.PLAYER_R);
        this.pid = PlayerDisk.playerAmount++;
        score = 0;
        
    }
    
    public void addScore(int amount) {
        this.score += amount;
        
    }
    
    public void removeScore(int amount) {
        this.score -= amount;
    }
    
    public void moveNorth(float tpf){
        this.setVelocity(this.getVelocity().getX(), this.getVelocity().getY() + 500 * tpf);
    }
    
    public void moveSouth(float tpf){
        this.setVelocity(this.getVelocity().getX(), this.getVelocity().getY() - 500 * tpf);
    }
    
    public void moveEast(float tpf){
        this.setVelocity(this.getVelocity().getX() + 500 * tpf, this.getVelocity().getY());
    }
    
    public void moveWest(float tpf){
        this.setVelocity(this.getVelocity().getX() - 500 * tpf, this.getVelocity().getY());
    }
    
}
