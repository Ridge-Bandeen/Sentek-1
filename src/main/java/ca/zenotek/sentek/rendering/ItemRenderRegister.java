package ca.zenotek.sentek.rendering;

import ca.zenotek.sentek.Sentek;
import ca.zenotek.sentek.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister {

	public static void registerItemRenderer()
	{
		//reg(ModItems.itemTest);
	}
	
	public static String modid = Sentek.MODID;
	
	public static void reg(Item item)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(modid + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}
