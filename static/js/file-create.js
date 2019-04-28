/* File creation */

window.vue_loadFileCreationView = function () {
	Vue.component('file-create-section', {
        data: function () {
            return {
            	content: "",
            	title: "",
            	error: "",
            };
        },
        props: ['display'],
        methods: {
            clear: function () {
            	this.title = "";
            	this.content = "";
            },
            post: function () {
            	this.error = "";
            	if (event) {
            		event.preventDefault();
            	}
            	this.$emit("post");
            },
        },
        template: '<div v-bind:class="{\'d-none\': !display}">'
        	+ '<form v-on:submit="post($event)">'
        	+ '<div class="form-group">'
            + '<label>Título</label>'
            + '<input v-model="title" type="text" name="title" class="form-control" placeholder="Título">'
            + '</div>'
            + '<div class="form-group">'
            + '<label>Contenido</label>'
            + '<textarea v-model="content" class="form-control" rows="40"></textarea>'
            + '</div>'
        	+ '<div class="form-group">'
        	+ '<small class="error-label" v-text="error"></small>'
        	+ '</div>'
        	+ '<button type="submit" class="btn btn-primary">Crear Texto</button>'
        	+ '</form>'
        	+ '</div>'
    });	
};

