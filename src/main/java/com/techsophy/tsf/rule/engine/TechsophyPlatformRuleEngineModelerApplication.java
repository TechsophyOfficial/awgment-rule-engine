package com.techsophy.tsf.rule.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.CURRENT_PROJECT_NAME;
import static com.techsophy.tsf.rule.engine.constants.RuleEngineConstants.MULTI_TENANCY_PROJECT;

@RefreshScope
@SpringBootApplication
@ComponentScan({CURRENT_PROJECT_NAME,MULTI_TENANCY_PROJECT})
public class TechsophyPlatformRuleEngineModelerApplication extends SpringBootServletInitializer
{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(TechsophyPlatformRuleEngineModelerApplication.class);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(TechsophyPlatformRuleEngineModelerApplication.class, args);
	}
}
