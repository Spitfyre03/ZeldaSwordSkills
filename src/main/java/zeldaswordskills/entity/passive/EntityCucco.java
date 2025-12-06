package zeldaswordskills.entity.passive;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import zeldaswordskills.ref.Sounds;

import java.util.UUID;

public class EntityCucco extends EntityChicken {

	/** Represents how many Cuccos this entity has left to spawn. Decremented each time a swarm member is spawned */
	private int swarmSpawnCount;
	/** How many ticks left before another Cucco is spawned to the swarm */
	private int swarmTimer;
	/** How many ticks before this Cucco calms down */
	private int revengeAttackTimer;
	/** The UUID of the Player this Cucco is angry at */
	private UUID attackTargetUUID;

	public EntityCucco(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 0.4F);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		this.swarmSpawnCount = tagCompund.getInteger("SwarmToSpawn");
		this.swarmTimer = tagCompund.getInteger("SwarmSpawnTimer");
		this.revengeAttackTimer = tagCompund.getInteger("SwarmTimer");
		String target = tagCompund.getString("SwarmTarget");
		if (target.length() > 0) {
			this.attackTargetUUID = UUID.fromString(target);
			EntityPlayer entityplayer = this.worldObj.getPlayerEntityByUUID(this.attackTargetUUID);
			this.setRevengeTarget(entityplayer);
			if (entityplayer != null) {
				this.attackingPlayer = entityplayer;
				this.recentlyHit = this.getRevengeTimer();
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setInteger("SwarmToSpawn", this.swarmSpawnCount);
		tagCompound.setInteger("SwarmSpawnTimer", this.swarmTimer);
		tagCompound.setInteger("SwarmTimer", this.revengeAttackTimer);
		if (this.attackTargetUUID != null) {
			tagCompound.setString("SwarmTarget", this.attackTargetUUID.toString());
		}
		else {
			tagCompound.setString("SwarmTarget", "");
		}
	}

	private boolean isTargetHarvy(EntityLivingBase target) {
		return target.getName().equals("_LastGuard_");
	}

	@Override
	protected String getLivingSound() {
		return Sounds.CUCCO_LIVING;
	}

	@Override
	protected String getHurtSound() {
		return Sounds.CUCCO_HURT;
	}

	@Override
	protected String getDeathSound() {
		return Sounds.CUCCO_DIE;
	}

	@Override
	public EntityCucco createChild(EntityAgeable ageable) { return new EntityCucco(ageable.worldObj); }

	public boolean isAngry(){
		return this.revengeAttackTimer > 0 && this.attackTargetUUID != null;
	}

	public void setAngryAt(EntityPlayer target) {
		if (target == null) {
			this.swarmTimer = 0;
			this.swarmSpawnCount = 0;
			this.revengeAttackTimer = 0;
			this.attackTargetUUID = null;
			this.setRevengeTarget(null);
		}
		else {
			if (this.isTargetHarvy(target)) {
				this.revengeAttackTimer = 1200 + this.rand.nextInt(600);
			}
			else {
				this.revengeAttackTimer = 400 + this.rand.nextInt(400);
			}
			this.attackTargetUUID = target.getUniqueID();
			// possibly a random angry sound timer like EntityPigZombie. Doesn't need to persist to NBT
			this.setRevengeTarget(target);
		}
	}

	@Override
	public boolean isChickenJockey() { return false; }

	@Override
	public void setChickenJockey(boolean jockey) {}

}
