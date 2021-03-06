package nl.scribblon.riftcraft.util.helper;


import cpw.mods.fml.common.FMLLog;
import nl.scribblon.riftcraft.reference.Reference;
import nl.scribblon.riftcraft.reference.Settings;
import org.apache.logging.log4j.Level;


/**
 * Created by Scribblon for RiftCraft.
 * Inspired by LetsModReboot by Pahimar
 * https://github.com/pahimar/Equivalent-Exchange-3
 * Date Creation: 26-7-2014
 */
final public class LogHelper {

    private static void log (Level logLevel, Object object){
        FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
    }

    public static void all(Object object){
        log(Level.ALL, object);
    }
    public static void debug(Object object){
        log(Level.DEBUG, object);
    }
    public static void error(Object object){
        log(Level.ERROR, object);
    }
    public static void fatal(Object object){
        log(Level.FATAL, object);
    }
    public static void info(Object object){
        log(Level.INFO, object);
    }
    public static void off(Object object){
        log(Level.OFF, object);
    }
    public static void trace(Object object){
        log(Level.TRACE, object);
    }
    public static void warn(Object object){
        log(Level.WARN, object);
    }

    public static void reportWhenDebugging(Object object) {
        if(Settings.Debug.isDebugging) info(object);
    }
}
