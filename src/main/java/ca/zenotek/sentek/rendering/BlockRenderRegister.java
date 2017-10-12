package ca.zenotek.sentek.rendering;

import ca.zenotek.sentek.Sentek;
import ca.zenotek.sentek.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class BlockRenderRegister {

	public static String modid = Sentek.MODID;
	
	public static void registerBlockRenderer()
	{
		reg(ModBlocks.blockNode, 0);
		reg(ModBlocks.blockCore, 0);
		reg(ModBlocks.blockCore, 1);
		reg(ModBlocks.blockMCore, 0);
		reg(ModBlocks.blockMCore, 1);
		reg(ModBlocks.blockRCore, 0);
		reg(ModBlocks.blockRCore, 1);
	}
	
	public static void reg(Block block, int meta)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(modid + ":" + block.getUnlocalizedName().substring(5), "inventory"));
	}
	
}
