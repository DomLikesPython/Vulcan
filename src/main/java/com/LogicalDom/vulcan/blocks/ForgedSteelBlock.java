package com.LogicalDom.vulcan.blocks;

import net.minecraft.block.*;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ForgedSteelBlock extends Block {
    public ForgedSteelBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0F, 6.0F)
                .sound(SoundType.METAL)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }
}
