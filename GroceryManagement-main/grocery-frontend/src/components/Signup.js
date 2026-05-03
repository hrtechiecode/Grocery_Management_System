import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import "./Auth.css";

function Signup() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const signup = async () => {
    if (!name || !email || !password) {
      alert("All fields are required ⚠️");
      return;
    }

    try {
      setLoading(true);

      const res = await api.post("/users/register", {
        name,
        email,
        password,
      });

      console.log("SIGNUP SUCCESS:", res.data);

      alert(res.data.message || "Signup successful 🎉");
      navigate("/");

    } catch (err) {
      console.log("SIGNUP ERROR:", err);
      console.log("ERROR RESPONSE:", err.response?.data);

      alert(
        err.response?.data?.message ||
        err.response?.data ||
        "Signup failed ❌"
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-box">
        <h2>Create Account 📝</h2>

        <input
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={signup} disabled={loading}>
          {loading ? "Creating..." : "Signup"}
        </button>

        <p>
          Already have an account?{" "}
          <span onClick={() => navigate("/")}>Login</span>
        </p>
      </div>
    </div>
  );
}

export default Signup;