/* ============================================================
   Online Food Ordering System — Client-Side JavaScript
   Toast notifications, quantity controls, and UI interactions
   ============================================================ */

document.addEventListener('DOMContentLoaded', function () {

    // ===== Toast Notification System =====
    initToastSystem();

    // ===== Quantity Controls =====
    initQuantityControls();

    // ===== Search Enhancements =====
    initSearchEnhancements();

    // ===== Card Hover Effects =====
    initCardInteractions();
});

// ============================================================
// Toast Notification System
// ============================================================
function initToastSystem() {
    // Create toast container if not present
    if (!document.querySelector('.toast-container')) {
        const container = document.createElement('div');
        container.className = 'toast-container';
        container.id = 'toastContainer';
        document.body.appendChild(container);
    }
}

function showToast(message, type = 'success') {
    const container = document.getElementById('toastContainer');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;

    const icon = type === 'success' ? '✅' : type === 'error' ? '❌' : 'ℹ️';
    toast.innerHTML = `<span>${icon}</span><span>${message}</span>`;

    container.appendChild(toast);

    // Auto-remove after 3 seconds
    setTimeout(() => {
        if (toast.parentNode) {
            toast.parentNode.removeChild(toast);
        }
    }, 3000);
}

// ============================================================
// Quantity Controls (Cart Page)
// ============================================================
function initQuantityControls() {
    // Decrease quantity buttons
    document.querySelectorAll('.qty-decrease').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const menuId = this.dataset.menuId;
            const qtyDisplay = document.getElementById('qty-' + menuId);
            let currentQty = parseInt(qtyDisplay.textContent);

            if (currentQty > 1) {
                updateCartQuantity(menuId, currentQty - 1);
            }
        });
    });

    // Increase quantity buttons
    document.querySelectorAll('.qty-increase').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const menuId = this.dataset.menuId;
            const qtyDisplay = document.getElementById('qty-' + menuId);
            let currentQty = parseInt(qtyDisplay.textContent);

            updateCartQuantity(menuId, currentQty + 1);
        });
    });
}

function updateCartQuantity(menuId, quantity) {
    // Submit form to update quantity via POST
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = getContextPath() + '/cart';
    form.style.display = 'none';

    const actionInput = document.createElement('input');
    actionInput.name = 'action';
    actionInput.value = 'update';
    form.appendChild(actionInput);

    const menuIdInput = document.createElement('input');
    menuIdInput.name = 'menuId';
    menuIdInput.value = menuId;
    form.appendChild(menuIdInput);

    const qtyInput = document.createElement('input');
    qtyInput.name = 'quantity';
    qtyInput.value = quantity;
    form.appendChild(qtyInput);

    document.body.appendChild(form);
    form.submit();
}

function removeCartItem(menuId) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = getContextPath() + '/cart';
    form.style.display = 'none';

    const actionInput = document.createElement('input');
    actionInput.name = 'action';
    actionInput.value = 'remove';
    form.appendChild(actionInput);

    const menuIdInput = document.createElement('input');
    menuIdInput.name = 'menuId';
    menuIdInput.value = menuId;
    form.appendChild(menuIdInput);

    document.body.appendChild(form);
    form.submit();
}

// ============================================================
// Search Enhancements
// ============================================================
function initSearchEnhancements() {
    const searchInput = document.getElementById('searchInput');
    if (!searchInput) return;

    // Clear button functionality
    searchInput.addEventListener('input', function () {
        const clearBtn = document.getElementById('searchClear');
        if (clearBtn) {
            clearBtn.style.display = this.value ? 'block' : 'none';
        }
    });
}

function clearSearch() {
    const searchInput = document.getElementById('searchInput');
    if (searchInput) {
        searchInput.value = '';
        searchInput.form.submit();
    }
}

// ============================================================
// Card Interactions
// ============================================================
function initCardInteractions() {
    // Restaurant cards — clicking navigates to menu
    document.querySelectorAll('.restaurant-card[data-href]').forEach(card => {
        card.addEventListener('click', function () {
            window.location.href = this.dataset.href;
        });
    });
}

// ============================================================
// Add to Cart with Animation
// ============================================================
function addToCart(menuId, restaurantId) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = getContextPath() + '/cart';
    form.style.display = 'none';

    const actionInput = document.createElement('input');
    actionInput.name = 'action';
    actionInput.value = 'add';
    form.appendChild(actionInput);

    const menuIdInput = document.createElement('input');
    menuIdInput.name = 'menuId';
    menuIdInput.value = menuId;
    form.appendChild(menuIdInput);

    const redirectInput = document.createElement('input');
    redirectInput.name = 'redirectTo';
    redirectInput.value = '/menu?restaurantId=' + restaurantId;
    form.appendChild(redirectInput);

    document.body.appendChild(form);
    form.submit();
}

// ============================================================
// Utility: Get Context Path
// ============================================================
function getContextPath() {
    const path = window.location.pathname;
    const contextPath = path.substring(0, path.indexOf('/', 1));
    return contextPath || '';
}

// ============================================================
// Confirm Restaurant Change (Cart Reset)
// ============================================================
function confirmRestaurantChange(menuId, restaurantId) {
    if (confirm('Adding items from a different restaurant will clear your current cart. Continue?')) {
        addToCart(menuId, restaurantId);
    }
}
