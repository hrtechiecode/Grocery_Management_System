import { useEffect, useState } from "react";
import { getOrders, updateOrderStatus } from "../../services/api";

function Order() {
  const [orders, setOrders] = useState([]);
  const [loadingId, setLoadingId] = useState(null);

  const loadOrders = async () => {
    try {
      const res = await getOrders();
      setOrders(res.data);
    } catch (err) {
      console.log("Error loading orders", err);
    }
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const updateStatus = async (id, status) => {
    try {
      setLoadingId(id);

      await updateOrderStatus(id, status);

      // optimistic update
      setOrders((prev) =>
        prev.map((o) =>
          o.id === id ? { ...o, status } : o
        )
      );
    } catch (err) {
      console.log("Status update failed", err);
      alert("Failed to update order ❌");
    } finally {
      setLoadingId(null);
    }
  };

  return (
    <div>
      <h2>Orders 🧾</h2>

      {orders.length === 0 ? (
        <p>No orders found 📦</p>
      ) : (
        orders.map((order) => {
          const status = order.status || "PENDING";

          return (
            <div
              key={order.id}
              style={{
                border: "1px solid #ddd",
                margin: 10,
                padding: 10,
                borderRadius: "8px",
              }}
            >
              <p><b>Order ID:</b> {order.id}</p>
              <p><b>User:</b> {order.userEmail}</p>
              <p><b>Total:</b> ₹{order.totalPrice}</p>

              <p>
                <b>Status:</b>{" "}
                <span
                  style={{
                    color:
                      status === "DELIVERED"
                        ? "green"
                        : status === "SHIPPED"
                        ? "blue"
                        : "orange",
                  }}
                >
                  {status}
                </span>
              </p>

              <button
                onClick={() => updateStatus(order.id, "SHIPPED")}
                disabled={status === "DELIVERED" || loadingId === order.id}
              >
                Ship
              </button>

              <button
                onClick={() => updateStatus(order.id, "DELIVERED")}
                disabled={status === "DELIVERED" || loadingId === order.id}
                style={{ marginLeft: "10px" }}
              >
                Deliver
              </button>
            </div>
          );
        })
      )}
    </div>
  );
}

export default Order;