/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;
import networking.Packet.InitClient;

/**
 *
 * @author mrowlie
 */
public class InitState extends BaseAppState{
    private static ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<Message>();
    @Override
    public void update(float tpf){
        if(!messageQueue.isEmpty())handleMessage(messageQueue.remove());
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
        Main.refInputManager.addMapping("Ready", new KeyTrigger(KeyInput.KEY_R));
        Main.refInputManager.addListener(actionListener, "Ready");
    }

    @Override
    protected void onDisable() {
        Main.refInputManager.removeListener(actionListener);
        Main.refInputManager.deleteMapping("Ready");
    }
    
    
    public void initDisks() {
        int id = 0;
        PositiveDisk pd0 = new PositiveDisk(Main.refAssetManager, - Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD, id++);
        PositiveDisk pd1 = new PositiveDisk(Main.refAssetManager, 0, Main.POSNEG_MAX_COORD, id++);
        PositiveDisk pd2 = new PositiveDisk(Main.refAssetManager, Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD, id++);
        PositiveDisk pd3 = new PositiveDisk(Main.refAssetManager, - Main.POSNEG_MAX_COORD, 0, id++);
        PositiveDisk pd4 = new PositiveDisk(Main.refAssetManager, Main.POSNEG_MAX_COORD, 0, id++);
        PositiveDisk pd5 = new PositiveDisk(Main.refAssetManager, - Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD, id++);
        PositiveDisk pd6 = new PositiveDisk(Main.refAssetManager, 0, - Main.POSNEG_MAX_COORD, id++);
        PositiveDisk pd7 = new PositiveDisk(Main.refAssetManager, Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD, id++);
        
        NegativeDisk nd0 = new NegativeDisk(Main.refAssetManager, - Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD, id++);
        NegativeDisk nd1 = new NegativeDisk(Main.refAssetManager, - Main.POSNEG_MAX_COORD, -Main.POSNEG_BETWEEN_COORD, id++);
        NegativeDisk nd2 = new NegativeDisk(Main.refAssetManager, - Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD, id++);
        NegativeDisk nd3 = new NegativeDisk(Main.refAssetManager, Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD, id++);
        NegativeDisk nd4 = new NegativeDisk(Main.refAssetManager, Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD, id++);
        NegativeDisk nd5 = new NegativeDisk(Main.refAssetManager, Main.POSNEG_MAX_COORD, - Main.POSNEG_BETWEEN_COORD, id++);
        NegativeDisk nd6 = new NegativeDisk(Main.refAssetManager, Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD, id++);
        NegativeDisk nd7 = new NegativeDisk(Main.refAssetManager, - Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD, id++);
        
        
    }
    
    public static void addMessage(Message message){
        messageQueue.add(message);
    }
    
    public void handleMessage(Message message){
        if(message instanceof InitClient){
            InitClient packet = (InitClient)message;
            Input.addPlayer(new PlayerDisk(Main.refAssetManager, packet.getDiskID(), packet.getStartPos()));
        }
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf){
            Input.addMessage(new ActionInputContainer(tpf, name, isPressed));
        }
    }; 
    
}
