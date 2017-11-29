/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.MyAbstractMessage;
import networking.Packet.TestPacket;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<HostedConnection> {

    GameServer server;
    
    boolean exit = false;
    
    static ConcurrentLinkedQueue<MessageConnectionPair> messageQueue;
    
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
                MessageConnectionPair pair = NetRead.messageQueue.remove();
                MyAbstractMessage m = (MyAbstractMessage) pair.m;
                if(m instanceof TestPacket) {
                    TestPacket p = (TestPacket) m;
                    System.out.println(p.getMessage());
                }
                if(m instanceof TimeSync) {
                    TimeSync p = (TimeSync) m;
                    float diff = (Main.timeElapsed - p.getTime()) / 2;
                    this.server.server.broadcast(Filters.in(pair.c), new TimeDiff(diff));
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
        NetRead.messageQueue.add(new MessageConnectionPair(m, source));
    }
    
}
