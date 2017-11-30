/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import com.jme3.network.HostedConnection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mrowlie
 */
public class PlayerDisk extends Disk{
    
    
    public int score;
    
    public static int playerAmount = 0;
    public HostedConnection conn;
    
    public boolean ready = false;
    
    public static ArrayList<PlayerDisk> playerDisks = new ArrayList();
    
    public boolean[] keyPressed = new boolean[4];
    
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerDisk(HostedConnection conn) {
        super(Main.PLAYER_R);
        this.conn = conn;
        score = 0;
        PlayerDisk.playerDisks.add(this);
        
    }
    
    @Override
    public void tick(float tpf) {
        super.tick(tpf);
        for(int i = 0; i < this.keyPressed.length; i++) {
            if(this.keyPressed[i] == true){
                switch(i) {
                    case 0:
                        this.moveNorth(tpf);
                        NetWrite.updatePlayerDisk(this);
                        break;
                        
                    case 1:
                        this.moveWest(tpf);
                        NetWrite.updatePlayerDisk(this);
                        break;
                        
                    case 2:
                        this.moveSouth(tpf);
                        NetWrite.updatePlayerDisk(this);
                        break;
                        
                    case 3:
                        this.moveEast(tpf);
                        NetWrite.updatePlayerDisk(this);
                        break;
                        
                }
            }
        }
        
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
