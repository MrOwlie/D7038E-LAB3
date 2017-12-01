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
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author mrowlie
 */
public class GameState extends BaseAppState {
    
    @Override
    public void update(float tpf) {
        Main.model.update(tpf);
    }
    @Override
    protected void initialize(Application app) {
        Main.model.initialize();
    }

    @Override
    protected void cleanup(Application app) {
        
    }

    @Override
    protected void onEnable(){
        Main.model.startTimer();
        Main.refInputManager.addMapping("p1Up", new KeyTrigger(KeyInput.KEY_T));
        Main.refInputManager.addMapping("p1Down", new KeyTrigger(KeyInput.KEY_G));
        Main.refInputManager.addMapping("p1Left", new KeyTrigger(KeyInput.KEY_F));
        Main.refInputManager.addMapping("p1Right", new KeyTrigger(KeyInput.KEY_H));
        
        Main.refInputManager.addMapping("p2Up", new KeyTrigger(KeyInput.KEY_I));
        Main.refInputManager.addMapping("p2Down", new KeyTrigger(KeyInput.KEY_K));
        Main.refInputManager.addMapping("p2Left", new KeyTrigger(KeyInput.KEY_J));
        Main.refInputManager.addMapping("p2Right", new KeyTrigger(KeyInput.KEY_L));
        
        Main.refInputManager.addMapping("Debug", new KeyTrigger(KeyInput.KEY_M));
        
        Main.refInputManager.addListener(actionListener, "p1Up", "p1Down", "p1Left", "p1Right",
                                                         "p2Up", "p2Down", "p2Left", "p2Right");
        Main.refInputManager.addListener(analogListener, "p1Up", "p1Down", "p1Left", "p1Right",
                                                         "p2Up", "p2Down", "p2Left", "p2Right");   
    }

    @Override
    protected void onDisable() {
        Main.refInputManager.deleteMapping("p1Up");
        Main.refInputManager.deleteMapping("p1Down");
        Main.refInputManager.deleteMapping("p1Left");
        Main.refInputManager.deleteMapping("p1Right");
        Main.refInputManager.deleteMapping("p2Up");
        Main.refInputManager.deleteMapping("p2Down");
        Main.refInputManager.deleteMapping("p2Left");
        Main.refInputManager.deleteMapping("p2Right");
        
        Main.refInputManager.removeListener(actionListener);
        Main.refInputManager.removeListener(analogListener);
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf){
            Input.addMessage(new ActionInputContainer(tpf, name, isPressed));
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float howmuch, float tpf){
            Input.addMessage(new AnalogInputContainer(tpf, name));
        }
    };   
}
