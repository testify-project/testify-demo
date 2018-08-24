package org.testifyproject.fixture;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.testifyproject.annotation.Application;
import org.testifyproject.annotation.Bundle;
import org.testifyproject.annotation.ConfigHandler;
import org.testifyproject.annotation.Module;
import org.testifyproject.annotation.VirtualResource;
import org.testifyproject.demo.GreetingApplication;

@Bundle
@Application(GreetingApplication.class)
@Module(value = TestModule.class, test = true)
@ConfigHandler(TestConfigHandler.class)
@VirtualResource(value = "elasticsearch", version = "2.4.6")
@Retention(RUNTIME)
@Target(TYPE)
public @interface GreetingsSystemTest {

}
