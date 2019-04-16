/// Master en ingenieria informatica
/// Modelado avanzado de sistemas de informacion
/// Agustin San Roman Guzman

package models;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * TextFile Bean (auto-generated)
 */
@Entity
public class TextFile extends Model {
	// Attributes
		@Id
		private java.lang.String identifier;

		private java.lang.String title;

		private java.lang.String content;

		private java.util.Date creationDate;

		private java.util.Date lastEditDate;

	// References
		private java.lang.String user;
 
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
			public TextFile(java.lang.String identifier) {
				this.identifier = identifier;
			}

		/**
		 * Gets the attribute title
		 */
		public java.lang.String getTitle() {
			return this.title;
		}

			/**
			 * Sets the attribute title
			 */
			public void setTitle(java.lang.String value) {
				this.title = value;
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
		 * Gets the attribute creationDate
		 */
		public java.util.Date getCreationDate() {
			return this.creationDate;
		}

			/**
			 * Sets the attribute creationDate
			 */
			public void setCreationDate(java.util.Date value) {
				this.creationDate = value;
			}


		/**
		 * Gets the attribute lastEditDate
		 */
		public java.util.Date getLastEditDate() {
			return this.lastEditDate;
		}

			/**
			 * Sets the attribute lastEditDate
			 */
			public void setLastEditDate(java.util.Date value) {
				this.lastEditDate = value;
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

