/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.ConnectionListener;
import com.jme3.network.Filters;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.ClientReady;
import networking.Packet.MyAbstractMessage;
import networking.Packet.TestPacket;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;

/**
 *
 * @author mrowlie
 */
public class NetRead implements Runnable, MessageListener<HostedConnection>, ConnectionListener {

    GameServer server;
    
    boolean exit = false;
    
    static ConcurrentLinkedQueue<MessageConnectionPair> messageQueue;
    
    public NetRead(GameServer server) {
        messageQueue = new ConcurrentLinkedQueue();
        this.server = server;
    }
    
    private void initialize() {
        this.server.server.addMessageListener(this);
        this.server.server.addConnectionListener(this);
    }
    
    private void update() {
        while(!this.exit) {
            if(!messageQueue.isEmpty()){
                MessageConnectionPair pair = NetRead.messageQueue.remove();
                MyAbstractMessage m = (MyAbstractMessage) pair.m;
                if(m instanceof TimeSync) {
                    TimeSync p = (TimeSync) m;
                    float diff = (Main.timeElapsed - p.getTime()) / 2;
                    NetWrite.sendTimeDiff(diff, Filters.in(pair.c));
                }
                if(m instanceof ClientReady) {
                    ClientReady p = (ClientReady) m;
                    Disk disk = Disk.diskMap.get(p.getDiskID());
                    if (disk instanceof PlayerDisk) {
                        PlayerDisk player = (PlayerDisk) disk;
                        player.ready = true;
                        
                    }
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

    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        PlayerDisk player1 = new PlayerDisk(conn);
        PlayerDisk player2 = new PlayerDisk(conn);
        NetWrite.initClient(player1.diskID, Filters.in(conn));
        NetWrite.initClient(player2.diskID, Filters.in(conn));
        NetWrite.syncTime(Filters.in(conn));
        
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        Enumeration<Disk> players = PlayerDisk.diskMap.elements();
        while(players.hasMoreElements()) {
            Disk disk = players.nextElement();
            if(disk instanceof PlayerDisk){
                PlayerDisk player = (PlayerDisk) disk;
                if(player.conn.getId() == conn.getId()) {
                    NetWrite.disconnectClient(player.diskID, null);
                }
            }
        }
    }
    
}
