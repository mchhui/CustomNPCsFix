package mchhui.customnpcsfix.api.event.client;

import java.io.File;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * 如果不在特殊的事件里在EventBus中注册事件监听器 你很可能接受不到第一次加载
 * */
public class ResourcePackRepositoryGetFilesEvent extends Event {
    public final List<File> files;

    public ResourcePackRepositoryGetFilesEvent(List<File> files) {
        this.files = files;
    }
}
