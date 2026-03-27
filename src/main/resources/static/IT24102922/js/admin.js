document.addEventListener('DOMContentLoaded', () => {
    loadOrders();
    loadPayments();
});

// --- 1. Load Orders (US_4.3) ---
async function loadOrders() {
    try {
        const response = await fetch('http://localhost:8080/api/orders'); // නිවැරදි URL එක
        if (!response.ok) throw new Error("Orders fetch failed");

        const orders = await response.json();
        const tableBody = document.getElementById('orderTableBody');
        if (!tableBody) return;
        tableBody.innerHTML = "";

        orders.forEach(order => {
            const status = order.status || 'Pending';
            // JSON එකේ තියෙන field names වලට අනුව (firstName, lastName)
            const name = `${order.firstName || ''} ${order.lastName || ''}`;

            tableBody.innerHTML += `
                <tr>
                    <td>#${order.id}</td>
                    <td>${name}</td>
                    <td>${order.product}</td>
                    <td><span class="status-badge ${status.toLowerCase()}">${status}</span></td>
                    <td>
                        <button class="btn-status" style="background:#be185d; color:white;" onclick="updateStatus(${order.id})">Update Status</button>
                        <button class="btn-del" style="background:#ef4444; color:white;" onclick="deleteOrder(${order.id})">Delete Order</button>
                    </td>
                </tr>
            `;
        });
    } catch (error) { console.error("Error:", error); }
}

// --- 2. Load Payments (US_4.7) ---
async function loadPayments() {
    try {

        const response = await fetch('http://localhost:8080/api/orders');
        const orders = await response.json();
        const payBody = document.getElementById('paymentTableBody');

        if (!payBody) return;
        payBody.innerHTML = "";

        orders.forEach(order => {

            const pStatus = order.paymentStatus || 'PENDING';

            payBody.innerHTML += `
                <tr>
                    <td>#${order.id}</td> 
                    <td>$${order.amount ? order.amount.toFixed(2) : '0.00'}</td>
                    <td><strong>${order.paymentMethod || 'N/A'}</strong></td>
                    <td><span class="status-badge ${pStatus.toLowerCase()}">${pStatus}</span></td>
                    <td>
                        ${pStatus === 'PENDING' ?
                `<button class="btn-pay" style="background:#059669; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;" onclick="confirmPayment(${order.id})">Confirm Receipt</button>` :
                '<span style="color: #059669; font-weight: bold;">✅ Verified</span>'}
                    </td>
                </tr>
            `;
        });
    } catch (error) {
        console.error("Error loading payments:", error);
    }
}
// --- 3. Status Update (US_4.4) ---
async function updateStatus(id) {
    const s = prompt("New Status (Shipped / Delivered / Pending):");
    if(s) {
        const res = await fetch(`http://localhost:8080/api/orders/${id}/status`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(s)
        });
        if (res.ok) { alert("Status Updated!"); loadOrders(); }
    }
}

// --- 4. Payment Confirm (US_4.7) ---
async function confirmPayment(id) {
    if(confirm("Confirm receipt of payment for Order #" + id + "?")) {
        const res = await fetch(`http://localhost:8080/api/orders/${id}/pay-confirm`, { method: 'PUT' });
        if (res.ok) {
            alert("Payment Verified!");
            loadOrders();
            loadPayments();
        }
    }
}

// --- 5. Delete Order ---
async function deleteOrder(id) {
    if(confirm("Delete this order?")) {
        const res = await fetch(`http://localhost:8080/api/orders/${id}`, { method: 'DELETE' });
        if (res.ok) { loadOrders(); loadPayments(); }
    }
}