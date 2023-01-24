package net.archasmiel.arcanumapparatus.block;

import net.archasmiel.arcanumapparatus.block.entity.LavaSmelteryBE;
import net.archasmiel.arcanumapparatus.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class LavaSmeltery extends BaseEntityBlock {

  public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

  public LavaSmeltery(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos,
      CollisionContext pContext) {
    return Shapes.block();
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
    pBuilder.add(HALF);
  }

  @Override
  public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
    pLevel.setBlock(pPos, pState.setValue(HALF, DoubleBlockHalf.LOWER), 3);
    pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    pLevel.removeBlockEntity(pPos.above());
  }

  @Override
  public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player,
      boolean willHarvest, FluidState fluid) {
    playerWillDestroy(level, pos, state, player);
    level.destroyBlock(state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below(), false);
    return level.setBlock(pos, fluid.createLegacyBlock(), level.isClientSide ? 11 : 3);
  }

  /* BLOCK ENTITY */

  @Nullable
  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new LavaSmelteryBE(pos, state);
  }

  @Override
  public RenderShape getRenderShape(BlockState state) {
    return RenderShape.MODEL;
  }

  @Override
  public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState,
      boolean pIsMoving) {
    BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
    if (blockEntity instanceof LavaSmelteryBE smelteryBE) {
      smelteryBE.drops();
    }
    super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
  }

  @Override
  public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
      InteractionHand pHand, BlockHitResult pHit) {
    if (pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
      pPos = pPos.below();
    }

    if (!pLevel.isClientSide) {
      BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
      if (blockEntity instanceof LavaSmelteryBE smelteryBE) {
        NetworkHooks.openGui(((ServerPlayer)pPlayer), smelteryBE, pPos);
      } else {
        throw new IllegalStateException("Invalid block entity");
      }
    } else {
      return InteractionResult.SUCCESS;
    }

    return InteractionResult.sidedSuccess(pPlayer.level.isClientSide);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState,
      BlockEntityType<T> type) {
    return createTickerHelper(type, ModBlockEntities.LAVA_SMELTERY_BE.get(),
      LavaSmelteryBE::tick);
  }

}
