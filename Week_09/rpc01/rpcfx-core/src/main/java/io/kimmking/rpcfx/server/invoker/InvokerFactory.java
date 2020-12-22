package io.kimmking.rpcfx.server.invoker;

import io.kimmking.rpcfx.api.RpcfxException;
import javassist.*;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InvokerFactory {

    private Map<Class<?>, Invoker> invokerMap;
    private AtomicLong classCounter;

    private InvokerFactory() {
        invokerMap = new ConcurrentHashMap<>();
        classCounter = new AtomicLong(0);
    }

    public static InvokerFactory getInstance() {
        return Singleton.invokerFactory;
    }

    public Invoker getInvoker(Class<?> klass) throws RpcfxException {
        Invoker methodInvoker = invokerMap.get(klass);
        if (methodInvoker != null) {
            return methodInvoker;
        }

        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass invokerClass = pool.makeClass(Invoker.class.getName() + classCounter.getAndIncrement(), pool.getCtClass("io.kimmking.rpcfx.server.invoker.Invoker"));
            String invokeMethod = composeInvokeMethodFromKlass(klass);
            invokerClass.addMethod(CtNewMethod.make(invokeMethod, invokerClass));
            methodInvoker = (Invoker) invokerClass.toClass().newInstance();
            invokerMap.putIfAbsent(klass, methodInvoker);
            return methodInvoker;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpcfxException(e.getMessage(), e.getCause());
        }

    }

    private String composeInvokeMethodFromKlass(Class<?> klass) {
        Method[] methods = klass.getMethods();
        String className = klass.getName();
        StringBuilder methodStr = new StringBuilder("public Object invoke(Object service, String method, Object[] params) {");

        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            String methodName = method.getName();
            methodStr.append("if(method.equals(\"").append(methodName).append("\"))");
            if (returnType.getName().equals("void")) {
                methodStr.append("{((").append(className).append(")service).").append(methodName).append("(");
            } else {
                methodStr.append("return ((").append(className).append(")service).").append(methodName).append("(");
            }
            int paramCount = method.getParameterCount();
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int j = 0; j < paramCount; j++) {
                Class<?> cls = parameterTypes[j];
                methodStr.append("(").append(cls.getName()).append(")params[").append(j).append("]");
                if ((j + 1) < paramCount)
                    methodStr.append(",");
            }
            methodStr.append(");");
            if (returnType.getName().equals("void")) {
                methodStr.append("return null;");
                methodStr.append("}");
            }
        }

        methodStr.append("return null;}");
        return methodStr.toString();
    }

    private static class Singleton {
        private static InvokerFactory invokerFactory = new InvokerFactory();
    }
}
