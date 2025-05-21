document.getElementById("btn-clientes-top").addEventListener("click", () => {
  fetch("/api/reclamos/clientes-top")
    .then(response => response.json())
    .then(data => {
      mostrarResultados(data, "Clientes con más reclamos", ["cliente", "totalReclamos"]);
    })
    .catch(error => {
      console.error("Error al obtener clientes top:", error);
    });
});

document.getElementById("btn-productos-top").addEventListener("click", () => {
  fetch("/api/reclamos/productos-top")
    .then(response => response.json())
    .then(data => {
      mostrarResultados(data, "Productos más reclamados", ["nombreProducto", "totalReclamos"]);
    })
    .catch(error => {
      console.error("Error al obtener productos top:", error);
    });
});

document.getElementById("btn-reclamos-rapidos").addEventListener("click", () => {
  fetch("/api/reclamos/resolved-in-less-than-7-days")
    .then(response => response.json())
    .then(data => {
      mostrarResultados(data, "Reclamos resueltos en menos de 7 días", ["claimId", "claimDate", "status"]);
    })
    .catch(error => {
      console.error("Error al obtener reclamos rápidos:", error);
    });
});

function mostrarResultados(data, titulo, campos) {
  const contenedor = document.getElementById("resultado");
  contenedor.innerHTML = `<h2>${titulo}</h2>`;

  if (!data || data.length === 0) {
    contenedor.innerHTML += "<p>No hay resultados.</p>";
    return;
  }

  const table = document.createElement("table");
  const thead = document.createElement("thead");
  const trHead = document.createElement("tr");

  campos.forEach(campo => {
    const th = document.createElement("th");
    th.textContent = campo;
    trHead.appendChild(th);
  });

  thead.appendChild(trHead);
  table.appendChild(thead);

  const tbody = document.createElement("tbody");

  data.forEach(item => {
    const tr = document.createElement("tr");
    campos.forEach(campo => {
      const td = document.createElement("td");
      td.textContent = item[campo] ?? "-";
      tr.appendChild(td);
    });
    tbody.appendChild(tr);
  });

  table.appendChild(tbody);
  contenedor.appendChild(table);
}
