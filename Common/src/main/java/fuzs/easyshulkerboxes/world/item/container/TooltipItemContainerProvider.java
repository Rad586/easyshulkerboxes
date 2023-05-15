package fuzs.easyshulkerboxes.world.item.container;

import fuzs.easyshulkerboxes.api.world.item.container.ItemContainerProvider;
import fuzs.easyshulkerboxes.world.item.container.helper.ContainerItemHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public interface TooltipItemContainerProvider extends ItemContainerProvider {

    @Override
    default boolean canProvideTooltipImage(ItemStack containerStack, Player player) {
        return this.hasItemContainerData(containerStack);
    }

    @Override
    default Optional<TooltipComponent> getTooltipImage(ItemStack containerStack, Player player) {
        SimpleContainer container = this.getItemContainer(containerStack, player, false);
        NonNullList<ItemStack> items = ContainerItemHelper.containerToList(container);
        return Optional.of(this.createTooltipImageComponent(containerStack, items));
    }

    TooltipComponent createTooltipImageComponent(ItemStack containerStack, NonNullList<ItemStack> items);
}