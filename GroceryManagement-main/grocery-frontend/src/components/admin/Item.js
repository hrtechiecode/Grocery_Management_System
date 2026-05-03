import { useEffect, useState } from "react";
import { getItems, addItem, deleteItem } from "../../services/api";

function Item() {
  const [items, setItems] = useState([]);
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const [loading, setLoading] = useState(false);

  const loadItems = async () => {
    try {
      const res = await getItems();
      setItems(res.data);
    } catch (err) {
      console.log("Error loading items", err);
    }
  };

  useEffect(() => {
    loadItems();
  }, []);

  const handleAdd = async () => {
    if (!name.trim() || !price || isNaN(price) || Number(price) <= 0) {
      alert("Enter valid name and price ⚠️");
      return;
    }

    try {
      setLoading(true);

      const res = await addItem({
        name,
        price: Number(price),
      });

      setItems((prev) => [...prev, res.data]);

      setName("");
      setPrice("");
    } catch (err) {
      console.log(err);
      alert("Failed to add item ❌");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteItem(id);
      setItems((prev) => prev.filter((item) => item.id !== id));
    } catch (err) {
      console.log(err);
      alert("Delete failed ❌");
    }
  };

  return (
    <div>
      <h2>Items 🛍️</h2>

      <input
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        placeholder="Price"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
      />

      <button onClick={handleAdd} disabled={loading}>
        {loading ? "Adding..." : "Add Item"}
      </button>

      <hr />

      {items.map((item) => (
        <div
          key={item.id}
          style={{ border: "1px solid gray", margin: 10, padding: 10 }}
        >
          <p><b>Name:</b> {item.name}</p>
          <p><b>Price:</b> ₹{item.price}</p>

          <button onClick={() => handleDelete(item.id)}>
            Delete
          </button>
        </div>
      ))}
    </div>
  );
}

export default Item;