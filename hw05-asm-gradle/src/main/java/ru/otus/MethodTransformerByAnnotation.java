package ru.otus;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.Method;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;
import static ru.otus.helpers.PropertiesHelper.getProperty;
import static ru.otus.helpers.PropertiesHelper.prepMsg;

public class MethodTransformerByAnnotation extends MethodVisitor {
    private final Method oldMethod;
    private boolean isLogged = false;
    private final String annotationName;
    private final int access;
    private final StringBuilder descriptor = new StringBuilder("(");

    public static final String METHOD_LOG_PREFIX = "method: ";
    public static final String PARAMETER_LOG_PREFIX = ": param \u0001";

    public MethodTransformerByAnnotation(int api, MethodVisitor methodVisitor, Method oldMethod, String annotationName, int access) {
        super(api, methodVisitor);
        this.oldMethod = oldMethod;
        this.annotationName = annotationName;
        this.access = access;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.contains(this.annotationName)) {
            this.isLogged = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitCode() {
        if (this.isLogged) {
            super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            concat(loadArguments());
            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
        super.visitCode();
    }

    private void concat(int numArgs) {
        this.descriptor.append(")Ljava/lang/String;");
        super.visitInvokeDynamicInsn("makeConcatWithConstants", this.descriptor.toString(),
                concatHandle(),
                prepMsg("log", "methodPrefix", this.oldMethod.getName())  +
                        getProperty("log", "parameterPrefix").repeat(numArgs));
    }

    private Handle concatHandle() {
        return new Handle(
                H_INVOKESTATIC,
                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);
    }

    private int loadArguments() {
        int varIndex = 0, numArgs = 0;
        if ((this.access&ACC_STATIC) != ACC_STATIC) {
            varIndex++; // Пропускаем this для non-static методов
        }
        for (var arg : oldMethod.getArgumentTypes()) {
            this.descriptor.append(arg.getDescriptor());
            switch (arg.getDescriptor()) {
                case "J" -> {
                    super.visitVarInsn(Opcodes.LLOAD, varIndex);
                    ++varIndex;
                }
                case "D" -> {
                    super.visitVarInsn(Opcodes.DLOAD, varIndex);
                    ++varIndex;
                }
                case "F" -> super.visitVarInsn(Opcodes.FLOAD, varIndex);
                case "I" -> super.visitVarInsn(Opcodes.ILOAD, varIndex);
                default -> super.visitVarInsn(Opcodes.ALOAD, varIndex);
            }
            varIndex++;
            numArgs++;
        }
        return numArgs;
    }
}
