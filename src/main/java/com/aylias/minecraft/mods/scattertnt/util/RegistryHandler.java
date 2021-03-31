package com.aylias.minecraft.mods.scattertnt.util;

import com.aylias.minecraft.mods.scattertnt.ModBase;
import com.aylias.minecraft.mods.scattertnt.blocks.BlockItemBase;
import com.aylias.minecraft.mods.scattertnt.blocks.UniversalScatterTNTBlock;
import com.aylias.minecraft.mods.scattertnt.blocks.properties.TNTType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModBase.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModBase.MODID);

    static List<Block> blocks = new ArrayList<>();

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items

    // Blocks
    public static final RegistryObject<Block> SMALL_STONE_TNT = BLOCKS.register("small_stone_tnt", () -> new UniversalScatterTNTBlock(Blocks.COBBLESTONE, 8));

    // Block Items
    public static final RegistryObject<Item> SMALL_STONE_TNT_ITEM = ITEMS.register("small_stone_tnt", () -> new BlockItemBase(SMALL_STONE_TNT.get()));
}
