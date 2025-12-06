package zeldaswordskills.entity.passive;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import zeldaswordskills.ref.Sounds;

import java.util.UUID;

public class EntityCucco extends EntityChicken {

	private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("FAA758B7-3ADE-4496-86A6-485FBDD71E46");
	private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.1D, 0)).setSaved(false);
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
		this.tasks.taskEntries.clear();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAITempt(this, 1.0D, Items.wheat_seeds, false));
		this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAICuccoHurtByTarget(this));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		// Whether or not this Cucco is angry. Used by the client for rendering the angry model
		this.dataWatcher.addObject(10, (byte)0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		IAttributeInstance moveSpeed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		// If we are currently angry, i.e. have a local target stored
		if (this.isAngry()) {
			// Run the anger cycle. If it is finished, clear targets and remove speed
			if (--this.revengeAttackTimer == 0) {
				this.setAngryAt(null, 0);
				if (moveSpeed.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
					moveSpeed.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
				}
			}
			// If the cycle is still running but we don't have a revenge target, update and let AI decide on attack target
			else if (this.getAITarget() == null) {
				EntityPlayer entityPlayer = this.worldObj.getPlayerEntityByUUID(this.attackTargetUUID);
				this.setRevengeTarget(entityPlayer);
				this.attackingPlayer = entityPlayer;
				this.recentlyHit = this.getRevengeTimer();
				this.dataWatcher.updateObject(10, (byte)0);
				if (moveSpeed.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
					moveSpeed.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
				}
			}
			// If the Cucco is actively targetting a player, check for speed and cycle swarm counter
			else if (this.getAttackTarget() instanceof EntityPlayer) {
				this.dataWatcher.updateObject(10, (byte)1);
				if (!moveSpeed.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
					moveSpeed.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
				}
				EntityPlayer target = (EntityPlayer)this.getAttackTarget();
				if (this.swarmTimer > 0 && --this.swarmTimer == 0) {
					if (this.isTargetHarvy(target)) {
						int cuccos = this.rand.nextInt(4) + 2;// Between 2 and 5 (3-inclusive + 2)
						for (int i = 0; i < cuccos; i++) {
							double posX = this.posX + ((this.rand.nextDouble() - this.rand.nextDouble()) * 6.0D) + 0.5D;
							double posY = this.posY + this.rand.nextInt(3);
							double posZ = this.posZ + ((this.rand.nextDouble() - this.rand.nextDouble()) * 6.0D) + 0.5D;
							EntityCucco cucco = new EntityCucco(this.worldObj);
							cucco.setPosition(posX, posY, posZ);
							if (this.worldObj.spawnEntityInWorld(cucco)) {
								cucco.setAngryAt(target, this.revengeAttackTimer);
							}
						}
						this.swarmTimer = this.rand.nextInt(16) + 1;
					}
					else if (this.swarmSpawnCount > 0) {
						double posX = target.posX + ((this.rand.nextDouble() - this.rand.nextDouble()) * 6.0D) + 0.5D;
						double posY = target.posY + this.rand.nextInt(3);
						double posZ = target.posZ + ((this.rand.nextDouble() - this.rand.nextDouble()) * 6.0D) + 0.5D;
						EntityCucco cucco = new EntityCucco(this.worldObj);
						cucco.setPosition(posX, posY, posZ);
						if (this.worldObj.spawnEntityInWorld(cucco)) {
							--this.swarmSpawnCount;
							cucco.setAngryAt(target, this.revengeAttackTimer);
						}
						if (this.swarmSpawnCount != 0) {
							this.swarmTimer = this.rand.nextInt(20) + 1;
						}
					}
				}
			}
		}
		this.dataWatcher.updateObject(10, (byte)(this.isAngry() && this.getAttackTarget() != null ? 1 : 0));
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

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		// Dunno. Stole it from EntityPigZombie
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		// If this Cucco isn't angry and the player isn't in invulnerable
		if (!this.isAngry() && source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)source.getEntity();
			if (!player.capabilities.isCreativeMode && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
				int timer = this.isTargetHarvy(player) ? 1200 + this.rand.nextInt(600) : 200 + this.rand.nextInt(200);
				this.setAngryAt(player, timer);
				this.swarmTimer = this.rand.nextInt(20) + 1;
				if (!this.isTargetHarvy(player)) {
					this.swarmSpawnCount = this.rand.nextInt(4) + 5;
				}
			}
		}
		return super.attackEntityFrom(source, amount);
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

	public void setAngryAt(EntityPlayer target, int timer) {
		if (target == null) {
			this.swarmTimer = 0;
			this.swarmSpawnCount = 0;
			this.revengeAttackTimer = 0;
			this.attackTargetUUID = null;
			this.setRevengeTarget(null);
			this.dataWatcher.updateObject(10, (byte)0);
		}
		else {
			this.revengeAttackTimer = timer;
			this.attackTargetUUID = target.getUniqueID();
			// possibly a random angry sound timer like EntityPigZombie. Doesn't need to persist to NBT
			this.setRevengeTarget(target);
		}
	}

	public static class EntityAICuccoHurtByTarget extends EntityAIHurtByTarget {

		private final EntityCucco cuccoEntity;

		public EntityAICuccoHurtByTarget(EntityCucco creature) {
			super(creature, true);
			this.cuccoEntity = creature;
		}

		@Override
		public boolean shouldExecute() {
			// if the mode is peaceful, do nothing
			if (this.cuccoEntity.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
				return false;
			}
			// if the target is dead, do nothing
			if (this.taskOwner.getAITarget() != null && this.taskOwner.getAITarget().isDead) {
				return false;
			}
			return super.shouldExecute();
		}

		@Override
		public boolean continueExecuting() {
			// If we aren't angry anymore, reset the task
			if (!this.cuccoEntity.isAngry()) {
				return false;
			}
			// if the mode is peaceful, cancel AI but don't unset revenge target
			else if (this.cuccoEntity.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
				return false;
			}
			else {
				EntityLivingBase target = this.taskOwner.getAITarget();
				// If the target dies, revenge and forgiveness go hand-in-hand
				if (target != null && !this.cuccoEntity.isTargetHarvy(target) && target.isDead) {
					this.setEntityAttackTarget(this.taskOwner, null);
					return false;
				}
				// If the player is in creative, disengage, but don't forget
				else if (target instanceof EntityPlayer && ((EntityPlayer)target).capabilities.isCreativeMode) {
					return false;
				}
			}
			return super.continueExecuting();
		}

		@Override
		protected void setEntityAttackTarget(EntityCreature victim, EntityLivingBase assailant) {
			super.setEntityAttackTarget(victim, assailant);
			if (victim instanceof EntityCucco && assailant instanceof EntityPlayer) {
				EntityCucco cucco = (EntityCucco)victim;
				cucco.setAngryAt((EntityPlayer)assailant, this.cuccoEntity.revengeAttackTimer);
			}
		}
	}

}
