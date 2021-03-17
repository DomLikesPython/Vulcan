package com.LogicalDom.vulcan.util;

import com.LogicalDom.vulcan.Vulcan;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(Properties properties) { super(properties); }

    public ItemBase() {
        super(new Properties().group(Vulcan.TAB));
    }
}
