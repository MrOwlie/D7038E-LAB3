/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import networking.Packet.DiskUpdate;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeSync;
import networking.Packet.DisconnectClient;
import networking.Packet.TimeDiff;

/**
 *
 * @author mrowlie
 */
public class Modeling implements Runnable {
    static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    
    ReentrantLock frameTimeLock = new ReentrantLock();
    ReentrantLock gameTimeLock = new ReentrantLock();
    Boolean running = true;
    
    float timeElpasedThisFrame = 0f;
    float timeElpasedNextFrame = 0f;
    float gameTimeElpased = 0f;
    float serverTimeDiff = 0f;    
    
    public void initialize(){
        
    }
    
    @Override
    public void run() {
        update();
    }
    
    public void update(){
        while(running){
            if(!messageQueue.isEmpty()){
                handleMessage(messageQueue.remove());
            }            
            frameTimeLock.lock();
            try{
                timeElpasedThisFrame = timeElpasedNextFrame;
                timeElpasedNextFrame = 0f;
            }
            finally{
                frameTimeLock.unlock();
            }
            if(timeElpasedThisFrame != 0f){
                for(Disk d : Disk.disks){
                    d.tick(timeElpasedThisFrame);
                }
            }
        }     
        
    }
    
    public void updateFrameTime(float tpf){
        frameTimeLock.lock();
        try{
            timeElpasedNextFrame += tpf;
        }
        finally{
            frameTimeLock.unlock();
        }
    }
    
    public void updateGameTime(float tpf){
        gameTimeLock.lock();
        try{
            gameTimeElpased += tpf;
        }
        
        finally{
            gameTimeLock.unlock();
        }
    }
    
    private void setGameTime(float gameTime){
        gameTimeLock.lock();
        try{
            gameTimeElpased = gameTime;
        }
        
        finally{
            gameTimeLock.unlock();
        }
    }
    
    public void handleMessage(Message message){
        if(message instanceof DiskUpdate) {
            DiskUpdate packet = (DiskUpdate) message;
            Disk disk = Disk.diskMap.get(packet.getDiskID());
            disk.setPosition(packet.getX(), packet.getY());
            disk.setVelocity(packet.getVX(), packet.getVY());
        
        } else if(message instanceof ScoreUpdate) {
            ScoreUpdate packet = (ScoreUpdate) message;
            PlayerDisk player = PlayerDisk.playerMap.get(packet.getPid());
            player.score = packet.getNewScore();
            
        } else if(message instanceof TimeSync) {
            TimeSync packet = (TimeSync) message;
            this.gameTimeLock.lock();
            try {
                this.gameTimeElpased = packet.getTime();
                NetWrite.addMessage(new TimeSync(packet.getTime()));
                
            } finally {
                this.gameTimeLock.unlock();
            }
            
        } else if(message instanceof DisconnectClient){
            DisconnectClient packet = (DisconnectClient)message;
            Disk.diskMap.remove(packet.getDiskID());
            
        } else if(message instanceof TimeDiff){
            TimeDiff packet = (TimeDiff)message;
            serverTimeDiff = packet.getDiff();
        }
        
    }
    
    public static void addMessage(Message message){
        messageQueue.add(message);
    }
    
}
