package com.LogicalDom.vulcan.blocks;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.LogicalDom.vulcan.Vulcan;
import com.LogicalDom.vulcan.containers.ForgedSteelAnvilContainer;
import com.LogicalDom.vulcan.util.Registries;
import com.LogicalDom.vulcan.tileentities.ForgedSteelAnvilTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public class ForgedSteelAnvil extends Block {
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(1.92857, 0, 3.07143, 13.92857, 2, 13.07143),
            Block.makeCuboidShape(3.92857, 3, 6.07143, 11.92857, 6, 10.07143),
            Block.makeCuboidShape(-0.07143, 8, 2.07143, 15.92857, 15, 14.07143),
            Block.makeCuboidShape(0.92857, 15, 3.07143, 14.92857, 16, 13.07143),
            Block.makeCuboidShape(2.92857, 6, 4.07143, 12.92857, 7, 12.07143),
            Block.makeCuboidShape(1.92857, 7, 3.07143, 13.92857, 8, 13.07143),
            Block.makeCuboidShape(2.92857, 2, 4.07143, 12.92857, 3, 12.07143),
            Block.makeCuboidShape(9.67857, 3, 5.07143, 10.67857, 6, 6.07143),
            Block.makeCuboidShape(8.17857, 3, 5.07143, 9.17857, 6, 6.07143),
            Block.makeCuboidShape(5.17857, 3, 5.07143, 6.17857, 6, 6.07143),
            Block.makeCuboidShape(6.67857, 3, 5.07143, 7.67857, 6, 6.07143),
            Block.makeCuboidShape(6.67857, 3, 9.82143, 7.67857, 6, 10.82143),
            Block.makeCuboidShape(5.17857, 3, 9.82143, 6.17857, 6, 10.82143),
            Block.makeCuboidShape(8.17857, 3, 9.82143, 9.17857, 6, 10.82143),
            Block.makeCuboidShape(9.67857, 3, 9.82143, 10.67857, 6, 10.82143)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(2.8571400000000002, 0, 2, 12.857140000000001, 2, 14),
            Block.makeCuboidShape(5.85714, 3, 4, 9.85714, 6, 12),
            Block.makeCuboidShape(1.8571400000000002, 8, 0, 13.857140000000001, 15, 16),
            Block.makeCuboidShape(2.8571400000000002, 15, 1, 12.857140000000001, 16, 15),
            Block.makeCuboidShape(3.8571400000000002, 6, 3, 11.85714, 7, 13),
            Block.makeCuboidShape(2.8571400000000002, 7, 2, 12.857140000000001, 8, 14),
            Block.makeCuboidShape(3.8571400000000002, 2, 3, 11.85714, 3, 13),
            Block.makeCuboidShape(9.85714, 3, 9.75, 10.85714, 6, 10.75),
            Block.makeCuboidShape(9.85714, 3, 8.25, 10.85714, 6, 9.25),
            Block.makeCuboidShape(9.85714, 3, 5.25, 10.85714, 6, 6.25),
            Block.makeCuboidShape(9.85714, 3, 6.75, 10.85714, 6, 7.75),
            Block.makeCuboidShape(5.10714, 3, 6.75, 6.10714, 6, 7.75),
            Block.makeCuboidShape(5.10714, 3, 5.25, 6.10714, 6, 6.25),
            Block.makeCuboidShape(5.10714, 3, 8.25, 6.10714, 6, 9.25),
            Block.makeCuboidShape(5.10714, 3, 9.75, 6.10714, 6, 10.75)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(1.9285699999999997, 0, 2.9285700000000006, 13.92857, 2, 12.92857),
            Block.makeCuboidShape(3.9285699999999997, 3, 5.928570000000001, 11.92857, 6, 9.92857),
            Block.makeCuboidShape(-0.07143000000000033, 8, 1.9285700000000006, 15.92857, 15, 13.92857),
            Block.makeCuboidShape(0.9285699999999997, 15, 2.9285700000000006, 14.92857, 16, 12.92857),
            Block.makeCuboidShape(2.9285699999999997, 6, 3.9285700000000006, 12.92857, 7, 11.92857),
            Block.makeCuboidShape(1.9285699999999997, 7, 2.9285700000000006, 13.92857, 8, 12.92857),
            Block.makeCuboidShape(2.9285699999999997, 2, 3.9285700000000006, 12.92857, 3, 11.92857),
            Block.makeCuboidShape(5.17857, 3, 9.92857, 6.17857, 6, 10.92857),
            Block.makeCuboidShape(6.67857, 3, 9.92857, 7.67857, 6, 10.92857),
            Block.makeCuboidShape(9.67857, 3, 9.92857, 10.67857, 6, 10.92857),
            Block.makeCuboidShape(8.17857, 3, 9.92857, 9.17857, 6, 10.92857),
            Block.makeCuboidShape(8.17857, 3, 5.178570000000001, 9.17857, 6, 6.178570000000001),
            Block.makeCuboidShape(9.67857, 3, 5.178570000000001, 10.67857, 6, 6.178570000000001),
            Block.makeCuboidShape(6.67857, 3, 5.178570000000001, 7.67857, 6, 6.178570000000001),
            Block.makeCuboidShape(5.17857, 3, 5.178570000000001, 6.17857, 6, 6.178570000000001)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(3, 0, 2, 13, 2, 14),
            Block.makeCuboidShape(6, 3, 4, 10, 6, 12),
            Block.makeCuboidShape(2, 8, 0, 14, 15, 16),
            Block.makeCuboidShape(3, 15, 1, 13, 16, 15),
            Block.makeCuboidShape(4, 6, 3, 12, 7, 13),
            Block.makeCuboidShape(3, 7, 2, 13, 8, 14),
            Block.makeCuboidShape(4, 2, 3, 12, 3, 13),
            Block.makeCuboidShape(5, 3, 5.25, 6, 6, 6.25),
            Block.makeCuboidShape(5, 3, 6.75, 6, 6, 7.75),
            Block.makeCuboidShape(5, 3, 9.75, 6, 6, 10.75),
            Block.makeCuboidShape(5, 3, 8.25, 6, 6, 9.25),
            Block.makeCuboidShape(9.75, 3, 8.25, 10.75, 6, 9.25),
            Block.makeCuboidShape(9.75, 3, 9.75, 10.75, 6, 10.75),
            Block.makeCuboidShape(9.75, 3, 6.75, 10.75, 6, 7.75),
            Block.makeCuboidShape(9.75, 3, 5.25, 10.75, 6, 6.25)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public ForgedSteelAnvil() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(5.0F, 6.0F)
                .sound(SoundType.ANVIL)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(FACING)) {
            case EAST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_N;
            case WEST:
                return SHAPE_E;
            case NORTH:
                return SHAPE_S;
        }
        return null;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Nullable
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /**Change the block shadow -- Lower return values = more shadow*/
    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 0.6F;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return Registries.FORGED_STEEL_ANVIL_TILE_ENTITY_TYPE.get().create();
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof ForgedSteelAnvilTileEntity) {
                INamedContainerProvider containerProvider = new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new TranslationTextComponent("screen." + Vulcan.MOD_ID + ".forged_steel_anvil.name");
                    }

                    @Override
                    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                        return new ForgedSteelAnvilContainer(i, playerInventory, (ForgedSteelAnvilTileEntity) tileEntity);
                    }
                };
                NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }
}
