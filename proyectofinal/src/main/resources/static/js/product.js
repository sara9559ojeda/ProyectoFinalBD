const apiUrl = '/api/productos';
const productList = document.getElementById('productList');
const productForm = document.getElementById('productForm');

function loadProducts() {
    fetch(apiUrl)
        .then(res => res.json())
        .then(products => {
            productList.innerHTML = '';
            products.forEach(product => {
                const li = document.createElement('li');
                li.innerHTML = `
                    <strong>${product.name}</strong> - ${product.description}<br>
                    Price: $${product.price} | Warranty: ${product.warranty}
                    <button onclick="deleteProduct(${product.productId})">Delete</button>
                    <button onclick="editProduct(${product.productId})">Edit</button>
                `;
                productList.appendChild(li);
            });
        });
}

productForm.addEventListener('submit', function (e) {
    e.preventDefault();
    const data = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        warranty: document.getElementById('warranty').value
    };

    fetch(apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    }).then(() => {
        productForm.reset();
        loadProducts();
    });
});

function deleteProduct(id) {
    fetch(`${apiUrl}/${id}`, { method: 'DELETE' })
        .then(() => loadProducts());
}

function editProduct(id) {
    fetch(`${apiUrl}/${id}`)
        .then(res => res.json())
        .then(product => {
            document.getElementById('name').value = product.name;
            document.getElementById('description').value = product.description;
            document.getElementById('price').value = product.price;
            document.getElementById('warranty').value = product.warranty;

            productForm.onsubmit = function (e) {
                e.preventDefault();
                const updatedProduct = {
                    productId: id,
                    name: document.getElementById('name').value,
                    description: document.getElementById('description').value,
                    price: parseFloat(document.getElementById('price').value),
                    warranty: document.getElementById('warranty').value
                };

                fetch(`${apiUrl}/${id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updatedProduct)
                }).then(() => {
                    productForm.reset();
                    loadProducts();
                    productForm.onsubmit = productFormSubmitDefault;
                });
            };
        });
}

const productFormSubmitDefault = productForm.onsubmit;

window.onload = loadProducts;
