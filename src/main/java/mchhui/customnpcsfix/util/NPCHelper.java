package mchhui.customnpcsfix.util;

import mchhui.customnpcsfix.Config;

public class NPCHelper {
    public static String onSay(String line) {
        if(!Config.NoMsgPercentSymbolBug) {
            return line;
        }
        return line.replaceAll("%", "%%");
    }
}
