/// Master en ingenieria informatica
/// Modelado avanzado de sistemas de informacion
/// Agustin San Roman Guzman

package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Session Bean (auto-generated)
 */
@Entity
public class Session extends Model {
	// Attributes
		@Id
		private java.lang.String token;

		private java.util.Date expires;

	// References
		private java.lang.String user;
 
		/**
		 * Gets the attribute token
		 */
		public java.lang.String getToken() {
			return this.token;
		}

			/**
			 * Sets the attribute token
			 */
			public void setToken(java.lang.String value) {
				this.token = value;
			}

			/**
			 * Gets the ID
			 */
			public java.lang.String getID() {
				return this.token;
			}

			/**
			 * Sets the ID
			 */
			public void setID(java.lang.String value) {
				this.token = value;
			}

			// Constructor
			public Session(java.lang.String token) {
				this.token = token;
			}

		/**
		 * Gets the attribute expires
		 */
		public java.util.Date getExpires() {
			return this.expires;
		}

			/**
			 * Sets the attribute expires
			 */
			public void setExpires(java.util.Date value) {
				this.expires = value;
			}


		/**
		 * Gets the reference user
		 */
		public java.lang.String getUser() {
			return this.user;
		}

		/**
		 * Sets the reference user
		 */
		public void setUser(java.lang.String value) {
			this.user = value;
		}
}

