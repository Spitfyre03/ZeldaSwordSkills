package zeldaswordskills.entity.passive;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;
import zeldaswordskills.ref.Sounds;

public class EntityCucco extends EntityChicken {

	public EntityCucco(World worldIn) {
		super(worldIn);
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
}
