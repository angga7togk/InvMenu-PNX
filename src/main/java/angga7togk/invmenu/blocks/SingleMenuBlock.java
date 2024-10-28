package angga7togk.invmenu.blocks;

import java.util.List;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.BlockEntityDataPacket;
import cn.nukkit.network.protocol.UpdateBlockPacket;

public class SingleMenuBlock extends MenuBlock {

  protected final String blockId;
  protected final String tileId;
  protected List<Vector3> lastPositions;

  public SingleMenuBlock(String blockId, String titleId) {
    this.blockId = blockId;
    this.tileId = titleId;
  }

  @Override
  public void create(Player player, String title) {
    List<Vector3> positions = this.getPositions(player);

    this.lastPositions = positions;
    positions.forEach(position -> {
      UpdateBlockPacket updateBlockPacket = new UpdateBlockPacket();
      updateBlockPacket.blockRuntimeId = Block.get(blockId).getRuntimeId();
      updateBlockPacket.flags = UpdateBlockPacket.FLAG_ALL;
      updateBlockPacket.x = position.getFloorX();
      updateBlockPacket.y = position.getFloorY();
      updateBlockPacket.z = position.getFloorZ();
      player.dataPacket(updateBlockPacket);

      BlockEntityDataPacket blockEntityDataPacket = new BlockEntityDataPacket();
      blockEntityDataPacket.x = position.getFloorX();
      blockEntityDataPacket.y = position.getFloorY();
      blockEntityDataPacket.z = position.getFloorZ();
      blockEntityDataPacket.namedTag = this.getBlockEntityDataAt(position, title);

      player.dataPacket(blockEntityDataPacket);
    });
  }

  @Override
  public void remove(Player player) {
    this.lastPositions.forEach(position -> {
      UpdateBlockPacket packet = new UpdateBlockPacket();
      packet.blockRuntimeId = Block.get(blockId).getRuntimeId();
      packet.flags = UpdateBlockPacket.FLAG_ALL;
      packet.x = position.getFloorX();
      packet.y = position.getFloorY();
      packet.z = position.getFloorZ();
      player.dataPacket(packet);
    });
  }

  protected CompoundTag getBlockEntityDataAt(Vector3 position, String title) {
    return new CompoundTag()
        .putString("id", tileId)
        .putString("CustomName", title);
  }

}
