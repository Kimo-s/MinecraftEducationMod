package net.karim.edu.block.blocks;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.karim.edu.EduChemMod;
import net.karim.edu.block.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GenericFireBlock extends FireBlock {

    public static final int COLOR = 0x000000;
    public static final int COLOR_BRIGHT = 0x000000;
    public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
    public static final BooleanProperty EAST = ConnectingBlock.EAST;
    public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
    public static final BooleanProperty WEST = ConnectingBlock.WEST;
    public static final BooleanProperty UP = ConnectingBlock.UP;
    public static final IntProperty AGE = Properties.AGE_15;
    private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

    private VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 0.1D, 16.0D);
    private final Map<BlockState, VoxelShape> shapesByState;
    private final Object2IntMap<Block> burnChances = new Object2IntOpenHashMap<Block>();
    private final Object2IntMap<Block> spreadChances = new Object2IntOpenHashMap<Block>();
    public static final Block defaultBlock = ModBlocks.GENERIC_FIRE;

    private static final Map<Direction, BooleanProperty> DIRECTION_PROPERTIES = ConnectingBlock.FACING_PROPERTIES.entrySet().stream().filter(entry -> entry.getKey() != Direction.DOWN).collect(Util.toMap());

    public GenericFireBlock() {
        super((AbstractBlock.Settings)FabricBlockSettings.of(Material.FIRE).noCollision().breakInstantly().luminance(state -> 15).sounds(BlockSoundGroup.WOOL));
        BlockState stateT = ((BlockState)this.stateManager.getDefaultState()).with(AGE, 0);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)stateT.with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(UP, false));
        this.shapesByState = ImmutableMap.copyOf(this.stateManager.getStates().stream().filter(state -> state.get(AGE) == 0).collect(Collectors.toMap(Function.identity(), GenericFireBlock::getShapeForState)));
    }


    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.empty();
        if (state.get(UP).booleanValue()) {
            voxelShape = UP_SHAPE;
        }
        if (state.get(NORTH).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, NORTH_SHAPE);
        }
        if (state.get(SOUTH).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, SOUTH_SHAPE);
        }
        if (state.get(EAST).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, EAST_SHAPE);
        }
        if (state.get(WEST).booleanValue()) {
            voxelShape = VoxelShapes.union(voxelShape, WEST_SHAPE);
        }
        return voxelShape.isEmpty() ? BASE_SHAPE : voxelShape;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        world.scheduleBlockTick(pos, this, GenericFireBlock.getFireTickDelay(world.random));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getStateForPosition(ctx.getWorld(), ctx.getBlockPos());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (this.canPlaceAt(state, world, pos)) {
            return this.getStateWithAge(world, pos, state.get(AGE));
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || this.areBlocksAroundFlammable(world, pos);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean bl2;
        EduChemMod.LOGGER.info("Ticked inside the generic flame block " + burnChances.size());
        world.scheduleBlockTick(pos, this, GenericFireBlock.getFireTickDelay(world.random));
        if (!world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
            return;
        }
        if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
        }
        BlockState blockState = world.getBlockState(pos.down());
        boolean bl = blockState.isIn(world.getDimension().infiniburn());
        int i = state.get(AGE);
        if (!bl && world.isRaining() && this.isRainingAround(world, pos) && random.nextFloat() < 0.2f + (float)i * 0.03f) {
            world.removeBlock(pos, false);
            EduChemMod.LOGGER.info("1");
            return;
        }
        int j = Math.min(15, i + random.nextInt(3) / 2);
        if (i != j) {
            state = (BlockState)state.with(AGE, j);
            world.setBlockState(pos, state, Block.NO_REDRAW);
        }
        if (!bl) {
            if (!this.areBlocksAroundFlammable(world, pos)) {
                BlockPos blockPos = pos.down();
                if (!world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP) || i > 3) {
                    world.removeBlock(pos, false);
                }
                EduChemMod.LOGGER.info("2");
                return;
            }
            if (i == 15 && random.nextInt(4) == 0 && !this.isFlammable(world.getBlockState(pos.down()))) {
                world.removeBlock(pos, false);
                EduChemMod.LOGGER.info("3");
                return;
            }
        }
        EduChemMod.LOGGER.info("Tried to spread");
        int k = (bl2 = world.hasHighHumidity(pos)) ? -50 : 0;
        this.trySpreadingFire(world, pos.east(), 300 + k, random, i);
        this.trySpreadingFire(world, pos.west(), 300 + k, random, i);
        this.trySpreadingFire(world, pos.down(), 250 + k, random, i);
        this.trySpreadingFire(world, pos.up(), 250 + k, random, i);
        this.trySpreadingFire(world, pos.north(), 300 + k, random, i);
        this.trySpreadingFire(world, pos.south(), 300 + k, random, i);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = -1; l <= 1; ++l) {
            for (int m = -1; m <= 1; ++m) {
                for (int n = -1; n <= 4; ++n) {
                    if (l == 0 && n == 0 && m == 0) continue;
                    int o = 100;
                    if (n > 1) {
                        o += (n - 1) * 100;
                    }
                    mutable.set(pos, l, n, m);
                    int p = this.getBurnChance(world, mutable);
                    if (p <= 0) continue;
                    int q = (p + 40 + world.getDifficulty().getId() * 7) / (i + 30);
                    if (bl2) {
                        q /= 2;
                    }
                    if (q <= 0 || random.nextInt(o) > q || world.isRaining() && this.isRainingAround(world, mutable)) continue;
                    int r = Math.min(15, i + random.nextInt(5) / 4);
                    world.setBlockState(mutable, this.getStateWithAge(world, mutable, r), Block.NOTIFY_ALL);
                }
            }
        }
    }

    protected boolean isRainingAround(World world, BlockPos pos) {
        return world.hasRain(pos) || world.hasRain(pos.west()) || world.hasRain(pos.east()) || world.hasRain(pos.north()) || world.hasRain(pos.south());
    }

    private static int getFireTickDelay(Random random) {
        return 30 + random.nextInt(10);
    }

    private void trySpreadingFire(World world, BlockPos pos, int spreadFactor, Random rand, int currentAge) {
        int i = this.getSpreadChance(world.getBlockState(pos));
        if (rand.nextInt(spreadFactor) < i) {
            BlockState blockState = world.getBlockState(pos);
            if (rand.nextInt(currentAge + 10) < 5 && !world.hasRain(pos)) {
                int j = Math.min(currentAge + rand.nextInt(5) / 4, 15);
                world.setBlockState(pos, (BlockState)this.getStateForPosition(world, pos).with(AGE, j), 3);
                EduChemMod.LOGGER.info("Tried to spread to " + pos);
            } else {
                world.removeBlock(pos, false);
            }

            Block block = blockState.getBlock();
            if (block instanceof TntBlock) {
                TntBlock var10000 = (TntBlock)block;
                TntBlock.primeTnt(world, pos);
            }
        }

    }

    private boolean areBlocksAroundFlammable(BlockView world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (!this.isFlammable(world.getBlockState(pos.offset(direction)))) continue;
            return true;
        }
        return false;
    }

    private BlockState getStateWithAge(WorldAccess world, BlockPos pos, int age) {
        //BlockState blockState = this.getState(world, pos);
        BlockState blockState = this.getDefaultState();
        if (blockState.isOf(ModBlocks.GENERIC_FIRE)) {
            return (BlockState)blockState.with(AGE, age);
        }
        return blockState;
    }

    public static BlockState getState(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (SoulFireBlock.isSoulBase(blockState)) {
            return Blocks.SOUL_FIRE.getDefaultState();
        }

        return ((GenericFireBlock)defaultBlock).getStateForPosition(world, pos);
    }

    private int getSpreadChance(BlockState state) {
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED).booleanValue()) {
            return 0;
        }
        return this.spreadChances.getInt(state.getBlock());
    }

    private int getBurnChance(WorldView world, BlockPos pos) {
        if (!world.isAir(pos)) {
            return 0;
        }
        int i = 0;
        for (Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(pos.offset(direction));
            i = Math.max(this.getBurnChance(blockState), i);
        }
        return i;
    }

    private int getBurnChance(BlockState state) {
        if (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED).booleanValue()) {
            return 0;
        }
        return this.burnChances.getInt(state.getBlock());
    }


    private void registerFlammableBlock(Block block, int burnChance, int spreadChance) {
        this.burnChances.put(block, burnChance);
        this.spreadChances.put(block, spreadChance);
    }


    @Override
    public BlockState getStateForPosition(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        if (!this.isFlammable(blockState) && !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
            BlockState blockState2 = this.getDefaultState();
            Direction[] var6 = Direction.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Direction direction = var6[var8];
                BooleanProperty booleanProperty = (BooleanProperty)DIRECTION_PROPERTIES.get(direction);
                if (booleanProperty != null) {
                    blockState2 = (BlockState)blockState2.with(booleanProperty, this.isFlammable(world.getBlockState(pos.offset(direction))));
                }
            }

            return blockState2;
        } else {
            return this.getDefaultState();
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.isFireImmune()) {
            entity.setFireTicks(entity.getFireTicks() + 1);
            if (entity.getFireTicks() == 0) {
                entity.setOnFireFor(8);
            }
        }
        entity.damage(DamageSource.IN_FIRE, 1.0f);
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    protected boolean isFlammable(BlockState state) {
        return this.getBurnChance(state) > 0;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public static void registerDefaultFlammablesF(GenericFireBlock fireBlock) {
        //GenericFireBlock fireBlock = (BlueFireBlock) ModBlocks.BLUE_FIRE;
        fireBlock.registerFlammableBlock(Blocks.OAK_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_PLANKS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_MOSAIC, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_MOSAIC_SLAB, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_FENCE_GATE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_FENCE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_MOSAIC_STAIRS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO_BLOCK, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_SPRUCE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_BIRCH_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_JUNGLE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_ACACIA_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_DARK_OAK_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_MANGROVE_LOG, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_BAMBOO_BLOCK, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_SPRUCE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_BIRCH_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_JUNGLE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_ACACIA_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_DARK_OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.STRIPPED_MANGROVE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_WOOD, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_ROOTS, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.OAK_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.SPRUCE_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BIRCH_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.JUNGLE_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.ACACIA_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.DARK_OAK_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.MANGROVE_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BOOKSHELF, 30, 20);
        fireBlock.registerFlammableBlock(Blocks.TNT, 15, 100);
        fireBlock.registerFlammableBlock(Blocks.GRASS, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.FERN, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.DEAD_BUSH, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.SUNFLOWER, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.LILAC, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.ROSE_BUSH, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.PEONY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.TALL_GRASS, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.LARGE_FERN, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.DANDELION, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.POPPY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.BLUE_ORCHID, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.ALLIUM, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.AZURE_BLUET, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.RED_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.ORANGE_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.WHITE_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.PINK_TULIP, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.OXEYE_DAISY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.CORNFLOWER, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.LILY_OF_THE_VALLEY, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.WITHER_ROSE, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.WHITE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.ORANGE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.MAGENTA_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_BLUE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.YELLOW_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.LIME_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.PINK_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.GRAY_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_GRAY_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.CYAN_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.PURPLE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BLUE_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BROWN_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.GREEN_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.RED_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BLACK_WOOL, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.VINE, 15, 100);
        fireBlock.registerFlammableBlock(Blocks.COAL_BLOCK, 5, 5);
        fireBlock.registerFlammableBlock(Blocks.HAY_BLOCK, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.TARGET, 15, 20);
        fireBlock.registerFlammableBlock(Blocks.WHITE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.ORANGE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.MAGENTA_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_BLUE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.YELLOW_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.LIME_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.PINK_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.GRAY_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.LIGHT_GRAY_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.CYAN_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.PURPLE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.BLUE_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.BROWN_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.GREEN_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.RED_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.BLACK_CARPET, 60, 20);
        fireBlock.registerFlammableBlock(Blocks.DRIED_KELP_BLOCK, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BAMBOO, 60, 60);
        fireBlock.registerFlammableBlock(Blocks.SCAFFOLDING, 60, 60);
        fireBlock.registerFlammableBlock(Blocks.LECTERN, 30, 20);
        fireBlock.registerFlammableBlock(Blocks.COMPOSTER, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.SWEET_BERRY_BUSH, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.BEEHIVE, 5, 20);
        fireBlock.registerFlammableBlock(Blocks.BEE_NEST, 30, 20);
        fireBlock.registerFlammableBlock(Blocks.AZALEA_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.FLOWERING_AZALEA_LEAVES, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.CAVE_VINES, 15, 60);
        fireBlock.registerFlammableBlock(Blocks.CAVE_VINES_PLANT, 15, 60);
        fireBlock.registerFlammableBlock(Blocks.SPORE_BLOSSOM, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.AZALEA, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.FLOWERING_AZALEA, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.BIG_DRIPLEAF, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.BIG_DRIPLEAF_STEM, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.SMALL_DRIPLEAF, 60, 100);
        fireBlock.registerFlammableBlock(Blocks.HANGING_ROOTS, 30, 60);
        fireBlock.registerFlammableBlock(Blocks.GLOW_LICHEN, 15, 100);
    }

}
