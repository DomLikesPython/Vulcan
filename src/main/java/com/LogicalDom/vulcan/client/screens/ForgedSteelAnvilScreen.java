package com.LogicalDom.vulcan.client.screens;

import com.LogicalDom.vulcan.Vulcan;
import com.LogicalDom.vulcan.containers.ForgedSteelAnvilContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ForgedSteelAnvilScreen extends ContainerScreen<ForgedSteelAnvilContainer> {

    private static final ResourceLocation GUI_IMAGE = new ResourceLocation(Vulcan.MOD_ID, "textures/gui/forged_steel_anvil.png");
    private static final float GUI_TITLE_X = 10.0f;
    private static final float GUI_TITLE_Y = 10.0f;
    private static final float PLAYER_INVENTORY_TITLE_X = 10.0f;
    private static final float PLAYER_INVENTORY_TITLE_Y = 325.0f;

    public ForgedSteelAnvilScreen(ForgedSteelAnvilContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.guiLeft = 0;
        this.guiTop = 0;
        this.xSize = 352;
        this.ySize = 514;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawText(matrixStack, new TranslationTextComponent("screen." + Vulcan.MOD_ID + ".name"), GUI_TITLE_X, GUI_TITLE_Y, 4210752);
        this.font.drawText(matrixStack, this.playerInventory.getDisplayName(), PLAYER_INVENTORY_TITLE_X, PLAYER_INVENTORY_TITLE_Y, 4210752);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        this.minecraft.textureManager.bindTexture(GUI_IMAGE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
    }
}
