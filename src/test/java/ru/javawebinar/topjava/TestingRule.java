package ru.javawebinar.topjava;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;

public class TestingRule implements TestRule {

    private final Logger log;

    private final StringBuilder finalString;

    public TestingRule(Logger log, StringBuilder finalString) {
        this.log = log;
        this.finalString = finalString;
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.nanoTime();
                base.evaluate();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                String text = description.getMethodName() + ": " + duration + " ms";
                log.info(text);
                finalString.append("\n"+text);
            }
        };
    }
}