package com.xhn.chat.chatwaveserver.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author xhn
 * @date 2025/8/29 9:34
 * @description
 */
public class JavaCodeGenerate {

    public static void main(String[] args) {
        //定义模块名名
        String moduleName = "user";

        try {
            Properties props = new Properties();
            try (InputStream in = JavaCodeGenerate.class.getClassLoader()
                    .getResourceAsStream("application-dev.properties")) {
                props.load(in);
            }
            String url = props.getProperty("spring.datasource.url");
            String username = props.getProperty("spring.datasource.username");
            String password = props.getProperty("spring.datasource.password");

            //"D:\\IDEA_Workspace\\chat-wave-server\\src\\main\\java"  直接读取项目目录

            String projectPath = System.getProperty("user.dir");
            String outputDir = projectPath + "/src/main/java";
            String xmlPath = projectPath + "/src/main/resources/mapper/";
            FastAutoGenerator.create(url, username, password)
                    .globalConfig(builder ->
                            builder.author("xhn")
                                    .disableOpenDir()
                                    .outputDir(outputDir)
                    )
                    .packageConfig(builder ->
                            builder.parent("com.xhn.chat.chatwaveserver")
                                    .moduleName(moduleName)
                                    .entity("model.base")
                                    .pathInfo(Map.of(
                                            OutputFile.xml, xmlPath
                                    ))
                    )
                    .strategyConfig(builder ->
                            builder.likeTable(new LikeTable(moduleName + "_"))
                                    .entityBuilder()
                                    .enableLombok()
                                    .enableChainModel()
                                    .enableFileOverride()
                                    .convertFileName(entityName -> {
                                        String prefix = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
                                        if (entityName.startsWith(prefix)) {
                                            entityName = entityName.substring(prefix.length());
                                        }
                                        return "Base" + entityName + "Entity";
                                    })
                                    .controllerBuilder()
                                    .enableRestStyle()
                                    .convertFileName(controllerName -> {
                                        String prefix = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
                                        if (controllerName.startsWith(prefix)) {
                                            controllerName = controllerName.substring(prefix.length());
                                        }
                                        return controllerName + "Controller";
                                    })
                                    .serviceBuilder()
                                    .convertServiceFileName(serviceName -> {
                                        String prefix = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
                                        if (serviceName.startsWith(prefix)) {
                                            serviceName = serviceName.substring(prefix.length());
                                        }
                                        return "I" + serviceName + "Service";
                                    })
                                    .convertServiceImplFileName(serviceImplName -> {
                                        String prefix = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
                                        if (serviceImplName.startsWith(prefix)) {
                                            serviceImplName = serviceImplName.substring(prefix.length());
                                        }
                                        return serviceImplName + "ServiceImpl";
                                    })
                                    .mapperBuilder()
                                    .convertMapperFileName(mapperName -> {
                                        String prefix = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
                                        if (mapperName.startsWith(prefix)) {
                                            mapperName = mapperName.substring(prefix.length());
                                        }
                                        return mapperName + "Mapper";
                                    })
                                    .convertXmlFileName(xmlName -> {
                                        String prefix = Character.toUpperCase(moduleName.charAt(0)) + moduleName.substring(1);
                                        if (xmlName.startsWith(prefix)) {
                                            xmlName = xmlName.substring(prefix.length());
                                        }
                                        return xmlName + "Mapper";
                                    })
                                    .enableBaseResultMap()
                                    .enableBaseColumnList()
                                    .build())
                    .templateEngine(new FreemarkerTemplateEngine())
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
