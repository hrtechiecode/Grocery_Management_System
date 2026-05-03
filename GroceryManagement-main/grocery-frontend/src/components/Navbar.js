import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div style={{
      display: "flex",
      justifyContent: "space-between",
      padding: "10px",
      background: "#6c63ff",
      color: "white"
    }}>
      <h3>🛒 Grocery Store</h3>

      <div>
        <button onClick={() => navigate("/dashboard")}>Home</button>
        <button onClick={logout}>Logout</button>
      </div>
    </div>
  );
}

export default Navbar;