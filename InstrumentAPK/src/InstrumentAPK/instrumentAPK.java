package InstrumentAPK;

import java.util.*;

import soot.*;
import soot.jimple.*;
import soot.options.Options;


public class instrumentAPK {
    // initializing soot
    private static void initSoot(String[] args) {
        G.reset();  // reset soot environment
        Options.v().set_allow_phantom_refs(true);       // allow soot to create phantom ref for unknown classes
        Options.v().set_prepend_classpath(true);        //prepend the VM's classpath to Soot's own classpath
        //prefer Android APK files// -src-prec apk
        Options.v().set_src_prec(Options.src_prec_apk);
        //output as APK, too//-f J
        Options.v().set_output_format(Options.output_format_dex);
        Options.v().set_process_multiple_dex(true);

        Options.v().set_android_jars(Constants.ANDROID_JAR);       //Set android jar location
        Options.v().set_force_android_jar(Constants.ANDROID_JAR + "android-21/android.jar");
        Options.v().set_process_dir(Collections.singletonList(Constants.APK_DIR + Constants.APK_NAME));        // set target APK

        // set soot class path
//        Options.v().set_soot_classpath("/Users/dulinglai/Documents/Study/ResearchProjects/LocationFreq/Soot_Instrumenter/InstrumentAPK/libs/sootclasses-trunk-jar-with-dependencies.jar");

        Scene.v().loadNecessaryClasses();
        //call soot.Main
        //soot.Main.main(args);
    }

    public static void main(String[] args) {
        initSoot(args);
        PackManager.v().getPack("jtp").add(new Transform("jtp.myTransformer", new myTransformer()));//添加自己的BodyTransformer
        PackManager.v().runPacks();
        PackManager.v().writeOutput();

//        soot.Main.main(args);
        // sign the apk
        Utils.runSystemCmd(Constants.SCRIPTS_DIR + "signAPK " + Constants.APK_NAME);
    }
}

class myTransformer extends BodyTransformer {
    @Override
    protected void internalTransform(Body body, String s, Map<String, String> map) {
        Iterator<Unit> unitsIterator = body.getUnits().snapshotIterator();//获取Body里所有的units,一个Body对应Java里一个方法的方法体，Unit代表里面的语句
        while(unitsIterator.hasNext()){
            Stmt stmt=(Stmt)unitsIterator.next();//将Unit强制转换为Stmt,Stmt为Jimple里每条语句的表示

            if(stmt.containsInvokeExpr()){//如果是一条调用语句
                String declaringClass = stmt.getInvokeExpr().getMethod().getDeclaringClass().getName();//获取这个方法所属的类
                String methodName = stmt.getInvokeExpr().getMethod().getName();//获取这个方法的名称
                if(declaringClass.equals("android.location.LocationManager") && methodName.equals("requestLocationUpdates")){
                    List<Unit> logUnit = makeLog("Starting Location Logic");
                    body.getUnits().insertAfter(logUnit, stmt);//在这条语句之后插入Toast消息
                    //check that we did not mess up the Jimple
                    body.validate();
                }
            }
        }
    }

    private List<Unit> makeLog(String msg){
        List<Unit> unitsList = new ArrayList<Unit>();
        //插入语句Log.i("test",toast);
        SootClass logClass = Scene.v().getSootClass("android.util.Log");     //获取android.util.Log类
        SootMethod sootMethod = logClass.getMethod("int i(java.lang.String,java.lang.String)");
        StaticInvokeExpr staticInvokeExpr = Jimple.v().newStaticInvokeExpr(sootMethod.makeRef(), StringConstant.v("Duling"), StringConstant.v(msg));
        InvokeStmt invokeStmt = Jimple.v().newInvokeStmt(staticInvokeExpr);
        unitsList.add(invokeStmt);
        return unitsList;
    }
}