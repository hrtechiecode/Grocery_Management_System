import { Navigate } from "react-router-dom";

function ProtectedRoute({ children, roleRequired }) {
  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

  if (!token) {
    return <Navigate to="/" />;
  }

  if (roleRequired && role !== roleRequired) {
    return <Navigate to="/dashboard" />;
  }

  return children;
}

export default ProtectedRoute;