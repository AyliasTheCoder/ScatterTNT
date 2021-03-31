package com.aylias.minecraft.mods.scattertnt.blocks;

import com.aylias.minecraft.mods.scattertnt.blocks.properties.TNTType;
import com.aylias.minecraft.mods.scattertnt.entities.UniversalScatterTNTEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TNTBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class UniversalScatterTNTBlock extends TNTBlock {

    Block block;
    int amount;

    public UniversalScatterTNTBlock(Block block, int amount) {
        super(Properties.create(Material.TNT).zeroHardnessAndResistance());
        this.block = block;
        this.amount = amount;
    }

    @Override
    public void catchFire(BlockState state, World world, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        explode(world, pos, igniter);
    }

    private void explode(World worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
        if (!worldIn.isRemote) {

            UniversalScatterTNTEntity tntentity = getEntity(worldIn, pos, entityIn);
            worldIn.addEntity(tntentity);
            tntentity.setState(getDefaultState());
            worldIn.playSound((PlayerEntity)null, tntentity.getPosX(), tntentity.getPosY(), tntentity.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
    }

    public UniversalScatterTNTEntity getEntity(World worldIn, BlockPos pos, @Nullable LivingEntity entityIn) {
        return new UniversalScatterTNTEntity(worldIn, pos, entityIn, block, amount, getDefaultState());
    }
}
