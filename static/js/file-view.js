/* File view */

window.vue_loadFileView = function () {
	Vue.component('file-section', {
        data: function () {
            return {
            	file: "",
            	canEdit: false,
            	title: "",
            	content: "",
            	date: "",
            	saved: true,
            	userId: "",
            	error: "",
            };
        },
        props: ['display'],
        methods: {
        	post: function () {
            	this.error = "";
            	if (event) {
            		event.preventDefault();
            	}
            	this.$emit("post");
            },
            reloadComments: function () {
            	
            },
            change: function () {
            	this.saved = false;
            },
        },
        template: '<div v-bind:class="{\'d-none\': !display}">'
        	+ '<form v-on:submit="post($event)">'
        	+ '<div class="form-group">'
            + '<label>Título</label>'
            + '<input v-on:change="change" v-on:keyup="change" v-on:paste="change" v-bind:readonly="!canEdit" v-model="title" type="text" name="title" class="form-control" placeholder="Título">'
            + '</div>'
            + '<div class="form-group">'
            + '<label>Contenido</label>'
            + '<textarea v-on:change="change" v-on:keyup="change" v-on:paste="change"  v-bind:readonly="!canEdit" v-model="content" class="form-control" rows="40"></textarea>'
            + '</div>'
        	+ '<div class="form-group">'
        	+ '<small class="error-label" v-text="error"></small>'
        	+ '</div>'
        	+ '<button v-bind:disabled="saved" v-if="canEdit" type="submit" class="btn btn-primary">Guardar Cambios</button>'
        	+ '</form>'
        	+ '<hr>'
        	+ '<comments-section ref="commentSection"></comments-section>'
        	+ '</div>'
    });
};

