/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author Leonardo
 */
public class Tunel extends Canvas{

    int iditem;
    int x;
    int y;
    int xc;
    int yc;
    Universo u;
    BufferedImage imgCar;
    boolean full;
    int idactualitem;
    Item actualitem;
    String tipo;

    public Tunel(int iditem, Universo u, String tipo, int x, int y, int xc, int yc, String image) throws IOException {
        this.iditem = iditem;
        this.u = u;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.xc = xc;
        this.yc = yc;
        full = false;
        this.idactualitem = -1;
        
        setBounds(x, y, xc, yc);
        //this.imgCar = ImageIO.read(new File(image));
        //Graphics2D g = this.imgCar.createGraphics();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawImage(this.imgCar, 0, 0, null);
        //g.drawImage(this.imgCar, 0, 0, xc, yc, null);
        //this.setBackground(Color.red);
    }

    public synchronized void enteringTunel(Item i) throws InterruptedException {
        if (!full || this.idactualitem == i.getIditem()) {
            if (idactualitem == -1) {
                full = true;
                this.idactualitem = i.getIditem();
                actualitem = i;
            } else if (!i.getBounds().intersects(this.getBounds())) {
                actualitem = null;
                idactualitem = -1;
                full = false;
                notifyAll();
            }
        } else {
            wait();
        }
    }

    public synchronized void travessingTunel(Item i) {
        if (!i.getBounds().intersects(this.getBounds())) {
            actualitem = null;
            idactualitem = -1;
            full = false;
            notifyAll();
        }
    }
    

    public void itemIsdeadonTunel(){
        if(this.actualitem!=null){
            if(!actualitem.getBounds().intersects(this.getBounds())){
                System.out.println(this.actualitem.toString());
                actualitem = null;
                idactualitem = -1;
                full = false;
                wakeup();
            }  
        }
    }
    
    public synchronized void wakeup(){
        notifyAll();
    }

    public Universo getU() {
        return u;
    }

    public void setU(Universo u) {
        this.u = u;
    }

 

    
}
