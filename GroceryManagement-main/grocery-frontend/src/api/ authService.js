import api from "./axiosInstance";

// Register user
export const registerUser = (user) =>
  api.post("/users/register", user);

// Login user
export const loginUser = (user) =>
  api.post("/users/login", user);