package game.resistances;

import game.damage.Damage;
import game.damage.magic.DamageMagic;
import game.damage.magic.DamageMagicAir;
import game.damage.magic.DamageMagicEarth;
import game.damage.magic.DamageMagicElectricity;
import game.damage.magic.DamageMagicWater;

public class ResistanceMagic extends Resistance {
    private double blk;
    
    public ResistanceMagic(double block) {
        this.blk = block;
        
        addValidDamageTypes(DamageMagic.class , DamageMagicAir.class , DamageMagicEarth.class , DamageMagicElectricity.class , DamageMagicWater.class);
    }
    
    public double getBlock() {
        return blk;
    }
    
    public void setBlock(double block) {
        blk = block;
    }
    
    @Override
    protected double getRawModifier(Damage damage) {
        return blk;
    }
}
