package angga7togk.invmenu;

import java.util.HashMap;
import java.util.Map;

import angga7togk.invmenu.blocks.MenuBlock;
import angga7togk.invmenu.handler.MenuHandler;
import angga7togk.invmenu.type.MenuType;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.inventory.InventoryMoveItemEvent;
import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ContainerClosePacket;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import lombok.Setter;

public class InvMenu extends ContainerInventory {

  private final Map<Integer, MenuHandler> handlers = new HashMap<>();

  private MenuBlock menuBlock;
  @Setter
  private String title;
  @Setter
  private MenuHandler defaultItemHandler;

  public InvMenu(MenuType menuType) {
    super(null, menuType.getInventoryType(), menuType.getSize());
  }

  @Override
  public void onOpen(Player player) {
    this.menuBlock.create(player, this.getTitle());

    Server.getInstance().getScheduler().scheduleDelayedTask(() -> {
      ContainerOpenPacket packet = new ContainerOpenPacket();
      packet.windowId = player.getWindowId(this);
      packet.type = this.getType().getNetworkType();

      Vector3 position = this.menuBlock.getPositions(player).get(0);
      packet.x = position.getFloorX();
      packet.y = position.getFloorY();
      packet.z = position.getFloorZ();
      player.dataPacket(packet);

      super.onOpen(player);

      this.sendContents(player);
    }, 5);
  }

  @Override
  public void onClose(Player player) {
    ContainerClosePacket packet = new ContainerClosePacket();
    packet.windowId = player.getWindowId(this);
    packet.wasServerInitiated = player.getClosingWindowId() != packet.windowId;
    player.dataPacket(packet);

    super.onClose(player);

    Server.getInstance().getScheduler().scheduleDelayedTask(() -> {
      this.menuBlock.remove(player);
    }, 5);
  }

  public String getTitle() {
    return this.title;
  }

  public void handle(int index, Item item, InventoryMoveItemEvent event) {
    MenuHandler handler = this.handlers.getOrDefault(index, this.defaultItemHandler);
    handler.handle(item, event);
  }
}
