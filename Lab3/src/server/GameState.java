/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

/**
 *
 * @author mrowlie
 */
public class GameState extends BaseAppState {

    
    @Override
    public void update(float tpf) {
        for(Disk d : Disk.disks){
            d.tick(tpf);
        }
        
    }
    @Override
    protected void initialize(Application app) {
        
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
    
}