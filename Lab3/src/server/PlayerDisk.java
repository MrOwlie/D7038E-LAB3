/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mrowlie
 */
public class PlayerDisk extends Disk{
    
    
    public int score;
    
    public int pid;
    public static int playerAmount = 0;
    public static ConcurrentHashMap<Integer, PlayerDisk> playerMap = new ConcurrentHashMap();
    
    @SuppressWarnings("LeakingThisInConstructor")
    public PlayerDisk(int id) {
        super(Main.PLAYER_R);
        this.pid = PlayerDisk.playerAmount++;
        PlayerDisk.playerMap.put(this.pid, this);
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
