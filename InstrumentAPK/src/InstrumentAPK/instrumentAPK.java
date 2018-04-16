package InstrumentAPK;

import java.io.File;
import java.util.*;

import soot.*;
import soot.jimple.*;


public class instrumentAPK {

    public static void main(String[] args) {
        // initialize soot with options
        Settings.initialiseSoot();

//        // delete the output apk file if already exists
//        File outputApk = new File("./sootOutput/"+Constants.APK_NAME);
//        outputApk.delete();

        PackManager.v().getPack("jtp").add(new Transform("jtp.myTransformer", new myTransformer()));//添加自己的BodyTransformer
        PackManager.v().runPacks();
        PackManager.v().writeOutput();

//        soot.Main.main(args);
        // sign the apk
        Utils.runSystemCmd(Constants.SCRIPTS_DIR + "signAPK " + Constants.APK_NAME);
        Utils.runSystemCmd(Constants.SCRIPTS_DIR + "deploy " + Constants.APK_NAME);

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
                    // insert log message
                    body.getUnits().insertAfter(logUnit, stmt);
                    // delete the original message
                    body.getUnits().remove(stmt);

                    //check that we did not mess up the Jimple
                    body.validate();
                }
            }
        }
    }

    private List<Unit> buildIntent(){
        List<Unit> unitsList = new ArrayList<>();

        // Intent dulingIntent;
        SootClass intentClass = Scene.v().getSootClass("android.content.Intent");
        RefType intentType = RefType.v(intentClass);
        Local intentLocal = Jimple.v().newLocal("dulingIntent", intentType);

        // dulingIntent = new Intent(this, dulingActivityAwareLocation.class)
        AssignStmt dulingIntent = Jimple.v().newAssignStmt(intentLocal, Jimple.v().newNewExpr(RefType.v(intentClass)));
        SpecialInvokeExpr invokeIntent = Jimple.v().newSpecialInvokeExpr(intentLocal, intentClass.getMethod("void <init>(android.content.Context,java.lang.Class)").makeRef(), "$r0", );
    }

    private List<Unit> startService(String newClassName){
        List<Unit> unitsList = new ArrayList<>();
        SootClass contextClass = Scene.v().getSootClass("android.content.Context");
        SootMethod startMethod = contextClass.getMethod("android.content.ComponentName startService(android.content.Intent)");
        VirtualInvokeExpr virtualInvoke = Jimple.v().newVirtualInvokeExpr(startMethod.makeRef(), );
    }

    private List<Unit> makeLog(String msg){
        List<Unit> unitsList = new ArrayList<>();
        SootClass logClass = Scene.v().getSootClass("android.util.Log");     //获取android.util.Log类
        SootMethod sootMethod = logClass.getMethod("int i(java.lang.String,java.lang.String)");
        StaticInvokeExpr staticInvokeExpr = Jimple.v().newStaticInvokeExpr(sootMethod.makeRef(), StringConstant.v("Duling"), StringConstant.v(msg));
        InvokeStmt invokeStmt = Jimple.v().newInvokeStmt(staticInvokeExpr);
        unitsList.add(invokeStmt);
        return unitsList;
    }
}