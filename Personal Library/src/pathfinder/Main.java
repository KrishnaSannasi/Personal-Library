package pathfinder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import io.AbstractCanvas;
import io.CanvasPanel;
import pathfinder.AStar2dPathFinder.AStar2dNode;

public class Main {
    public static void main(String[] args) {
        final int width = 10 , height = 10;
        PathFinder.Node.Type[] rawtypes = {PathFinder.Node.Type.EMPTY , PathFinder.Node.Type.OBSTACLE , PathFinder.Node.Type.ATTRACTOR , PathFinder.Node.Type.REPULSOR ,};
        PathFinder.Node.Type[] types;
        int[] weights = {12 , 8 , 0 , 0};
        int sum = 0;
        
        for(int w: weights)
            sum += w;
        
        types = new PathFinder.Node.Type[sum];
        for(int i = 0; i < weights.length; i++) {
            for(int j = 0; j < weights[i]; j++) {
                types[--sum] = rawtypes[i];
            }
        }
        
        AStar2dPathFinder.AStar2dNode[] nodes = new AStar2dPathFinder.AStar2dNode[width * height];
        
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                nodes[i * height + j] = new AStar2dPathFinder.AStar2dNode(types[(int) (Math.random() * types.length)] , i , j);
            }
        }
        
        AStar2dPathFinder finder = new AStar2dPathFinder(nodes);
        
        AbstractCanvas canvas = new AbstractCanvas(800 , 800) {
            volatile AStar2dNode[] path;
            AStar2dNode            start;
            
            final double           dx = 1d * getWidth() / width , dy = 1d * getHeight() / height;
            
            @Override
            public void tick() {
            }
            
            @Override
            public void render(Graphics2D g) {
                for(int i = 0; i < width; i++) {
                    for(int j = 0; j < width; j++) {
                        switch (finder.getNode(i , j).getType()) {
                            case OBSTACLE:
                                g.setColor(Color.BLACK);
                                break;
                            case EMPTY:
                                g.setColor(Color.WHITE);
                                break;
                            case ATTRACTOR:
                                g.setColor(Color.CYAN);
                                break;
                            case REPULSOR:
                                g.setColor(Color.MAGENTA);
                                break;
                        }
                        
                        g.fillRect(i * 80 , j * 80 , 80 , 80);
                    }
                }
                
                g.setColor(Color.GREEN);
                if(path != null) {
                    AStar2dNode last = null;
                    for(AStar2dNode n: path) {
                        System.out.println(dy);
                        g.drawRect((int) (n.getX() * dx + dx / 4) , (int) (n.getY() * dy + dy / 4) , (int) (dx / 2) , (int) (dy / 2));
                        g.setColor(Color.RED);
                        
                        if(last != null)
                            g.drawLine((int) (last.getX() * dx + dx / 2) , (int) (last.getY() * dy + dy / 2) , (int) (n.getX() * dx + dx / 2) , (int) (n.getY() * dy + dy / 2));
                        
                        last = n;
                    }
                }
                else if(start != null) {
                    g.setColor(Color.ORANGE);
                    g.drawRect((int) (start.getX() * dx + dx / 4) , (int) (start.getY() * dx + dx / 4) , (int) (dx / 2) , (int) (dx / 2 ));
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                if(start == null) {
                    start = finder.getNode(e.getX() / 80 , e.getY() / 80);
                    path = null;
                }
                else {
                    start = null;
                }
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                path = finder.getPath(start , finder.getNode(e.getX() / 80 , e.getY() / 80));
            }
        };
        CanvasPanel canvasPanel = new CanvasPanel(canvas);
        
        CanvasPanel.createFrame(canvasPanel).setVisible(true);
        canvas.start();
    }
}
