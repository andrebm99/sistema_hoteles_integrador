// SIN VALIDACIONES COMPLEJAS: solo envía la reserva y redirige a la página de éxito.

function parseDateTimeInput(v) {
  if (!v) return null;
  if (v.length === 16) return v + ":00";
  return v;
}
function daysBetween(a, b) {
  try {
    const d1 = new Date(a),
      d2 = new Date(b);
    return Math.max(1, Math.ceil((d2 - d1) / (1000 * 60 * 60 * 24)));
  } catch {
    return 1;
  }
}
function setSummary({ room, inicio, fin, ocupantes, price }) {
  const noches = daysBetween(inicio, fin);
  const total = noches * (price || 0);
  document.getElementById("res_roomType").innerText = room || "-";
  document.getElementById("res_dates").innerText =
    inicio && fin ? inicio + " a " + fin : "-";
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
  if (resp.status === 201) {
    return { ok: true, data: await resp.json() };
  }
  return { ok: false, status: resp.status, text: await resp.text() };
}

(function init() {
  const checkin = document.getElementById("checkin");
  const checkout = document.getElementById("checkout");
  const guestsSel = document.getElementById("guests");
  const roomSel = document.getElementById("roomtype");
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

  document.querySelectorAll(".reserve-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      const room = btn.getAttribute("data-room");
      const price = Number(btn.getAttribute("data-price") || 0);
      if (f.habitacion) f.habitacion.value = room;
      if (checkin && f.inicio && checkin.value) f.inicio.value = checkin.value;
      if (checkout && f.salida && checkout.value)
        f.salida.value = checkout.value;
      if (guestsSel && f.ocupantes) f.ocupantes.value = guestsSel.value;
      const inicioIso = parseDateTimeInput(f.inicio.value);
      const salidaIso = parseDateTimeInput(f.salida.value);
      setSummary({
        room,
        inicio: inicioIso,
        fin: salidaIso,
        ocupantes: f.ocupantes.value,
        price,
      });
    });
  });

  if (roomSel) {
    roomSel.addEventListener("change", () => {
      const val = roomSel.value;
      document.querySelectorAll(".room-card").forEach((card) => {
        const type = card.getAttribute("data-type") || "";
        card.style.display = !val || val === type ? "" : "none";
      });
    });
  }
  if (clearBtn) {
    clearBtn.addEventListener("click", () => {
      if (checkin) checkin.value = "";
      if (checkout) checkout.value = "";
      if (guestsSel) guestsSel.selectedIndex = 0;
      if (roomSel) roomSel.value = "";
      document
        .querySelectorAll(".room-card")
        .forEach((c) => (c.style.display = ""));
      setSummary({ room: "-", inicio: "", fin: "", ocupantes: "-", price: 0 });
    });
  }

  if (f.reservar) {
    f.reservar.addEventListener("click", async () => {
      // Solo toma lo que hay y lo envía (sin validaciones extras)
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

      // Actualiza resumen antes de enviar
      const base =
        reservaObj.habitacion === "Matrimonial"
          ? 100
          : reservaObj.habitacion === "Estandar"
          ? 150
          : reservaObj.habitacion === "Doble"
          ? 130
          : 120;
      setSummary({
        room: reservaObj.habitacion,
        inicio: reservaObj.fechaInicio,
        fin: reservaObj.fecha_salida,
        ocupantes: reservaObj.ocupantes,
        price: base,
      });

      const r = await crearReserva(reservaObj);
      if (!r.ok) {
        alert("Error creando reserva: " + (r.text || r.status));
        return;
      }
      // Redirige a página de éxito con el código
      const codigo = r.data.codigo;
      window.location.href =
        "/reserva_exitosa?codigo=" + encodeURIComponent(codigo);
    });
  }

  // Modal imágenes
  const images = document.querySelectorAll(".room-card img");
  const modal = document.getElementById("imgModal");
  const modalImg = document.getElementById("modalImg");
  images.forEach((img) => {
    img.addEventListener("click", () => {
      modalImg.src = img.src;
      modal.classList.add("active");
    });
  });
  if (modal)
    modal.addEventListener("click", () => modal.classList.remove("active"));
})();
