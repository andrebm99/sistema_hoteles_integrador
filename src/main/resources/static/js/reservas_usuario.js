// reservas_usuario.js - Reemplaza el archivo actual
// Carga habitaciones del backend y valida disponibilidad antes de crear reserva

function parseDateTimeInput(v) {
  if (!v) return null;
  if (v.length === 16) return v + ":00";
  return v;
}
function daysBetween(a, b) {
  try {
    const d1 = new Date(a), d2 = new Date(b);
    return Math.max(1, Math.ceil((d2 - d1) / (1000 * 60 * 60 * 24)));
  } catch {
    return 1;
  }
}

function setSummary({ room, inicio, fin, ocupantes, price }) {
  const noches = daysBetween(inicio, fin);
  const total = noches * (price || 0);
  document.getElementById("res_roomType").innerText = room || "-";
  document.getElementById("res_dates").innerText = inicio && fin ? inicio + " a " + fin : "-";
  document.getElementById("res_guests").innerText = ocupantes || "-";
  document.getElementById("res_days").innerText = noches;
  document.getElementById("res_price").innerText = "s/. " + (price || 0);
  document.getElementById("res_total").innerText = "s/. " + total;
}

async function crearReserva(obj) {
  const resp = await fetch("/api/reserva", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(obj),
  });

  if (resp.status === 201) return { ok: true, data: await resp.json() };

  const text = await resp.text();
  return { ok: false, status: resp.status, text };
}

async function checkAvailability(tipoHabitacion, inicioIso, finIso) {
  if (!tipoHabitacion || !inicioIso || !finIso) {
    return { ok: false, error: "Faltan datos para validar disponibilidad." };
  }
  const url = `/api/reserva/disponibilidad?habitacion=${encodeURIComponent(tipoHabitacion)}&fechaInicio=${encodeURIComponent(inicioIso)}&fechaFin=${encodeURIComponent(finIso)}`;
  const resp = await fetch(url);
  if (!resp.ok) {
    return { ok: false, error: "Error al consultar disponibilidad", status: resp.status, text: await resp.text() };
  }
  const json = await resp.json();
  return { ok: true, data: json };
}

async function loadRooms() {
  const container = document.getElementById("roomsList");
  const placeholder = document.getElementById("roomsPlaceholder");
  container.innerHTML = ""; // Clear container

  try {
    const resp = await fetch("/api/habitaciones");
    if (!resp.ok) {
      container.innerHTML = '<div class="text-danger">No se pudieron cargar habitaciones.</div>';
      return [];
    }
    const rooms = await resp.json();
    if (!Array.isArray(rooms) || rooms.length === 0) {
      container.innerHTML = '<div class="text-muted">No se han registrado habitaciones.</div>';
      return [];
    }

    // Build unique types for selects
    const tiposUnicos = Array.from(new Set(rooms.map(r => r.nombre_comercial))).filter(Boolean);

    // Fill the `roomtype` filter and the `habitacion` select
    const roomtypeSel = document.getElementById("roomtype");
    const habitacionSel = document.getElementById("habitacion");
    if (roomtypeSel) {
      roomtypeSel.innerHTML = '<option value="">Tipo de habitación (todas)</option>' + tiposUnicos.map(t => `<option value="${t}">${t}</option>`).join('');
    }
    if (habitacionSel) {
      // keep first default option, fill others
      habitacionSel.innerHTML = '<option value="">--Elija una habitación--</option>' + tiposUnicos.map(t => `<option value="${t}">${t}</option>`).join('');
    }

    // Render cards
    rooms.forEach(r => {
      const id = r.id_habitacion;
      const price = r.precio || 0;
      const html = document.createElement("div");
      html.className = "room-card";
      html.setAttribute("data-id", id);
      html.setAttribute("data-type", r.nombre_comercial || "");
      html.setAttribute("data-price", price);
      html.innerHTML = `
        <img src="/api/habitaciones/${id}/foto" alt="${r.nombre_comercial || ''}" onerror="this.onerror=null;this.src='/img/placeholder-room.png'"/>
        <div class="room-info">
          <h5>${r.nombre_comercial || '-'}</h5>
          <p>${r.descripcion || ''}</p>
          <div class="tags">
            <span class="tag">${r.capacidad_total || '-'}</span>
            <span class="tag">${r.medidas || '-'}</span>
          </div>
          <div class="price-reserve">
            <p>Desde s/. ${price}</p>
            <button class="btn btn-primary reserve-btn" data-id="${id}" data-room="${r.nombre_comercial}" data-price="${price}" type="button">Reservar</button>
          </div>
          <div class="mt-2">
            <span class="status-badge badge ${r.estado_operativo && r.estado_operativo.toLowerCase()==='ocupada' ? 'bg-danger' : 'bg-success'}">${r.estado_operativo || 'Disponible'}</span>
          </div>
        </div>
      `;
      container.appendChild(html);
    });

    // Hook events for newly created buttons and images
    document.querySelectorAll(".reserve-btn").forEach(btn => {
      btn.addEventListener("click", (ev) => {
        const id = btn.getAttribute("data-id");
        const room = btn.getAttribute("data-room");
        const price = Number(btn.getAttribute("data-price") || 0);

        // Fill form fields
        const habitacionField = document.getElementById("habitacion");
        const checkinInput = document.getElementById("checkin");
        const checkoutInput = document.getElementById("checkout");
        const fechaInicio = document.getElementById("fechaInicio");
        const fechaSalida = document.getElementById("fecha_salida");
        const guestsSel = document.getElementById("guests");
        const ocupanteField = document.getElementById("ocupantes");

        if (habitacionField) habitacionField.value = room;
        if (checkinInput && checkinInput.value && fechaInicio) fechaInicio.value = checkinInput.value;
        if (checkoutInput && checkoutInput.value && fechaSalida) fechaSalida.value = checkoutInput.value;
        if (guestsSel && ocupanteField) ocupanteField.value = guestsSel.value;

        setSummary({
          room,
          inicio: parseDateTimeInput(fechaInicio?.value),
          fin: parseDateTimeInput(fechaSalida?.value),
          ocupantes: ocupanteField?.value,
          price
        });

        // Scroll to reservation form
        document.getElementById("userBookingForm").scrollIntoView({ behavior: "smooth", block: "center" });
      });
    });

    // Setup modal images for dynamic images
    const modal = document.getElementById("imgModal");
    const modalImg = document.getElementById("modalImg");
    container.querySelectorAll("img").forEach(img =>{
      img.addEventListener("click", () => {
        modalImg.src = img.src;
        modal.classList.add("active");
      });
    });

    return rooms;
  } catch (err) {
    console.error("Error al cargar habitaciones:", err);
    container.innerHTML = '<div class="text-danger">Error cargando habitaciones.</div>';
    return [];
  }
}

async function updateAvailabilityForRange(inicioIso, finIso){
  if (!inicioIso || !finIso) {
    // We can clear status badges or keep previous states
    return;
  }
  try {
    const url = `/api/habitaciones/estado?fechaInicio=${encodeURIComponent(inicioIso)}&fechaFin=${encodeURIComponent(finIso)}`;
    const resp = await fetch(url);
    if (!resp.ok) return;
    const data = await resp.json();
    // data is array of {id_habitacion, numerohabitacion,..., disponible}
    (data || []).forEach(item => {
      const card = document.querySelector(`.room-card[data-id="${item.id_habitacion}"]`);
      if (!card) return;
      const badge = card.querySelector(".status-badge");
      const btn = card.querySelector(".reserve-btn");
      if (item.disponible) {
        if (badge) { badge.innerText = 'Disponible'; badge.classList.remove('bg-danger'); badge.classList.add('bg-success'); }
        if (btn) { btn.disabled = false; btn.classList.remove('btn-secondary'); btn.classList.add('btn-primary'); }
      } else {
        if (badge) { badge.innerText = 'Ocupada'; badge.classList.remove('bg-success'); badge.classList.add('bg-danger'); }
        if (btn) { btn.disabled = true; btn.classList.remove('btn-primary'); btn.classList.add('btn-secondary'); }
      }
    });
  } catch (err) {
    console.warn("No se actualizo disponibilidad:", err);
  }
}

(function init() {
  const checkin = document.getElementById("checkin");
  const checkout = document.getElementById("checkout");
  const guestsSel = document.getElementById("guests");
  const roomtypeSel = document.getElementById("roomtype");
  const clearBtn = document.getElementById("clearFilters");

  const f = {
    nombres: document.getElementById("nombresapellidos"),
    dni: document.getElementById("dni"),
    edad: document.getElementById("edad"),
    habitacion: document.getElementById("habitacion"),
    ocupantes: document.getElementById("ocupantes"),
    inicio: document.getElementById("fechaInicio"),
    salida: document.getElementById("fecha_salida"),
    metodo: document.getElementById("metodo_pago"),
    reservar: document.getElementById("btnReservar"),
  };

  // Load and render rooms
  loadRooms().then(() => {
    // Make sure filters are applied after rooms are loaded
  });

  // Filters: hide/show cards according to roomtype select
  if (roomtypeSel) {
    roomtypeSel.addEventListener("change", () => {
      const val = roomtypeSel.value;
      document.querySelectorAll(".room-card").forEach((card) => {
        const type = card.getAttribute("data-type") || "";
        card.style.display = !val || val === type ? "" : "none";
      });
    });
  }

  // Clear filters
  if (clearBtn) {
    clearBtn.addEventListener("click", () => {
      if (checkin) checkin.value = "";
      if (checkout) checkout.value = "";
      if (guestsSel) guestsSel.selectedIndex = 0;
      if (roomtypeSel) roomtypeSel.value = "";
      document.querySelectorAll(".room-card").forEach((c) => (c.style.display = ""));
      setSummary({ room: "-", inicio: "", fin: "", ocupantes: "-", price: 0 });
    });
  }

  // Update availability badges when date range changes (use debounce)
  let availabilityTimeout = null;
  function onDatesChange(){
    const inicioIso = parseDateTimeInput(checkin?.value || f.inicio?.value);
    const finIso = parseDateTimeInput(checkout?.value || f.salida?.value);
    if (availabilityTimeout) clearTimeout(availabilityTimeout);
    availabilityTimeout = setTimeout(()=> {
      updateAvailabilityForRange(inicioIso, finIso);
    }, 400);
  }

  if (checkin) checkin.addEventListener('change', onDatesChange);
  if (checkout) checkout.addEventListener('change', onDatesChange);
  if (f.inicio) f.inicio.addEventListener('change', onDatesChange);
  if (f.salida) f.salida.addEventListener('change', onDatesChange);

  // When the "Reservar" button on the form is clicked, validate availability and create the reservation
  if (f.reservar) {
    f.reservar.addEventListener("click", async () => {
      const reservaObj = {
        id_reserva: null,
        nombresapellidos: (f.nombres.value || "").trim(),
        dni: f.dni.value ? Number(f.dni.value) : null,
        edad: f.edad.value ? Number(f.edad.value) : null,
        habitacion: f.habitacion.value,
        ocupantes: f.ocupantes.value,
        fechaInicio: parseDateTimeInput(f.inicio.value),
        fecha_salida: parseDateTimeInput(f.salida.value),
        metodo_pago: f.metodo.value,
      };

      if (!reservaObj.habitacion || !reservaObj.fechaInicio || !reservaObj.fecha_salida) {
        alert("Complete la habitación y las fechas antes de reservar.");
        return;
      }

      // Check availability first
      const check = await checkAvailability(reservaObj.habitacion, reservaObj.fechaInicio, reservaObj.fecha_salida);
      if (!check.ok) {
        alert('Error validando disponibilidad: ' + (check.error || check.text || check.status));
        return;
      }
      if (!check.data.disponible) {
        const msg = check.data.conflictos && check.data.conflictos.length > 0 ? 'No disponible. Conflictos: ' + check.data.conflictos.length : 'No hay habitaciones disponibles para este rango.';
        alert(msg);
        return;
      }

      const roomCard = document.querySelector(`.room-card[data-type="${reservaObj.habitacion}"]`);
      const priceNum = roomCard ? Number(roomCard.getAttribute('data-price') || 0) : 0;
      setSummary({room: reservaObj.habitacion, inicio: reservaObj.fechaInicio, fin: reservaObj.fecha_salida, ocupantes: reservaObj.ocupantes, price: priceNum});

      const r = await crearReserva(reservaObj);
      if (!r.ok) {
        alert("Error creando reserva: " + (r.text || r.status));
        return;
      }
      // Redirigir a la página de éxito con el código de la reserva
      const codigo = r.data.codigo;
      window.location.href = "/reserva_exitosa?codigo=" + encodeURIComponent(codigo);
    });
  }

  // Modal handling for images
  const modal = document.getElementById("imgModal");
  const modalImg = document.getElementById("modalImg");
  if (modal) modal.addEventListener("click", () => modal.classList.remove("active"));
})();