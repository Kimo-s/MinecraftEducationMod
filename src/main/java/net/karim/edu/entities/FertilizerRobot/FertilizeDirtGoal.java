package net.karim.edu.entities.FertilizerRobot;

import net.karim.edu.EduChemMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class FertilizeDirtGoal extends Goal {

    protected final PathAwareEntity mob;
    protected BlockPos targetPos = BlockPos.ORIGIN;
    private boolean reached;
    protected int tryingTime;
    public final double speed;
    private final int range;
    protected int lowestY;
    private final int maxYDifference;
    private final Block trackBlock;
    private int cooldown;

    public FertilizeDirtGoal(PathAwareEntity mob, int range, double speed, Block block) {
        this.mob = mob;
        this.speed = speed;
        this.range = range;
        this.lowestY = 0;
        this.maxYDifference = 4;
        this.trackBlock = block;
    }

    @Override
    public void start() {
        this.startMovingToTarget();
    }

    @Override
    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = 30;
//        EduChemMod.LOGGER.info("Cooldown finished for finding dirt goal for " + this.mob);
        return this.findTargetPos();
    }

    protected void startMovingToTarget() {
        this.mob.getNavigation().startMovingTo((double)this.targetPos.getX() + 0.5, this.targetPos.getY() + 1, (double)this.targetPos.getZ() + 0.5, this.speed);
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    protected BlockPos getTargetPos() {
        return this.targetPos.up();
    }

    public boolean shouldResetPath() {
        return this.tryingTime % 300 == 0;
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.getTargetPos();
        if (!blockPos.isWithinDistance(this.mob.getPos(), 1.0)) {
            this.reached = false;
            ++this.tryingTime;
            if (this.shouldResetPath()) {
                this.mob.getNavigation().startMovingTo((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, this.speed);
            }
        } else {
            this.reached = true;
            for(int i = 0; i < 4; i++){
                useOnFertilizable(this.mob.world, getTargetPos());
            }
            --this.tryingTime;
        }
    }

    protected boolean findTargetPos() {
        int i = this.range;
        int j = this.maxYDifference;
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int k = this.lowestY;
        DefaultedList<BlockPos> farmlandBlockPositions =  DefaultedList.of();

        while (k <= j) {
            for (int l = 0; l < i; ++l) {
                int m = 0;
                while (m <= l) {
                    int n;
                    int n2 = n = m < l && m > -l ? l : 0;
                    while (n <= l) {
                        mutable.set(blockPos, m, k - 1, n);
                        if (this.mob.isInWalkTargetRange(mutable) && this.mob.world.getBlockState(mutable).getBlock() instanceof FarmlandBlock && !this.mob.world.isAir(mutable.up())) {
                            farmlandBlockPositions.add(mutable.toImmutable());
                        }
                        n = n > 0 ? -n : 1 - n;
                    }
                    m = m > 0 ? -m : 1 - m;
                }
            }
            k = k > 0 ? -k : 1 - k;
        }

        if(farmlandBlockPositions.size() == 0){
            return false;
        } else {
            int ind = Random.create().nextBetweenExclusive(0, farmlandBlockPositions.size());
            BlockPos pos = farmlandBlockPositions.get(ind);
            this.targetPos = pos;
            return true;
        }
    }

    public static boolean useOnFertilizable(World world, BlockPos pos) {
        Fertilizable fertilizable;
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable && (fertilizable = (Fertilizable)((Object)blockState.getBlock())).isFertilizable(world, pos, blockState, world.isClient)) {
            if (world instanceof ServerWorld) {
                if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                    fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                }
            }
            return true;
        }
        return false;
    }
}
