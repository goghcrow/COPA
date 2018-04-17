package com.youzan.enable.ddd.test;


import com.youzan.enable.ddd.TestConfig;
import com.youzan.enable.ddd.annotation.Extension;
import com.youzan.enable.ddd.context.Context;
import com.youzan.enable.ddd.extension.ExtensionExecutor;
import com.youzan.enable.ddd.extension.ExtensionPoint;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Convert2MethodRef")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class ExtensionOrderTest {

    interface TestExtPt extends ExtensionPoint {
        String who();
    }

    static class TestExt {
        public String who() {
            return this.getClass().getSimpleName();
        }
    }

    @Extension(order = 10) @Component
    static class Test1Ext extends TestExt implements TestExtPt {
        @Override
        public boolean match() {
            return "A".equals(Context.get("bizCode"));
        }
    }

    @Extension(order = 10) @Component
    static class Test2Ext extends TestExt implements TestExtPt {
        @Override
        public boolean match() {
            return "B".equals(Context.get("bizCode"));
        }
    }

    @Extension(order = 20) @Component
    static class Test3Ext extends TestExt implements TestExtPt {
        @Override
        public boolean match() {
            return "B".equals(Context.get("bizCode")) &&
                    Objects.equals(1, Context.get("tenantId"));
        }
    }

    @Extension(order = 0) @Component
    static class Test4Ext extends TestExt implements TestExtPt {
        @Override
        public boolean match() {
            return true;
        }
    }

    @Resource
    private ExtensionExecutor extensionExecutor;

    @Before
    public void setUp() {
    }

    @After
    public void clearCtx() {
        Context.remove();
    }

//    当前用户身份为 {bizCode = "BIZ_A", tenantId = 1} 会匹配到扩展点1，
//    当前用户身份为 {bizCode = "BIZ_A", tenantId = 2} 会匹配到扩展点1，
//    当前用户身份为 {bizCode = "BIZ_B", tenantId = 1} 会匹配到扩展点3，
//    当前用户身份为 {bizCode = "BIZ_B", tenantId = 2} 会匹配到扩展点2，
//    当前用户身份为 {bizCode = "BIZ_C", tenantId = 1} 会匹配到扩展点4，
//    当前用户身份为 {bizCode = "BIZ_C", tenantId = 2} 会匹配到扩展点4


    @Test
    public void testExtensionOrder1() {
        Context.set("bizCode", "A");
        Context.set("tenantId", 1);

        String who = extensionExecutor.execute(TestExtPt.class, ext -> ext.who());
        assertEquals(Test1Ext.class.getSimpleName(), who);
    }

    @Test
    public void testExtensionOrder2() {
        Context.set("bizCode", "A");
        Context.set("tenantId", 2);

        String who = extensionExecutor.execute(TestExtPt.class, ext -> ext.who());
        assertEquals(Test1Ext.class.getSimpleName(), who);
    }

    @Test
    public void testExtensionOrder3() {
        Context.set("bizCode", "B");
        Context.set("tenantId", 1);

        String who = extensionExecutor.execute(TestExtPt.class, ext -> ext.who());
        assertEquals(Test3Ext.class.getSimpleName(), who);
    }

    @Test
    public void testExtensionOrder4() {
        Context.set("bizCode", "B");
        Context.set("tenantId", 2);

        String who = extensionExecutor.execute(TestExtPt.class, ext -> ext.who());
        assertEquals(Test2Ext.class.getSimpleName(), who);
    }

    @Test
    public void testExtensionOrder5() {
        Context.set("bizCode", "C");
        Context.set("tenantId", 1);

        String who = extensionExecutor.execute(TestExtPt.class, ext -> ext.who());
        assertEquals(Test4Ext.class.getSimpleName(), who);
    }

    @Test
    public void testExtensionOrder6() {
        Context.set("bizCode", "C");
        Context.set("tenantId", 2);

        String who = extensionExecutor.execute(TestExtPt.class, ext -> ext.who());
        assertEquals(Test4Ext.class.getSimpleName(), who);
    }
}
