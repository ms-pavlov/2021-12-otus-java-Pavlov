package ru.otus;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.Method;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class Agent {
    public static final String PREFIX = "proxies_";
    public static final String LOG_ANNOTATION = "Lru/otus/annotations/Log;";

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                    return addLogger(classfileBuffer);
            }
        });
    }

    private static byte[] addLogger(byte[] classfileBuffer) {
        ClassReader reader = new ClassReader(classfileBuffer);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ArrayList<String> list = new ArrayList<>();
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
                Method thisMethod = new Method(name, descriptor);
                return new MethodTransformerByAnnotation(Opcodes.ASM5,
                        super.visitMethod(access, name, descriptor, signature, exceptions),
                        thisMethod,
                        LOG_ANNOTATION,
                        access);
            }
        };

        reader.accept(visitor, Opcodes.ASM5);
        return writer.toByteArray();
    }
}
