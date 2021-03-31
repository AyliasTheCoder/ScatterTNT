package com.aylias.minecraft.mods.scattertnt.events;

import com.aylias.minecraft.mods.scattertnt.ModBase;
import com.aylias.minecraft.mods.scattertnt.entities.UniversalScatterTNTEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegisterEvents {

    public static EntityType<UniversalScatterTNTEntity> SCATTER_TNT = EntityType.Builder.<UniversalScatterTNTEntity>create(UniversalScatterTNTEntity::new, EntityClassification.MISC).size(0.25F, 0.25F).func_233606_a_(4).func_233608_b_(10).build(ModBase.MODID + ":scatter_tnt");

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> e) {
        e.getRegistry().registerAll(
                SCATTER_TNT.setRegistryName(ModBase.MODID, "scatter_tnt")
        );
    }

}
