package net.wildyax.petprotect;

import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.wildyax.petprotect.config.Config;
import net.wildyax.petprotect.item.ModItems;
import net.wildyax.petprotect.utils.ModAttachments;
import net.wildyax.petprotect.utils.ModCreativeTabs;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod(PetProtect.MOD_ID)
public class PetProtect
{
    public static final String MOD_ID = "petprotect";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PetProtect(IEventBus modEventBus, ModContainer modContainer)
    {
        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModAttachments.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
