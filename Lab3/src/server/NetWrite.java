/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.Filter;
import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet;
import networking.Packet.MyAbstractMessage;
import server.MessageFilterPair;

/**
 *
 * @author mrowlie
 */
public class NetWrite implements Runnable {

    boolean exit = false;
    
    ConcurrentLinkedQueue<MessageFilterPair> messageQueue;
    
    GameServer server;
    
    public NetWrite(GameServer server) {
        messageQueue = new ConcurrentLinkedQueue();
        this.addMessage(new Packet.TestPacket("yolo"));
        this.server = server;
    }
    
    public void addMessage(MyAbstractMessage message, Filter filter) {
        messageQueue.add(new MessageFilterPair(filter, message));
    }
    
    public void addMessage(MyAbstractMessage message) {
        messageQueue.add(new MessageFilterPair(null, message));
    }
    
    
    
    private void initialize() {
    }
    
    private void update() {
        while(!exit){
            if(!messageQueue.isEmpty()){
                MessageFilterPair pair = messageQueue.remove();
                if (pair.filter == null) {
                    server.server.broadcast(pair.message);
                } else {
                    server.server.broadcast(pair.filter, pair.message);
                }
                
            }
        }
    }
    
    private void destroy() {
        
    }
    
    @Override
    public void run() {
        initialize();
        update();
        destroy();
    }
    
}
