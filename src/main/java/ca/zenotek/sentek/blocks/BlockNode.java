package ca.zenotek.sentek.blocks;

import java.util.HashMap;

import ca.zenotek.sentek.Sentek;
import ca.zenotek.sentek.SentekCatchVector;
import ca.zenotek.sentek.SentekVector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNode extends Block{

	public BlockNode(String unlocalizedName, Material material, float hardness, float resistance)
	{
        super(material);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }
	
	public BlockNode(String unlocalizedName, float hardness, float resistance)
	{
		this(unlocalizedName, Material.ROCK, hardness, resistance);
	}
	
	public BlockNode(String unlocalizedName)
	{
		this(unlocalizedName, 2.0f, 10.0f);
	}
	
	public SentekVector getVector(World world, BlockPos pos, int dir, HashMap map){
		map.put(pos.getX() + ":" + pos.getY() + ":" + pos.getZ(), "marked");
		SentekVector vector = new SentekVector();
		int i = 1;
		boolean xPosCatch = dir != 2;
		boolean xNegCatch = dir != 1;
		boolean yPosCatch = dir != 4;
		boolean yNegCatch = dir != 3;
		boolean zPosCatch = dir != 6;
		boolean zNegCatch = dir != 5;
		while(i < (Sentek.instance.maxSearchDistance + 1)){
			if(xPosCatch){
				SentekCatchVector cVector = checkBranch(world, pos, i, 0, 0, 1, map);
				vector.addVector(cVector.getVector());
				xPosCatch = cVector.getCatch();
			}
			if(xNegCatch){
				SentekCatchVector cVector = checkBranch(world, pos, -i, 0, 0, 2, map);
				vector.addVector(cVector.getVector());
				xNegCatch = cVector.getCatch();
			}
			if(yPosCatch){
				SentekCatchVector cVector = checkBranch(world, pos, 0, i, 0, 3, map);
				vector.addVector(cVector.getVector());
				yPosCatch = cVector.getCatch();
			}
			if(yNegCatch){
				SentekCatchVector cVector = checkBranch(world, pos, 0, -i, 0, 4, map);
				vector.addVector(cVector.getVector());
				yNegCatch = cVector.getCatch();
			}
			if(zPosCatch){
				SentekCatchVector cVector = checkBranch(world, pos, 0, 0, i, 5, map);
				vector.addVector(cVector.getVector());
				zPosCatch = cVector.getCatch();
			}
			if(zNegCatch){
				SentekCatchVector cVector = checkBranch(world, pos, 0, 0, -i, 6, map);
				vector.addVector(cVector.getVector());
				zNegCatch = cVector.getCatch();
			}
			i++;
		}
		
		return vector;
	}
	
	private SentekCatchVector checkBranch(World world, BlockPos pos, int x, int y, int z, int dir, HashMap map){
		SentekVector vector = new SentekVector();
		boolean catchVal = true;
		
		//int cDist = Sentek.instance.cobblestoneDistance;
		//int sDist = Sentek.instance.stoneDistance;
		//int sbDist = Sentek.instance.stonebrickDistance;
		//int ssDist = Sentek.instance.sandstoneDistance;
		//int oDist = Sentek.instance.obsidianDistance;
		
		BlockPos posI = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
		IBlockState state = world.getBlockState(posI);
		Block block = state.getBlock();
		//System.out.println("Found a " + block.getRegistryName() + " at " + posI.getX() + "," + posI.getY() + "," + posI.getZ());
		String key = block.getRegistryName().toString();
		if(block == ModBlocks.blockCore || block == ModBlocks.blockMCore || block == ModBlocks.blockNode){
			if(!(map.containsKey(posI.getX() + ":" + posI.getY() + ":" + posI.getZ()))){
				BlockNode node = (BlockNode)block;
				vector.addVector(node.getVector(world, posI, dir, map));
			}
			if(block == Block.getBlockFromName(Sentek.instance.platformBlockType)){
				vector.addVector(new SentekVector(true));
			}
			if(block == Block.getBlockFromName(Sentek.instance.rangefindingBlock)){
				vector.addVector(new SentekVector(Sentek.instance.rangefindingDistance));
			}
			catchVal = false;
			//System.out.println("[SENTEK]: Found a stop-block: " + key);
		}else if(Sentek.instance.distanceMap.containsKey(key)){
			int dist = (Integer) Sentek.instance.distanceMap.get(key);
			vector.addVector(new SentekVector((x != 0 ? (x < 0 ? -dist : dist) : 0), (y != 0 ? (y < 0 ? -dist : dist) : 0), (z != 0 ? (z < 0 ? -dist : dist) : 0)));	
			if(key == Sentek.instance.platformBlockType){
				vector.addVector(new SentekVector(true));
			}
			if(key == Sentek.instance.rangefindingBlock){
				vector.addVector(new SentekVector(Sentek.instance.rangefindingDistance));
			}
			//System.out.println("[SENTEK]: Found a block: " + key);
		}else if(block == Block.getBlockFromName(Sentek.instance.platformBlockType)){
			vector.addVector(new SentekVector(true));
			if(!Sentek.instance.requirePlatformBlock){
				catchVal = false;
			}
			//System.out.println("[SENTEK]: Found a platform block");
		}else if(block == Block.getBlockFromName(Sentek.instance.rangefindingBlock)){
			vector.addVector(new SentekVector(Sentek.instance.rangefindingDistance));
		}else{
			catchVal = false;
			//System.out.println("[SENTEK]: Found a stop-block: " + key);
		}
		
		//if(block == Blocks.COBBLESTONE){
		//	vector.addVector(new SentekVector((x != 0 ? (x < 0 ? -cDist : cDist) : 0), (y != 0 ? (y < 0 ? -cDist : cDist) : 0), (z != 0 ? (z < 0 ? -cDist : cDist) : 0)));
		//}else if(block == Blocks.STONE){
		//	vector.addVector(new SentekVector((x != 0 ? (x < 0 ? -sDist : sDist) : 0), (y != 0 ? (y < 0 ? -sDist : sDist) : 0), (z != 0 ? (z < 0 ? -sDist : sDist) : 0)));
		//}else if(block == Blocks.STONEBRICK){
		//	vector.addVector(new SentekVector((x != 0 ? (x < 0 ? -sbDist : sbDist) : 0), (y != 0 ? (y < 0 ? -sbDist : sbDist) : 0), (z != 0 ? (z < 0 ? -sbDist : sbDist) : 0)));
		//}else if(block == Blocks.SANDSTONE){
		//	vector.addVector(new SentekVector((x != 0 ? (x < 0 ? -ssDist : ssDist) : 0), (y != 0 ? (y < 0 ? -ssDist : ssDist) : 0), (z != 0 ? (z < 0 ? -ssDist : ssDist) : 0)));
		//}else if(block == Blocks.OBSIDIAN){
		//	vector.addVector(new SentekVector((x != 0 ? (x < 0 ? -oDist : oDist) : 0), (y != 0 ? (y < 0 ? -oDist : oDist) : 0), (z != 0 ? (z < 0 ? -oDist : oDist) : 0)));
		//}else if(block == ModBlocks.blockCore || block == ModBlocks.blockNode || block == ModBlocks.blockMCore){
		//	if(!(map.containsKey(posI.getX() + ":" + posI.getY() + ":" + posI.getZ()))){
		//		BlockNode node = (BlockNode)block;
		//		vector.addVector(node.getVector(world, posI, dir, map));
		//	}
		//	catchVal = false;
		//}else{
		//	catchVal = false;
		//}
		
		return new SentekCatchVector(vector, catchVal);
	}
	
	
}
