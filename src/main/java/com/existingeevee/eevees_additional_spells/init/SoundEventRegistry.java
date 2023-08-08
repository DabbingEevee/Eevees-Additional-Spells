package com.existingeevee.eevees_additional_spells.init;


import com.existingeevee.eevees_additional_spells.EeveesAdditionalSpells;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundEventRegistry {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, EeveesAdditionalSpells.MODID);

    public static final RegistryObject<SoundEvent> TRUMPET_DOOT = SOUND_EVENTS.register(
            "misc.trumpet.doot",
            () -> new SoundEvent(new ResourceLocation(EeveesAdditionalSpells.MODID, "misc.trumpet.doot"))
    );
	
}
