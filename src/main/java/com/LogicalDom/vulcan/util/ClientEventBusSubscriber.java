package com.LogicalDom.vulcan.util;

import com.LogicalDom.vulcan.Vulcan;
import com.LogicalDom.vulcan.client.screens.ForgedSteelAnvilScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Vulcan.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventBusSubscriber {

    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(Registries.FORGED_STEEL_ANVIL_CONTAINER_TYPE.get(), ForgedSteelAnvilScreen::new);

    }
}
