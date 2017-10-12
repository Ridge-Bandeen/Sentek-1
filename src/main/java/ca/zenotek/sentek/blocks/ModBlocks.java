package ca.zenotek.sentek.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModBlocks {

	@GameRegistry.ObjectHolder("sentek:blocknode")
	public static Block blockNode = new BlockNode("blocknode");
	//public static ItemBlock blockNodeItem;
	@GameRegistry.ObjectHolder("sentek:blockcore")
	public static Block blockCore = new BlockCore("blockcore");
	//public static ItemBlock blockCoreItem;
	@GameRegistry.ObjectHolder("sentek:blockmcore")
	public static Block blockMCore = new BlockCore("blockmcore");
	//public static ItemBlock blockMCoreItem;
	@GameRegistry.ObjectHolder("sentek:blockrcore")
	public static Block blockRCore = new BlockCore("blockrcore", true, true);
	//public static ItemBlock blockRCoreItem;
	
	public static void init(){
		 
		//blockNodeItem = new ItemBlock(blockNode);

		//blockCoreItem = new ItemBlock(blockCore);

		//blockMCoreItem = new ItemBlock(blockMCore);

		//blockRCoreItem = new ItemBlock(blockRCore);
	}
	
	
	/*
	public static void register(){
		 GameRegistry.register(blockNode);
		 GameRegistry.register(blockNodeItem, blockNode.getRegistryName());
		 GameRegistry.register(blockCore);
		 GameRegistry.register(blockCoreItem, blockCore.getRegistryName());
		 GameRegistry.register(blockMCore);
		 GameRegistry.register(blockMCoreItem, blockMCore.getRegistryName());
		 GameRegistry.register(blockRCore);
		 GameRegistry.register(blockRCoreItem, blockRCore.getRegistryName());
	}
	*/
	
	public static void registerRenders(){
		registerRender(blockNode, 0);
		registerRender(blockCore, 0);
		registerRender(blockCore, 1);
		registerRender(blockMCore, 0);
		registerRender(blockMCore, 1);
		registerRender(blockRCore, 0);
		registerRender(blockRCore, 1);
	}
	
	public static void registerRender(Block block, int meta){
		Item item = Item.getItemFromBlock(block);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
}
