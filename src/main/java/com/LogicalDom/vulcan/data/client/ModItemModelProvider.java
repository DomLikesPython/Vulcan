package com.LogicalDom.vulcan.data.client;

import com.LogicalDom.vulcan.Vulcan;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Vulcan.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("forged_steel_anvil", modLoc("blocks/forged_steel_anvil"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));


    }

    private ItemModelBuilder builder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
