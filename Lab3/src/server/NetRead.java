/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.MyAbstractMessage;
import networking.Packet.TestPacket;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<HostedConnection> {

    GameServer server;
    
    boolean exit = false;
    
    static ConcurrentLinkedQueue<Message> messageQueue;
    
    public NetRead(GameServer server) {
        messageQueue = new ConcurrentLinkedQueue();
        this.server = server;
    }
    
    private void initialize() {
        this.server.server.addMessageListener(this);
    }
    
    private void update() {
        while(!this.exit) {
            if(!messageQueue.isEmpty()){
                Message message = NetRead.messageQueue.remove();
                MyAbstractMessage m = (MyAbstractMessage) message;
                if(m instanceof TestPacket) {
                    TestPacket p = (TestPacket) m;
                    System.out.println(p.getMessage());
                }
                
            }
        }
    }
    
    private void destroy() {
        this.server.server.removeMessageListener(this);
    }
    
    @Override
    public void run() {
        initialize();
        update();
        destroy();
    }
    
    @Override
    public void messageReceived(HostedConnection source, Message m) {
        NetRead.messageQueue.add(m);
    }
    
}
