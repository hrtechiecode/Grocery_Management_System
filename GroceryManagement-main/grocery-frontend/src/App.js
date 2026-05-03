import { BrowserRouter, Routes, Route } from "react-router-dom";

// AUTH
import Login from "./components/Login";
import Signup from "./components/Signup";

// USER
import Dashboard from "./pages/Dashboard";

// ADMIN
import AdminDashboard from "./components/admin/AdminDashboard";
import Item from "./components/admin/Item";
import Order from "./components/admin/Order";

// PROTECTED ROUTE
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* AUTH */}
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        {/* USER (CORRECT) */}
        <Route
          path="/dashboard"
          element={
            <ProtectedRoute roleRequired="ROLE_USER">
              <Dashboard />
            </ProtectedRoute>
          }
        />

        {/* ADMIN */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute roleRequired="ROLE_ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* ADMIN SUB ROUTES */}
        <Route
          path="/admin/items"
          element={
            <ProtectedRoute roleRequired="ROLE_ADMIN">
              <Item />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/orders"
          element={
            <ProtectedRoute roleRequired="ROLE_ADMIN">
              <Order />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;