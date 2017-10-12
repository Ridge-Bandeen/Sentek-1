package ca.zenotek.sentek.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemTest extends Item{

	public ItemTest(String name)
	{
		super();
		
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.TOOLS);
	}
	
}
