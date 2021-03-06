package nl.scribblon.riftcraft.util;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import nl.scribblon.riftcraft.util.iplace.ILocationRC;

/**
 * Created by Scribblon for RiftCraft.
 * Date Creation: 1-8-2014
 */
public class Location implements ILocationRC {

    public static final Location INVALID_LOCATION = new Location(null, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);

    private World world;
    private double x,y,z;

    public Location(World world, double x, double y, double z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(World world, int x, int y, int z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(double x, double y, double z){
        this.world = null;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(ILocationRC locatedObject){
        this.world = locatedObject.getWorld();
        this.x = locatedObject.getX();
        this.y = locatedObject.getY();
        this.z = locatedObject.getZ();
    }

    public Location(Entity entity){
        this.world = entity.worldObj;
        this.x = entity.posX;
        this.y = entity.posY;
        this.z = entity.posZ;
    }

    public Location(TileEntity tileEntity){
        this.world = tileEntity.getWorldObj();
        this.x = tileEntity.xCoord;
        this.y = tileEntity.yCoord;
        this.z = tileEntity.zCoord;
    }

    /*_*********************************************************************************************************
     * Literal getters of various kinds
     */
    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public int getIntX() {
        return (int) Math.floor(x);
    }

    @Override
    public int getIntY() {
        return (int) Math.floor(y);
    }

    @Override
    public int getIntZ() {
        return (int) Math.floor(z);
    }

    @Override
    public int getDimensionID() {
        return world.provider.dimensionId;
    }

    @Override
    public Block getBlockAtLocation() {
        return world.getBlock(getIntX(), getIntY(), getIntZ());
    }

    @Override
    public TileEntity getTileEntityAtLocation() {
        return world.getTileEntity(getIntX(), getIntY(), getIntZ());
    }

    /*_*********************************************************************************************************
     * Auto-Generated things
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location that = (Location) o;

        if (Double.compare(that.x, x) != 0) return false;
        if (Double.compare(that.y, y) != 0) return false;
        if (Double.compare(that.z, z) != 0) return false;
        if(this.world != null)
            if (this.getDimensionID() != that.getDimensionID()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        long temp;
        if(this.world != null)
            result = world.provider.dimensionId;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
