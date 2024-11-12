Vue.createApp({
    data() {
        return {
            cliente: {},
            errorToats: null,
            errorMsg: null,
            accountToDeleteId: null,
        }
    },
    methods: {
        getData: function () {
            //Obtendrá solo un cliente seleccionado por ahora, en mi caso Rejas tiene id 7
            axios.get("/api/clientes/current")
                .then((response) => {
                    this.cliente = response.data;
                    console.log("aca va el cliente");
                    console.log(this.cliente);
                })
                .catch((error) => {
                    this.errorMsg = "Error getting data";
                    this.errorToats.show();
                })
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        modificar: function (event) {
            event.preventDefault();
            const clienteModificado = {
                nombre : this.cliente.nombre,
                apellido : this.cliente.apellido
            }
            axios.put('/api/clientes', clienteModificado)
                .then(response => console.log("Usuario modificado."))
                .catch(() => {
                    console.log("Falló el registro");
                })
        },
        mostrarCliente: function(){
            console.log(this.cliente);
        }  
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
        this.modal = new bootstrap.Modal(document.getElementById('confirModal'));
        this.getData();
    }
}).mount('#app')