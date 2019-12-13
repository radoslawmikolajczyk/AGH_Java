package main;

import java.awt.*;

public class Chain implements XmasShape {
    int x;
    int y;
    int x1,x2;
    int y1,y2;
    double scale;
    GradientPaint grad;

    public Chain(int x,int y, int x1,int y1, int x2, int y2, double scale, GradientPaint grad) {
        this.x = x;
        this.y = y;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.scale = scale;
        this.grad = grad;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setPaint(grad);
        g2d.drawLine(x1,y1,x2,y2);
    }
}
