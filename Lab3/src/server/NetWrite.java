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
import networking.Packet.ChangeState;
import networking.Packet.DiskUpdate;
import networking.Packet.MyAbstractMessage;
import networking.Packet.ScoreUpdate;
import networking.Packet.TimeDiff;
import networking.Packet.TimeSync;
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
        this.server = server;
    }
    
    public void updateDisk(int pid, float x, float y, float vx, float vy){
        messageQueue.add(new MessageFilterPair(null, new DiskUpdate(pid, x, y, vx, vy)));
    }
    
    public void updateScore(int pid, int newScore) {
        messageQueue.add(new MessageFilterPair(null, new ScoreUpdate(pid, newScore)));
    }
    
    public void syncTime(){
        messageQueue.add(new MessageFilterPair(null, new TimeSync(Main.timeElapsed)));
    }
    
    public void sendTimeDiff(Filter filter, float timeDiff) {
        messageQueue.add(new MessageFilterPair(filter, new TimeDiff(timeDiff)));
    }
    
    public void changeState(byte stateID){
        messageQueue.add(new MessageFilterPair(null, new ChangeState(stateID)));
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
