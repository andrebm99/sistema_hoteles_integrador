(function(){
  const qs = new URLSearchParams(window.location.search);
  const reservaId = qs.get('id');

  const el = (id) => document.getElementById(id);
  const fmtMoney = (n) => {
    const v = Number(n || 0);
    return 'S/. ' + v.toFixed(2);
  };
  const parseISO = (s) => s ? new Date(s) : null;
  const diffNights = (ini, fin) => {
    if(!ini || !fin) return 1;
    const ms = fin.getTime() - ini.getTime();
    const nights = Math.floor(ms / (1000 * 60 * 60 * 24));
    return Math.max(1, nights);
  };
  const fmtDates = (ini, fin) => {
    if(!ini || !fin) return '-';
    return ini.toLocaleString() + ' - ' + fin.toLocaleString();
  };

  let metodoSeleccionado = 'Tarjeta';

  function setActivePayment(idActive){
    const options = [
      { id: 'tarjeta-option', form: 'tarjeta-form', name: 'Tarjeta' },
      { id: 'paypal-option', form: 'paypal-form', name: 'Paypal' },
      { id: 'transferencia-option', form: 'transferencia-form', name: 'Transferencia' }
    ];
    options.forEach(opt => {
      const optEl = document.getElementById(opt.id);
      const formEl = document.getElementById(opt.form);
      if(!optEl || !formEl) return;
      const active = (opt.id === idActive);
      optEl.classList.toggle('active', active);
      formEl.style.display = active ? '' : 'none';
      if(active) metodoSeleccionado = opt.name;
    });
  }

  async function cargarReserva(){
    if(!reservaId){
      alert('Falta el id de la reserva en la URL (?id=...)');
      return;
    }
    try{
      const resp = await fetch('/api/reserva/' + encodeURIComponent(reservaId));
      if(!resp.ok){
        const txt = await resp.text();
        throw new Error(txt || ('Error ' + resp.status));
      }
      const r = await resp.json();

      const ini = parseISO(r.fechaInicio);
      const fin = parseISO(r.fecha_salida);
      const noches = diffNights(ini, fin);

      // Mostrar datos
      if(el('pp-room')) el('pp-room').textContent = r.habitacion || '-';
      if(el('pp-dates')) el('pp-dates').textContent = fmtDates(ini, fin);
      if(el('pp-guests')) el('pp-guests').textContent = r.ocupantes || '-';
      if(el('pp-days')) el('pp-days').textContent = noches;

      // Precio / Total
      const unit = (r.precio_aplicado != null) ? r.precio_aplicado : 0;
      const total = (r.total_aplicado != null) ? r.total_aplicado : (unit * noches);
      if(el('pp-total')) el('pp-total').textContent = fmtMoney(total);

      // Preseleccionar método existente
      if(r.metodo_pago){
        if(r.metodo_pago === 'Paypal') setActivePayment('paypal-option');
        else if(r.metodo_pago === 'Transferencia') setActivePayment('transferencia-option');
        else setActivePayment('tarjeta-option');
      } else {
        setActivePayment('tarjeta-option');
      }

      // Botones confirmar pago (de cualquier formulario)
      document.querySelectorAll('.btn-confirm-payment').forEach(btn => {
        btn.addEventListener('click', async () => {
          try{
            const pr = await fetch('/api/pago/confirmar?id=' + encodeURIComponent(reservaId), {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify({ metodo_pago: metodoSeleccionado })
            });
            if(pr.ok){
              alert('Pago confirmado. ¡Gracias!');
              window.location.href = '/reservas';
            } else {
              const txt = await pr.text();
              alert('Error al confirmar pago: ' + txt);
            }
          } catch(e){
            console.error(e);
            alert('Error de red al confirmar pago');
          }
        });
      });

    } catch(e){
      console.error(e);
      alert('No se pudo cargar la reserva: ' + e.message);
    }
  }

  document.addEventListener('DOMContentLoaded', () => {
    // Toggle de métodos
    const tar = document.getElementById('tarjeta-option');
    const pay = document.getElementById('paypal-option');
    const tra = document.getElementById('transferencia-option');
    if(tar) tar.addEventListener('click', () => setActivePayment('tarjeta-option'));
    if(pay) pay.addEventListener('click', () => setActivePayment('paypal-option'));
    if(tra) tra.addEventListener('click', () => setActivePayment('transferencia-option'));

    cargarReserva();
  });
})();