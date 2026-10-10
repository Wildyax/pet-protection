package net.wildyax.petprotection;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.wildyax.petprotection.component.ModDataComponent;
import net.wildyax.petprotection.config.Config;
import net.wildyax.petprotection.entities.ModEntities;
import net.wildyax.petprotection.item.ModItems;
import net.wildyax.petprotection.utils.ModAttachments;
import net.wildyax.petprotection.utils.ModCreativeTabs;
import org.slf4j.Logger;


@Mod(PetProtection.MOD_ID)
public class PetProtection
{
    public static final String MOD_ID = "petprotection";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PetProtection(IEventBus modEventBus, ModContainer modContainer)
    {
        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModDataComponent.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
    }
}
