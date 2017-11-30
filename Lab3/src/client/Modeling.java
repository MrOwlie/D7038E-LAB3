/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.DiskUpdate;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeSync;
import networking.Packet.DisconnectClient;
import networking.Packet.TimeDiff;

/**
 *
 * @author mrowlie
 */
public class Modeling{
    static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    
    Boolean running = true;
    
    float gameTimeElapsed = 0f;
    float serverTimeDiff = 0f;
    
    
    public void initialize(){
        
    }
    
    public void update(float tpf){
        if(!messageQueue.isEmpty()){
            handleMessage(messageQueue.remove());
        }            
        for(Disk d : Disk.disks){
            d.tick(tpf);
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
            this.gameTimeElapsed = packet.getTime();
            NetWrite.addMessage(new TimeSync(packet.getTime()));
                        
        } else if(message instanceof DisconnectClient){
            DisconnectClient packet = (DisconnectClient)message;
            Disk.diskMap.remove(packet.getDiskID());
            
        } else if(message instanceof TimeDiff){
            TimeDiff packet = (TimeDiff)message;
            serverTimeDiff = packet.getDiff();
        }       
    }
    
    public void startTimer(){
        gameTimeElapsed = serverTimeDiff;   
    }
    

    public static void addMessage(Message message){
        messageQueue.add(message);
    }
    
}
