/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import com.jme3.network.Message;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author mrowlie
 */
public class Input implements Runnable {
    boolean running =  true;
    static ConcurrentLinkedQueue<InputContainer> inputQueue = new ConcurrentLinkedQueue<InputContainer>();
    @Override
    public void run() {
        update();
    }
    
    private void update(){
        while(running){
            while(!inputQueue.isEmpty()){
                handleMessage(inputQueue.remove());
            }
        }
    }
    
    public static void addMessage(InputContainer inputContainer){
        inputQueue.add(inputContainer);      
    }
    
    public void handleMessage(InputContainer inputContainer){
        
    }
    
}
