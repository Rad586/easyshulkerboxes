package fuzs.easyshulkerboxes.client.gui.screens.inventory.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import fuzs.easyshulkerboxes.world.inventory.tooltip.MapTooltip;
import fuzs.iteminteractionscore.api.client.container.v1.tooltip.ExpandableClientTooltipComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class ClientMapTooltip extends ExpandableClientTooltipComponent {
    private static final ResourceLocation MAP_BACKGROUND_CHECKERBOARD = new ResourceLocation("textures/map/map_background_checkerboard.png");

    private final int mapId;
    private final MapItemSavedData savedData;

    public ClientMapTooltip(MapTooltip tooltip) {
        this.mapId = tooltip.mapId();
        this.savedData = tooltip.savedData();
    }

    @Override
    public int getExpandedHeight() {
        return 64;
    }

    @Override
    public int getExpandedWidth(Font font) {
        return 64;
    }

    @Override
    public void renderExpandedImage(Font font, int mouseX, int mouseY, PoseStack poseStack, ItemRenderer itemRenderer) {
        // thanks cartography table screen
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, MAP_BACKGROUND_CHECKERBOARD);
        poseStack.pushPose();
        GuiComponent.blit(poseStack, mouseX, mouseY, 0, 0, 0, 64, 64, 64, 64);
        poseStack.translate(mouseX + 3, mouseY + 3, 500.0);
        poseStack.scale(0.45F, 0.45F, 1.0F);
        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().gameRenderer.getMapRenderer().render(poseStack, buffer, this.mapId, this.savedData, true, 15728880);
        buffer.endBatch();
        poseStack.popPose();
    }
}
