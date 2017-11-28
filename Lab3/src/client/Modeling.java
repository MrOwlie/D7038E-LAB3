/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author mrowlie
 */
public class Modeling implements Runnable {
    ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    
    ReentrantLock frameTimeLock = new ReentrantLock();
    ReentrantLock gameTimeLock = new ReentrantLock();
    Boolean running = true;
    
    float timeElpasedThisFrame = 0f;
    float timeElpasedNextFrame = 0f;
    float gameTimeElpased = 0f;
    
    public void initialize(){
        
    }
    
    @Override
    public void run() {
        update();
    }
    
    public void update(){
        while(running){
            while(!messageQueue.isEmpty()){
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
                for(Disk d : server.Disk.disks){
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
    
    public void handleMessage(Message message){
        
    }
    
    public void addMessage(Message message){
        messageQueue.add(message);
    }
    
}
