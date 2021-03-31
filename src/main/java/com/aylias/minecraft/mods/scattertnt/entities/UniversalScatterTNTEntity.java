package com.aylias.minecraft.mods.scattertnt.entities;

import com.aylias.minecraft.mods.scattertnt.events.EntityRegisterEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class UniversalScatterTNTEntity extends Entity {

    Random velRandom;
    BlockState block;
    int amount;
    BlockState state = Blocks.STONE.getDefaultState();
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(UniversalScatterTNTEntity.class, DataSerializers.VARINT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 80;

    public UniversalScatterTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(EntityRegisterEvents.SCATTER_TNT, worldIn);
        velRandom = new Random();
        amount = 8;
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.tntPlacedBy = igniter;
    }

    protected void registerData() {
        this.dataManager.register(FUSE, 80);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
                this.explode();
            }
        } else {
            this.func_233566_aG_();
            if (this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    /**
     * returns null or the entityliving it was placed or ignited by
     */
    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }

    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public UniversalScatterTNTEntity(World worldIn, BlockPos pos, @Nullable LivingEntity igniter, Block block, int amount, BlockState state) {
        this(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, igniter);
        this.block = block.getDefaultState();
        this.amount = amount;
        this.state = state;
        velRandom = new Random();

    }

    public UniversalScatterTNTEntity(EntityType<? extends UniversalScatterTNTEntity> type, World worldIn) {
        super(type, worldIn);
        velRandom = new Random();
        amount = 8;
        this.preventEntitySpawning = true;
    }

    void explode() {

        FallingBlockEntity fallingBlock;

        for (int i = 0; i < amount; i++) {
            fallingBlock = new FallingBlockEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), block);
            fallingBlock.setVelocity((velRandom.nextDouble() * 3) - 1.5, (velRandom.nextDouble() * 1.5) + 1.5, (velRandom.nextDouble() * 3) - 1.5);
            fallingBlock.fallTime = 1;
            world.addEntity(fallingBlock);
        }

        System.out.println(state.getBlock().getRegistryName().toString());
    }

    public BlockState getState() {
        return state;
    }

    public void setState(BlockState state) {
        this.state = state;
    }

    // public abstract String getStringLocation();
}
