package InstrumentAPK;

import soot.G;
import soot.Scene;
import soot.options.Options;

import java.util.Collections;

public class Settings {
    private static boolean SOOT_INITIALIZED = false;

    public static void initialiseSoot(){
        if (SOOT_INITIALIZED) return;
        G.reset();
        Options.v().set_force_overwrite(true);
        Options.v().set_allow_phantom_refs(true);       // allow soot to create phantom ref for unknown classes
        Options.v().set_prepend_classpath(true);        //prepend the VM's classpath to Soot's own classpath
        //prefer Android APK files// -src-prec apk
        Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_whole_program(true);
        Options.v().set_app(true);

        //output as APK, too//-f J
        Options.v().set_output_format(Options.output_format_dex);
        Options.v().set_process_multiple_dex(true);

        Options.v().set_android_jars(Constants.ANDROID_JAR);       //Set android jar location
        Options.v().set_force_android_jar(Constants.ANDROID_JAR + "android-21/android.jar");
        Options.v().set_process_dir(Collections.singletonList(Constants.APK_DIR + Constants.APK_NAME));        // set target APK

        // set soot class path
//        Options.v().set_soot_classpath("/Users/dulinglai/Documents/Study/ResearchProjects/LocationFreq/Soot_Instrumenter/InstrumentAPK/libs/sootclasses-trunk-jar-with-dependencies.jar");

        Options.v().set_validate(true);

//        Options.v().set_soot_classpath(androidJAR);

        Scene.v().loadNecessaryClasses();

        SOOT_INITIALIZED = true;
    }
}
