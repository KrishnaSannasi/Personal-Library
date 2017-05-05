package voronoi;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import math.matrix.Vector;

public class VoronoiUtils {
    public static List<GraphEdge> normalize(Voronoi voronoi , int pointCount , int width , int height , List<GraphEdge> edges , double[][] points) {
        Set<Vector>[] poly = getPolygonPoints(edges , pointCount , width , height);
        
        for(int i = 0; i < poly.length; i++) {
            points[0][i] = 0;
            points[1][i] = 0;
            
            for(Vector p: poly[i]) {
                points[0][i] += p.get(0);
                points[1][i] += p.get(1);
            }
            
            points[0][i] /= poly[i].size();
            points[1][i] /= poly[i].size();
        }
        
        return voronoi.generateVoronoi(points[0] , points[1] , 0 , width , 0 , height);
    }
    
    public static Set<Vector>[] getPolygonPoints(List<GraphEdge> edges , int pointCount , int width , int height) {
        @SuppressWarnings("unchecked")
        Set<Vector>[] poly = new HashSet[pointCount];
        
        for(int i = 0; i < pointCount; i++)
            poly[i] = new HashSet<>();
        
        boolean[] hasX0 = new boolean[pointCount];
        boolean[] hasY0 = new boolean[pointCount];
        boolean[] hasXW = new boolean[pointCount];
        boolean[] hasYH = new boolean[pointCount];
        
        for(GraphEdge e: edges) {
            if(e.x1 == 0)
                hasX0[e.site2] = hasX0[e.site1] = true;
            if(e.y1 == 0)
                hasY0[e.site2] = hasY0[e.site1] = true;
            if(e.x1 == width)
                hasXW[e.site2] = hasXW[e.site1] = true;
            if(e.y1 == height)
                hasYH[e.site2] = hasYH[e.site1] = true;
            
            if(e.x2 == 0)
                hasX0[e.site2] = hasX0[e.site1] = true;
            if(e.y2 == 0)
                hasY0[e.site2] = hasY0[e.site1] = true;
            if(e.x2 == width)
                hasXW[e.site2] = hasXW[e.site1] = true;
            if(e.y2 == height)
                hasYH[e.site2] = hasYH[e.site1] = true;
            
            poly[e.site1].add(new Vector(e.x1 , e.y1));
            poly[e.site1].add(new Vector(e.x2 , e.y2));
            poly[e.site2].add(new Vector(e.x1 , e.y1));
            poly[e.site2].add(new Vector(e.x2 , e.y2));
        }
        
        for(int i = 0; i < pointCount; i++) {
            if(hasX0[i] && hasY0[i])
                poly[i].add(new Vector(0 , 0));
            if(hasX0[i] && hasYH[i])
                poly[i].add(new Vector(0 , height));
            if(hasXW[i] && hasY0[i])
                poly[i].add(new Vector(width , 0));
            if(hasXW[i] && hasYH[i])
                poly[i].add(new Vector(width , height));
        }
        
        return poly;
    }
}
