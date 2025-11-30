package meldexun.entityculling.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import meldexun.entityculling.EntityCulling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntityCullingConfigGui extends GuiConfig {
    public EntityCullingConfigGui(GuiScreen parent) {
        super(
                parent,
                new ConfigElement(EntityCullingConfig.config.getCategory(EntityCullingConfig.GENERAL.getName())).getChildElements(),
                EntityCulling.MOD_ID,
                false,
                false,
                "EntityCulling"
        );
    }

    @Override
    protected void actionPerformed(GuiButton button){
        super.actionPerformed(button);

        if (button.id == 2000) {
            EntityCullingConfig.config.save();
            EntityCullingConfig.loadConfig(EntityCulling.configFile);
        }
    }
}
