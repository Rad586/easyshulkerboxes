package fuzs.easyshulkerboxes.client;

import fuzs.easyshulkerboxes.EasyShulkerBoxes;
import fuzs.easyshulkerboxes.api.client.event.MouseDragEvents;
import fuzs.easyshulkerboxes.api.event.PlayLevelSoundEvents;
import fuzs.easyshulkerboxes.client.handler.EnderChestMenuClientHandler;
import fuzs.easyshulkerboxes.client.handler.KeyBindingHandler;
import fuzs.easyshulkerboxes.client.handler.MouseDragHandler;
import fuzs.easyshulkerboxes.client.handler.MouseScrollHandler;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class EasyShulkerBoxesFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientFactories.INSTANCE.clientModConstructor(EasyShulkerBoxes.MOD_ID).accept(new EasyShulkerBoxesClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ClientEntityEvents.ENTITY_LOAD.register(EnderChestMenuClientHandler::onEntityJoinLevel);
        ScreenEvents.BEFORE_INIT.register((client, _screen, scaledWidth, scaledHeight) -> {
            if (_screen instanceof AbstractContainerScreen<?>) {
                ScreenMouseEvents.allowMouseScroll(_screen).register((screen, mouseX, mouseY, horizontalAmount, verticalAmount) -> {
                    return MouseScrollHandler.onMouseScroll(screen, mouseX, mouseY, horizontalAmount, verticalAmount).isEmpty();
                });
                ScreenMouseEvents.allowMouseClick(_screen).register((Screen screen, double mouseX, double mouseY, int button) -> {
                    return MouseDragHandler.INSTANCE.onMousePress(screen, mouseX, mouseY, button).isEmpty();
                });
                ScreenMouseEvents.allowMouseRelease(_screen).register((Screen screen, double mouseX, double mouseY, int button) -> {
                    return MouseDragHandler.INSTANCE.onMouseRelease(screen, mouseX, mouseY, button).isEmpty();
                });
                ScreenKeyboardEvents.allowKeyPress(_screen).register((Screen screen, int key, int scancode, int modifiers) -> {
                    return KeyBindingHandler.onKeyPressed$Pre(screen, key, scancode, modifiers).isEmpty();
                });
            }
        });
        MouseDragEvents.BEFORE.register(MouseDragHandler.INSTANCE::onMouseDrag);
        PlayLevelSoundEvents.ENTITY.register(MouseDragHandler.INSTANCE::onPlaySoundAtPosition);
    }
}
