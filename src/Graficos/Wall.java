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

/**
 *
 * @author Leonardo
 */
public class Wall extends Canvas {
    int iditem;
    int x ;
    int y ;
    int xc ;
    int yc ;
    Universo u;
    BufferedImage imgCar;
    boolean done;
    String tipo;

    public Wall(int iditem, Universo u, String tipo, int x, int y, int xc, int yc,String image) throws IOException {
        this.iditem = iditem;
        this.u = u;
        this.tipo = tipo;
        this.x=x;
        this.y=y;
        this.xc=xc;
        this.yc=yc;
        setBounds(x, y, xc, yc);
        this.imgCar = ImageIO.read(new File(image));
        Graphics2D g = this.imgCar.createGraphics();
        //g.drawImage(this.imgCar, 0, 0, null);
        //g.drawImage(this.imgCar, 0, 0,xc,yc, null);
    }
        @Override
    public void paint(Graphics g) {
        super.paint(g);
        //g.drawImage(this.imgCar, 0, 0, null);
        g.drawImage(this.imgCar, 0, 0,xc,yc, null);
        //this.setBackground(Color.red);
    }

    public int getIditem() {
        return iditem;
    }

    public void setIditem(int iditem) {
        this.iditem = iditem;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXc() {
        return xc;
    }

    public void setXc(int xc) {
        this.xc = xc;
    }

    public int getYc() {
        return yc;
    }

    public void setYc(int yc) {
        this.yc = yc;
    }

    public Universo getU() {
        return u;
    }

    public void setU(Universo u) {
        this.u = u;
    }

    public BufferedImage getImgCar() {
        return imgCar;
    }

    public void setImgCar(BufferedImage imgCar) {
        this.imgCar = imgCar;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    


}
