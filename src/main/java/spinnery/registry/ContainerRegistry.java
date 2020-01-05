package spinnery.registry;

import net.minecraft.block.InventoryProvider;
import net.minecraft.util.math.BlockPos;
import spinnery.block.TestBlock;
import spinnery.container.common.BaseContainer;
import spinnery.container.common.TestContainer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.util.Identifier;

public class ContainerRegistry {
	public ContainerRegistry() {
		// NO-OP
	}

	public static void initialize() {
		// NO-OP
	}

	public static final Identifier TEST_CONTAINER = register(new Identifier("test"));

	public static <I extends  Identifier> I register(I ID) {
		ContainerProviderRegistry.INSTANCE.registerFactory(ID, (syncId, id, player, buffer) -> new TestContainer(syncId, buffer.readBlockPos(), player.inventory));
		return ID;
	}
}
