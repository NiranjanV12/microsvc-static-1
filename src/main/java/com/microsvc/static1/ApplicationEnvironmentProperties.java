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
        private String at_Url;
        private String at_username;
        private String at_password;
        private String at_grant_type;
        private String at_client_id;
        private String itrLimit;
        private String delay;
        private String trds;


        
        
        public String getTrds() {
			return trds;
		}

		public void setTrds(String trds) {
			this.trds = trds;
		}

		public String getDelay() {
			return delay;
		}

		public void setDelay(String delay) {
			this.delay = delay;
		}

		public String getItrLimit() {
			return itrLimit;
		}

		public void setItrLimit(String itrLimit) {
			this.itrLimit = itrLimit;
		}

		public String getAt_Url() {
			return at_Url;
		}

		public void setAt_Url(String at_Url) {
			this.at_Url = at_Url;
		}

		public String getAt_username() {
			return at_username;
		}

		public void setAt_username(String at_username) {
			this.at_username = at_username;
		}

		public String getAt_password() {
			return at_password;
		}

		public void setAt_password(String at_password) {
			this.at_password = at_password;
		}

		public String getAt_grant_type() {
			return at_grant_type;
		}

		public void setAt_grant_type(String at_grant_type) {
			this.at_grant_type = at_grant_type;
		}

		public String getAt_client_id() {
			return at_client_id;
		}

		public void setAt_client_id(String at_client_id) {
			this.at_client_id = at_client_id;
		}

	
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