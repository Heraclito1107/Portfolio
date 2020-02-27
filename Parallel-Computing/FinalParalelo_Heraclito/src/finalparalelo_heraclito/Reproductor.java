/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalparalelo_heraclito;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Heraclito
 */
public class Reproductor extends Thread {
    AudioStream as;
    @Override
    public void run()
    {
        InputStream music;
        try
        {
            music = new FileInputStream(new File("Disco Inferno.wav"));//("Hasta que te conoci - Raul di Blasio.wav"));
            as = new AudioStream(music);
            AudioPlayer.player.start(as);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void parar()
    {
        AudioPlayer.player.stop(as);
    }
}
