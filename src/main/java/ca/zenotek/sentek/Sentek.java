package ca.zenotek.sentek;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = Sentek.MODID, name = Sentek.MODNAME, version = Sentek.VERSION)
public class Sentek
{
    public static final String MODID = "sentek";
    public static final String MODNAME = "Sentek";
    public static final String VERSION = "1.6";
    
    public static boolean toggleAir = false;
    public static boolean toggleGlass = false;
    public static boolean toggleInventoryPlatformBlock = false;
    public static String platformBlockType = "minecraft:glass";
    public static boolean requirePlatformBlock = false;
    public static boolean bedrockProtection = true;
    public static boolean voidProtection = false;
    public static HashMap distanceMap = new HashMap();
    public static HashMap rangeMap = new HashMap();
    //public static int cobblestoneDistance = 1;
    //public static int stoneDistance = 5;
    //public static int stonebrickDistance = 20;
    //public static int sandstoneDistance = 20;
    //public static int obsidianDistance = 100;
    //public static String coreComponent = "minecraft:sapling";
    //public static boolean coreComponentItem = false;
    //public static String nodeComponent = "minecraft:stone";
    //public static boolean nodeComponentItem = false;
    //public static boolean minersBlock = true;
    //public static String mcoreComponent = "minecraft:redstone";
    //public static boolean mcoreComponentItem = true;
    //public static String rcoreComponent = "minecraft:sapling";
    //public static boolean rcoreComponentItem = false;
    //public static String rcoreComponent2 = "minecraft:redstone";
    //public static boolean rcoreComponent2Item = true;
    public static int maxSearchDistance = 10;
    public static String rangefindingBlock = "minecraft:planks";
    public static int rangefindingDistance = 5;
    
    
    @Instance("sentek")
    public static Sentek instance;
    
    @SidedProxy(clientSide="ca.zenotek.sentek.SentekClientProxy", serverSide="ca.zenotek.sentek.SentekServerProxy")
    public static SentekCommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		this.proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		this.proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		this.proxy.postInit(event);
    }
}
