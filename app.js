const API = 'http://localhost:8080/api';

// ─── Auth Protection ──────────────────────────────────────────
const _role = sessionStorage.getItem('role');
const _user = sessionStorage.getItem('user');
if (!_role || !_user || _role !== 'admin') {
  window.location.href = 'login.html';
}

// ─── Navigation ───────────────────────────────────────────────
function showSection(id) {
  document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
  document.querySelectorAll('.nav-links li').forEach(l => l.classList.remove('active'));
  document.getElementById(id).classList.add('active');
  const items = document.querySelectorAll('.nav-links li');
  const map = ['dashboard','donors','hospitals','donations','requests','stock'];
  items[map.indexOf(id)]?.classList.add('active');
  loadSection(id);
}

function loadSection(id) {
  if (id === 'dashboard') loadDashboard();
  else if (id === 'donors') loadDonors();
  else if (id === 'hospitals') loadHospitals();
  else if (id === 'donations') loadDonations();
  else if (id === 'requests') loadRequests();
  else if (id === 'stock') loadStock();
}

function logout() {
  sessionStorage.clear();
  window.location.href = 'login.html';
}

// ─── Toast ─────────────────────────────────────────────────────
function toast(msg) {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.classList.add('show');
  setTimeout(() => t.classList.remove('show'), 2800);
}

// ─── Modal ─────────────────────────────────────────────────────
function openModal(id) { document.getElementById(id).classList.add('open'); }
function closeModal(id) { document.getElementById(id).classList.remove('open'); }
window.onclick = e => { if (e.target.classList.contains('modal-overlay')) e.target.classList.remove('open'); }

// ─── API helpers ───────────────────────────────────────────────
async function apiFetch(path, options = {}) {
  try {
    const res = await fetch(API + path, {
      headers: { 'Content-Type': 'application/json' },
      ...options
    });
    if (!res.ok) throw new Error(await res.text());
    return res.status === 204 ? null : res.json();
  } catch (e) {
    toast('Error: ' + e.message);
    throw e;
  }
}

// ─── DASHBOARD ─────────────────────────────────────────────────
async function loadDashboard() {
  try {
    const [donors, hospitals, donations, requests, stock] = await Promise.all([
      apiFetch('/donors'),
      apiFetch('/hospitals'),
      apiFetch('/donations'),
      apiFetch('/requests'),
      apiFetch('/stock')
    ]);
    document.getElementById('totalDonors').textContent = donors.length;
    document.getElementById('totalHospitals').textContent = hospitals.length;
    document.getElementById('totalDonations').textContent = donations.length;
    const pending = requests.filter(r => r.status === 'Pending').length;
    document.getElementById('pendingRequests').textContent = pending;
    renderBloodStockGrid(stock);
  } catch(e) {}
}

function renderBloodStockGrid(stock) {
  const groups = ['A+','A-','B+','B-','AB+','AB-','O+','O-'];
  const totals = {};
  groups.forEach(g => totals[g] = 0);
  stock.forEach(s => { if (totals[s.bloodGroup] !== undefined) totals[s.bloodGroup] += s.unitsAvailable; });
  const grid = document.getElementById('bloodStockGrid');
  grid.innerHTML = groups.map(g => `
    <div class="blood-card ${totals[g] < 5 ? 'low' : 'ok'}">
      <div class="blood-type">${g}</div>
      <div class="blood-units">${totals[g]}</div>
      <div class="blood-label">units</div>
    </div>`).join('');
}

// ─── DONORS ────────────────────────────────────────────────────
let allDonors = [];

async function loadDonors() {
  allDonors = await apiFetch('/donors');
  renderDonors(allDonors);
}

function renderDonors(donors) {
  document.getElementById('donorBody').innerHTML = donors.map(d => `
    <tr>
      <td><strong>${d.fullName}</strong></td>
      <td>${d.gender}</td>
      <td>${d.age}</td>
      <td><span class="badge badge-red">${d.bloodGroup}</span></td>
      <td>${d.phone}</td>
      <td>${d.city || '—'}</td>
      <td>${d.availableToDonate ? '<span class="badge badge-green">Yes</span>' : '<span class="badge badge-gray">No</span>'}</td>
      <td>
        <button class="btn-icon" onclick="deleteDonor(${d.donorId})" title="Delete">&#128465;</button>
      </td>
    </tr>`).join('') || '<tr><td colspan="8" style="text-align:center;color:#aaa;padding:2rem">No donors found</td></tr>';
}

function filterDonors() {
  const q = document.getElementById('donorSearch').value.toLowerCase();
  renderDonors(allDonors.filter(d =>
    d.fullName.toLowerCase().includes(q) || d.bloodGroup.toLowerCase().includes(q)
  ));
}

async function saveDonor(e) {
  e.preventDefault();
  const donor = {
    fullName: document.getElementById('dName').value,
    gender: document.getElementById('dGender').value,
    age: +document.getElementById('dAge').value,
    bloodGroup: document.getElementById('dBloodGroup').value,
    phone: document.getElementById('dPhone').value,
    email: document.getElementById('dEmail').value,
    city: document.getElementById('dCity').value,
    address: document.getElementById('dAddress').value,
    availableToDonate: document.getElementById('dAvailable').checked
  };
  await apiFetch('/donors', { method: 'POST', body: JSON.stringify(donor) });
  closeModal('donorModal');
  toast('Donor added successfully!');
  loadDonors();
  e.target.reset();
}

async function deleteDonor(id) {
  if (!confirm('Delete this donor?')) return;
  await apiFetch('/donors/' + id, { method: 'DELETE' });
  toast('Donor deleted.');
  loadDonors();
}

// ─── HOSPITALS ─────────────────────────────────────────────────
async function loadHospitals() {
  const hospitals = await apiFetch('/hospitals');
  document.getElementById('hospitalBody').innerHTML = hospitals.map(h => `
    <tr>
      <td><strong>${h.hospitalName}</strong></td>
      <td>${h.city || '—'}</td>
      <td>${h.phone || '—'}</td>
      <td>${h.email || '—'}</td>
      <td><button class="btn-icon" onclick="deleteHospital(${h.hospitalId})" title="Delete">&#128465;</button></td>
    </tr>`).join('') || '<tr><td colspan="5" style="text-align:center;color:#aaa;padding:2rem">No hospitals found</td></tr>';
}

async function saveHospital(e) {
  e.preventDefault();
  const h = {
    hospitalName: document.getElementById('hName').value,
    city: document.getElementById('hCity').value,
    phone: document.getElementById('hPhone').value,
    email: document.getElementById('hEmail').value,
    address: document.getElementById('hAddress').value
  };
  await apiFetch('/hospitals', { method: 'POST', body: JSON.stringify(h) });
  closeModal('hospitalModal');
  toast('Hospital added!');
  loadHospitals();
  e.target.reset();
}

async function deleteHospital(id) {
  if (!confirm('Delete this hospital?')) return;
  await apiFetch('/hospitals/' + id, { method: 'DELETE' });
  toast('Hospital deleted.');
  loadHospitals();
}

// ─── DONATIONS ─────────────────────────────────────────────────
async function loadDonations() {
  const donations = await apiFetch('/donations');
  document.getElementById('donationBody').innerHTML = donations.map(d => `
    <tr>
      <td>${d.donorName || 'Donor #' + d.donorId}</td>
      <td>${d.hospitalName || 'Hospital #' + d.hospitalId}</td>
      <td>${d.donationDate}</td>
      <td><strong>${d.unitsDonated}</strong> units</td>
    </tr>`).join('') || '<tr><td colspan="4" style="text-align:center;color:#aaa;padding:2rem">No records found</td></tr>';
}

async function saveDonation(e) {
  e.preventDefault();
  const d = {
    donorId: +document.getElementById('dnDonorId').value,
    hospitalId: +document.getElementById('dnHospitalId').value,
    donationDate: document.getElementById('dnDate').value,
    unitsDonated: +document.getElementById('dnUnits').value
  };
  await apiFetch('/donations', { method: 'POST', body: JSON.stringify(d) });
  closeModal('donationModal');
  toast('Donation recorded!');
  loadDonations();
  e.target.reset();
}

// ─── REQUESTS ─────────────────────────────────────────────────
async function loadRequests() {
  const requests = await apiFetch('/requests');
  document.getElementById('requestBody').innerHTML = requests.map(r => `
    <tr>
      <td><strong>${r.patientName}</strong></td>
      <td><span class="badge badge-red">${r.bloodGroup}</span></td>
      <td>${r.unitsRequired}</td>
      <td>${r.requestDate}</td>
      <td>${r.city || '—'}</td>
      <td><span class="status-${(r.status||'Pending').toLowerCase()}">${r.status || 'Pending'}</span></td>
      <td>
        <button class="btn-icon" onclick="approveRequest(${r.requestId})" title="Approve">&#10003;</button>
        <button class="btn-icon" onclick="rejectRequest(${r.requestId})" title="Reject">&#10007;</button>
      </td>
    </tr>`).join('') || '<tr><td colspan="7" style="text-align:center;color:#aaa;padding:2rem">No requests found</td></tr>';
}

async function saveRequest(e) {
  e.preventDefault();
  const r = {
    patientName: document.getElementById('rPatient').value,
    bloodGroup: document.getElementById('rBloodGroup').value,
    unitsRequired: +document.getElementById('rUnits').value,
    requestDate: document.getElementById('rDate').value,
    city: document.getElementById('rCity').value,
    hospitalId: +document.getElementById('rHospitalId').value,
    status: 'Pending'
  };
  await apiFetch('/requests', { method: 'POST', body: JSON.stringify(r) });
  closeModal('requestModal');
  toast('Blood request submitted!');
  loadRequests();
  e.target.reset();
}

async function approveRequest(id) {
  await apiFetch('/requests/' + id + '/status?status=Approved', { method: 'PATCH' });
  toast('Request approved!');
  loadRequests();
}

async function rejectRequest(id) {
  await apiFetch('/requests/' + id + '/status?status=Rejected', { method: 'PATCH' });
  toast('Request rejected.');
  loadRequests();
}

// ─── STOCK ─────────────────────────────────────────────────────
async function loadStock() {
  const stock = await apiFetch('/stock');
  document.getElementById('stockBody').innerHTML = stock.map(s => `
    <tr>
      <td>${s.hospitalName || 'Hospital #' + s.hospitalId}</td>
      <td><span class="badge badge-red">${s.bloodGroup}</span></td>
      <td class="${s.unitsAvailable < 5 ? 'status-rejected' : 'status-approved'}">
        <strong>${s.unitsAvailable}</strong> units
      </td>
      <td>${s.lastUpdated || '—'}</td>
    </tr>`).join('') || '<tr><td colspan="4" style="text-align:center;color:#aaa;padding:2rem">No stock data found</td></tr>';
}

async function saveStock(e) {
  e.preventDefault();
  const s = {
    hospitalId: +document.getElementById('sHospitalId').value,
    bloodGroup: document.getElementById('sBloodGroup').value,
    unitsAvailable: +document.getElementById('sUnits').value,
    lastUpdated: new Date().toISOString().split('T')[0]
  };
  await apiFetch('/stock', { method: 'POST', body: JSON.stringify(s) });
  closeModal('stockModal');
  toast('Stock updated!');
  loadStock();
  e.target.reset();
}

// ─── Init ───────────────────────────────────────────────────────
loadDashboard();
