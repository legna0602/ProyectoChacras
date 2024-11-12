Vue.createApp({
    data() {
        return {
            cliente: {}
            
        }
    },
    methods: {
        registrar: function (event) {
            event.preventDefault();
            axios.post('/api/clientes', this.cliente)
                .then(response => window.location.href = "/web/cuentas.html")
                .catch(() => {
                    console.log("Falló el registro");
                })
        },
        mostrarCliente: function(){
            console.log(this.cliente);
        }      
    },
    mounted: function () {

    }
}).mount('#app')