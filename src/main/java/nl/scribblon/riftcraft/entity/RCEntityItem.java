package nl.scribblon.riftcraft.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import nl.scribblon.riftcraft.event.item.EntityItemDeathEvent;
import nl.scribblon.riftcraft.event.item.EntityItemHurtEvent;

/**
 * Created by Scribblon for RiftCraft.
 * Date Creation: 31-7-2014
 *
 * Made to check on the enderpearl.
 * For other EntityItem crafting I might utilize this class or create one for every type of crafting.
 */
public class RCEntityItem extends EntityItem {

    private DamageSource previousDamageSource;

    public RCEntityItem(World world, double x, double y, double z, ItemStack itemStack) {
        super(world, x, y, z, itemStack);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damage) {
        //Even though this is always false, lets catch this and send it along after being done with it.
        boolean returnValue = super.attackEntityFrom(damageSource, damage);

        this.previousDamageSource = damageSource;

        if (this.velocityChanged)
            MinecraftForge.EVENT_BUS.post(new EntityItemHurtEvent(this, damageSource, damage));

        if (this.isDead)
            MinecraftForge.EVENT_BUS.post(new EntityItemDeathEvent(this, damageSource));

        return returnValue;
    }

    public DamageSource getPreviousDamageSource(){
        return this.previousDamageSource;
    }

    public static RCEntityItem convert(EntityItem oldEntityItem){
        RCEntityItem newEntityItem =  new RCEntityItem(oldEntityItem.worldObj, oldEntityItem.posX, oldEntityItem.posY, oldEntityItem.posZ, oldEntityItem.getEntityItem());
        newEntityItem.motionX = oldEntityItem.motionX;
        newEntityItem.motionY = oldEntityItem.motionY;
        newEntityItem.motionZ = oldEntityItem.motionZ;
        newEntityItem.age = oldEntityItem.age;
        newEntityItem.delayBeforeCanPickup = oldEntityItem.delayBeforeCanPickup;
        newEntityItem.hoverStart = oldEntityItem.hoverStart;
        newEntityItem.lifespan = oldEntityItem.lifespan;

        return newEntityItem;
    }
}
