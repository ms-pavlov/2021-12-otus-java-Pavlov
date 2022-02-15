package ru.otus;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 0)
@Measurement(iterations = 1, batchSize = 5)
@State(Scope.Benchmark)
public class MyClassFactoryJMHTest {

    private static final int COUNT = 10;

    public TestLogging base;
    public TestLogging noLogging;
    public TestLogging proxy;

    @Setup(Level.Invocation)
    public void setUp() {
        base = new TestLoggingImpl();
        noLogging = (TestLogging) MyClassFactory.createMyClass(TestWithoutLoggingImpl.class);
        proxy = (TestLogging) MyClassFactory.createMyClass(TestLoggingImpl.class);
    }

    @Benchmark
    public void baseTest() {
        for (var i = 0; i < COUNT; i++) {
            base.calculation(1);
            base.calculation(1, 2);
            base.calculation(1, 2, 3);
            int[] params = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            base.calculation(params);
        }
    }

/*    @Benchmark
    public void noLoggingTest() {
        for (var i = 0; i < COUNT; i++) {
            noLogging.calculation(1);
            noLogging.calculation(1, 2);
            noLogging.calculation(1, 2, 3);
            int[] params = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            noLogging.calculation(params);
        }
    }*/

    @Benchmark
    public void proxyTest() {
        for (var i = 0; i < COUNT; i++) {
            proxy.calculation(1);
            proxy.calculation(1, 2);
            proxy.calculation(1, 2, 3);
            int[] params = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            proxy.calculation(params);
        }
    }
}
