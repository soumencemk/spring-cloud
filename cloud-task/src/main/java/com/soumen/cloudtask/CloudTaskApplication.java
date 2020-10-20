package com.soumen.cloudtask;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.repository.TaskExecution;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTask
public class CloudTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleCommandLineRunner.class, args);
    }

}

@Component
@Log
class SimpleCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Specified Task");
    }
}

@Component
class CustomTaksConfigurer extends DefaultTaskConfigurer {
    public CustomTaksConfigurer(@Qualifier("secondDataSource") DataSource dataSource) {
        super(dataSource);
    }
}

@Configuration
class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .build();
    }

    @Bean
    public DataSource secondDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

}

@Configuration
class TaskConfig {
    @Bean
    public SimpleCommandLineRunner simpleCommandLineRunner() {
        return new SimpleCommandLineRunner();
    }
}

@Configuration
class AppConfigurtion {

    @Bean
    public AppTaskListener appTaskListener() {
        return new AppTaskListener();
    }
}

@Component
@Log
class AppTaskListener {
    @BeforeTask
    public void beforeTaskInvocation(TaskExecution taskExecution) {
        log.info("Before task");
    }

    @AfterTask
    public void afterTaskInvocation(TaskExecution taskExecution) {
        log.info("After task");
    }

    @FailedTask
    public void afterFailedTaskInvocation(TaskExecution taskExecution, Throwable throwable) {
        log.info("Failed task");
    }
}
