package nl.scribblon.riftcraft.item.shard;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nl.scribblon.riftcraft.item.RCItem;
import nl.scribblon.riftcraft.reference.Names;
import nl.scribblon.riftcraft.util.helper.LogHelper;
import nl.scribblon.riftcraft.util.helper.nbt.NBTItemStackHelper;

/**
 * Created by Scribblon for riftcraft.
 * Date Creation: 17-6-2014
 */
public class ItemEnderShard extends RCItem {
    // Default references
    public static final int MAX_STACK_SIZE = 32;

    // References involving NBT - Throw data.
    public static final String THROW_STRENGTH_TAG = "enderShardThrowStrength";
    public static final float DEFAULT_THROW_STRENGTH = 1.0F; // Will make the enderpearl be thrown at vanilla speed.
    public static final float THROW_STRENGTH_INTERVAL = 0.2F;
    public static final float MIN_THROW_STRENGTH = 0.2F;
    public static final float MAX_THROW_STRENGTH = 1.6F;

    public ItemEnderShard() {
        super();
        this.setUnlocalizedName(Names.Items.ENDER_SHARD);
        this.setMaxStackSize(MAX_STACK_SIZE);
    }



    /*
     * When player uses the item, it should act like an EnderPearl.
     * When player is sneaking it should increase throw power.
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        // When sneaking do not process stack.
        if(entityPlayer.isSneaking()){
            LogHelper.reportWhenDebugging(entityPlayer.getDisplayName() + " is adjusting throw strength UP!");

            // If the the tag is not present make it, or adjust it.
            if(NBTItemStackHelper.hasTagKey(itemStack, THROW_STRENGTH_TAG)) {
                NBTItemStackHelper.setFloat(itemStack, THROW_STRENGTH_TAG, DEFAULT_THROW_STRENGTH);
            } else {
                float newThrowStrength = NBTItemStackHelper.getFloat(itemStack, THROW_STRENGTH_TAG) + THROW_STRENGTH_INTERVAL;
                if(newThrowStrength < MAX_THROW_STRENGTH){
                    NBTItemStackHelper.setFloat(itemStack, THROW_STRENGTH_TAG, newThrowStrength);
                } else {
                    //Maybe do something with the icon?
                }
            }
            return itemStack;
        }

        //When player is in creative mode, just throw the damn thing... Don't know why vanilla disables this.
        if(!entityPlayer.capabilities.isCreativeMode){
            --itemStack.stackSize;
        }

        if(!world.isRemote) {
            //TODO Throw the damn thing!
        }
        return itemStack;
    }

    /*
     * When player is sneaking decrease power
     */
    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack itemStack) {
        // When entity isn't a player don't do a thing
        if(!(entityLiving instanceof EntityPlayer)) return false;
        // When entity isn't sneaking... yeah
        if(!entityLiving.isSneaking()) return false;

        LogHelper.reportWhenDebugging(((EntityPlayer) entityLiving).getDisplayName() + " is adjusting throw strength DOWN!");

        // If the the tag is not present make it, or adjust it.
        if(NBTItemStackHelper.hasTagKey(itemStack, THROW_STRENGTH_TAG)) {
            NBTItemStackHelper.setFloat(itemStack, THROW_STRENGTH_TAG, DEFAULT_THROW_STRENGTH);
        } else {
            float newThrowStrength = NBTItemStackHelper.getFloat(itemStack, THROW_STRENGTH_TAG) - THROW_STRENGTH_INTERVAL;
            if(newThrowStrength > MIN_THROW_STRENGTH){
                NBTItemStackHelper.setFloat(itemStack, THROW_STRENGTH_TAG, newThrowStrength);
            } else {
                //Maybe do something with the icon?
            }
        }

        return false;
    }

    /*
     * When player drops item, remove throw values.
     */
    @Override
    public boolean onDroppedByPlayer(ItemStack itemStack, EntityPlayer entityPlayer) {
        //Remove tag (check for tag is build in the removal of NBTItemHelper
        NBTItemStackHelper.removeTag(itemStack, THROW_STRENGTH_TAG);
        return true;
    }

    /*
     * When player stops using the item remove NBT data
     * TODO try making a more cpu-friendly method?
     */
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        //check if entity is player
        if(!(entity instanceof EntityPlayer)) return;
        //check if itemstack is being held, then do nothing.
        if(((EntityPlayer) entity).getHeldItem().getItem().getUnlocalizedName().equals(itemStack.getItem().getUnlocalizedName())) return;
        //remove throw tag (Check is present NBTItemHelper)
        NBTItemStackHelper.removeTag(itemStack, THROW_STRENGTH_TAG);
    }
}
