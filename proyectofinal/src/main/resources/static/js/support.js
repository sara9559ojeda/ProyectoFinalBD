document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('support-form');
    const supportList = document.getElementById('support-items');

    const apiUrl = '/api/soportes';

    function fetchSupports() {
        fetch(apiUrl)
            .then(response => response.json())
            .then(data => {
                supportList.innerHTML = '';
                data.forEach(support => {
                    const li = document.createElement('li');
                    li.textContent = `Support ID: ${support.idSupport}, Claim ID: ${support.claim.claimId}, Technician ID: ${support.technician?.idTechnician || 'N/A'}, Start: ${support.startDate}, End: ${support.endDate}, Action: ${support.actionTaken}, Result: ${support.result}`;
                    supportList.appendChild(li);
                });
            });
    }

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const support = {
            claim: { claimId: parseInt(document.getElementById('claimId').value) },
            technician: { idTechnician: parseInt(document.getElementById('technicianId').value) },
            startDate: document.getElementById('startDate').value,
            endDate: document.getElementById('endDate').value,
            actionTaken: document.getElementById('actionTaken').value,
            result: document.getElementById('result').value
        };

        fetch(apiUrl, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(support)
        })
        .then(response => response.json())
        .then(data => {
            form.reset();
            fetchSupports();
        });
    });

    fetchSupports();
});
