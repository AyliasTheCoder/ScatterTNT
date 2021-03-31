package com.aylias.minecraft.mods.scattertnt.client;

import com.aylias.minecraft.mods.scattertnt.events.EntityRegisterEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class RenderRegistry {

    public static void registerEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegisterEvents.SCATTER_TNT, new ScatterTNTRenderer.RenderFactory());
    }
}