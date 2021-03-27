package com.LogicalDom.vulcan.items;

import com.LogicalDom.vulcan.Vulcan;
import com.LogicalDom.vulcan.enums.ModItemTier;
import com.LogicalDom.vulcan.util.Registries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

public class ForgeHammer extends ToolItem {

    private static final Consumer<PlayerEntity> onBreakItem = new Consumer<PlayerEntity>() {
        @Override
        public void accept(PlayerEntity playerEntity) {
            if(playerEntity.getHeldItem(playerEntity.getActiveHand()).getDamage() == Registries.FORGE_HAMMER.get().getDefaultInstance().getMaxDamage()) {
                playerEntity.setHeldItem(playerEntity.getActiveHand(), ItemStack.EMPTY);
            }
        }
    };

    private static final Set<Block> effectiveBlocks = new Set<Block>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Block> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Block block) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Block> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }
    };

    public ForgeHammer() {
        super(8.0F, 0.0F, ModItemTier.FORGE_HAMMER,
            effectiveBlocks, new Item.Properties().group(Vulcan.TAB)
            .addToolType(ToolType.PICKAXE, 2)
            .maxStackSize(1)
            );
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote()) {
            ItemStack itemInPlayerHand = playerIn.getHeldItem(handIn);
            damageThisTool(worldIn, playerIn, itemInPlayerHand);
            return ActionResult.resultSuccess(itemInPlayerHand);
        }
        return ActionResult.resultFail(ItemStack.EMPTY);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        Vector3d lookVec = player.getLookVec();
        entity.addVelocity(lookVec.getX() * 5.0, lookVec.getY() * 2, lookVec.getZ() * 5.0);
        player.getEntityWorld().addParticle(ParticleTypes.CRIT, player.getPosX(), player.getPosY(), player.getPosZ(), 1.0, 1.0, 1.0);
        return super.onLeftClickEntity(stack, player, entity);
    }

    public void damageThisTool(World world, PlayerEntity player, ItemStack stack) {
        if (stack.getItem().getDefaultInstance().equals(Registries.FORGE_HAMMER.get().getDefaultInstance())) {
            stack.damageItem(1, player, onBreakItem);
            world.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(),
                    SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 10f, 1f);
        }
    }

}
