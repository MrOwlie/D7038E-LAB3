/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.ChangeState;
import networking.Packet.ClientReady;
import networking.Packet.DiskUpdate;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<Client> {
    
    boolean exit = false;
    
    ConcurrentLinkedQueue<Message> messageQueue;
    
    public NetRead(){
        messageQueue = new ConcurrentLinkedQueue<>();
    }
    
    @Override
    public void run() {
        initialize();
        update();
        destroy();
    }
    
    private void initialize(){
        
    }
    
    private void update(){
        while(!this.exit) {
            if(!messageQueue.isEmpty()){
                Message message = messageQueue.remove();
                this.handlePacket(message);
                
                
            }
        }
    }
    
    private void handlePacket(Message message) {
        if(message instanceof DiskUpdate){
            Modeling.addMessage(message);
            
        } else if(message instanceof ScoreUpdate) {
            
            ScoreUpdate packet = (ScoreUpdate) message;
            
        } else if(message instanceof TimeSync) {
            
            TimeSync packet = (TimeSync) message;
            
        } else if(message instanceof TimeDiff) {
            
            TimeDiff packet = (TimeDiff) message;
            
        } else if(message instanceof ChangeState) {
            
            ChangeState packet = (ChangeState) message;
            
        } else if(message instanceof ClientReady) {
            
            ClientReady packet = (ClientReady) message;
            
        }
    }
    
    private void destroy(){
        
    }
    

    @Override
    public void messageReceived(Client source, Message m) {
        messageQueue.add(m);
    }
}
