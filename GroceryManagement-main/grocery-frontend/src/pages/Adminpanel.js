import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosInstance";

function AdminPanel() {
  const [items, setItems] = useState([]);
  const [loading, setLoading] = useState(false);

  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [category, setCategory] = useState("");
  const [imageUrl, setImageUrl] = useState("");

  const navigate = useNavigate();

  // AUTH CHECK
  useEffect(() => {
    const role = localStorage.getItem("role");

    if (role !== "ROLE_ADMIN") {
      alert("Access Denied 🚫");
      navigate("/");
      return;
    }

    fetchItems();
  }, []);

  // FETCH ITEMS
  const fetchItems = async () => {
    try {
      setLoading(true);
      const res = await api.get("/items");
      setItems(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // ADD PRODUCT
  const addProduct = async () => {
    if (!name || !price || !category) {
      alert("Fill required fields ⚠️");
      return;
    }

    try {
      setLoading(true);

      await api.post("/items", {
        name,
        price: Number(price),
        category,
        imageUrl,
      });

      setName("");
      setPrice("");
      setCategory("");
      setImageUrl("");

      fetchItems();
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // DELETE PRODUCT
  const deleteProduct = async (id) => {
    try {
      await api.delete(`/items/${id}`);
      fetchItems();
    } catch (err) {
      console.error(err);
    }
  };

  // GROUP BY CATEGORY
  const groupedItems = items.reduce((acc, item) => {
    const cat = item.category || "Others";
    if (!acc[cat]) acc[cat] = [];
    acc[cat].push(item);
    return acc;
  }, {});

  return (
    <div style={styles.container}>
      <h2>🧑‍💼 Admin Dashboard</h2>

      {/* FORM */}
      <div style={styles.form}>
        <h3>Add Product</h3>

        <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} style={styles.input} />
        <input placeholder="Price" type="number" value={price} onChange={(e) => setPrice(e.target.value)} style={styles.input} />
        <input placeholder="Category" value={category} onChange={(e) => setCategory(e.target.value)} style={styles.input} />
        <input placeholder="Image URL" value={imageUrl} onChange={(e) => setImageUrl(e.target.value)} style={styles.input} />

        <button onClick={addProduct} style={styles.btn} disabled={loading}>
          {loading ? "Adding..." : "➕ Add Product"}
        </button>
      </div>

      {/* PRODUCTS */}
      {Object.keys(groupedItems).map((cat) => (
        <div key={cat}>
          <h3>📂 {cat}</h3>

          <div style={styles.grid}>
            {groupedItems[cat].map((item) => (
              <div key={item.id} style={styles.card}>
                <img
                  src={item.imageUrl || "https://via.placeholder.com/150"}
                  alt={item.name}
                  style={styles.image}
                />

                <h4>{item.name}</h4>
                <p>₹{item.price}</p>

                <button onClick={() => deleteProduct(item.id)} style={styles.deleteBtn}>
                  Delete ❌
                </button>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
}

export default AdminPanel;

// STYLES
const styles = {
  container: {
    padding: "25px",
    fontFamily: "Arial",
    background: "#f4f6f8",
    minHeight: "100vh",
  },
  form: {
    background: "white",
    padding: "20px",
    borderRadius: "12px",
    marginBottom: "25px",
  },
  input: {
    padding: "10px",
    margin: "5px 0",
    borderRadius: "8px",
    border: "1px solid #ddd",
    width: "100%",
  },
  btn: {
    padding: "10px",
    background: "#2563eb",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    width: "100%",
  },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
    gap: "15px",
  },
  card: {
    background: "white",
    padding: "12px",
    borderRadius: "12px",
    textAlign: "center",
  },
  image: {
    width: "100%",
    height: "130px",
    objectFit: "cover",
    borderRadius: "10px",
  },
  deleteBtn: {
    background: "red",
    color: "white",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px",
    cursor: "pointer",
  },
};