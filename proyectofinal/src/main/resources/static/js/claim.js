 const URL = "/api/reclamos";
        const lista = document.getElementById("lista-reclamos");
        const form = document.getElementById("form-reclamo");

        let modoEdicion = false;
        let idActual = null;

        function showError(msg) {
            const errorDiv = document.getElementById('error-message');
            errorDiv.textContent = msg;
            errorDiv.style.display = 'block';
            document.getElementById('success-message').style.display = 'none';
        }

        function showSuccess(msg) {
            const successDiv = document.getElementById('success-message');
            successDiv.textContent = msg;
            successDiv.style.display = 'block';
            document.getElementById('error-message').style.display = 'none';
        }

        function clearMessages() {
            document.getElementById('error-message').style.display = 'none';
            document.getElementById('success-message').style.display = 'none';
        }

        async function cargarReclamos() {
            const res = await fetch(URL);
            const reclamos = await res.json();
            lista.innerHTML = "";
            reclamos.forEach((r) => {
                const li = document.createElement("li");
                li.textContent = `ID: ${r.claimId} | Sale ID: ${r.sale?.saleId || "?"} | Product ID: ${r.product?.productId || "?"} | Date: ${r.claimDate} | Type: ${r.type} | Status: ${r.status}`;

                const btnEliminar = document.createElement("button");
                btnEliminar.textContent = "Delete";
                btnEliminar.onclick = () => eliminarReclamo(r.claimId);

                const btnEditar = document.createElement("button");
                btnEditar.textContent = "Edit";
                btnEditar.onclick = () => cargarFormularioParaEdicion(r);

                li.appendChild(btnEditar);
                li.appendChild(btnEliminar);
                lista.appendChild(li);
            });
        }

        async function agregarReclamo(e) {
            e.preventDefault();
            clearMessages();

            const reclamo = {
                sale: { saleId: parseInt(document.getElementById("ventaId").value) },
                product: { productId: parseInt(document.getElementById("productoId").value) },
                claimDate: document.getElementById("fecha").value,
                type: document.getElementById("tipo").value,
                status: document.getElementById("estado").value,
            };

            const url = modoEdicion ? `${URL}/${idActual}` : URL;
            const method = modoEdicion ? "PUT" : "POST";

            try {
                const res = await fetch(url, {
                    method: method,
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(reclamo),
                });

                if (!res.ok) {
                    const error = await res.json();
                    throw new Error(error.message || "Error saving claim.");
                }

                showSuccess("Claim saved successfully.");
                form.reset();
                modoEdicion = false;
                idActual = null;
                cargarReclamos();

            } catch (err) {
                showError(err.message);
            }
        }

        function cargarFormularioParaEdicion(reclamo) {
            document.getElementById("ventaId").value = reclamo.sale?.saleId || "";
            document.getElementById("productoId").value = reclamo.product?.productId || "";
            document.getElementById("fecha").value = reclamo.claimDate || "";
            document.getElementById("tipo").value = reclamo.type || "";
            document.getElementById("estado").value = reclamo.status || "";
            modoEdicion = true;
            idActual = reclamo.claimId;
        }

        async function eliminarReclamo(id) {
            clearMessages();
            try {
                const res = await fetch(`${URL}/${id}`, { method: "DELETE" });
                if (!res.ok) {
                    const error = await res.json();
                    throw new Error(error.message || "Error deleting claim.");
                }
                showSuccess("Claim deleted.");
                cargarReclamos();
            } catch (err) {
                showError(err.message);
            }
        }

        form.addEventListener("submit", agregarReclamo);
        cargarReclamos();