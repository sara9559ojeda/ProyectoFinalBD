const form = document.getElementById('clienteForm');
const tableBody = document.getElementById('clientesTableBody');
const errorDiv = document.getElementById('error-message');
const successDiv = document.getElementById('success-message');

window.onload = loadClientes;

function loadClientes() {
    fetch('/api/clientes')
        .then(res => res.json())
        .then(data => {
            tableBody.innerHTML = '';
            data.forEach(c => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${c.clientId}</td>
                    <td>${c.fullName}</td>
                    <td>${c.email}</td>
                    <td>${c.phone}</td>
                    <td>${c.identificationNumber}</td>
                    <td>${c.address}</td>
                    <td>
                        <button onclick='editCliente(${JSON.stringify(c)})'>Editar</button>
                        <button onclick='deleteCliente(${c.clientId})'>Eliminar</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        });
}

form.onsubmit = (e) => {
    e.preventDefault();
    clearMessages();

    const clientId = document.getElementById('clientId').value;
    const cliente = {
        fullName: document.getElementById('fullName').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        identificationNumber: document.getElementById('identificationNumber').value,
        address: document.getElementById('address').value
    };

    const method = clientId ? 'PUT' : 'POST';
    const url = clientId ? `/api/clientes/${clientId}` : '/api/clientes';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(cliente)
    })
    .then(res => {
        if (!res.ok) {
            return res.json().then(err => {
                throw new Error(err.message || "Error al guardar el cliente.");
            });
        }
        return res.json();
    })
    .then(data => {
        showSuccess("Cliente guardado correctamente.");
        resetForm();
        loadClientes();
    })
    .catch(err => showError(err.message));
};

function editCliente(cliente) {
    clearMessages();
    document.getElementById('clientId').value = cliente.clientId;
    document.getElementById('fullName').value = cliente.fullName;
    document.getElementById('email').value = cliente.email;
    document.getElementById('phone').value = cliente.phone;
    document.getElementById('identificationNumber').value = cliente.identificationNumber;
    document.getElementById('address').value = cliente.address;
}

function deleteCliente(id) {
    clearMessages();
    if (confirm('¿Seguro que deseas eliminar este cliente?')) {
        fetch(`/api/clientes/${id}`, { method: 'DELETE' })
            .then(res => {
                if (!res.ok) {
                    return res.json().then(err => {
                        throw new Error(err.message || "Error al eliminar el cliente.");
                    });
                }
                return res.text();
            })
            .then(() => {
                showSuccess("Cliente eliminado correctamente.");
                loadClientes();
            })
            .catch(err => showError(err.message));
    }
}

function resetForm() {
    form.reset();
    document.getElementById('clientId').value = '';
}

function showError(msg) {
    errorDiv.textContent = msg;
    errorDiv.style.display = 'block';
    successDiv.style.display = 'none';
}

function showSuccess(msg) {
    successDiv.textContent = msg;
    successDiv.style.display = 'block';
    errorDiv.style.display = 'none';
}

function clearMessages() {
    errorDiv.textContent = '';
    successDiv.textContent = '';
    errorDiv.style.display = 'none';
    successDiv.style.display = 'none';
}
