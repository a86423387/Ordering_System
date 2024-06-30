document.addEventListener("DOMContentLoaded", function () {
    const orderSummary = document.getElementById("orderSummary");
    const totalAmount = document.getElementById("totalAmount");
    const placeOrderBtn = document.getElementById("placeOrderBtn");

    let orders = [];

    document.querySelectorAll(".list-group-item div").forEach(item => {
        item.addEventListener("click", function () {
            const dishId = this.getAttribute("data-dish-id");
            const dishName = this.querySelector("span:first-child").innerText;
            const dishPrice = parseFloat(this.querySelector("span:last-child").innerText.replace("$", ""));

            let order = orders.find(o => o.dishId === dishId);
            if (order) {
                order.quantity += 1;
            } else {
                orders.push({ dishId, dishName, dishPrice, quantity: 1 });
            }

            renderOrderSummary();
        });
    });

    function renderOrderSummary() {
        orderSummary.innerHTML = "";

        let total = 0;
        orders.forEach(order => {
            const li = document.createElement("li");
            li.className = "list-group-item d-flex justify-content-between align-items-center";
            li.innerHTML = `
                ${order.dishName} - $${order.dishPrice}
                <span>
                    <button class="btn btn-warning btn-sm" onclick="decreaseQuantity('${order.dishId}')">-</button>
                    ${order.quantity}
                    <button class="btn btn-success btn-sm" onclick="increaseQuantity('${order.dishId}')">+</button>
                    $${order.dishPrice * order.quantity}
                    <button class="btn btn-danger btn-sm" onclick="removeOrder('${order.dishId}')">🗑️</button>
                </span>
            `;
            orderSummary.appendChild(li);
            total += order.dishPrice * order.quantity;
        });

        totalAmount.innerText = `$${total.toFixed(2)}`;
        placeOrderBtn.disabled = orders.length === 0;
    }

    window.decreaseQuantity = function (dishId) {
        let order = orders.find(o => o.dishId === dishId);
        if (order) {
            order.quantity -= 1;
            if (order.quantity === 0) {
                orders = orders.filter(o => o.dishId !== dishId);
            }
            renderOrderSummary();
        }
    };

    window.increaseQuantity = function (dishId) {
        let order = orders.find(o => o.dishId === dishId);
        if (order) {
            order.quantity += 1;
            renderOrderSummary();
        }
    };

    window.removeOrder = function (dishId) {
        orders = orders.filter(o => o.dishId !== dishId);
        renderOrderSummary();
    };

    placeOrderBtn.addEventListener("click", function() {
        if (orders.length > 0) {
            const payMethod = document.getElementById("payMethod").value;
            if (payMethod) {
                // 只包含 dishId 和 quantity
                const orderItems = orders.map(order => ({
                    dishId: order.dishId,
                    quantity: order.quantity
                }));

                fetch("/orders/place", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ orderItems: orderItems, payMethod: payMethod })
                })
                    .then(response => response.json())
                    .then(data => {
                        // 處理訂單提交成功
                        alert("訂單提交成功！");
                        orders = [];
                        renderOrderSummary();
                    })
                    .catch(error => {
                        // 處理訂單提交失敗
                        alert("訂單提交失敗，請重試！");
                        console.error("Error:", error);
                    });
            } else {
                alert("請選擇支付方式！");
            }
        }
    });
});
