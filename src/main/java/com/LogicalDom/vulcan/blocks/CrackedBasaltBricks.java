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

public class CrackedBasaltBricks extends Block {
    public CrackedBasaltBricks() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.STONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }
}
