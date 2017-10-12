package ca.zenotek.sentek;

import ca.zenotek.sentek.blocks.ModBlocks;
import ca.zenotek.sentek.crafting.ModCrafting;
import ca.zenotek.sentek.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class SentekCommonProxy {
	
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		
		String catDist = "block_distances";
		String catRecip = "recipes";
		
		Sentek.instance.toggleAir = config.getBoolean("toggle_air", Configuration.CATEGORY_GENERAL, false, "Creates an air pocket at the destination. Warning: deletes the blocks relaced with air. Do not aim at valuable targets.");
		Sentek.instance.toggleGlass = config.getBoolean("toggle_glass", Configuration.CATEGORY_GENERAL, false, "Creates a block of glass beneath the target it there would otherwise be air. Stops you from plummeting to an untimely death.");
		Sentek.instance.toggleInventoryPlatformBlock = config.getBoolean("toggle_inventory_platform_block", Configuration.CATEGORY_GENERAL, false, "Take glass from the player's inventory when placing it as a platform.");
		Sentek.instance.platformBlockType = config.getString("platform_block_type", Configuration.CATEGORY_GENERAL, "minecraft:glass", "The block to use for platforms when arriving in midair.");
		System.out.println("[SENTEK]: Found platform block type: " + Block.getBlockFromName(Sentek.instance.platformBlockType).getLocalizedName());
		Sentek.instance.requirePlatformBlock = config.getBoolean("require_platform_block", Configuration.CATEGORY_GENERAL, false, "Requires you to place a glass block (or whatever you have it set to) as part of the teleporter in order for platforms to form when you teleport into midair.");
		Sentek.instance.bedrockProtection = config.getBoolean("bedrock_protection", Configuration.CATEGORY_GENERAL, true, "Prevents air-pockets from being formed in bedrock, thus preventing teleporters from being used to destroy adminium.");
		Sentek.instance.voidProtection = config.getBoolean("void_protection", Configuration.CATEGORY_GENERAL, false, "Prevents players from teleporting below y-level 1, by shunting the destination up until it hits 1. Stops them from drowning in the void, instead lets them drown in adminium.");
		
		String blockIn = config.getString("distance_setting_blocklist", catDist, "minecraft:cobblestone,minecraft:stone,minecraft:stonebrick,minecraft:sandstone,minecraft:obsidian", "Comma-separated ordered list of block names that add distance to a teleporter setup.");
		String intsIn = config.getString("distance_setting_distlist", catDist, "1,5,20,20,100", "Comma-separated ordered list of distances to add for the blocks in distance_setting_blocklist.");
		String[] blockInSplit = blockIn.split(",");
		String[] intsInSplit = intsIn.split(",");
		int[] intsInSplitInt = new int[intsInSplit.length];
		int i = 0;
		for(String string : intsInSplit){
			try{
				intsInSplitInt[i] = Integer.parseInt(string);
			}catch(NumberFormatException e){
				intsInSplitInt[i] = 1;
			}
			i++;
		}
		for(int j = 0; j < blockInSplit.length; j++){
			Sentek.instance.distanceMap.put(blockInSplit[j], intsInSplitInt[j]);
			System.out.println("[SENTEK]: Loaded Sentek distance record: " + blockInSplit[j] + " moves you " + intsInSplitInt[j] + " blocks.");
		}
		
		
		
		//Sentek.instance.cobblestoneDistance = config.getInt("cobblestone_distance", catDist, 1, 1, 100000, "Distance value for cobblestone");
		//Sentek.instance.stoneDistance = config.getInt("stone_distance", catDist, 5, 1, 100000, "Disance value for stone");
		//Sentek.instance.stonebrickDistance = config.getInt("stonebrick_distance", catDist, 20, 1, 100000, "Distance value for stone brick");
		//Sentek.instance.sandstoneDistance = config.getInt("sandstone_distance", catDist, 20, 1, 100000, "Distance value for sandstone");
		//Sentek.instance.obsidianDistance = config.getInt("obsidian_distance", catDist, 100, 1, 100000, "Distance value for obsidian");
		
		//Sentek.instance.coreComponent = config.getString("core_component", catRecip, "minecraft:sapling", "ID name of block/item for core crafting");
		//Sentek.instance.coreComponentItem = config.getBoolean("core_component_item", catRecip, false, "Is the core component an item? (as opposed to a block)");
		//Sentek.instance.nodeComponent = config.getString("node_component", catRecip, "minecraft:stone", "ID name of block/item for node crafting");
		//Sentek.instance.nodeComponentItem = config.getBoolean("node_component_item", catRecip, false, "Is the node component an item? (as opposed to a block)");
		
		//Sentek.instance.minersBlock = config.getBoolean("miners_core_enabled", Configuration.CATEGORY_GENERAL, true, "Enable miner's core variant block recipe");
		//Sentek.instance.mcoreComponent = config.getString("miners_core_component", catRecip, "minecraft:redstone", "ID name of block/item for miner's core crafting");
		//Sentek.instance.mcoreComponentItem = config.getBoolean("miners_core_component_item", catRecip, true, "Is the miner's core component an item? (as opposed to a block)");
		
		//Sentek.instance.rcoreComponent = config.getString("redstone_core_component", catRecip, "minecraft:sapling", "ID name of upper block/item for redstone core crafting");
		//Sentek.instance.rcoreComponentItem = config.getBoolean("redstone_core_component_item", catRecip, false, "Is the redstone core upper component an item? (as opposed to a block?)");
		//Sentek.instance.rcoreComponent2 = config.getString("redstone_core_component2", catRecip, "minecraft:redstone", "ID name of lower block/item for redstone core crafting");
		//Sentek.instance.rcoreComponent2Item = config.getBoolean("redstone_core_component2_item", catRecip, true, "Is the redstone core lower component an item? (as opposed to a block?)");
		
		Sentek.instance.maxSearchDistance = config.getInt("max_search_distance", Configuration.CATEGORY_GENERAL, 10, 1, 64, "Maximum distance a core or node will follow a chain of stone blocks.");
		
		Sentek.instance.rangefindingBlock = config.getString("rangefinding_block", catDist, "minecraft:planks", "The block that increases the range for proximity-cores (like redstone cores).");
		Sentek.instance.rangefindingDistance = config.getInt("rangefinding_increment", catDist, 5, 1, 100, "The range increment for rangefinding blocks.");
		
		config.save();
		

	}
	
	public void init(FMLInitializationEvent event)
	{
		//ModCrafting.initCrafting();
	}
	
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event){
		//event.getRegistry().registerAll(ModBlocks.blockNode, ModBlocks.blockCore, ModBlocks.blockMCore, ModBlocks.blockRCore);
		System.out.println("[SENTEK]: Block Registration Phase Hit");
		event.getRegistry().register(ModBlocks.blockCore);
		event.getRegistry().register(ModBlocks.blockNode);
		event.getRegistry().register(ModBlocks.blockMCore);
		event.getRegistry().register(ModBlocks.blockRCore);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event){
		//event.getRegistry().registerAll(ModBlocks.blockNodeItem, ModBlocks.blockCoreItem, ModBlocks.blockMCoreItem, ModBlocks.blockRCoreItem);
		System.out.println("[SENTEK]: Item Registration Phase Hit");
		event.getRegistry().register(new ItemBlock(ModBlocks.blockCore).setRegistryName(ModBlocks.blockCore.getRegistryName()));
		event.getRegistry().register(new ItemBlock(ModBlocks.blockNode).setRegistryName(ModBlocks.blockNode.getRegistryName()));
		event.getRegistry().register(new ItemBlock(ModBlocks.blockMCore).setRegistryName(ModBlocks.blockMCore.getRegistryName()));
		event.getRegistry().register(new ItemBlock(ModBlocks.blockRCore).setRegistryName(ModBlocks.blockRCore.getRegistryName()));
		
	}

}
