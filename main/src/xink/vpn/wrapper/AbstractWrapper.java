package xink.vpn.wrapper;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import dalvik.system.PathClassLoader;

public abstract class AbstractWrapper implements Cloneable {

    /**
     * subclass to customize stub object creation
     */
    protected static class StubInstanceCreator {
        protected Object newStubInstance(final Class<?> stubClass, final Context context) throws Exception {
            return stubClass.newInstance();
        }
    }

    private static final String STUB_PACK = "com.android.settings";

    private static PathClassLoader stubClassLoader;

    private transient Context context;

    private String stubClassName;

    private Class<?> stubClass;

    private Object stub;

    private StubInstanceCreator stubInstanceCreator;

    protected AbstractWrapper(final Context ctx, final String stubClass) {
        super();
        this.context = ctx;
        stubClassName = stubClass;
        stubInstanceCreator = new StubInstanceCreator();
        init();
    }

    protected AbstractWrapper(final Context ctx, final String stubClass, final StubInstanceCreator stubCreator) {
        super();
        this.context = ctx;
        stubClassName = stubClass;
        stubInstanceCreator = stubCreator;
        init();
    }

    public Context getContext() {
        return context;
    }

    public String getStubClassName() {
        return stubClassName;
    }

    public Class<?> getStubClass() {
        return stubClass;
    }

    public void setStub(final Object stub) {
        this.stub = stub;
    }

    public Object getStub() {
        return stub;
    }

    private void init() {
        try {
            initClassLoader(context);
            stubClass = loadClass(stubClassName);
            stub = stubInstanceCreator.newStubInstance(stubClass, context);
        } catch (Throwable e) {
            throw new WrapperException("init classloader failed", e);
        }
    }

    private static void initClassLoader(final Context ctx) throws NameNotFoundException {
        if (stubClassLoader != null) {
            return;
        }
        ApplicationInfo vpnAppInfo = ctx.getPackageManager().getApplicationInfo(STUB_PACK, 0);
        stubClassLoader = new PathClassLoader(vpnAppInfo.sourceDir, ClassLoader.getSystemClassLoader());
    }

    protected static final Class<?> loadClass(final String qname) {
        try {
            return Class.forName(qname, true, stubClassLoader);
        } catch (ClassNotFoundException e) {
            throw new WrapperException("failed to load class: " + qname, e);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T invokeStubMethod(final String methodName, final Object... args) {
        try {
            Method method = findStubMethod(methodName, args);
            return (T) method.invoke(stub, args);
        } catch (Throwable e) {
            throw new IllegalArgumentException("failed to invoke mehod '" + methodName + "' on stub", e);
        }
    }

    protected Method findStubMethod(final String methodName, final Object... args) throws NoSuchMethodException {
        return findMethod(stubClass, methodName, args);
    }

    protected Method findMethod(final Class<?> clazz, final String methodName, final Object... args) throws NoSuchMethodException {
        Class<?>[] argTypes = new Class<?>[args.length];
        int i = 0;
        for (Object arg : args) {
            argTypes[i++] = arg.getClass();
        }

        Method method = clazz.getMethod(methodName, argTypes);
        method.setAccessible(true);
        return method;
    }

    @Override
    protected AbstractWrapper clone() {
        AbstractWrapper c = null;
        try {
            c = (AbstractWrapper) super.clone();
            c.init();
        } catch (CloneNotSupportedException e) {
            Log.e("xink", "clone failed", e);
        }
        return c;
    }
}
