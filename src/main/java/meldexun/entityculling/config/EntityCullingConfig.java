package meldexun.entityculling.config;

import java.util.Arrays;
import java.io.File;


import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.config.ConfigCategory;

import meldexun.entityculling.util.raytracing.RaytraceDistanceCalculator;
import meldexun.renderlib.util.ResourceLocationMap;
import meldexun.renderlib.config.RenderLibConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.tileentity.TileEntity;

public class EntityCullingConfig {

    public static Configuration config;
	public static final ConfigCategory GENERAL = new ConfigCategory("general");
	public static final ConfigCategory ENTITY_OPTIONS = new ConfigCategory("entity", GENERAL);
	public static final ConfigCategory TILE_ENTITY_OPTIONS = new ConfigCategory("tileentity", GENERAL);
	public static final ConfigCategory OPTIFINE_SHADER_OPTIONS = new ConfigCategory("optifineshaderoptions", GENERAL);

	/*=====GENERAL=====*/
	public static int cacheSize; 	//default is 12
	public static boolean disabledInSpectator;	  //default is true
	public static boolean enabled; //default is true
	public static boolean enableRaytraceCache; //default is false
	public static boolean openglBasedCulling; //default is true
	public static RaytraceDistanceCalculator raytraceDistanceCalculator;  //default is SPHERICAL
	public static final String[] options = Arrays.stream(RaytraceDistanceCalculator.values()).map(Enum::name).toArray(String[]::new);	// for raytraceDistanceCalculator
	public static double raytraceDistanceLimitAdder;  //default is 16.0D		min = 0.0D, max = 1024.0D
	public static double raytraceDistanceLimitMultiplier;  //default is 1.0D		min = 0.0D, max = 1024.0D
	public static double raytraceThreshold;  //default is 1.0D
	public static boolean tileEntityAABBGrowth;  //default is true

	private static Property cacheSizeProp;
	private static Property disabledInSpectatorProp;
	private static Property enabledProp;
	private static Property enableRaytraceCacheProp;
	private static Property openglBasedCullingProp;
	private static Property raytraceDistanceCalculatorProp;
	private static Property raytraceDistanceLimitAdderProp;
	private static Property raytraceDistanceLimitMultiplierProp;
	private static Property raytraceThresholdProp;
	private static Property tileEntityAABBGrowthProp;

	/*=====ENTITIES=====*/
	public static class EntityOptions {
		public boolean alwaysRenderBosses;  //default is true
		public boolean alwaysRenderEntitiesWithName;  //default is true
		public boolean alwaysRenderPlayers;  //default is true
		public boolean alwaysRenderViewEntity;  //default is true
		public boolean ignoreEndCrystalsWithBeam;  //default is true	(prob wont work)
		public boolean skipHiddenEntityRendering;  //default is true
		public double skipHiddenEntityRenderingSize;  //default is 16.0D	min = 0.0D, max = 1024.0D
		public String[] skipHiddenEntityRenderingBlacklist;  //default is new String[0]
		public ResourceLocationMap<Entity, Boolean> skipHiddenEntityRenderingBlacklistImpl = new ResourceLocationMap<>(RenderLibConfig::getEntityStringHelper, false, s -> true);//EntityList::getKey
	}
	public static EntityOptions entity = new EntityOptions();
	private static Property alwaysRenderBossesProp;
	private static Property alwaysRenderEntitiesWithNameProp;
	private static Property alwaysRenderPlayersProp;
	private static Property alwaysRenderViewEntityProp;
	private static Property ignoreEndCrystalsWithBeamProp;
	private static Property skipHiddenEntityRenderingProp;
	private static Property skipHiddenEntityRenderingSizeProp;
	private static Property skipHiddenEntityRenderingBlacklistProp;

	/*=====TILE-ENTITIES=====*/
	public static class TileEntityOptions {
		public boolean skipHiddenTileEntityRendering;  //default is true
		public double skipHiddenTileEntityRenderingSize;  //default is 16.0D	min = 0.0D, max = 1024.0D
		public String[] skipHiddenTileEntityRenderingBlacklist;  //default is { "enderio:tile_travel_anchor" }
		public ResourceLocationMap<TileEntity, Boolean> skipHiddenTileEntityRenderingBlacklistImpl = new ResourceLocationMap<>(RenderLibConfig::getTileEntityStringHelper, false, s -> true);//TileEntity.REGISTRY::getNameForObject
	}
	public static TileEntityOptions tileEntity = new TileEntityOptions();
	private static Property skipHiddenTileEntityRenderingProp;
	private static Property skipHiddenTileEntityRenderingSizeProp;
	private static Property skipHiddenTileEntityRenderingBlacklistProp;

	/*=====OPTIFINE-SHADER-OPTIONS=====*/
	public static class OptifineShaderOptions {
		public boolean entityShadowsCulling;  //default is true
		public boolean entityShadowsCullingLessAggressiveMode;  //default is true
		public double entityShadowsCullingLessAggressiveModeDiff;  //default is 4.0D	min = 0.0D, max = 1024.0D
		public boolean tileEntityShadowsCulling;  //default is true
		public boolean tileEntityShadowsCullingLessAggressiveMode;  //default is true
		public double tileEntityShadowsCullingLessAggressiveModeDiff;  //default is 4.0D	min = 0.0D, max = 1024.0D
	}
	public static OptifineShaderOptions optifineShaderOptions = new OptifineShaderOptions();
	private static Property entityShadowsCullingProp;
	private static Property entityShadowsCullingLessAggressiveModeProp;
	private static Property entityShadowsCullingLessAggressiveModeDiffProp;
	private static Property tileEntityShadowsCullingProp;
	private static Property tileEntityShadowsCullingLessAggressiveModeProp;
	private static Property tileEntityShadowsCullingLessAggressiveModeDiffProp;


	public static void loadConfig(File file) {
        config = new Configuration(file);
        config.load();

		/*=====GENERAL=====*/

		/*-Properties-*/
		cacheSizeProp = config.get(GENERAL.getQualifiedName(), "cacheSize", 12, "Ideally should be set to equal the render distance. Ram usage (in Bytes) = 1063 * (2x + 1) ^ 3");
		disabledInSpectatorProp = config.get(GENERAL.getQualifiedName(), "disabledInSpectator", true);
		enabledProp = config.get(GENERAL.getQualifiedName(), "enabled", true, "Disable all changes from this mod (This is not equal to removing the mod!).");
		enableRaytraceCacheProp = config.get(GENERAL.getQualifiedName(), "enableRaytraceCache", false, "If you have a weak CPU enabling this option might help reducing the CPU usage.");
		openglBasedCullingProp = config.get(GENERAL.getQualifiedName(), "openglBasedCulling", true, "If enabled and OpenGl 4.4 is supported OpenGl based culling is used which is a lot faster and more accurate. If you have a weak GPU you might want to disable this.");
		raytraceDistanceCalculatorProp = config.get(GENERAL.getQualifiedName(), "raytraceDistanceCalculator", "SPHERICAL", "Mode that is used to calculate the distance from camera to a raytrace end point.");
		raytraceDistanceLimitAdderProp = config.get(GENERAL.getQualifiedName(), "raytraceDistanceLimitAdder", 16.0D, "Used to calculate the raytrace distance limit. Points farther away than the limit are not raytraced. Distance limit = (renderDistance * 16 + adder) * multiplier");
		raytraceDistanceLimitMultiplierProp = config.get(GENERAL.getQualifiedName(), "raytraceDistanceLimitMultiplier", 1.0D, "Used to calculate the raytrace distance limit. Points farther away than the limit are not raytraced. Distance limit = (renderDistance * 16 + adder) * multiplier");
		raytraceThresholdProp = config.get(GENERAL.getQualifiedName(), "raytraceThreshold", 1.0D, "If you feel the need to increase this value because of entities being culled falsely then another modder probably messed up their render bounding boxes and you should report the issue to them. Alternatively you can use the (tile-)entityBoundingBoxGrowthList settings to fix bounding boxes on your own.");
		tileEntityAABBGrowthProp = config.get(GENERAL.getQualifiedName(), "tileEntityAABBGrowth", true, "If enabled tile entity bounding boxes are increased slightly to avoid issues when other mods don't correctly set their bounding boxes (requires opengl based culling). If you still have culling or flickering issues you can use the 'debugRenderBoxes', 'entityBoundingBoxGrowthList' and 'tileEntityBoundingBoxGrowthList' config options to try to fix the bounding box of that entity or tile entity.");
		raytraceDistanceLimitAdderProp.setMaxValue(1024.0D);
		raytraceDistanceLimitAdderProp.setMinValue(0.0D);
		raytraceDistanceLimitMultiplierProp.setMaxValue(1024.0D);
		raytraceDistanceLimitMultiplierProp.setMinValue(0.0D);
		raytraceThresholdProp.setMaxValue(1024.0D);
		raytraceThresholdProp.setMinValue(0.0D);
		raytraceDistanceCalculatorProp.setValidValues(options);
		cacheSizeProp.setRequiresMcRestart(true);
		enabledProp.setRequiresWorldRestart(true);

		/*-Variable assigining-*/
		cacheSize = cacheSizeProp.getInt();
		disabledInSpectator = disabledInSpectatorProp.getBoolean();
		enabled = enabledProp.getBoolean();
		enableRaytraceCache = enableRaytraceCacheProp.getBoolean();
		openglBasedCulling = openglBasedCullingProp.getBoolean();
		raytraceDistanceCalculator = RaytraceDistanceCalculator.valueOf(raytraceDistanceCalculatorProp.getString());
		raytraceDistanceLimitAdder = raytraceDistanceLimitAdderProp.getDouble();
		raytraceDistanceLimitMultiplier = raytraceDistanceLimitMultiplierProp.getDouble();
		raytraceThreshold = raytraceThresholdProp.getDouble();
		tileEntityAABBGrowth = tileEntityAABBGrowthProp.getBoolean();


		/*=====ENTITIES=====*/

		/*-Properties-*/
		alwaysRenderBossesProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "alwaysRenderBosses", true);
		alwaysRenderEntitiesWithNameProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "alwaysRenderEntitiesWithName", true);
		alwaysRenderPlayersProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "alwaysRenderPlayers", true);
		alwaysRenderViewEntityProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "alwaysRenderViewEntity", true);
		ignoreEndCrystalsWithBeamProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "ignoreEndCrystalsWithBeam", true);
		skipHiddenEntityRenderingProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "skipHiddenEntityRendering", true, "Skip rendering of entities that are not visible (hidden behind blocks). This might cause issues where an entity is partly behind a block and thus does not get rendered but it's usually not really noticable.");
		skipHiddenEntityRenderingSizeProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "skipHiddenEntityRenderingSize", 16.0D, "Entities with a width or height greater than this value will always get rendered.");
		skipHiddenEntityRenderingBlacklistProp = config.get(ENTITY_OPTIONS.getQualifiedName(), "skipHiddenEntityRenderingBlacklist", new String[0], "Entities which will always be rendered. (Accepts {entity_name} e.g: Cow, Sheep)");
		skipHiddenEntityRenderingSizeProp.setMaxValue(1024.0D);
		skipHiddenEntityRenderingSizeProp.setMinValue(0.0D);

		/*-Variable assigining-*/
		entity.alwaysRenderBosses = alwaysRenderBossesProp.getBoolean();
		entity.alwaysRenderEntitiesWithName = alwaysRenderEntitiesWithNameProp.getBoolean();
		entity.alwaysRenderPlayers = alwaysRenderPlayersProp.getBoolean();
		entity.alwaysRenderViewEntity = alwaysRenderViewEntityProp.getBoolean();
		entity.ignoreEndCrystalsWithBeam = ignoreEndCrystalsWithBeamProp.getBoolean();
		entity.skipHiddenEntityRendering = skipHiddenEntityRenderingProp.getBoolean();
		entity.skipHiddenEntityRenderingSize = skipHiddenEntityRenderingSizeProp.getDouble();
		entity.skipHiddenEntityRenderingBlacklist = skipHiddenEntityRenderingBlacklistProp.getStringList();


		/*=====TILE-ENTITIES=====*/

		/*-Properties-*/
		skipHiddenTileEntityRenderingProp = config.get(TILE_ENTITY_OPTIONS.getQualifiedName(), "skipHiddenTileEntityRendering", true, "Skip rendering of entities that are not visible (hidden behind blocks). This might cause issues where a tile entity is partly behind a block and thus does not get rendered but it's usually not really noticable.");
		skipHiddenTileEntityRenderingSizeProp = config.get(TILE_ENTITY_OPTIONS.getQualifiedName(), "skipHiddenTileEntityRenderingSize", 16.0D, "Tile entities with a width or height greater than this value will always get rendered.");
		skipHiddenTileEntityRenderingBlacklistProp = config.get(TILE_ENTITY_OPTIONS.getQualifiedName(), "skipHiddenTileEntityRenderingBlacklist", new String[0], "Tile entities which will always be rendered. (Accepts {tile_entity_name} e.g: Chest, EnderChest)");
		skipHiddenTileEntityRenderingSizeProp.setMaxValue(1024.0D);
		skipHiddenTileEntityRenderingSizeProp.setMinValue(0.0D);

		/*-Variable assigining-*/
		tileEntity.skipHiddenTileEntityRendering = skipHiddenTileEntityRenderingProp.getBoolean();
		tileEntity.skipHiddenTileEntityRenderingSize = skipHiddenTileEntityRenderingSizeProp.getDouble();
		tileEntity.skipHiddenTileEntityRenderingBlacklist = skipHiddenTileEntityRenderingBlacklistProp.getStringList();

		/*=====OPTIFINE-SHADER-OPTIONS=====*/

		/*-Properties-*/
		entityShadowsCullingProp = config.get(OPTIFINE_SHADER_OPTIONS.getQualifiedName(), "entityShadowsCulling", true);
		entityShadowsCullingLessAggressiveModeProp = config.get(OPTIFINE_SHADER_OPTIONS.getQualifiedName(), "entityShadowsCullingLessAggressiveMode", true);
		entityShadowsCullingLessAggressiveModeDiffProp = config.get(OPTIFINE_SHADER_OPTIONS.getQualifiedName(), "entityShadowsCullingLessAggressiveModeDiff", 4.0D);
		tileEntityShadowsCullingProp = config.get(OPTIFINE_SHADER_OPTIONS.getQualifiedName(), "tileEntityShadowsCullingProp", true);
		tileEntityShadowsCullingLessAggressiveModeProp = config.get(OPTIFINE_SHADER_OPTIONS.getQualifiedName(), "tileEntityShadowsCullingLessAggressiveMode", true);
		tileEntityShadowsCullingLessAggressiveModeDiffProp = config.get(OPTIFINE_SHADER_OPTIONS.getQualifiedName(), "tileEntityShadowsCullingLessAggressiveModeDiff", 4.0D);
		entityShadowsCullingLessAggressiveModeDiffProp.setMaxValue(1024.0D);
		entityShadowsCullingLessAggressiveModeDiffProp.setMinValue(0.0D);
		tileEntityShadowsCullingLessAggressiveModeDiffProp.setMaxValue(1024.0D);
		tileEntityShadowsCullingLessAggressiveModeDiffProp.setMinValue(0.0D);

		/*-Variable assigining-*/
		optifineShaderOptions.entityShadowsCulling = entityShadowsCullingProp.getBoolean();
		optifineShaderOptions.entityShadowsCullingLessAggressiveMode = entityShadowsCullingLessAggressiveModeProp.getBoolean();
		optifineShaderOptions.entityShadowsCullingLessAggressiveModeDiff = entityShadowsCullingLessAggressiveModeDiffProp.getDouble();
		optifineShaderOptions.tileEntityShadowsCulling = tileEntityShadowsCullingProp.getBoolean();
		optifineShaderOptions.tileEntityShadowsCullingLessAggressiveMode = tileEntityShadowsCullingLessAggressiveModeProp.getBoolean();
		optifineShaderOptions.tileEntityShadowsCullingLessAggressiveModeDiff = tileEntityShadowsCullingLessAggressiveModeDiffProp.getDouble();

		entity.skipHiddenEntityRenderingBlacklistImpl.load(entity.skipHiddenEntityRenderingBlacklist);
		tileEntity.skipHiddenTileEntityRenderingBlacklistImpl.load(tileEntity.skipHiddenTileEntityRenderingBlacklist);
	}

	public static void saveConfig() {

		/*=====GENERAL=====*/
		cacheSizeProp.set(cacheSize);
		disabledInSpectatorProp.set(disabledInSpectator);
		enabledProp.set(enabled);
		enableRaytraceCacheProp.set(enableRaytraceCache);
		openglBasedCullingProp.set(openglBasedCulling);
		raytraceDistanceCalculatorProp.set(raytraceDistanceCalculator.name());
		raytraceDistanceLimitAdderProp.set(raytraceDistanceLimitAdder);
		raytraceDistanceLimitMultiplierProp.set(raytraceDistanceLimitMultiplier);
		raytraceThresholdProp.set(raytraceThreshold);
		tileEntityAABBGrowthProp.set(tileEntityAABBGrowth);

		/*=====ENTITIES=====*/
		alwaysRenderBossesProp.set(entity.alwaysRenderBosses);
		alwaysRenderEntitiesWithNameProp.set(entity.alwaysRenderEntitiesWithName);
		alwaysRenderPlayersProp.set(entity.alwaysRenderPlayers);
		alwaysRenderViewEntityProp.set(entity.alwaysRenderViewEntity);
		ignoreEndCrystalsWithBeamProp.set(entity.ignoreEndCrystalsWithBeam);
		skipHiddenEntityRenderingProp.set(entity.skipHiddenEntityRendering);
		skipHiddenEntityRenderingSizeProp.set(entity.skipHiddenEntityRenderingSize);
		skipHiddenEntityRenderingBlacklistProp.set(entity.skipHiddenEntityRenderingBlacklist);

		/*=====TILE-ENTITIES=====*/
		skipHiddenTileEntityRenderingProp.set(tileEntity.skipHiddenTileEntityRendering);
		skipHiddenTileEntityRenderingSizeProp.set(tileEntity.skipHiddenTileEntityRenderingSize);
		skipHiddenTileEntityRenderingBlacklistProp.set(tileEntity.skipHiddenTileEntityRenderingBlacklist);

		/*=====OPTIFINE-SHADER-OPTIONS=====*/
		entityShadowsCullingProp.set(optifineShaderOptions.entityShadowsCulling);
		entityShadowsCullingLessAggressiveModeProp.set(optifineShaderOptions.entityShadowsCullingLessAggressiveMode);
		entityShadowsCullingLessAggressiveModeDiffProp.set(optifineShaderOptions.entityShadowsCullingLessAggressiveModeDiff);
		tileEntityShadowsCullingProp.set(optifineShaderOptions.tileEntityShadowsCulling);
		tileEntityShadowsCullingLessAggressiveModeProp.set(optifineShaderOptions.tileEntityShadowsCullingLessAggressiveMode);
		tileEntityShadowsCullingLessAggressiveModeDiffProp.set(optifineShaderOptions.tileEntityShadowsCullingLessAggressiveModeDiff);

		if (config.hasChanged()) {
			config.save();
		}
	}
}


