package spinnery.container.common;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.math.BlockPos;
import spinnery.block.TestBlock;
import spinnery.container.common.widget.WAnchor;
import spinnery.container.common.widget.WButton;
import spinnery.container.common.widget.WDropdown;
import spinnery.container.common.widget.WDynamicText;
import spinnery.container.common.widget.WHorizontalSlider;
import spinnery.container.common.widget.WList;
import spinnery.container.common.widget.WPanel;
import spinnery.container.common.widget.WSlot;
import spinnery.container.common.widget.WStaticText;
import spinnery.container.common.widget.WToggle;

public class TestContainer extends BaseContainer {
	public TestContainer(int synchronizationID, BlockPos newLinkedInventoryPosition, PlayerInventory newLinkedPlayerInventory) {
		super(synchronizationID, newLinkedPlayerInventory);

		setLinkedInventory(((TestBlock) newLinkedPlayerInventory.player.world.getBlockState(newLinkedInventoryPosition).getBlock()).getInventory());

		setLinkedPanel(new WPanel(30, 0, - 10, 170, 210, this));

		linkedPanel.center();

		WSlot.addPlayerInventory(0, 18, 18, linkedPlayerInventory, linkedPanel);

		WDropdown dropdownA = new WDropdown(WAnchor.MC_ORIGIN, - 70, 0, 0, 66, 18, 66, 170, linkedPanel);

		WStaticText staticTextA = new WStaticText(WAnchor.MC_ORIGIN, 0, 0, 20, "? ?? ???", linkedPanel);

		dropdownA.add(staticTextA);

		WList listA = new WList(WAnchor.MC_ORIGIN, 174, 0, 0, 66, 170, linkedPanel);

		for (int i = 0; i < 2; ++ i) {
			WSlot slotA = new WSlot(WAnchor.MC_ORIGIN, 0, 0, 3, 18, 18, ++ i, linkedInventory, linkedPanel);

			listA.add(slotA);
		}

		WHorizontalSlider sliderA = new WHorizontalSlider(WAnchor.MC_ORIGIN, 30, 25, 0, 18, 9, 9, linkedPanel);

		WToggle toggleA = new WToggle(WAnchor.MC_ORIGIN, 30, 50, 0, 18, 9, linkedPanel);
		WToggle toggleB = new WToggle(WAnchor.MC_ORIGIN, 30, 70, 0, 18, 9, linkedPanel);
		WToggle toggleC = new WToggle(WAnchor.MC_ORIGIN, 30, 90, 0, 18, 9, linkedPanel);

		WButton buttonA = new WButton(WAnchor.MC_ORIGIN, 55, 50, 20, 9, 9, linkedPanel);
		WButton buttonB = new WButton(WAnchor.MC_ORIGIN, 55, 70, 20, 9, 9, linkedPanel);
		WButton buttonC = new WButton(WAnchor.MC_ORIGIN, 55, 90, 20, 9, 9, linkedPanel);

		buttonA.setLabel("Yeet");
		buttonB.setLabel("Yoot");
		buttonC.setLabel("Yuut");

		WDynamicText dynamicTextA = new WDynamicText(WAnchor.MC_ORIGIN, 30, 110, 0, 100, 18, linkedPanel);

		dynamicTextA.setLabel("Type here...");

		listA.setLabel("Slots!");

		linkedPanel.setLabel("Hello, world!");

		dropdownA.setLabel("OwO");

		getLinkedPanel().add(listA, sliderA, dropdownA, toggleA, toggleB, buttonA, buttonB, buttonC, toggleC, dynamicTextA);
	}
}
