/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Client;
import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author mrowlie
 */
public class NetWrite implements Runnable {
    
    boolean exit = false;
    Client myClient;
    
    static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    
    public NetWrite(Client myClient){
        this.myClient = myClient;
        
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
        while(!exit){
            if(!messageQueue.isEmpty()){
                Message message = messageQueue.remove();
                myClient.send(message);
            }
        }
    }
    
    public static void addMessage(Message message){
        messageQueue.add(message);
    }
    private void destroy(){
        
    }
    
}
