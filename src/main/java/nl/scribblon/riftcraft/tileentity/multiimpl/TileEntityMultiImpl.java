package nl.scribblon.riftcraft.tileentity.multiimpl;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import nl.scribblon.riftcraft.tileentity.RCTileEntity;
import nl.scribblon.riftcraft.util.Location;
import nl.scribblon.riftcraft.util.helper.LogHelper;
import nl.scribblon.riftcraft.util.helper.nbt.NBTLocationHelper;
import nl.scribblon.riftcraft.util.imulti.IMultiTiled;
import nl.scribblon.riftcraft.util.imulti.IMultiTiledMaster;
import nl.scribblon.riftcraft.util.iplace.ILocationRC;

/**
 * Created by Scribblon for RiftCraft.
 * Date Creation: 1-8-2014
 *
 * TODO delete note
 * x w > e
 * z s > n
 * Y d > u
 */
public abstract class TileEntityMultiImpl extends RCTileEntity implements IMultiTiled {

    public static final String IS_MASTER_TAG = "isMaster";
    public static final String HAS_MASTER_TAG = "hasMaster";

    protected boolean hasMaster; //Is persistent
    protected boolean isMaster; //Is persistent

    //For the NBTLocationHelper (Should be purged when no master is set.
    private static final String LOCATION_TAG_PREFIX = "master";
    protected ILocationRC masterLocation;

    public TileEntityMultiImpl(){
        super();
        this.hasMaster = false;
        this.isMaster = false;
        this.masterLocation = Location.INVALID_LOCATION;
    }

    /*_*********************************************************************************************************
     * NBT Stuff
     */
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(IS_MASTER_TAG, isMaster);
        nbtTagCompound.setBoolean(HAS_MASTER_TAG, hasMaster);

        if (hasStatusChanged(nbtTagCompound))
            purgeNBTTagCompound(nbtTagCompound);

        if(hasMaster)
            NBTLocationHelper.writeLocationNBT(nbtTagCompound, masterLocation, LOCATION_TAG_PREFIX);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.isMaster = nbtTagCompound.getBoolean(IS_MASTER_TAG);
        this.hasMaster = nbtTagCompound.getBoolean(HAS_MASTER_TAG);

        if(hasMaster)
            this.masterLocation = NBTLocationHelper.readLocationNBT(nbtTagCompound, LOCATION_TAG_PREFIX);
    }

    /**
     * Check if the status of the TileEntity has changed in comparison to the NBT-data.
     * @param nbtTagCompound
     * @return
     */
    protected boolean hasStatusChanged(NBTTagCompound nbtTagCompound) {
        return (nbtTagCompound.getBoolean(IS_MASTER_TAG) && isMaster) && (nbtTagCompound.getBoolean(HAS_MASTER_TAG) && hasMaster);
    }

    /**
     * Purges NBTTagCompound of data which aren't needed anymore.
     * @param nbtTagCompound
     * @return
     */
    protected NBTTagCompound purgeNBTTagCompound(NBTTagCompound nbtTagCompound) {
        NBTLocationHelper.removeLocationNBT(nbtTagCompound, LOCATION_TAG_PREFIX);
        return nbtTagCompound;
    }

    /*_*********************************************************************************************************
     * Getters + Setters for this Object
     */
    @Override
    public boolean isMaster() {
        return isMaster;
    }

    @Override
    public void setIsMaster(boolean isMaster){
        this.isMaster = isMaster;
    }

    @Override
    public boolean hasMaster() {
        return hasMaster;
    }

    @Override
    public void setHasMaster(boolean hasMaster) {
        this.hasMaster = hasMaster;
    }

    @Override
    public ILocationRC getLocation() {
        return new Location(this);
    }

    @Override
    public boolean isInMultiStructure() {
        return isMaster || hasMaster;
    }

    @Override
    public Block getBlockType() {
        return this.getBlockType();
    }

    /*_*********************************************************************************************************
     * Getters for the Master
     */
    @Override
    public ILocationRC getMasterLocation() {
        return masterLocation;
    }

    @Override
    public TileEntity getMasterTileEntity() {
        return masterLocation.getTileEntityAtLocation();
    }

    @Override
    public Block getMasterBlockType() {
        return masterLocation.getBlockAtLocation();
    }

    @Override
    public IMultiTiledMaster getMaster() {
        try {
            return (IMultiTiledMaster) masterLocation.getTileEntityAtLocation();
        } catch (ClassCastException e){
            LogHelper.fatal("Slave: " + this + ". Has invalid master Block! \n" + e);
            return null;
        }
    }

    @Override
    public IMultiTiledMaster.MultiEntityType getMultiEntityType() {
        return this.getMaster().getMultiEntityType();
    }
}
