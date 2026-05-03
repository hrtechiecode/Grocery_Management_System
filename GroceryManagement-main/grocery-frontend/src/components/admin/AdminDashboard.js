import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function AdminDashboard() {
  const navigate = useNavigate();

  useEffect(() => {
    const role = localStorage.getItem("role");

    if (role !== "ROLE_ADMIN") {
      navigate("/dashboard");
    }
  }, [navigate]);

  const role = localStorage.getItem("role");
  if (role !== "ROLE_ADMIN") return null;

  return (
    <div>
      <h1>Admin Dashboard 🧑‍💼</h1>

      <button onClick={() => navigate("/admin/items")}>
        Manage Products
      </button>

      <button onClick={() => navigate("/admin/orders")}>
        Manage Orders
      </button>
    </div>
  );
}

export default AdminDashboard;