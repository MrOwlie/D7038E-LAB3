/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author mrowlie
 */
public class Modeling implements Runnable {
    ReentrantLock timeLock;
    Boolean running = true;
    
    float currentTime;
    float timeNextFrame;
    
    public void initialize(){
        
    }
    
    @Override
    public void run() {
        update();
    }
    
    public void update(){
        while(running){
            timeLock.lock();
            try{
                currentTime = timeNextFrame;
                timeNextFrame = 0f;
            }
            finally{
                timeLock.unlock();
            }
            
            
        }
        
        
        
    }
    
    public void updateTime(float tpf){
        timeLock.lock();
        try{
            timeNextFrame += tpf;
        }
        finally{
            timeLock.unlock();
        }
    }
    
}
