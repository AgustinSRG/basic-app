/// Master en ingenieria informatica
/// Modelado avanzado de sistemas de informacion
/// Agustin San Roman Guzman

package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Comment Bean (auto-generated)
 */
@Entity
public class Comment extends Model {
	// Attributes
		@Id
		private java.lang.String identifier;

		private java.util.Date date;

		private java.lang.String content;

	// References
		private java.lang.String user;

		private java.lang.String textFile;
 
		/**
		 * Gets the attribute identifier
		 */
		public java.lang.String getIdentifier() {
			return this.identifier;
		}

			/**
			 * Sets the attribute identifier
			 */
			public void setIdentifier(java.lang.String value) {
				this.identifier = value;
			}

			/**
			 * Gets the ID
			 */
			public java.lang.String getID() {
				return this.identifier;
			}

			/**
			 * Sets the ID
			 */
			public void setID(java.lang.String value) {
				this.identifier = value;
			}

			// Constructor
			public Comment(java.lang.String identifier) {
				this.identifier = identifier;
			}

		/**
		 * Gets the attribute date
		 */
		public java.util.Date getDate() {
			return this.date;
		}

			/**
			 * Sets the attribute date
			 */
			public void setDate(java.util.Date value) {
				this.date = value;
			}


		/**
		 * Gets the attribute content
		 */
		public java.lang.String getContent() {
			return this.content;
		}

			/**
			 * Sets the attribute content
			 */
			public void setContent(java.lang.String value) {
				this.content = value;
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
		public java.lang.String getTextFile() {
			return this.textFile;
		}

		/**
		 * Sets the reference textfile
		 */
		public void setTextFile(java.lang.String value) {
			this.textFile = value;
		}
}

