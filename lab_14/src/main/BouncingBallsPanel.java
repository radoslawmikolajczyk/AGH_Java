package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncingBallsPanel extends JPanel {
    static class Ball {
        int x;
        int y;
        double vx;
        double vy;
        Color color;

        public void changeSpeedVector() {
            if (x + vx < 0 || x + 20 + vx > 700) {
                vx *= -1;
            }
            if (y + vy < 0 || y + 20 + vy > 630) {
                vy *= -1;
            }
            x += vx;
            y += vy;
        }

        public Ball(int x, int y, double vx, double vy, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            g2.setColor(b.color);
            g2.fillOval(b.x, b.y, 20,20);
        }
    }

    void collision(Ball b) {
        for (int i = 0; i < balls.size(); i++) {
            Ball b2 = balls.get(i);
            if (b2 != b) {
                int dx = Math.abs(b2.x - b.x);
                int dy = Math.abs(b2.y - b.y);
                int distSqr = (dx * dx) + (dy * dy);
                if (distSqr < 160) {
                    b.vx *= -1;
                    b.vy *= -1;
                    b2.vx *= -1;
                    b2.vy *= -1;
                }
            }
        }
    }

    List<Ball> balls = new ArrayList<>();

    class AnimationThread extends Thread {
        boolean suspend = true;  //flaga do zawieszenia

        public void pause() {
            suspend = true;
        }

        public synchronized void finishPause() {
            suspend = false;
            notify();
        }

        public void run() {
            while (true) {
                for (Ball ball : balls) {
                    ball.changeSpeedVector();
                    collision(ball);
                }
                repaint();

                synchronized (this) {
                    try {
                        if (suspend) {
                            System.out.println("Suspending");
                            wait();
                        }
                    } catch (InterruptedException e) {
                    }
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private AnimationThread animation = new AnimationThread();
    int height;
    int width;

    BouncingBallsPanel(int width, int height) {
        this.height = height;
        this.width = width;
        animation.start();
        setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
    }

    void onStart() {
        System.out.println("Start or resume animation thread");
        animation.finishPause();
    }

    void onStop() {
        System.out.println("Suspend animation thread");
        animation.pause();
    }

    void onPlus() {
        Random r = new Random();
        Color color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        balls.add(new Ball(50 + r.nextInt(width - 100), 50 + r.nextInt(height - 100), r.nextInt(20) - 10, r.nextInt(20) - 10, color));
        System.out.println("Add a ball");
    }

    void onMinus() {
        if (!balls.isEmpty()) {
            balls.remove(balls.size() - 1);
            System.out.println("Remove a ball");
        } else
            System.out.println("There is nothing to delete.");
    }
}
