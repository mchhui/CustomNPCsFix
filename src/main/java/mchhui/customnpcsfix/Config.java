package mchhui.customnpcsfix;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.config.Configuration;
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
    public static boolean DontAccpetDubiousScript;
    public static boolean AutoUseNPCDamageSource;
    
    //Client
    public static boolean DontSendDubiousScript;
    public static boolean SafeRenderNoTexNPC;
    public static boolean LimitedScriptGuiAddButton;

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
        comment = "更快的JSON反序列化 虽然不是特别快 但是比NPC自带的快的多";
        FastJsonDeserialize = config.get(Configuration.CATEGORY_GENERAL, "FastJsonDeserialize", false, comment)
                .getBoolean();
        comment = "修复同伴的进食BUG";
        WIPNoFoodBUG = config.get(Configuration.CATEGORY_GENERAL, "WIPNoFoodBUG", false, comment).getBoolean();
        comment = "NPC的say方法说出%不再崩溃";
        NoMsgPercentSymbolBug = config.get(Configuration.CATEGORY_GENERAL, "NoMsgPercentSymbolBug", false, comment)
                .getBoolean();
        comment = "传送师使用世界名判断世界而不是ID \n注:\n1.启动此项后 会在设定传送点时保存其世界名\n2.启动此项后 新建一个传送点 就会自动为先前没有WorldName的传送点补全WorldName(不一定正确)";
        TPLocUseWorldName = config.get(Configuration.CATEGORY_GENERAL, "TPLocUseWorldName", false, comment)
                .getBoolean();
        comment = "传送师使用BukkitAPI进行传送 这通常用于修复某些插件显示的世界和玩家所在的世界不同\n需TPLocUseWorldName";
        TPUseBukkitAPI = config.get(Configuration.CATEGORY_GENERAL, "TPUseBukkitAPI", false, comment).getBoolean();
        comment = "*非常不建议启用\n不接受可疑的脚本保存请求 如果保存请求是无脚本内容且启动是关闭的 就过滤掉\n1.这会抛出Error with EnumPacketServer.null\n2.更加推荐在客户端开启DontSendDubiousScript";
        DontAccpetDubiousScript= config.get(Configuration.CATEGORY_GENERAL, "DontAccpetDubiousScript", false, comment).getBoolean();
        comment = "在玩家受到来源npc的伤害自动将其类型转为NPCDamageSource";
        AutoUseNPCDamageSource= config.get(Configuration.CATEGORY_GENERAL, "AutoUseNPCDamageSource", false, comment).getBoolean();
        
        //Client
        comment = "不发送无脚本内容的保存请求。 他的作用有点像DontAccpetDubiousScript";
        DontSendDubiousScript= config.get(Configuration.CATEGORY_CLIENT, "DontSendDubiousScript", false, comment).getBoolean();
        comment = "会为无材质的NPC使用透明材质,这防止了此错误信息刷屏";
        SafeRenderNoTexNPC= config.get(Configuration.CATEGORY_CLIENT, "SafeRenderNoTexNPC", false, comment).getBoolean();
        comment = "当脚本为11页时,隐藏添加按钮";
        LimitedScriptGuiAddButton= config.get(Configuration.CATEGORY_CLIENT, "LimitedScriptGuiAddButton", false, comment).getBoolean();
        
        
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
