package com.LogicalDom.vulcan.blocks;

import com.LogicalDom.vulcan.util.Registries;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.function.Consumer;

public class BasaltBricks extends Block {
    public BasaltBricks() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.STONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()) {
            ItemStack stack = player.getHeldItem(handIn);
            if(stack.getItem().equals(Registries.FORGE_HAMMER.get())) {
                player.getEntityWorld().setBlockState(pos, Registries.BASALT_TILES.get().getDefaultState());
                stack.damageItem(1, player, new Consumer<PlayerEntity>() {
                    @Override
                    public void accept(PlayerEntity playerEntity) {
                        playerEntity.setHeldItem(handIn, ItemStack.EMPTY);
                    }
                });
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }
}
