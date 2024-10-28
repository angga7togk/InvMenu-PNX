package angga7togk.invmenu.type;

import angga7togk.invmenu.blocks.DoubleMenuBlock;
import angga7togk.invmenu.blocks.MenuBlock;
import angga7togk.invmenu.blocks.SingleMenuBlock;
import cn.nukkit.block.BlockChest;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.inventory.InventoryType;
import lombok.Getter;

public enum MenuType {

  CHEST(InventoryType.CONTAINER, 27, new SingleMenuBlock(BlockChest.CHEST, BlockEntity.CHEST)),
  DOUBLE_CHEST(InventoryType.CONTAINER, 54, new DoubleMenuBlock(BlockChest.CHEST, BlockEntity.CHEST));

  @Getter
  private InventoryType inventoryType;
  @Getter
  private int size;
  @Getter
  private MenuBlock menuBlock;

  MenuType(InventoryType inventoryType, int size, MenuBlock menuBlock) {
    this.inventoryType = inventoryType;
    this.size = size;
    this.menuBlock = menuBlock;
  }
}
