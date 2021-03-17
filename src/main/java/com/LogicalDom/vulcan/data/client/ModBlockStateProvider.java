package com.LogicalDom.vulcan.data.client;

import com.LogicalDom.vulcan.Vulcan;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, Vulcan.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
