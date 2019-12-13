package main;

import java.awt.*;

public class Star implements XmasShape {
    int x;
    int y;
    double scale;

    Color lineColor;
    Color fillColor;

    Star(int x, int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    Star(int x,int y,double scale,Color fillColor){
        this(x,y,scale);
        this.setFillColor(fillColor);
    }
    Star(int x,int y,double scale,Color fillColor,Color lineColor){
        this(x,y,scale,fillColor);
        this.setLineColor(lineColor);
    }

    void setLineColor(Color color){
        this.lineColor = color;
    }
    void setFillColor(Color color){
        this.fillColor = color;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(this.fillColor);
        int [] x= new int[]{55, 67, 109, 73, 83, 55, 27, 37, 1, 43};   ////////
        int [] y = new int[]{0, 36, 36, 54, 96, 72, 96, 54, 36, 36};
        g2d.fillPolygon(x,y,x.length);
        g2d.setColor(this.lineColor);
    }
}
