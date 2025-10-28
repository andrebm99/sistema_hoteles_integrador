document.addEventListener('DOMContentLoaded', function () {
    const sidebarButtons = document.querySelectorAll('.btn-sidebar');
    const contentSections = document.querySelectorAll('.content-section');
    const internalLinks = document.querySelectorAll('.internal-link');
    const updateInfoBtn = document.querySelector('.btn-update-info');
    const configSelects = document.querySelectorAll('.config-box .form-select');
    const darkModeToggle = document.getElementById('dark-mode-toggle');

    function showSection(targetId) {
        contentSections.forEach(section => {
            section.style.display = 'none';
        });

        const targetSection = document.querySelector(targetId);
        if (targetSection) {
            targetSection.style.display = 'block';
        }

        sidebarButtons.forEach(btn => {
            if (btn.getAttribute('href') === targetId) {
                btn.classList.add('active');
            } else {
                btn.classList.remove('active');
            }
        });
    }

    sidebarButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();
            const targetId = this.getAttribute('href');

            if (targetId && targetId.startsWith('#')) {
                showSection(targetId);
            }
            else {
                 window.location.href = this.getAttribute('href');
            }
        });
    });

    internalLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            const targetId = this.getAttribute('href');
            showSection(targetId);
        });
    });

    // Show the section corresponding to the active button on page load
    const initialActiveButton = document.querySelector('.btn-sidebar.active');
    if (initialActiveButton) {
        const initialTargetId = initialActiveButton.getAttribute('href');
        showSection(initialTargetId);
    } else if (contentSections.length > 0) {
        // Fallback to show the first section if no button is active
        contentSections[0].style.display = 'block';
    }

    // Handle form submission for updating user info
    if (updateInfoBtn) {
        updateInfoBtn.addEventListener('click', function(e) {
            e.preventDefault(); // Prevent actual form submission
            // Here you would typically gather the form data and send it to the server
            alert('Información actualizada correctamente (simulación).');
            console.log('Datos del formulario listos para ser enviados.');
        });
    }

    // Handle changes in configuration dropdowns
    configSelects.forEach(select => {
        select.addEventListener('change', function() {
            const setting = this.closest('.config-item').querySelector('span').textContent;
            const value = this.value;
            console.log(`'${setting}' cambiado a: ${this.options[this.selectedIndex].text}`);
            // Here you would typically send the new setting to the server
        });
    });

    // Dark Mode Toggle
    if (darkModeToggle) {
        darkModeToggle.addEventListener('click', function(e) {
            e.preventDefault();
            document.body.classList.toggle('dark-mode');

            const icon = this.querySelector('i');
            if (document.body.classList.contains('dark-mode')) {
                icon.classList.remove('bi-moon');
                icon.classList.add('bi-sun');
            } else {
                icon.classList.remove('bi-sun');
                icon.classList.add('bi-moon');
            }
        });
    }
});
