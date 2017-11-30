/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.network.Filter;
import com.jme3.network.Filters;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.ChangeState;
import networking.Packet.ClientReady;
import networking.Packet.DisconnectClient;
import networking.Packet.DiskUpdate;
import networking.Packet.InitClient;
import networking.Packet.MyAbstractMessage;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;

/**
 *
 * @author mrowlie
 */
public class NetWrite implements Runnable {

    boolean exit = false;
    
    static ConcurrentLinkedQueue<MessageFilterPair> messageQueue;
    
    static GameServer server;
    
    public NetWrite(GameServer server) {
        messageQueue = new ConcurrentLinkedQueue();
        NetWrite.server = server;
    }
    
    public static void updateDisk(int pid, float x, float y, float vx, float vy){
        messageQueue.add(new MessageFilterPair(new DiskUpdate(pid, x, y, vx, vy), null));
    }
    
    public static void updatePlayerDisk(PlayerDisk player) {
        
        messageQueue.add(new MessageFilterPair(
                    new DiskUpdate(player.diskID, player.pos.x, player.pos.y, player.getVelocity().x, player.getVelocity().y), 
                    Filters.in(player.conn)));
    }
    
    public static void initClient(int diskID, int startPos, Filter filter) {
        messageQueue.add(new MessageFilterPair(new InitClient(diskID, startPos), filter));
    }
    
    public static void clientReady(int diskID, Filter filter) {
        messageQueue.add(new MessageFilterPair(new ClientReady(diskID), filter));
    }
    
    public static void disconnectClient(int diskID, Filter filter) {
        messageQueue.add(new MessageFilterPair(new DisconnectClient(diskID), null));
    }
    
    public static void updateScore(int pid, int newScore) {
        messageQueue.add(new MessageFilterPair(new ScoreUpdate(pid, newScore), null));
    }
    
    public static void syncTime(Filter filter){
        messageQueue.add(new MessageFilterPair(new TimeSync(Main.timeElapsed), filter));
    }
    
    public static void sendTimeDiff(float timeDiff, Filter filter) {
        messageQueue.add(new MessageFilterPair(new TimeDiff(timeDiff), filter));
    }
    
    public static void changeState(byte stateID){
        messageQueue.add(new MessageFilterPair(new ChangeState(stateID), null));
    }
    
    public static void addMessage(MyAbstractMessage message, Filter filter) {
        messageQueue.add(new MessageFilterPair(message, filter));
    }
    
    public static void addMessage(MyAbstractMessage message) {
        messageQueue.add(new MessageFilterPair(message, null));
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
