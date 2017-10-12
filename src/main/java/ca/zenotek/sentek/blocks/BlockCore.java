package ca.zenotek.sentek.blocks;

import java.util.HashMap;

import ca.zenotek.sentek.Sentek;
import ca.zenotek.sentek.SentekVector;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCore extends BlockNode{

	private boolean redstone = false;
	private boolean proximity = false;
	public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
	
	public BlockCore(String unlocalizedName) {
		this(unlocalizedName, false, false);
	}
	
	public BlockCore(String unlocalizedName, boolean redstone, boolean proximity){
		super(unlocalizedName);
		this.redstone = redstone;
		this.proximity = proximity;
		this.setDefaultState(this.blockState.getBaseState().withProperty(TRIGGERED, false));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		if(state == this.blockState.getBaseState().withProperty(TRIGGERED, false)){
			return 0;
		}else{
			return 1;
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		if(meta == 0){
			return this.blockState.getBaseState().withProperty(TRIGGERED, false);
		}else{
			return this.blockState.getBaseState().withProperty(TRIGGERED, true);
		}
	}
	
	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, TRIGGERED);
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		
		
		this.teleportPlayer(worldIn, pos, state, playerIn);	
			
		return true;
	}
	
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		if(this.redstone){
			boolean flag = worldIn.isBlockPowered(pos) || worldIn.isBlockPowered(pos.up());
	        boolean flag1 = ((Boolean)state.getValue(TRIGGERED)).booleanValue();

	        if (flag && !flag1)
	        {
	        	teleportPlayer(worldIn, pos, state, null);
	            worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(true)), 4);
	        }
	        else if (!flag && flag1)
	        {
	            worldIn.setBlockState(pos, state.withProperty(TRIGGERED, Boolean.valueOf(false)), 4);
	        }
		}
		
    }
	
	private void teleportPlayer(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn){
		HashMap map = new HashMap();
		SentekVector vector = this.getVector(worldIn, pos, 0, map);
		EntityPlayer entity = playerIn;
		
		if(this.proximity){
			EntityPlayer entityTemp = worldIn.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 5 + vector.getDistance(), true);
			if(entityTemp != null){
				entity = entityTemp;
				System.out.println("[SENTEK]: Nearest player found in range!");
			}else{
				System.out.println("[SENTEK]: No near player found, defaulting to presser!");
			}
			
		}
		
		if(entity != null){
			
			//System.out.println("Got vector: " + vector.getX() + "," + vector.getY() + "," + vector.getZ());
			worldIn.playSound((EntityPlayer)null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F);
		
			if(pos.getY() + vector.getY() + 1 <= 0 && Sentek.voidProtection){
				int offset = 1 - (pos.getY() + vector.getY() + 1);
				vector.addVector(new SentekVector(0, offset, 0));
			}
			if(!worldIn.isRemote){
				entity.setPositionAndUpdate(pos.getX() + vector.getX() + 0.5, pos.getY() + vector.getY() + 1, pos.getZ() + vector.getZ() + 0.5);
			}
			if(Sentek.instance.toggleAir){
				if(worldIn.getBlockState(new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY() + 1, pos.getZ() + vector.getZ())).getBlock() != Blocks.BEDROCK || !Sentek.bedrockProtection)
					worldIn.setBlockToAir(new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY() + 1, pos.getZ() + vector.getZ()));
				if(worldIn.getBlockState(new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY() + 2, pos.getZ() + vector.getZ())).getBlock() != Blocks.BEDROCK || !Sentek.bedrockProtection)
					worldIn.setBlockToAir(new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY() + 2, pos.getZ() + vector.getZ()));
			}
			if(Sentek.instance.toggleGlass && worldIn.getBlockState(new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY(), pos.getZ() + vector.getZ())).getBlock() == Blocks.AIR){
				boolean inventoryBlock = true;
				boolean platformBlock = true;
				
				
				if(Sentek.instance.requirePlatformBlock){
					platformBlock = vector.getPlatform();
				}
				
				if(Sentek.instance.toggleInventoryPlatformBlock){
					inventoryBlock = false;
					int size = entity.inventory.getSizeInventory();
					for(int i = 0; i < size; i++){
						ItemStack stack = entity.inventory.getStackInSlot(i);
						if(inventoryBlock == false && stack.getItem() == Item.getItemFromBlock(Block.getBlockFromName(Sentek.instance.platformBlockType))){
							inventoryBlock = true;
							stack.shrink(1);
						}
					}
				}
				
				if(inventoryBlock && platformBlock){
					worldIn.setBlockState(new BlockPos(pos.getX() + vector.getX(), pos.getY() + vector.getY(), pos.getZ() + vector.getZ()), Blocks.GLASS.getDefaultState());
				}
				
			}
		}else{
			System.out.println("[SENTEK]: No player found for teleport.");
		}
	}

}
