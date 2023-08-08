package com.existingeevee.eevees_additional_spells.spells;

import java.util.List;
import java.util.Optional;

import com.existingeevee.eevees_additional_spells.EeveesAdditionalSpells;
import com.existingeevee.eevees_additional_spells.init.SoundEventRegistry;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.api.spells.SpellAnimations;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@AutoSpellConfig
public class ArcaneDootingSpell extends AbstractSpell {
	private final ResourceLocation spellId = new ResourceLocation(EeveesAdditionalSpells.MODID, "arcane_dooting");

	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(Component.translatable("ui." + EeveesAdditionalSpells.MODID + ".healing", Utils.stringTruncation(getSpellPower(spellLevel, caster), 3)));
	}

	public ArcaneDootingSpell() {
		this.manaCostPerLevel = 5;
		this.baseSpellPower = 1;
		this.spellPowerPerLevel = 1;
		this.castTime = 55;
		this.baseManaCost = 5;
	}

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.RARE)
			.setSchool(SchoolType.EVOCATION)
			.setMaxLevel(10)
			.setCooldownSeconds(2)
			.build();

	@Override
	public ResourceLocation getSpellResource() {
		return spellId;
	}

	@Override
	public DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	@Override
	public CastType getCastType() {
		return CastType.CONTINUOUS;
	}

	@Override
	public Optional<SoundEvent> getCastStartSound() {
		return Optional.empty();
	}

	@Override
	public Optional<SoundEvent> getCastFinishSound() {
		return Optional.of(SoundEventRegistry.TRUMPET_DOOT.get());
	}

	@Override
	public void onCast(Level world, int spellLevel, LivingEntity user, MagicData playerMagicData) {
		if (!world.isClientSide) {
			List<LivingEntity> spooked = world.getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(10.0));

			for (LivingEntity entity : spooked) {
				if (entity == user)
					continue;

				double deltaX = entity.position().x - user.position().x + world.random.nextDouble() - world.random.nextDouble();
				double deltaZ = entity.position().z - user.position().z + world.random.nextDouble() - world.random.nextDouble();

				double distance = Math.sqrt((deltaX * deltaX) + (deltaZ * deltaZ));

				double knockback = 1 + 0.5 * getSpellPower(spellLevel, user);				

				Vec3 delta = entity.getDeltaMovement(); 
				entity.setDeltaMovement(delta.add(0.5 * deltaX * knockback / distance, 5 / (10 + distance), 0.5 + deltaZ * knockback / distance));

				entity.hurtMarked = true;
				entity.setLastHurtByMob(user);
			}
		}
		super.onCast(world, spellLevel, user, playerMagicData);
	}

	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.SELF_CAST_ANIMATION;
	}
}
