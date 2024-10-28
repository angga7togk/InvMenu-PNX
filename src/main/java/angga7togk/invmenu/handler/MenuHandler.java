package angga7togk.invmenu.handler;

import cn.nukkit.event.inventory.InventoryMoveItemEvent;
import cn.nukkit.item.Item;

public interface MenuHandler {

  public void handle(Item item, InventoryMoveItemEvent event);
  
}
