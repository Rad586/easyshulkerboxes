package fuzs.easyshulkerboxes.config;

import com.google.common.collect.ImmutableMap;
import fuzs.easyshulkerboxes.client.handler.KeyBindingHandler;
import fuzs.puzzleslib.proxy.Proxy;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class TooltipContentsActivation {
    public static final TooltipContentsActivation TOGGLE_VISUAL_CONTENTS_KEY = new TooltipContentsActivation("KEY") {

        @Override
        public Component getComponent(String translationId) {
            String keyName = KeyBindingHandler.TOGGLE_VISUAL_CONTENTS_KEY_MAPPING.getTranslatedKeyMessage().getString().toUpperCase(Locale.ROOT);
            return Component.translatable(translationId, Component.translatable(TOOLTIP_PRESS_TRANSLATION_KEY), Component.literal(keyName).withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY);
        }

        @Override
        public boolean isActive() {
            return KeyBindingHandler.allowVisualContents;
        }
    };
    public static final TooltipContentsActivation TOGGLE_SELECTED_TOOLTIPS_KEY = new TooltipContentsActivation("KEY") {

        @Override
        public Component getComponent(String translationId) {
            String keyName = KeyBindingHandler.TOGGLE_SELECTED_TOOLTIPS_KEY_MAPPING.getTranslatedKeyMessage().getString().toUpperCase(Locale.ROOT);
            return Component.translatable(translationId, Component.translatable(TOOLTIP_PRESS_TRANSLATION_KEY), Component.literal(keyName).withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY);
        }

        @Override
        public boolean isActive() {
            return KeyBindingHandler.allowSelectedTooltips;
        }
    };
    public static final TooltipContentsActivation ALWAYS = new TooltipContentsActivation("ALWAYS") {

        @Override
        public boolean isActive() {
            return true;
        }
    };
    public static final TooltipContentsActivation SHIFT = new TooltipContentsActivation("SHIFT") {

        @Override
        public boolean isActive() {
            return Proxy.INSTANCE.hasShiftDown();
        }
    };
    public static final TooltipContentsActivation CONTROL = new TooltipContentsActivation("CONTROL", Minecraft.ON_OSX ? "CMD" : "CTRL") {

        @Override
        public boolean isActive() {
            return Proxy.INSTANCE.hasControlDown();
        }
    };
    public static final TooltipContentsActivation ALT = new TooltipContentsActivation("ALT") {

        @Override
        public boolean isActive() {
            return Proxy.INSTANCE.hasAltDown();
        }
    };

    public static final String REVEAL_CONTENTS_TRANSLATION_KEY = "item.container.tooltip.revealContents";
    public static final String SELECTED_ITEM_TOOLTIP_TRANSLATION_KEY = "item.container.tooltip.selectedItemTooltip";
    public static final String TOOLTIP_HOLD_TRANSLATION_KEY = "item.container.tooltip.hold";
    public static final String TOOLTIP_PRESS_TRANSLATION_KEY = "item.container.tooltip.press";
    public static final Map<String, TooltipContentsActivation> REVEAL_CONTENTS_BY_NAME = Stream.of(revealContents()).collect(ImmutableMap.toImmutableMap(Object::toString, Function.identity()));
    public static final Map<String, TooltipContentsActivation> SELECTED_ITEM_TOOLTIP_BY_NAME = Stream.of(selectedItemTooltip()).collect(ImmutableMap.toImmutableMap(Object::toString, Function.identity()));

    private final String displayString;
    private final String name;

    public TooltipContentsActivation(String name) {
        this(name, name);
    }

    private TooltipContentsActivation(String displayString, String name) {
        this.displayString = displayString;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.displayString;
    }

    public Component getComponent(String translationId) {
        return Component.translatable(translationId, Component.translatable(TOOLTIP_HOLD_TRANSLATION_KEY), Component.literal(this.name).withStyle(ChatFormatting.YELLOW)).withStyle(ChatFormatting.GRAY);
    }

    public abstract boolean isActive();

    public static TooltipContentsActivation[] revealContents() {
        return new TooltipContentsActivation[]{TOGGLE_VISUAL_CONTENTS_KEY, ALWAYS, SHIFT, CONTROL, ALT};
    }

    public static TooltipContentsActivation[] selectedItemTooltip() {
        return new TooltipContentsActivation[]{TOGGLE_SELECTED_TOOLTIPS_KEY, ALWAYS, SHIFT, CONTROL, ALT};
    }
}
