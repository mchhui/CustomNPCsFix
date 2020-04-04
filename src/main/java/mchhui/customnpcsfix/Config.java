package mchhui.customnpcsfix;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class Config {
    private static Configuration config;

    private static Logger logger;

    public static boolean FastJsonDeserialize;
    public static boolean UseNBTJson;
    public static boolean NoMsgPercentSymbolBug;

    //client
    public static boolean SafeRenderNoTexNPC;
    public static boolean AutoReaddNPCToChunk;

    public Config(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();
        load();
    }

    public static void load() {
        logger.info("Started loading config. ");
        String comment;

        //GENERAL
        comment = "更快的JSON反序列化 虽然不是特别快 但是比NPC自带的快的多";
        FastJsonDeserialize = config.get(Configuration.CATEGORY_GENERAL, "FastJsonDeserialize", false, comment)
                .getBoolean();
        comment = "慎用! Gson实现json(反)序列化 特大复杂数据比FastJsonDeserialize快 \n注:\n1.需先启用FastJsonDeserialize\n2.Gson序列化的文件原版NPC不可反序列化\n3.为了性能 放弃格式化";
        UseNBTJson = config.get(Configuration.CATEGORY_GENERAL, "UseNBTJson", false, comment).getBoolean();
        comment = "NPC的say方法说出%不再崩溃";
        NoMsgPercentSymbolBug = config.get(Configuration.CATEGORY_GENERAL, "NoMsgPercentSymbolBug", false, comment)
                .getBoolean();

        //Client
        comment = "会为无材质的NPC使用透明材质,这防止了此错误信息刷屏";
        SafeRenderNoTexNPC = config.get("client", "SafeRenderNoTexNPC", false, comment).getBoolean();
        comment = "死亡自动把NPC补充到区块 修复不可互交BUG";
        AutoReaddNPCToChunk = config.get("client", "AutoReaddNPCToChunk", false, comment).getBoolean();

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
