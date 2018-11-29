package com.nemosw.spigot.tap.packet.v1_12_R1;

import com.nemosw.spigot.tap.AnimationType;
import com.nemosw.spigot.tap.item.TapItemStack;
import com.nemosw.spigot.tap.item.v1_12_R1.NMSItemStack;
import com.nemosw.spigot.tap.packet.EntityPacket;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;

public class NMSEntityPacket implements EntityPacket
{

	private static final EnumItemSlot[] EQUIPMENT_SLOTS;

	static
	{
		EnumItemSlot[] slots = new EnumItemSlot[EnumItemSlot.values().length];
		slots[EquipmentSlot.CHEST.ordinal()] = EnumItemSlot.CHEST;
		slots[EquipmentSlot.FEET.ordinal()] = EnumItemSlot.FEET;
		slots[EquipmentSlot.HEAD.ordinal()] = EnumItemSlot.HEAD;
		slots[EquipmentSlot.LEGS.ordinal()] = EnumItemSlot.LEGS;
		slots[EquipmentSlot.HAND.ordinal()] = EnumItemSlot.MAINHAND;
		slots[EquipmentSlot.OFF_HAND.ordinal()] = EnumItemSlot.OFFHAND;
		EQUIPMENT_SLOTS = slots;
	}

	@Override
	public NMSPacket animation(org.bukkit.entity.Entity entity, AnimationType animation)
	{
		return new NMSPacketFixed(new PacketPlayOutAnimation(((CraftEntity) entity).getHandle(), animation.getId()));
	}

	@Override
	public NMSPacket destroy(int... entityIds)
	{
		return new NMSPacketFixed(new PacketPlayOutEntityDestroy(entityIds));
	}

	@Override
	public NMSPacket equipment(int entityId, EquipmentSlot slot, TapItemStack item)
	{
		return new NMSPacketFixed(new PacketPlayOutEntityEquipment(entityId, EQUIPMENT_SLOTS[slot.ordinal()], item == null ? ItemStack.a : ((NMSItemStack) item).getHandle()));
	}

	@Override
	public NMSPacket headRotation(org.bukkit.entity.Entity entity, float yaw)
	{
		return new NMSPacketFixed(new PacketPlayOutEntityHeadRotation(((CraftEntity) entity).getHandle(), (byte) (yaw * 255.0D / 360D))); 
	}

	@Override
	public NMSPacket metadata(org.bukkit.entity.Entity entity)
	{
		Entity nmsEntity = ((CraftEntity) entity).getHandle();

		return new NMSPacketFixed(new PacketPlayOutEntityMetadata(nmsEntity.getId(), nmsEntity.getDataWatcher(), true));
	}

	@Override
	public NMSPacket relativeMove(int entityId, double moveX, double moveY, double moveZ, boolean onGround)
	{
		return new NMSPacketFixed(new PacketPlayOutRelEntityMove(entityId, (long) (moveX * 4096), (long) (moveY * 4096), (long) (moveZ * 4096), onGround));
	}

	@Override
	public NMSPacket spawnMob(LivingEntity entity)
	{
		return new NMSPacketFixed(new PacketPlayOutSpawnEntityLiving(((CraftLivingEntity) entity).getHandle()));
	}

	@Override
	public NMSPacket teleport(org.bukkit.entity.Entity entity, double x, double y, double z, float yaw, float pitch, boolean onGround)
	{
		Entity nmsEntity = ((CraftEntity) entity).getHandle();
		nmsEntity.locX = x;
		nmsEntity.locY = y;
		nmsEntity.locZ = z;
		nmsEntity.yaw = yaw;
		nmsEntity.pitch = pitch;
		nmsEntity.onGround = onGround;

		return new NMSPacketFixed(new PacketPlayOutEntityTeleport(nmsEntity));
	}

}