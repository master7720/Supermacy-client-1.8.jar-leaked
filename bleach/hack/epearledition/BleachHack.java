package bleach.hack.epearledition;

import bleach.hack.epearledition.module.Module;
import bleach.hack.epearledition.module.ModuleManager;
import bleach.hack.epearledition.module.mods.ClickGui;
import bleach.hack.epearledition.utils.FriendManager;
import bleach.hack.epearledition.utils.Rainbow;
import bleach.hack.epearledition.utils.file.BleachFileHelper;
import bleach.hack.epearledition.utils.file.BleachFileMang;
import com.google.common.eventbus.EventBus;
import net.fabricmc.api.ModInitializer;

public class BleachHack
implements ModInitializer {
    private static BleachHack instance = null;
    public static final String VERSION = " 1.7.1";
    public static final int INTVERSION = 24;
    public static EventBus eventBus = new EventBus();
    public static FriendManager friendMang;

    public void onInitialize() {
        BleachFileMang.init();
        BleachFileHelper.readModules();
        ClickGui.clickGui.initWindows();
        BleachFileHelper.readClickGui();
        BleachFileHelper.readPrefix();
        BleachFileHelper.readFriends();
        for (Module m : ModuleManager.getModules()) {
            m.init();
        }
        eventBus.register(new Rainbow());
        eventBus.register(new ModuleManager());
        if (!BleachFileMang.fileExists(new String[]{"drawn.txt"})) {
            BleachFileMang.createFile(new String[]{"drawn.txt"});
        }
        if (!BleachFileMang.fileExists(new String[]{"cleanchat.txt"})) {
            BleachFileMang.createFile(new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("nigger", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("fag", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("discord.gg", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("retard", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("autism", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("chink", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("tranny", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("fuck", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("shit", new String[]{"cleanchat.txt"});
            BleachFileMang.appendFile("nigga", new String[]{"cleanchat.txt"});
        }
    }
}
