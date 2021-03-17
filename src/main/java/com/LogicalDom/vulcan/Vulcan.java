package com.LogicalDom.vulcan;

import com.LogicalDom.vulcan.util.Registries;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Vulcan.MOD_ID)
public class Vulcan {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "vulcan";
    public static final String TAB_NAME = MOD_ID.substring(0,1).toUpperCase() + MOD_ID.substring(1).toLowerCase();

    public Vulcan() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        Registries.init();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
    }

    private void doClientStuff(FMLClientSetupEvent event) {
    }

    public static final ItemGroup TAB = new ItemGroup("vulcan") {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Registries.FORGED_STEEL_INGOT.get());
        }
    };
}
