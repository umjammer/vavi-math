/*
 * http://d.hatena.ne.jp/bellbind/20050903/p2
 */

package vavi.util.memoization;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;


/**
 * MemoizeAgent.
 * 
 * @author <a href="http://d.hatena.ne.jp/bellbind/20050903/p2"></a>
 * @version 0.00 2009/07/06 nsano initial version <br>
 */
public class MemoizeAgent implements ClassFileTransformer {
    private ClassPool pool;

    private CtClass memoClass;

    private CtField.Initializer memoInitializer;

    public MemoizeAgent(Instrumentation inst) throws Exception {
        this.pool = new ClassPool();
        this.pool.appendSystemPath();
        this.memoClass = this.pool.get("java.util.HashMap");
        this.memoInitializer = CtField.Initializer.byExpr("new java.util.HashMap()");
    }

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        inst.addTransformer(new MemoizeAgent(inst));
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            return getMemorizedClass(classfileBuffer);
        } catch (Exception ex) {
            throw new IllegalClassFormatException(ex.getMessage());
        }
    }

    private byte[] getMemorizedClass(byte[] classfileBuffer) throws Exception {
        ByteArrayInputStream istream = new ByteArrayInputStream(classfileBuffer);
        CtClass newClass = pool.makeClass(istream);
        int index = 0;
        for (CtMethod method : newClass.getMethods()) {
            if (!isMemorizedMethod(method)) {
                continue;
            }

            // add memo field
            String fieldName = "$_memo_map_$" + index;
            CtField memoField = new CtField(memoClass, fieldName, newClass);
            newClass.addField(memoField, memoInitializer);

            // escape original method
            CtMethod bodyMethod = new CtMethod(method, newClass, null);
            String bodyMethodName = "$_memo_method_$" + index;
            bodyMethod.setName(bodyMethodName);
            newClass.addMethod(bodyMethod);

            // replace memorized body
            String code = "{" +
                "vavi.util.memoization.Args args = new vavi.util.memoization.Args($args);" + 
                "Object result = " + fieldName + ".get(args); " +
                "if (result != null) return ($r) result;" +
                "result = ($w) " + bodyMethodName + "($$);" +
                fieldName + ".put(args, result);" +
                "return ($r) result;" +
                "}";
            method.setBody(code);

            index++;
        }
        return newClass.toBytecode();
    }

    private boolean isMemorizedMethod(CtMethod method) throws Exception {
        for (Object annotation : method.getAnnotations()) {
            if (annotation instanceof Memoize) {
//System.err.println(method);
                return true;
            }
        }
        return false;
    }
}

/* */
