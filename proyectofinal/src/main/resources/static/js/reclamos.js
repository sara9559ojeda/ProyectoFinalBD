const URL = "/api/reclamos";
const lista = document.getElementById("lista-reclamos");
const form = document.getElementById("form-reclamo");

async function cargarReclamos() {
  const res = await fetch(URL);
  const reclamos = await res.json();
  lista.innerHTML = "";
  reclamos.forEach((r) => {
    const li = document.createElement("li");
    li.textContent = `ID: ${r.claimId} | Venta ID: ${r.sale?.saleId || "?"} | Producto ID: ${r.product?.productId || "?"} | Fecha: ${r.claimDate} | Tipo: ${r.type} | Estado: ${r.status}`;
    const btn = document.createElement("button");
    btn.textContent = "Eliminar";
    btn.onclick = () => eliminarReclamo(r.claimId);
    li.appendChild(btn);
    lista.appendChild(li);
  });
}

async function agregarReclamo(e) {
  e.preventDefault();
  const reclamo = {
    sale: { saleId: parseInt(document.getElementById("ventaId").value) },
    product: { productId: parseInt(document.getElementById("productoId").value) },
    claimDate: document.getElementById("fecha").value,
    type: document.getElementById("tipo").value,
    status: document.getElementById("estado").value,
  };

  await fetch(URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(reclamo),
  });

  form.reset();
  cargarReclamos();
}

async function eliminarReclamo(id) {
  await fetch(`${URL}/${id}`, { method: "DELETE" });
  cargarReclamos();
}

form.addEventListener("submit", agregarReclamo);
cargarReclamos();
