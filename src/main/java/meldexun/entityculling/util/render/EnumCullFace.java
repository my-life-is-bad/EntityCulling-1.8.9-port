package meldexun.entityculling.util.render;


public enum EnumCullFace {  //copied from net.minecraft.client.renderer.GlStateManager$CullFace from 1.12.2
    FRONT(1028),
    BACK(1029),
    FRONT_AND_BACK(1032);

    public final int mode;

    private EnumCullFace(int modeIn) {
        this.mode = modeIn;
    }
}
