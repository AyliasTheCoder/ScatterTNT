package com.aylias.minecraft.mods.scattertnt.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BasicBlock extends Block {
    public BasicBlock(Material material, Float hardness, Float resistance, SoundType soundType, int harvestLevel, ToolType toolType) {
        super(Block.Properties.create(material)
                .hardnessAndResistance(hardness, resistance)
                .sound(soundType)
                .harvestLevel(harvestLevel)
                .harvestTool(toolType));
    }
}
