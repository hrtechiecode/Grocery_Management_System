import api from "./axiosInstance";

// Get all items
export const getItems = () =>
  api.get("/items");

// Add item (Admin)
export const addItem = (item) =>
  api.post("/items", item);

// Delete item (Admin)
export const deleteItem = (id) =>
  api.delete(`/items/${id}`);