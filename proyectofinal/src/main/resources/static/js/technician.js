const URL = "/api/technicians";
const form = document.getElementById("technicianForm");
const tableBody = document.getElementById("technicianTableBody");

window.onload = loadTechnicians;

form.onsubmit = async (e) => {
  e.preventDefault();

  const technician = {
    fullName: document.getElementById("fullName").value,
    specialty: document.getElementById("specialty").value,
    email: document.getElementById("email").value
  };

  const id = document.getElementById("technicianId").value;
  const method = id ? "PUT" : "POST";
  const url = id ? `${URL}/${id}` : URL;

  try {
    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(technician)
    });

    if (!res.ok) {
      const errorText = await res.text();
      throw new Error(errorText || "Error saving technician");
    }

    resetForm();
    loadTechnicians();
  } catch (err) {
    alert(`Error: ${err.message}`);
  }
};

async function loadTechnicians() {
  const res = await fetch(URL);
  const data = await res.json();

  tableBody.innerHTML = "";
  data.forEach(t => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${t.technicianId}</td>
      <td>${t.fullName}</td>
      <td>${t.specialty}</td>
      <td>${t.email}</td>
      <td>
        <button onclick='editTechnician(${JSON.stringify(t)})'>Edit</button>
        <button onclick='deleteTechnician(${t.technicianId})'>Delete</button>
      </td>
    `;
    tableBody.appendChild(row);
  });
}

function editTechnician(t) {
  document.getElementById("technicianId").value = t.technicianId;
  document.getElementById("fullName").value = t.fullName;
  document.getElementById("specialty").value = t.specialty;
  document.getElementById("email").value = t.email;
}

async function deleteTechnician(id) {
  if (confirm("Are you sure you want to delete this technician?")) {
    const res = await fetch(`${URL}/${id}`, { method: "DELETE" });
    if (!res.ok) {
      const errorText = await res.text();
      alert(`Error: ${errorText}`);
      return;
    }
    loadTechnicians();
  }
}

function resetForm() {
  form.reset();
  document.getElementById("technicianId").value = "";
}
