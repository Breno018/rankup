// 
// Decompiled by Procyon v0.5.36
// 

package com.skymiticos.javautils;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Map;

public final class RefAPI
{
    private static Map<String, String> replacers;
    
    public static String getReplacer(final String key) {
        return RefAPI.replacers.get(key);
    }
    
    public static void newReplacer(final String key, final String replacer) {
        RefAPI.replacers.put(key, replacer);
    }
    
    public static void setValue(final Object object, final String name, final Object value) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        final Field field = getField(object, name);
        field.set(object, value);
    }
    
    public static Field getField(final Object object, final String name) throws ClassNotFoundException {
        final Class<?> claz = get(object);
        for (final Field field : claz.getDeclaredFields()) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                return field;
            }
        }
        for (final Field field : claz.getFields()) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                return field;
            }
        }
        return null;
    }
    
    public static Method getMethod(final Object object, final String name, final Object... parameters) throws ClassNotFoundException {
        final Class<?> claz = get(object);
        final Class<?>[] objects = getParameters(parameters);
        for (final Method method : claz.getDeclaredMethods()) {
            if (method.getName().equals(name) && equals(method.getParameterTypes(), objects)) {
                method.setAccessible(true);
                return method;
            }
        }
        for (final Method method : claz.getMethods()) {
            if (method.getName().equals(name) && equals(method.getParameterTypes(), objects)) {
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }
    
    public static boolean equals(final Class<?>[] firstArray, final Class<?>[] secondArray) {
        if (firstArray.length == secondArray.length) {
            for (int i = 0; i < secondArray.length; ++i) {
                if (!firstArray[i].equals(secondArray[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static Class<?>[] getParameters(final Object... parameters) throws ClassNotFoundException {
        final Class<?>[] objects = (Class<?>[])new Class[parameters.length];
        for (int i = 0; i < parameters.length; ++i) {
            objects[i] = get(parameters[i]);
        }
        return objects;
    }
    
    public static Constructor<?> getConstructor(final Object object, final Object... parameters) throws ClassNotFoundException {
        final Class<?> claz = get(object);
        final Class<?>[] objects = getParameters(parameters);
        for (final Constructor<?> constructor : claz.getDeclaredConstructors()) {
            if (equals(constructor.getParameterTypes(), objects)) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        for (final Constructor<?> constructor : claz.getConstructors()) {
            if (equals(constructor.getParameterTypes(), objects)) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return null;
    }
    
    public static Object getNew(final Object object, final Object... values) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        return getConstructor(object, values).newInstance(values);
    }
    
    public static Object getNew(final Object object, final Object[] parameters, final Object... values) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        return getConstructor(object, parameters).newInstance(values);
    }
    
    public static Object getValue(final Object object, final String name) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException {
        return getField(object, name).get(object);
    }
    
    public static Object getResult(final Object object, final String name, final Object... values) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        return getMethod(object, name, values).invoke(object, values);
    }
    
    public static Object getResult(final Object object, final String name, final Object[] parameters, final Object... values) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        return getMethod(object, name, parameters).invoke(object, values);
    }
    
    public static Class<?> get(final Object object) throws ClassNotFoundException {
        if (object instanceof Class) {
            return (Class<?>)object;
        }
        if (object instanceof String) {
            String string = (String)object;
            if (string.startsWith("#")) {
                for (final Map.Entry<String, String> entry : RefAPI.replacers.entrySet()) {
                    string = string.replace(entry.getKey(), entry.getValue());
                }
                return Class.forName(string);
            }
        }
        try {
            return (Class<?>)object.getClass().getField("TYPE").get(0);
        }
        catch (Exception ex) {
            return object.getClass();
        }
    }
    
    static {
        (RefAPI.replacers = new LinkedHashMap<String, String>()).put("#s", "org.spigotmc.");
        RefAPI.replacers.put("#a", "net.eduard.api.");
        RefAPI.replacers.put("#e", "net.eduard.eduardapi.");
        RefAPI.replacers.put("#k", "net.eduard.api.kits.");
        RefAPI.replacers.put("#p", "#mPacket");
        RefAPI.replacers.put("#m", "net.minecraft.server.#v.");
        RefAPI.replacers.put("#c", "org.bukkit.craftbukkit.#v.");
        RefAPI.replacers.put("#s", "org.bukkit.");
    }
}
