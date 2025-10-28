document.addEventListener('DOMContentLoaded', function () {
  // --- Payment Method Selection --- //
  const paymentOptions = document.querySelectorAll('.payment-option');
  const tarjetaForm = document.getElementById('tarjeta-form');
  const paypalForm = document.getElementById('paypal-form');
  const transferenciaForm = document.getElementById('transferencia-form');

  paymentOptions.forEach(option => {
    option.addEventListener('click', function () {
      paymentOptions.forEach(opt => opt.classList.remove('active'));
      this.classList.add('active');

      // Ocultar todos
      if (tarjetaForm) tarjetaForm.style.display = 'none';
      if (paypalForm) paypalForm.style.display = 'none';
      if (transferenciaForm) transferenciaForm.style.display = 'none';

      // Mostrar el seleccionado
      if (this.id === 'tarjeta-option' && tarjetaForm) {
        tarjetaForm.style.display = 'block';
      } else if (this.id === 'paypal-option' && paypalForm) {
        paypalForm.style.display = 'block';
      } else if (this.id === 'transferencia-option' && transferenciaForm) {
        transferenciaForm.style.display = 'block';
      }
    });
  });

  // --- Desactivar validación nativa en TODOS los forms --- //
  document.querySelectorAll('form').forEach(f => {
    f.setAttribute('novalidate', 'novalidate');
  });

  // --- Forzar navegación sin validar --- //
  const goTo = (url) => { window.location.href = url; };
  document.querySelectorAll('.btn-confirm-payment').forEach(btn => {
    btn.addEventListener('click', () => goTo('reserva_exitosa')); // cambia la URL si quieres otra
  });

  // --- Dark Mode Toggle (tu código) --- //
  const darkModeToggle = document.getElementById('dark-mode-toggle');
  const body = document.body;

  const applyTheme = () => {
    const savedTheme = localStorage.getItem('theme');
    const icon = darkModeToggle?.querySelector('i');

    if (savedTheme === 'dark') {
      body.classList.add('dark-mode');
      icon?.classList.remove('bi-moon');
      icon?.classList.add('bi-sun');
    } else {
      body.classList.remove('dark-mode');
      icon?.classList.remove('bi-sun');
      icon?.classList.add('bi-moon');
    }
  };

  darkModeToggle?.addEventListener('click', (e) => {
    e.preventDefault();
    body.classList.toggle('dark-mode');
    localStorage.setItem('theme', body.classList.contains('dark-mode') ? 'dark' : 'light');
    applyTheme();
  });

  applyTheme();
});
