package mchhui.customnpcsfix;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {
    private static Configuration config;

    private static Logger logger;
    //General
    public static boolean FastJsonDeserialize;
    public static boolean WIPNoFoodBUG;
    public static boolean NoMsgPercentSymbolBug;
    public static boolean TPLocUseWorldName;
    public static boolean TPUseBukkitAPI;
    public static boolean AutoUseNPCDamageSource;
    public static boolean EnabledQuestWaypoint;
    public static boolean EffectiveCollectItemQuest;

    //Client
    public static boolean DontSendDubiousScript;
    public static boolean SafeRenderNoTexNPC;
    public static boolean LimitedScriptGuiAddButton;
    public static boolean DontUseScriptItemTextures;

    public Config(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        load();
    }

    public static void load() {
        logger.info("Started loading config. ");
        String comment;

        //General
        comment = "更快的JSON(反)序列化 虽然不是特别快 但是比NPC自带的快的多\n" + "JSON(反)序列化常被npc用于 保存玩家数据 任务数据 对话数据等\n"
                + "NPC的IO和网络通讯使用同一线程\n" + "因此,如果JSON(反)序列化速度慢的话 可能会导致像交互延迟等现象";
        FastJsonDeserialize = config.get(Configuration.CATEGORY_GENERAL, "FastJsonDeserialize", false, comment)
                .getBoolean();
        comment = "修复同伴的进食BUG";
        WIPNoFoodBUG = config.get(Configuration.CATEGORY_GENERAL, "WIPNoFoodBUG", false, comment).getBoolean();
        comment = "修复%信息崩溃bug\n" + "这通常用于修复:\n" + "1.NPC台词里使用%导致错误\n" + "2.NPC脚本里NPC的say方法和玩家的message发送%导致错误";
        NoMsgPercentSymbolBug = config.get(Configuration.CATEGORY_GENERAL, "NoMsgPercentSymbolBug", false, comment)
                .getBoolean();
        comment = "传送师使用世界名判断世界而不是ID \n" + "注:\n" + "1.启动此项后 会在设定传送点时保存其世界名\n"
                + "2.启动此项后 新建一个传送点 就会自动为先前没有WorldName的传送点补全WorldName(不一定正确)\n" + "这通常用于修复MV插件与NPC传送师的数据不同步等异常";
        TPLocUseWorldName = config.get(Configuration.CATEGORY_GENERAL, "TPLocUseWorldName", false, comment)
                .getBoolean();
        comment = "传送师使用BukkitAPI进行传送 \n" + "这通常用于修复某些插件显示的世界和玩家所在的世界不同" + "\n需TPLocUseWorldName";
        TPUseBukkitAPI = config.get(Configuration.CATEGORY_GENERAL, "TPUseBukkitAPI", false, comment).getBoolean();
        comment = "*已完全废弃 失效 \n" + "不接受可疑的脚本保存请求 如果保存请求是无脚本内容且启动是关闭的 就过滤掉\n"
                + "1.这会抛出Error with EnumPacketServer.null\n" + "2.更加推荐在客户端开启DontSendDubiousScript\n"
                + "这通常用于解决有时候出现的脚本保存丢失";
        if (config.get(Configuration.CATEGORY_GENERAL, "DontAccpetDubiousScript", false, comment).getBoolean()) {
            FMLLog.log.warn("你在customnpcsfix.cfg内开启了一个已失效的选项 DontAccpetDubiousScript");
        }
        comment = "在玩家受到来源npc的伤害自动将其类型转为NPCDamageSource\n" + "这通常是方便开发者用的";
        AutoUseNPCDamageSource = config.get(Configuration.CATEGORY_GENERAL, "AutoUseNPCDamageSource", false, comment)
                .getBoolean();
        comment = "启动任务导航点功能\n" + "这允许你在玩家进行任务时在地图上标点为玩家导航\n" + "这需要在客户端安装CustomNPCsFix和Xaeros Minimap\n"+"推荐开启小地图选项\"以上距离路近点显示名称\"";
        EnabledQuestWaypoint = config.get(Configuration.CATEGORY_GENERAL, "EnabledQuestWaypoint", false, comment)
                .getBoolean();
        comment = "更精准的物品搜集任务判定\n" + "优化物品搜集任务的不精准判断";
        EffectiveCollectItemQuest = config
                .get(Configuration.CATEGORY_GENERAL, "EffectiveCollectItemQuest", false, comment).getBoolean();

        //Client
        comment = "不发送无脚本内容的保存请求。 \n" + "这通常用于解决有时候出现的脚本保存丢失";
        DontSendDubiousScript = config.get(Configuration.CATEGORY_CLIENT, "DontSendDubiousScript", false, comment)
                .getBoolean();
        comment = "会为无材质的NPC使用透明材质,这防止了此错误信息在客户端日志里刷屏\n" + "这通常服务强迫症和开发者";
        SafeRenderNoTexNPC = config.get(Configuration.CATEGORY_CLIENT, "SafeRenderNoTexNPC", false, comment)
                .getBoolean();
        comment = "当脚本为11页时,隐藏添加按钮\n" + "这通常服务强迫症和开发者";
        LimitedScriptGuiAddButton = config
                .get(Configuration.CATEGORY_CLIENT, "LimitedScriptGuiAddButton", false, comment).getBoolean();
        comment = "不使用脚本物品材质\n" + "这可以用来修复脚本物品不支持高清修复的自定义物品模型";
        DontUseScriptItemTextures = config
                .get(Configuration.CATEGORY_CLIENT, "DontUseScriptItemTextures", false, comment).getBoolean();

        config.save();
        logger.info("Finished loading config. ");
    }

    public static void reload() {
        config.load();
        load();
    }

    public static Logger logger() {
        return logger;
    }
}
