/// Master en ingenieria informatica
/// Modelado avanzado de sistemas de informacion
/// Agustin San Roman Guzman

package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * UserStar Bean (auto-generated)
 */
@Entity
public class UserStar extends Model {
	// Attributes
		private boolean star;

	// References
		private java.lang.String user;

		private java.lang.String textfile;
 
		/**
		 * Gets the attribute star
		 */
		public boolean getStar() {
			return this.star;
		}

			/**
			 * Sets the attribute star
			 */
			public void setStar(boolean value) {
				this.star = value;
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

		/**
		 * Gets the reference textfile
		 */
		public java.lang.String getTextfile() {
			return this.textfile;
		}

		/**
		 * Sets the reference textfile
		 */
		public void setTextfile(java.lang.String value) {
			this.textfile = value;
		}
}

