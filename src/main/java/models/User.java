/// Master en ingenieria informatica
/// Modelado avanzado de sistemas de informacion
/// Agustin San Roman Guzman

package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User Bean (auto-generated)
 */
@Entity
public class User extends Model {
	// Attributes
		@Id
		private java.lang.String uuid;

		private java.lang.String name;

		private java.lang.String passwordHash;

		private java.lang.String passwordSalt;

		private java.lang.String passwordAlgo;

		private java.util.Date registerDate;

	// References
 
		/**
		 * Gets the attribute uuid
		 */
		public java.lang.String getUuid() {
			return this.uuid;
		}

			/**
			 * Sets the attribute uuid
			 */
			public void setUuid(java.lang.String value) {
				this.uuid = value;
			}

			/**
			 * Gets the ID
			 */
			public java.lang.String getID() {
				return this.uuid;
			}

			/**
			 * Sets the ID
			 */
			public void setID(java.lang.String value) {
				this.uuid = value;
			}

			// Constructor
			public User(java.lang.String uuid) {
				this.uuid = uuid;
			}

		/**
		 * Gets the attribute name
		 */
		public java.lang.String getName() {
			return this.name;
		}

			/**
			 * Sets the attribute name
			 */
			public void setName(java.lang.String value) {
				this.name = value;
			}


		/**
		 * Gets the attribute passwordHash
		 */
		public java.lang.String getPasswordHash() {
			return this.passwordHash;
		}

			/**
			 * Sets the attribute passwordHash
			 */
			public void setPasswordHash(java.lang.String value) {
				this.passwordHash = value;
			}


		/**
		 * Gets the attribute passwordSalt
		 */
		public java.lang.String getPasswordSalt() {
			return this.passwordSalt;
		}

			/**
			 * Sets the attribute passwordSalt
			 */
			public void setPasswordSalt(java.lang.String value) {
				this.passwordSalt = value;
			}


		/**
		 * Gets the attribute passwordAlgo
		 */
		public java.lang.String getPasswordAlgo() {
			return this.passwordAlgo;
		}

			/**
			 * Sets the attribute passwordAlgo
			 */
			public void setPasswordAlgo(java.lang.String value) {
				this.passwordAlgo = value;
			}


		/**
		 * Gets the attribute registerDate
		 */
		public java.util.Date getRegisterDate() {
			return this.registerDate;
		}

			/**
			 * Sets the attribute registerDate
			 */
			public void setRegisterDate(java.util.Date value) {
				this.registerDate = value;
			}


}

