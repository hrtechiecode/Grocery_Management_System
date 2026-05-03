import api from "./axiosInstance";

// Place order (User)
export const placeOrder = (order) =>
  api.post("/orders", order);

// Get all orders (Admin - optional)
export const getOrders = () =>
  api.get("/orders");