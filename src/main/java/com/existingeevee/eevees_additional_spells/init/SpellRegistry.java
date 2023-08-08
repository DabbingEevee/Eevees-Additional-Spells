package com.existingeevee.eevees_additional_spells.init;

import com.existingeevee.eevees_additional_spells.EeveesAdditionalSpells;
import com.existingeevee.eevees_additional_spells.spells.ArcaneDootingSpell;

import io.redspace.ironsspellbooks.api.registry.IronsSpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(IronsSpellRegistry.SPELL_REGISTRY_KEY, EeveesAdditionalSpells.MODID);

    public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static final RegistryObject<AbstractSpell> ARCANE_DOOTING_SPELL = registerSpell(new ArcaneDootingSpell());

}