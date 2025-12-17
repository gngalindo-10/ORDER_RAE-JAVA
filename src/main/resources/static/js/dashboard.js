document.addEventListener('DOMContentLoaded', () => {
  // Solo para testing: simular cambio de rol (NO usar en producción para roles reales)
    const roleSwitch = document.getElementById('role-switch');
    let currentRole = 'admin'; // Rol predeterminado

    if (roleSwitch) {
        const savedRole = localStorage.getItem('dashboardRole') || 'admin';
        currentRole = savedRole;
        roleSwitch.value = savedRole;
        showRoleContent(savedRole);
        updateHeader(savedRole);
        renderChart(savedRole);

        roleSwitch.addEventListener('change', (e) => {
        const newRole = e.target.value;
        currentRole = newRole;
        localStorage.setItem('dashboardRole', newRole);
        showRoleContent(newRole);
        updateHeader(newRole);
        renderChart(newRole);
        });
    } else {
        // En producción: asume rol real desde backend (aquí solo muestra admin por defecto)
        showRoleContent('admin');
        updateHeader('admin');
        renderChart('admin');
    }

    // Activar ítem del sidebar según la página actual (opcional)
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-item').forEach(link => {
        if (link.getAttribute('href') === currentPath) {
        link.classList.add('active');
        }
    });

    // Renderizado inicial del gráfico
    renderChart(currentRole);
    });

    function showRoleContent(role) {
    document.querySelectorAll('.role-content').forEach(el => {
        el.classList.remove('active');
    });
    const target = document.getElementById(`${role}-content`);
    if (target) target.classList.add('active');
    }

    function updateHeader(role) {
    const title = document.querySelector('.header-dashboard h1');
    if (title) {
        title.textContent = role === 'admin' ? 'Panel de Administración' : 'Panel de Usuario';
    }
    }

    let myChart = null;

    function renderChart(role) {
    const ctx = document.getElementById('myChart').getContext('2d');

    if (myChart) {
        myChart.destroy();
    }

    const data = role === 'admin'
        ? {
            labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May'],
            datasets: [{
            label: 'Ventas Mensuales',
            data: [120, 190, 150, 220, 180],
            borderColor: '#8B5E3C',
            backgroundColor: 'rgba(139, 94, 60, 0.1)',
            borderWidth: 2,
            tension: 0.3,
            fill: true
            }]
        }
        : {
            labels: ['Lun', 'Mar', 'Mié', 'Jue', 'Vie'],
            datasets: [{
            label: 'Actividad Semanal',
            data: [30, 45, 25, 50, 40],
            borderColor: '#7A4B32',
            backgroundColor: 'rgba(122, 75, 50, 0.1)',
            borderWidth: 2,
            tension: 0.3,
            fill: true
            }]
        };

    myChart = new Chart(ctx, {
        type: 'line',
        data: data,
        options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
            labels: {
                color: '#3a3226',
                font: {
                family: "'Figtree', sans-serif",
                size: 12
                }
            }
            }
        },
        scales: {
            x: {
            ticks: { color: '#777' },
            grid: { color: '#eae0d5' }
            },
            y: {
            ticks: { color: '#777' },
            grid: { color: '#eae0d5' }
            }
        }
        }
    });
}