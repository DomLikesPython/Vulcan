package com.LogicalDom.vulcan.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class HornblendeBricks extends Block {
    public HornblendeBricks() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5F, 6.0F)
                .sound(SoundType.STONE)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }
}
