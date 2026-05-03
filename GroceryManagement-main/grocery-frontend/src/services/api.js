import axios from "axios";

const BASE_URL = "http://localhost:8080";

// ================= AXIOS INSTANCE =================
const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json"
  }
});

// ================= REQUEST INTERCEPTOR =================
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    // 🚨 IMPORTANT: Do NOT send token for login/register
    const isAuthRoute =
      config.url.includes("/users/register") ||
      config.url.includes("/users/login");

    if (token && !isAuthRoute) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

// ================= RESPONSE INTERCEPTOR =================
api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("API Error:", error.response?.data || error.message);

    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      window.location.href = "/login";
    }

    if (error.response?.status === 403) {
      console.log("Forbidden - check role or token");
    }

    return Promise.reject(error);
  }
);

export default api;

//
// ================= AUTH =================
//

export const registerUser = (data) =>
  api.post("/users/register", data);

export const loginUser = (data) =>
  api.post("/users/login", data);

//
// ================= ITEMS =================
//

export const getItems = () =>
  api.get("/items");

//
// ================= ADMIN =================
//

export const getDashboard = () =>
  api.get("/admin/dashboard");

export const addItem = (item) =>
  api.post("/admin/products", item);

export const updateItem = (id, item) =>
  api.put(`/admin/products/${id}`, item);

export const deleteItem = (id) =>
  api.delete(`/admin/products/${id}`);

export const getOrders = () =>
  api.get("/admin/orders");

export const updateOrderStatus = (id, status) =>
  api.put(`/admin/orders/${id}/status?status=${status}`);