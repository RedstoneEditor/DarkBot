package org.darkstorm.darkbot.minecraftbot.world.entity;

import org.darkstorm.darkbot.minecraftbot.MinecraftBot;
import org.darkstorm.darkbot.minecraftbot.handlers.ConnectionHandler;
import org.darkstorm.darkbot.minecraftbot.protocol.writeable.Packet7UseEntity;
import org.darkstorm.darkbot.minecraftbot.util.IntHashMap;
import org.darkstorm.darkbot.minecraftbot.world.World;

public class PigEntity extends PassiveEntity {
	protected boolean saddled;

	public PigEntity(World world, int id) {
		super(world, id);
	}

	public boolean isSaddled() {
		return saddled;
	}

	public void setSaddled(boolean saddled) {
		this.saddled = saddled;
	}

	public void ride() {
		if(!saddled)
			return;
		MinecraftBot bot = world.getBot();
		ConnectionHandler connectionHandler = bot.getConnectionHandler();
		Packet7UseEntity useEntityPacket = new Packet7UseEntity(world.getBot()
				.getPlayer().id, id, 0);
		connectionHandler.sendPacket(useEntityPacket);
	}

	@Override
	public void updateMetadata(IntHashMap<WatchableObject> metadata) {
		super.updateMetadata(metadata);
		if(metadata.containsKey(16))
			setSaddled(((Byte) metadata.get(16).getObject()).byteValue() == 1);
	}
}
