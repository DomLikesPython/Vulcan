package com.LogicalDom.vulcan.items;

import com.LogicalDom.vulcan.Vulcan;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public class StoneHammer extends Item {

    public StoneHammer() {
        super(new Item.Properties().group(Vulcan.TAB)
            .addToolType(ToolType.PICKAXE, 2)
            .maxStackSize(1)
            .defaultMaxDamage(150));
    }
}
