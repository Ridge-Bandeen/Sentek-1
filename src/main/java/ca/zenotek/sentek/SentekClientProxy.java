package ca.zenotek.sentek;

import ca.zenotek.sentek.blocks.ModBlocks;
import ca.zenotek.sentek.rendering.BlockRenderRegister;
import ca.zenotek.sentek.rendering.ItemRenderRegister;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@EventBusSubscriber
public class SentekClientProxy extends SentekCommonProxy{
	
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		ModBlocks.init();
		//ModBlocks.register();
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		//ItemRenderRegister.registerItemRenderer();
		ModBlocks.registerRenders();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
	
}
