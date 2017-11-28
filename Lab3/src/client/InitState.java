/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.network.Message;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author mrowlie
 */
public class InitState extends BaseAppState{

    static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    @Override
    public void update(float tpf) {
        while(!messageQueue.isEmpty()){
            handleMessage(messageQueue.remove());
        }
    }
    
    @Override
    protected void initialize(Application game) {
        initDisks();    
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
    
    private void handleMessage(Message message){
        if(true){
            
        }
        
        else{
            System.out.println("Error : Unhandled message");
        }
        
    }
    
    public void initDisks() {
        PositiveDisk pd0 = new PositiveDisk(Main.refAssetmanager, - Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD);
        PositiveDisk pd1 = new PositiveDisk(Main.refAssetmanager, 0, Main.POSNEG_MAX_COORD);
        PositiveDisk pd2 = new PositiveDisk(Main.refAssetmanager, Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD);
        PositiveDisk pd3 = new PositiveDisk(Main.refAssetmanager, - Main.POSNEG_MAX_COORD, 0);
        PositiveDisk pd4 = new PositiveDisk(Main.refAssetmanager, Main.POSNEG_MAX_COORD, 0);
        PositiveDisk pd5 = new PositiveDisk(Main.refAssetmanager, - Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD);
        PositiveDisk pd6 = new PositiveDisk(Main.refAssetmanager, 0, - Main.POSNEG_MAX_COORD);
        PositiveDisk pd7 = new PositiveDisk(Main.refAssetmanager, Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD);
        
        NegativeDisk nd0 = new NegativeDisk(Main.refAssetmanager, - Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd1 = new NegativeDisk(Main.refAssetmanager, - Main.POSNEG_MAX_COORD, -Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd2 = new NegativeDisk(Main.refAssetmanager, - Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD);
        NegativeDisk nd3 = new NegativeDisk(Main.refAssetmanager, Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD);
        NegativeDisk nd4 = new NegativeDisk(Main.refAssetmanager, Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd5 = new NegativeDisk(Main.refAssetmanager, Main.POSNEG_MAX_COORD, - Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd6 = new NegativeDisk(Main.refAssetmanager, Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD);
        NegativeDisk nd7 = new NegativeDisk(Main.refAssetmanager, - Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD);    
    }
    
}
