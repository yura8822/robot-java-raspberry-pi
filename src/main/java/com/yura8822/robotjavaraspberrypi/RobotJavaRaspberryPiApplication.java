package com.yura8822.robotjavaraspberrypi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class RobotJavaRaspberryPiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RobotJavaRaspberryPiApplication.class, args);
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("api-robotcontrol-context-xml.xml");
		ctx.refresh();


	}
}
