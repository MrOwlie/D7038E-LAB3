/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mrowlie
 */
public class InitState extends BaseAppState{


    @Override
    public void update(float tpf) {
        
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
    
    
    public void initDisks() {
        PositiveDisk pd0 = new PositiveDisk( - Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD);
        PositiveDisk pd1 = new PositiveDisk( 0, Main.POSNEG_MAX_COORD);
        PositiveDisk pd2 = new PositiveDisk( Main.POSNEG_MAX_COORD, Main.POSNEG_MAX_COORD);
        PositiveDisk pd3 = new PositiveDisk( - Main.POSNEG_MAX_COORD, 0);
        PositiveDisk pd4 = new PositiveDisk( Main.POSNEG_MAX_COORD, 0);
        PositiveDisk pd5 = new PositiveDisk( - Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD);
        PositiveDisk pd6 = new PositiveDisk( 0, - Main.POSNEG_MAX_COORD);
        PositiveDisk pd7 = new PositiveDisk( Main.POSNEG_MAX_COORD, - Main.POSNEG_MAX_COORD);
        
        NegativeDisk nd0 = new NegativeDisk( - Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd1 = new NegativeDisk( - Main.POSNEG_MAX_COORD, -Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd2 = new NegativeDisk( - Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD);
        NegativeDisk nd3 = new NegativeDisk( Main.POSNEG_BETWEEN_COORD, Main.POSNEG_MAX_COORD);
        NegativeDisk nd4 = new NegativeDisk( Main.POSNEG_MAX_COORD, Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd5 = new NegativeDisk( Main.POSNEG_MAX_COORD, - Main.POSNEG_BETWEEN_COORD);
        NegativeDisk nd6 = new NegativeDisk( Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD);
        NegativeDisk nd7 = new NegativeDisk( - Main.POSNEG_BETWEEN_COORD, - Main.POSNEG_MAX_COORD);
        
        for(Disk d : Disk.disks){
            if(d.getClass() == PositiveDisk.class) {
                PositiveDisk pd = (PositiveDisk) d;
                pd.randomizeVelocity();
            }
            if(d.getClass() == NegativeDisk.class) {
                NegativeDisk nd = (NegativeDisk) d;
                nd.randomizeVelocity();
            }
        }
        
        ArrayList<Vector2f> playerPos = new ArrayList();
        
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(0, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, Main.PLAYER_COORD));
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, 0));
        playerPos.add(new Vector2f(0, 0));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, 0));
        playerPos.add(new Vector2f(-Main.PLAYER_COORD, -Main.PLAYER_COORD));
        playerPos.add(new Vector2f(0, -Main.PLAYER_COORD));
        playerPos.add(new Vector2f(Main.PLAYER_COORD, -Main.PLAYER_COORD));
        
        Random rand = new Random();
        
        for(int i = 0; i < Main.playerAmount; i++){
            Main.players.add(new PlayerDisk(i));
            
        }
        
        for(PlayerDisk p : Main.players) {
            p.setPosition(playerPos.remove(rand.nextInt(playerPos.size())));
            
        }
        
    }
    
}
