package math.hexgrid;

import java.util.ArrayList;

import javafx.util.Pair;

public final class HexPos {
    public static class IntermediateHexPos {
        public static enum Operation {
            ADD ,
            SUB ,
            MUL ,
            DIV
        }
        
        private ArrayList<Pair<Pair<HexPos , Integer> , Operation>> list;
        
        private HexPos init;
        
        public IntermediateHexPos(HexPos pos) {
            this.init = pos;
            this.list = new ArrayList<>();
        }
        
        public HexPos eval() {
            int q = init.q , r = init.r , v;
            Pair<HexPos , Integer> p;
            HexPos h;
            
            for(Pair<Pair<HexPos , Integer> , Operation> pair: list) {
                p = pair.getKey();
                
                switch (pair.getValue()) {
                    case ADD:
                        h = p.getKey();
                        q += h.q;
                        r += h.r;
                        break;
                    case DIV:
                        v = p.getValue();
                        q /= v;
                        r /= v;
                        break;
                    case MUL:
                        v = p.getValue();
                        q *= v;
                        r *= v;
                        break;
                    case SUB:
                        h = p.getKey();
                        q -= h.q;
                        r -= h.r;
                        break;
                    default:
                        break;
                }
            }
            
            list.clear();
            return new HexPos(q , r);
        }
        
        public IntermediateHexPos addOp(Operation op , HexPos pos) {
            assert (op == Operation.ADD || op == Operation.SUB);
            list.add(new Pair<>(new Pair<>(pos , null) , op));
            return this;
        }
        
        public IntermediateHexPos addOp(Operation op , int value) {
            assert (op == Operation.MUL || op == Operation.DIV);
            list.add(new Pair<>(new Pair<>(null , value) , op));
            return this;
        }
    }
    
    public final int           q , r , s;
    private IntermediateHexPos inter;
    
    public HexPos() {
        this(0 , 0);
    }
    
    public HexPos(double q , double r) {
        int rq , rr , rs;
        double s = -q - r;
        rq = (int) Math.round(q);
        rr = (int) Math.round(r);
        rs = (int) Math.round(s);
        
        double dq , dr , ds;
        dq = Math.abs(rq - q);
        dr = Math.abs(rr - r);
        ds = Math.abs(rs - s);
        
        if(dq > dr && dq > ds) {
            rq = -rr - rs;
        }
        else if(dr > ds) {
            rr = -rq - rs;
        }
        
        this.q = rq;
        this.r = rr;
        this.s = rs;
    }
    
    public HexPos(int q , int r) {
        this.q = q;
        this.r = r;
        this.s = -q - r;
    }
    
    public HexPos(HexPos pos) {
        this(pos.q , pos.r);
    }
    
    public int dist(HexPos pos) {
        return (Math.abs(q - pos.q) + Math.abs(r - pos.r) + Math.abs(s - pos.s)) / 2;
    }
    
    public IntermediateHexPos addi(HexPos pos) {
        if(inter == null)
            inter = new IntermediateHexPos(this);
        return inter.addOp(IntermediateHexPos.Operation.ADD , pos);
    }
    
    public IntermediateHexPos subi(HexPos pos) {
        if(inter == null)
            inter = new IntermediateHexPos(this);
        return inter.addOp(IntermediateHexPos.Operation.SUB , pos);
    }
    
    public IntermediateHexPos muli(int value) {
        if(inter == null)
            inter = new IntermediateHexPos(this);
        return inter.addOp(IntermediateHexPos.Operation.MUL , value);
    }
    
    public IntermediateHexPos divi(int value) {
        if(inter == null)
            inter = new IntermediateHexPos(this);
        return inter.addOp(IntermediateHexPos.Operation.DIV , value);
    }
    
    public HexPos add(HexPos pos) {
        return new HexPos(q + pos.q , r + pos.r);
    }
    
    public HexPos sub(HexPos pos) {
        return new HexPos(q - pos.q , r - pos.r);
    }
    
    public HexPos mul(int value) {
        return new HexPos(q * value , r * value);
    }
    
    public HexPos div(int value) {
        return new HexPos(q / value , r / value);
    }
    
    public HexPos rotateCW() {
        return new HexPos(-s , -q);
    }
    
    public HexPos rotateCCW() {
        return new HexPos(-r , -s);
    }
    
    @Override
    public String toString() {
        return String.format("HexPos [q=%s, r=%s, s=%s]" , q , r , s);
    }
    
    @Override
    public int hashCode() {
        return q ^ (r + 0x9e3779b9 + (q << 6) + (q >> 2));
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        HexPos other = (HexPos) obj;
        if(q != other.q)
            return false;
        if(r != other.r)
            return false;
        if(s != other.s)
            return false;
        return true;
    }
}
