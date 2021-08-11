package com.microsvc.static1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app")
@Component
public class ApplicationEnvironmentProperties {

    private final Env env = new Env();

    public Env getEnv() {
        return env;
    }

    public static class Env {

        private String servUrl1;
        private String color;
        
        public String getServUrl1() {
			return servUrl1;
		}

		public void setServUrl1(String servUrl1) {
			this.servUrl1 = servUrl1;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		

    }
}