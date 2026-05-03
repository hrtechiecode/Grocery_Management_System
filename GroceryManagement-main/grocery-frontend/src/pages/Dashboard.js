import { useEffect, useMemo, useState } from "react";
import api from "../services/api";
import "./Dashboard.css";
import Navbar from "../components/Navbar";

function Dashboard() {
  const role = localStorage.getItem("role");

  const [items, setItems] = useState([]);
  const [cart, setCart] = useState([]);
  const [search, setSearch] = useState("");
  const [showCart, setShowCart] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState("All");

  // ================= LOAD ITEMS =================
  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    try {
      const res = await api.get("/items");
      setItems(res.data || []);
    } catch (err) {
      console.error(err);
    }
  };

  // ================= ID SAFE =================
  const getId = (item) => item.id ?? item.itemId;

  // ================= CART =================
  const addToCart = (item) => {
    const id = getId(item);

    setCart((prev) => {
      const exists = prev.find((i) => i.id === id);

      if (exists) {
        return prev.map((i) =>
          i.id === id ? { ...i, qty: i.qty + 1 } : i
        );
      }

      return [...prev, { ...item, id, qty: 1 }];
    });
  };

  const increase = (id) => {
    setCart((prev) =>
      prev.map((i) =>
        i.id === id ? { ...i, qty: i.qty + 1 } : i
      )
    );
  };

  const decrease = (id) => {
    setCart((prev) =>
      prev
        .map((i) =>
          i.id === id ? { ...i, qty: i.qty - 1 } : i
        )
        .filter((i) => i.qty > 0)
    );
  };

  // ================= TOTAL =================
  const total = useMemo(() => {
    return cart.reduce(
      (sum, i) => sum + (Number(i.price) || 0) * (i.qty || 0),
      0
    );
  }, [cart]);

  // ================= FILTER ITEMS =================
  const filteredItems = useMemo(() => {
    let data = items.filter((i) =>
      (i.name || "").toLowerCase().includes(search.toLowerCase())
    );

    if (selectedCategory !== "All") {
      data = data.filter((i) => i.category === selectedCategory);
    }

    return data;
  }, [items, search, selectedCategory]);

  // ================= CATEGORIES =================
  const categories = useMemo(() => {
    const set = new Set(items.map((i) => i.category || "Other"));
    return ["All", ...set];
  }, [items]);

  // ================= ADMIN =================
  if (role === "ROLE_ADMIN") {
    return (
      <div>
        <Navbar />
        <h2 style={{ padding: "20px" }}>Admin Dashboard</h2>
      </div>
    );
  }

  // ================= UI =================
  return (
    <div>
      <Navbar />

      {/* HEADER */}
      <div className="header">
        <h2>🛒 Grocery Store</h2>

        <input
          className="search"
          placeholder="Search products..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />

        <button onClick={() => setShowCart(true)}>
          Cart ({cart.reduce((s, i) => s + i.qty, 0)})
        </button>
      </div>

      {/* CATEGORY */}
      <div className="category-bar">
        {categories.map((cat) => (
          <button
            key={cat}
            className={selectedCategory === cat ? "active" : ""}
            onClick={() => setSelectedCategory(cat)}
          >
            {cat}
          </button>
        ))}
      </div>

      {/* PRODUCTS */}
      <div className="products">
        <div className="grid">
          {filteredItems.map((item) => (
            <div className="card" key={getId(item)}>
              <img
                src={item.image_url || item.imageUrl}
                alt={item.name}
              />

              <h4>{item.name}</h4>
              <p>₹ {item.price}</p>

              <button onClick={() => addToCart(item)}>
                Add to Cart
              </button>
            </div>
          ))}
        </div>
      </div>

      {/* CART */}
      {showCart && (
        <div className="cart-overlay" onClick={() => setShowCart(false)}>
          <div className="cart" onClick={(e) => e.stopPropagation()}>

            <h3>🛒 Cart</h3>

            {cart.length === 0 ? (
              <p style={{ padding: "10px" }}>Cart is empty</p>
            ) : (
              cart.map((item) => (
                <div className="cart-item" key={item.id}>
                  <span>{item.name}</span>

                  <div>
                    <button onClick={() => decrease(item.id)}>-</button>
                    {item.qty}
                    <button onClick={() => increase(item.id)}>+</button>
                  </div>

                  <span>₹{item.price * item.qty}</span>
                </div>
              ))
            )}

            <div className="cart-total">
              Total: ₹{total}
            </div>

            <div className="cart-checkout">
              <button
                onClick={() => {
                  alert("Order placed 🎉");
                  setCart([]);
                  setShowCart(false);
                }}
              >
                Checkout
              </button>

              <button
                style={{ marginTop: "10px", background: "#333", color: "white" }}
                onClick={() => setShowCart(false)}
              >
                Close
              </button>
            </div>

          </div>
        </div>
      )}
    </div>
  );
}

export default Dashboard;