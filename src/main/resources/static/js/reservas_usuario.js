// ===========================
// MODO OSCURO
// ===========================
(function() {
  const toggle = document.getElementById('dark-mode-toggle');
  if (!toggle) return;

  const icon = toggle.querySelector('i');
  const body = document.body;
  const media = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)');

  // Estado inicial: localStorage o preferencia del sistema
  const saved = localStorage.getItem('darkMode');
  let forced = (saved === 'enabled' || saved === 'disabled');
  let isDark = forced ? (saved === 'enabled') : (media ? media.matches : false);

  applyTheme(isDark);

  toggle.addEventListener('click', function(e) {
    e.preventDefault();
    isDark = !body.classList.contains('dark-mode');
    applyTheme(isDark);
    localStorage.setItem('darkMode', isDark ? 'enabled' : 'disabled');
    forced = true;
  });

  // Si el usuario no forzó nada, sigue cambios del SO
  if (media && typeof media.addEventListener === 'function') {
    media.addEventListener('change', function(ev) {
      if (!forced) applyTheme(ev.matches);
    });
  }

  function applyTheme(dark) {
    body.classList.toggle('dark-mode', dark);
    if (icon) {
      icon.classList.toggle('bi-sun', dark);
      icon.classList.toggle('bi-moon', !dark);
    }
    toggle.setAttribute('aria-pressed', dark ? 'true' : 'false');
  }
})();

// ===========================
// MODAL DE IMÁGENES
// ===========================
const images = document.querySelectorAll('.room-card img');
const modal = document.getElementById('imgModal');
const modalImg = document.getElementById('modalImg');

images.forEach(img => {
  img.addEventListener('click', () => {
    modalImg.src = img.src;
    modal.classList.add('active');
  });
});

// Cerrar modal al hacer clic
modal.addEventListener('click', () => {
  modal.classList.remove('active');
});

// ===========================
// UTILIDADES DE RESERVA
// ===========================
function daysBetween(d1, d2) {
  const date1 = new Date(d1);
  const date2 = new Date(d2);
  const diffTime = date2 - date1;
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  return Math.max(1, diffDays);
}

function selectRoom(room, pricePerNight) {
  const checkin = document.getElementById('checkin').value;
  const checkout = document.getElementById('checkout').value;
  const guestsSel = document.getElementById('guests');
  const guests = guestsSel.options[guestsSel.selectedIndex].text;

  if (!checkin || !checkout) {
    alert('Por favor seleccione las fechas de check-in y check-out.');
    return;
  }

  // Validar que checkout sea después de checkin
  if (new Date(checkout) <= new Date(checkin)) {
    alert('La fecha de salida debe ser posterior a la fecha de entrada.');
    return;
  }

  const nights = daysBetween(checkin, checkout);
  const total = nights * pricePerNight;

  document.getElementById('roomType').innerText = room;
  document.getElementById('dates').innerText = `${checkin} → ${checkout}`;
  document.getElementById('guestInfo').innerText = guests;
  document.getElementById('days').innerText = nights;
  document.getElementById('total').innerText = `s/. ${total}`;
}

// ===========================
// VALIDACIÓN DE FECHAS
// ===========================
// Establecer fecha mínima como hoy
const today = new Date().toISOString().split('T')[0];
const checkinInput = document.getElementById('checkin');
const checkoutInput = document.getElementById('checkout');

if (checkinInput) {
  checkinInput.setAttribute('min', today);
  
  // Cuando se selecciona check-in, actualizar mínimo de check-out
  checkinInput.addEventListener('change', function() {
    const checkinDate = new Date(this.value);
    checkinDate.setDate(checkinDate.getDate() + 1);
    const minCheckout = checkinDate.toISOString().split('T')[0];
    checkoutInput.setAttribute('min', minCheckout);
    
    // Si checkout es antes del nuevo mínimo, limpiarlo
    if (checkoutInput.value && checkoutInput.value < minCheckout) {
      checkoutInput.value = '';
    }
  });
}

if (checkoutInput) {
  checkoutInput.setAttribute('min', today);
}