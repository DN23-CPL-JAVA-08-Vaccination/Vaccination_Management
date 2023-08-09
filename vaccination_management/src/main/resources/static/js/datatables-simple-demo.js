window.addEventListener('DOMContentLoaded', event => {
    // Simple-DataTables
    // https://github.com/fiduswriter/Simple-DataTables/wiki

    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        let options = {
            perPage: 10,

        };
        new simpleDatatables.DataTable(datatablesSimple,options);
    }
});
