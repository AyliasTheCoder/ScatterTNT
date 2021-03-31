package com.aylias.minecraft.mods.scattertnt.blocks;

import com.aylias.minecraft.mods.scattertnt.ModBase;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class BlockItemBase extends BlockItem {


    public BlockItemBase(Block block) {
        super(block, new Properties().group(ModBase.TAB));
    }
}
