document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('sale-form');
    const saleList = document.getElementById('sale-items');

    const apiUrl = '/api/ventas';

    function fetchSales() {
        fetch(apiUrl)
            .then(response => response.json())
            .then(data => {
                saleList.innerHTML = '';
                data.forEach(sale => {
                    const li = document.createElement('li');
                    li.textContent = `Sale ID: ${sale.saleId}, Client ID: ${sale.client.clientId}, Product ID: ${sale.product.productId}, Date: ${sale.saleDate}`;
                    saleList.appendChild(li);
                });
            });
    }

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const sale = {
            client: { clientId: parseInt(document.getElementById('clientId').value) },
            product: { productId: parseInt(document.getElementById('productId').value) },
            saleDate: document.getElementById('saleDate').value
        };

        fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(sale)
        })
        .then(response => response.json())
        .then(data => {
            form.reset();
            fetchSales();
        });
    });

    fetchSales();
});
