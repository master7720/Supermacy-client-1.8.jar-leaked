package bleach.hack.epearledition.module.mods;

import bleach.hack.epearledition.event.events.EventTick;
import bleach.hack.epearledition.module.Category;
import bleach.hack.epearledition.module.Module;
import bleach.hack.epearledition.setting.base.SettingBase;
import bleach.hack.epearledition.setting.base.SettingMode;
import bleach.hack.epearledition.setting.base.SettingToggle;
import bleach.hack.epearledition.utils.DiscordRPCManager;
import bleach.hack.epearledition.utils.file.BleachFileHelper;
import com.google.common.eventbus.Subscribe;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.minecraft.class_155;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import org.apache.commons.lang3.RandomUtils;

public class DiscordRPCMod
extends Module {
    private String customText1 = "top text";
    private String customText2 = "bottom text";
    private int tick = 0;
    private boolean silent;

    public DiscordRPCMod() {
        super("DiscordRPC", -2, Category.misc, "Discord RPC, use the \"rpc\" command to set a custom status", new SettingBase[]{new SettingMode("Text 1: ", new String[]{"Playing %server%", "%server%", "%type%", "%username% ontop", "Minecraft %mcver%", "%username%", "<- bad client", "%custom%"}), new SettingMode("Text 2: ", new String[]{"%hp% hp - Holding %item%", "%username% - %hp% hp", "Holding %item%", "%hp% hp - At %coords%", "At %coords%", "%custom%"}), new SettingMode("Elapsed: ", new String[]{"Normal", "Random", "Backwards", "None"}), new SettingToggle("Silent", false)});
    }

    @Override
    public void init() {
        String t1 = BleachFileHelper.readMiscSetting("discordRPCTopText");
        String t2 = BleachFileHelper.readMiscSetting("discordRPCBottomText");
        if (t1 != null) {
            this.customText1 = t1;
        }
        if (t2 != null) {
            this.customText2 = t2;
        }
    }

    @Override
    public void onEnable() {
        this.silent = ((SettingBase)this.getSettings().get((int)3)).asToggle().state;
        this.tick = 0;
        DiscordRPCManager.start(this.silent ? "9493060483193252" : "9493060483193252");
        super.onEnable();
    }

    @Override
    public void onDisable() {
        DiscordRPCManager.stop();
        super.onDisable();
    }

    @Subscribe
    public void onTick(EventTick event) {
        if (this.silent != ((SettingBase)this.getSettings().get((int)3)).asToggle().state) {
            this.onDisable();
            this.onEnable();
        }
        if (this.tick % 40 == 0) {
            String text1 = this.customText1;
            String text2 = this.customText2;
            long start = 0L;
            switch (((SettingBase)this.getSettings().get((int)0)).asMode().mode) {
                case 0: {
                    if (this.mc.method_1558() != null && !this.mc.method_1558().field_3761.contains(".*\\d.*")) {
                        text1 = "Playing " + this.mc.method_1558().field_3761;
                        break;
                    }
                    text1 = "Playing Singleplayer";
                    break;
                }
                case 1: {
                    if (this.mc.method_1558() != null && !this.mc.method_1558().field_3761.contains(".*\\d.*")) {
                        text1 = this.mc.method_1558().field_3761;
                        break;
                    }
                    text1 = "Singleplayer";
                    break;
                }
                case 2: {
                    if (this.mc.method_1558() != null) {
                        text1 = "Multiplayer";
                        break;
                    }
                    text1 = "Singleplayer";
                    break;
                }
                case 3: {
                    text1 = this.mc.field_1724.method_5820() + " Ontop!";
                    break;
                }
                case 4: {
                    text1 = "Minecraft " + class_155.method_16673().getName();
                    break;
                }
                case 5: {
                    text1 = this.mc.field_1724.method_5820();
                    break;
                }
                case 6: {
                    text1 = "<- bad client";
                }
            }
            class_1799 currentItem = this.mc.field_1724.field_7514.method_7391();
            String itemName = currentItem.method_7909() == class_1802.field_8162 ? "Nothing" : (currentItem.method_7947() > 1 ? currentItem.method_7947() + " " : "") + currentItem.method_7909().method_7848().getString();
            switch (((SettingBase)this.getSettings().get((int)1)).asMode().mode) {
                case 0: {
                    text2 = (int)this.mc.field_1724.method_6032() + " hp - Holding " + itemName;
                    break;
                }
                case 1: {
                    text2 = this.mc.field_1724.method_5820() + " - " + (int)this.mc.field_1724.method_6032() + " hp";
                    break;
                }
                case 2: {
                    text2 = "Holding " + itemName;
                    break;
                }
                case 3: {
                    text2 = (int)this.mc.field_1724.method_6032() + " hp - At " + this.mc.field_1724.method_24515().method_23854();
                    break;
                }
                case 4: {
                    text2 = "At " + this.mc.field_1724.method_24515().method_23854();
                }
            }
            switch (((SettingBase)this.getSettings().get((int)2)).asMode().mode) {
                case 0: {
                    start = System.currentTimeMillis() - (long)(this.tick * 50);
                    break;
                }
                case 1: {
                    start = System.currentTimeMillis() - (long)RandomUtils.nextInt(0, 86400000);
                    break;
                }
                case 2: {
                    start = 1590000000000L + (long)(this.tick * 100);
                }
            }
            DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder(text2).setBigImage("icon", this.silent ? "Minecraft " + class_155.method_16673().getName() : "Supremacy Client v1.7.1 1.7.1").setDetails(text1).setStartTimestamps(start).build());
        }
        if (this.tick % 200 == 0) {
            DiscordRPC.discordRunCallbacks();
        }
        ++this.tick;
    }

    public void setText(String t1, String t2) {
        this.customText1 = t1;
        this.customText2 = t2;
    }
}
