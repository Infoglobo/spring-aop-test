open module globo.editoraglobo.springaoptest {

    requires java.sql;

    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.webmvc;

    requires slf4j.api;
    requires org.aspectj.weaver;

    requires jdk.unsupported;
}