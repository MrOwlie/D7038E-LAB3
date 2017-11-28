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
import networking.Packet;

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
                handleMessage(messageQueue.remove());   
            }
        }
    }
    
    private void destroy(){
        
    }
    

    @Override
    public void messageReceived(Client source, Message m) {
        messageQueue.add(m);
    }
    
    public void handleMessage(Message message){
       
    }
    
}
